/*
 * A server using select
 * refer from:https://eli.thegreenplace.net/2017/concurrent-servers-part-3-event-driven/
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/time.h>
#include <stdint.h>
#include <inttypes.h>
#include <fcntl.h>
#include <errno.h>
#include <stdbool.h>
#include <assert.h>

#define PORT "9090"  // the port users will be connected to
#define BACKLOG 10  // how many pending connections queue will hold


// get socket address IPV4 or IPV6
void *get_in_addr(struct sockaddr* sa)
{
	if(sa->sa_family == AF_INET)
	{
		return &(((struct sockaddr_in*)sa)->sin_addr);
	}
	else
	{
		return &(((struct sockaddr_in6*)sa)->sin6_addr);
	}
}

int listen_for_port(const char* port)
{
	int sockfd = -1;
	struct addrinfo hints, *serverinfo, *p;
	memset(&hints, 0 , sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_flags = AI_PASSIVE;   // fill with my ip

	// get server local address information
	int rv = -1;
	if((rv = getaddrinfo(NULL, port, &hints, &serverinfo)) != 0)
	{
		fprintf(stderr, "getaddrinfo:%s\n", gai_strerror(rv));
		exit(EXIT_FAILURE);
	}
	// loop through all the ip information and bind to the first one we can
	int yes=1;
	for(p = serverinfo; p != NULL; p = p->ai_next)
	{
		if((sockfd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1)
		{
			perror("server: socket");
			continue;
		}
		if(setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
		{
			perror("setsockopt");
			exit(EXIT_FAILURE);
		}
		if(bind(sockfd, p->ai_addr, p->ai_addrlen) == -1)
		{
			close(sockfd);
			perror("server: bind");
			continue;
		}
		break;
	}
	if(p == NULL)
	{
		perror("server: failed to bind.");
		exit(EXIT_FAILURE);
	}
	freeaddrinfo(serverinfo);

	// listen on socket
	if(listen(sockfd, BACKLOG) == -1)
	{
		perror("listen");
		exit(EXIT_FAILURE);
	}
	return sockfd;
}

uint64_t getTimeStamp()
{
    struct timeval tv;
    gettimeofday(&tv,NULL);
    return tv.tv_sec*(uint64_t)1000000+tv.tv_usec;
}

void report_client_address(const struct sockaddr_storage* client_addr)
{
	char s[INET6_ADDRSTRLEN];
	inet_ntop(client_addr->ss_family, get_in_addr((struct sockaddr*)client_addr), s, sizeof s);
	printf("server: got connection from %s at timeStamp=%" PRId64 "\n", s, getTimeStamp());
}

void set_socket_nonblocking(int sockfd)
{
	int flags = fcntl(sockfd, F_GETFL, 0);
	if(flags == -1)
	{
		perror("fcntl F_GETFL");
		exit(EXIT_FAILURE);
	}
	if(fcntl(sockfd, F_SETFL, flags | O_NONBLOCK) == -1)
	{
		perror("fcntl F_SETFL O_NONBLOCK");
		exit(EXIT_FAILURE);
	}
}

void perror_die(const char* msg)
{
	perror(msg);
	exit(EXIT_FAILURE);
}

// Note: FD_SETSIZE is 1024 on Linux, which is tricky to change. This provides a
// natural limit to the number of simultaneous FDs monitored by select().
#define MAXFDS 1000

typedef enum { INITIAL_ACK, WAIT_FOR_MSG, IN_MSG } ProcessingState;

#define SENDBUF_SIZE 1024

typedef struct {
  ProcessingState state;

  // sendbuf contains data the server has to send back to the client. The
  // on_peer_ready_recv handler populates this buffer, and on_peer_ready_send
  // drains it. sendbuf_end points to the last valid byte in the buffer, and
  // sendptr at the next byte to send.
  uint8_t sendbuf[SENDBUF_SIZE];
  int sendbuf_end;
  int sendptr;
} peer_state_t;

// Each peer is globally identified by the file descriptor (fd) it's connected
// on. As long as the peer is connected, the fd is unique to it. When a peer
// disconnects, a new peer may connect and get the same fd. on_peer_connected
// should initialize the state properly to remove any trace of the old peer on
// the same fd.
peer_state_t global_state[MAXFDS];

// Callbacks (on_XXX functions) return this status to the main loop; the status
// instructs the loop about the next steps for the fd for which the callback was
// invoked.
// want_read=true means we want to keep monitoring this fd for reading.
// want_write=true means we want to keep monitoring this fd for writing.
// When both are false it means the fd is no longer needed and can be closed.
typedef struct {
  bool want_read;
  bool want_write;
} fd_status_t;

// These constants make creating fd_status_t values less verbose.
const fd_status_t fd_status_R = {.want_read = true, .want_write = false};
const fd_status_t fd_status_W = {.want_read = false, .want_write = true};
const fd_status_t fd_status_RW = {.want_read = true, .want_write = true};
const fd_status_t fd_status_NORW = {.want_read = false, .want_write = false};

fd_status_t on_peer_connected(int sockfd, const struct sockaddr_storage* peer_addr,
                              socklen_t peer_addr_len) {
  assert(sockfd < MAXFDS);
  report_client_address(peer_addr);

  // Initialize state to send back a '*' to the peer immediately.
  peer_state_t* peerstate = &global_state[sockfd];
  peerstate->state = INITIAL_ACK;
  peerstate->sendbuf[0] = '*';
  peerstate->sendptr = 0;
  peerstate->sendbuf_end = 1;

  // Signal that this socket is ready for writing now.
  return fd_status_W;
}

fd_status_t on_peer_ready_recv(int sockfd) {
  assert(sockfd < MAXFDS);
  peer_state_t* peerstate = &global_state[sockfd];

  if (peerstate->state == INITIAL_ACK ||
      peerstate->sendptr < peerstate->sendbuf_end) {
    // Until the initial ACK has been sent to the peer, there's nothing we
    // want to receive. Also, wait until all data staged for sending is sent to
    // receive more data.
    return fd_status_W;
  }

  uint8_t buf[1024];
  int nbytes = recv(sockfd, buf, sizeof buf, 0);
  if (nbytes == 0) {
    // The peer disconnected.
    return fd_status_NORW;
  } else if (nbytes < 0) {
    if (errno == EAGAIN || errno == EWOULDBLOCK) {
      // The socket is not *really* ready for recv; wait until it is.
      return fd_status_R;
    } else {
      perror_die("recv");
    }
  }
  bool ready_to_send = false;
  for (int i = 0; i < nbytes; ++i) {
    switch (peerstate->state) {
    case INITIAL_ACK:
      assert(0 && "can't reach here");
      break;
    case WAIT_FOR_MSG:
      if (buf[i] == '^') {
        peerstate->state = IN_MSG;
      }
      break;
    case IN_MSG:
      if (buf[i] == '$') {
        peerstate->state = WAIT_FOR_MSG;
      } else {
        assert(peerstate->sendbuf_end < SENDBUF_SIZE);
        peerstate->sendbuf[peerstate->sendbuf_end++] = buf[i] + 1;
        ready_to_send = true;
      }
      break;
    }
  }
  // Report reading readiness iff there's nothing to send to the peer as a
  // result of the latest recv.
  return (fd_status_t){.want_read = !ready_to_send,
                       .want_write = ready_to_send};
}

fd_status_t on_peer_ready_send(int sockfd) {
  assert(sockfd < MAXFDS);
  peer_state_t* peerstate = &global_state[sockfd];

  if (peerstate->sendptr >= peerstate->sendbuf_end) {
    // Nothing to send.
    return fd_status_RW;
  }
  int sendlen = peerstate->sendbuf_end - peerstate->sendptr;
  int nsent = send(sockfd, peerstate->sendbuf+peerstate->sendptr, sendlen, 0);
  if (nsent == -1) {
    if (errno == EAGAIN || errno == EWOULDBLOCK) {
      return fd_status_W;
    } else {
      perror_die("send");
    }
  }
  if (nsent < sendlen) {
    peerstate->sendptr += nsent;
    return fd_status_W;
  } else {
    // Everything was sent successfully; reset the send queue.
    peerstate->sendptr = 0;
    peerstate->sendbuf_end = 0;

    // Special-case state transition in if we were in INITIAL_ACK until now.
    if (peerstate->state == INITIAL_ACK) {
      peerstate->state = WAIT_FOR_MSG;
    }

    return fd_status_R;
  }
}

int main(int argc, char** argv)
{
	char* port = PORT;
	if(argc >= 2)
	{
		port = argv[1];
	}
	int listener_sockfd = listen_for_port(port);

	// The select() manpage warns that select() can return a read notification
	// for a socket that isn't actually readable. Thus using blocking I/O isn't
	// safe.
	set_socket_nonblocking(listener_sockfd);

	if (listener_sockfd >= FD_SETSIZE)
	{
		printf("listener socket fd (%d) >= FD_SETSIZE (%d)", listener_sockfd,FD_SETSIZE);
		perror("listener_sockfd >= FD_SETSIZE");
		exit(EXIT_FAILURE);
	}

	// The "master" sets are owned by the loop, tracking which FDs we want to
	// monitor for reading and which FDs we want to monitor for writing.
	fd_set readfds_master;
	FD_ZERO(&readfds_master);
	fd_set writefds_master;
	FD_ZERO(&writefds_master);

	// The listenting socket is always monitored for read, to detect when new
	// peer connections are incoming.
	FD_SET(listener_sockfd, &readfds_master);

	// For more efficiency, fdset_max tracks the maximal FD seen so far; this
	// makes it unnecessary for select to iterate all the way to FD_SETSIZE on
	// every call.
	int fdset_max = listener_sockfd;

	while (1) {
	    // select() modifies the fd_sets passed to it, so we have to pass in copies.
	    fd_set readfds = readfds_master;
	    fd_set writefds = writefds_master;

	    int nready = select(fdset_max + 1, &readfds, &writefds, NULL, NULL);
	    if (nready < 0)
	    {
	      perror_die("select");
	    }

	    // nready tells us the total number of ready events; if one socket is both
	    // readable and writable it will be 2. Therefore, it's decremented when
	    // either a readable or a writable socket is encountered.
	    for (int fd = 0; fd <= fdset_max && nready > 0; fd++)
	    {
	      // Check if this fd became readable.
	      if (FD_ISSET(fd, &readfds))
	      {
	        nready--;

	        if (fd == listener_sockfd)
	        {
	          // The listening socket is ready; this means a new peer is connecting.
	          struct sockaddr_storage peer_addr;
	          socklen_t peer_addr_len = sizeof(peer_addr);
	          int newsockfd = accept(listener_sockfd, (struct sockaddr*)&peer_addr,
	                                 &peer_addr_len);
	          if (newsockfd < 0)
	          {
	            if (errno == EAGAIN || errno == EWOULDBLOCK)
	            {
	              // This can happen due to the nonblocking socket mode; in this
	              // case don't do anything, but print a notice (since these events
	              // are extremely rare and interesting to observe...)
	              printf("accept returned EAGAIN or EWOULDBLOCK\n");
	            }
	            else
	            {
	              perror_die("accept");
	            }
	          }
	          else
	          {
	        	set_socket_nonblocking(newsockfd);
	            if (newsockfd > fdset_max)
	            {
	              if (newsockfd >= FD_SETSIZE)
	              {
	                printf("socket fd (%d) >= FD_SETSIZE (%d)", newsockfd, FD_SETSIZE);
	                exit(EXIT_FAILURE);
	              }
	              fdset_max = newsockfd;
	            }

	            fd_status_t status = on_peer_connected(newsockfd, &peer_addr, peer_addr_len);
	            if (status.want_read)
	            {
	              FD_SET(newsockfd, &readfds_master);
	            }
	            else
	            {
	              FD_CLR(newsockfd, &readfds_master);
	            }
	            if (status.want_write)
	            {
	              FD_SET(newsockfd, &writefds_master);
	            }
	            else
	            {
	              FD_CLR(newsockfd, &writefds_master);
	            }
	          }
	        }
	        else
	        {
	          fd_status_t status = on_peer_ready_recv(fd);
	          if (status.want_read)
	          {
	            FD_SET(fd, &readfds_master);
	          } else
	          {
	            FD_CLR(fd, &readfds_master);
	          }
	          if (status.want_write)
	          {
	            FD_SET(fd, &writefds_master);
	          } else
	          {
	            FD_CLR(fd, &writefds_master);
	          }
	          if (!status.want_read && !status.want_write)
	          {
	            printf("socket %d closing\n", fd);
	            close(fd);
	          }
	        }
	      }

	      // Check if this fd became writable.
	      if (FD_ISSET(fd, &writefds))
	      {
	        nready--;
	        fd_status_t status = on_peer_ready_send(fd);
	        if (status.want_read)
	        {
	          FD_SET(fd, &readfds_master);
	        } else
	        {
	          FD_CLR(fd, &readfds_master);
	        }
	        if (status.want_write)
	        {
	          FD_SET(fd, &writefds_master);
	        } else
	        {
	          FD_CLR(fd, &writefds_master);
	        }
	        if (!status.want_read && !status.want_write)
	        {
	          printf("socket %d closing\n", fd);
	          close(fd);
	        }
	      }
	    }
	  }
	close(listener_sockfd);
	return 0;
}
