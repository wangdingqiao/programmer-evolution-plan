/*
 * A server using libuv
 * refer from:https://eli.thegreenplace.net/2017/concurrent-servers-part-4-libuv/
 * refer from: https://github.com/libuv/libuv
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
#include <uv.h>


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

void* xmalloc(size_t size) {
  void* ptr = malloc(size);
  if (!ptr) {
    printf("malloc failed");
    exit(EXIT_FAILURE);
  }
  return ptr;
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
  char sendbuf[SENDBUF_SIZE];
  int sendbuf_end;
  uv_tcp_t* client;
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

void on_alloc_buffer(uv_handle_t* handle, size_t suggested_size,
                     uv_buf_t* buf) {
  buf->base = (char*)xmalloc(suggested_size);
  buf->len = suggested_size;
}

void on_client_closed(uv_handle_t* handle) {
  uv_tcp_t* client = (uv_tcp_t*)handle;
  // The client handle owns the peer state storing its address in the data
  // field, so we free it here.
  if (client->data) {
    free(client->data);
  }
  free(client);
}

void on_wrote_buf(uv_write_t* req, int status) {
  if (status) {
    printf("Write error: %s\n", uv_strerror(status));
    exit(EXIT_FAILURE);
  }
  peer_state_t* peerstate = (peer_state_t*)req->data;

  // Kill switch for testing leaks in the server. When a client sends a message
  // ending with WXY (note the shift-by-1 in sendbuf), this signals the server
  // to clean up and exit, by stopping the default event loop. Running the
  // server under valgrind can now track memory leaks, and a run should be
  // clean except a single uv_tcp_t allocated for the client that sent the kill
  // signal (it's still connected when we stop the loop and exit).
  if (peerstate->sendbuf_end >= 3 &&
      peerstate->sendbuf[peerstate->sendbuf_end - 3] == 'X' &&
      peerstate->sendbuf[peerstate->sendbuf_end - 2] == 'Y' &&
      peerstate->sendbuf[peerstate->sendbuf_end - 1] == 'Z') {
    free(peerstate);
    free(req);
    uv_stop(uv_default_loop());
    return;
  }

  // The send buffer is done; move pointer back to 0.
  peerstate->sendbuf_end = 0;
  free(req);
}

void on_peer_read(uv_stream_t* client, ssize_t nread, const uv_buf_t* buf) {
  if (nread < 0) {
    if (nread != UV_EOF) {
      fprintf(stderr, "Read error: %s\n", uv_strerror(nread));
    }
    uv_close((uv_handle_t*)client, on_client_closed);
  } else if (nread == 0) {
    // From the documentation of uv_read_cb: nread might be 0, which does not
    // indicate an error or EOF. This is equivalent to EAGAIN or EWOULDBLOCK
    // under read(2).
  } else {
    // nread > 0
    assert(buf->len >= nread);

    peer_state_t* peerstate = (peer_state_t*)client->data; // get state from client data.
    if (peerstate->state == INITIAL_ACK) {
      // If the initial ACK hasn't been sent for some reason, ignore whatever
      // the client sends in.
      free(buf->base);
      return;
    }

    // Run the protocol state machine.
    for (int i = 0; i < nread; ++i) {
      switch (peerstate->state) {
      case INITIAL_ACK:
        assert(0 && "can't reach here");
        break;
      case WAIT_FOR_MSG:
        if (buf->base[i] == '^') {
          peerstate->state = IN_MSG;
        }
        break;
      case IN_MSG:
        if (buf->base[i] == '$') {
          peerstate->state = WAIT_FOR_MSG;
        } else {
          assert(peerstate->sendbuf_end < SENDBUF_SIZE);
          peerstate->sendbuf[peerstate->sendbuf_end++] = buf->base[i] + 1;
        }
        break;
      }
    }

    if (peerstate->sendbuf_end > 0) {
      // We have data to send. The write buffer will point to the buffer stored
      // in the peer state for this client.
      uv_buf_t writebuf =
          uv_buf_init(peerstate->sendbuf, peerstate->sendbuf_end);
      uv_write_t* writereq = (uv_write_t*)xmalloc(sizeof(*writereq));
      writereq->data = peerstate;
      int rc;
      if ((rc = uv_write(writereq, (uv_stream_t*)client, &writebuf, 1, on_wrote_buf)) < 0) {
        printf("uv_write failed: %s", uv_strerror(rc));
        exit(EXIT_FAILURE);
      }
    }
  }
  free(buf->base);
}

void on_wrote_init_ack(uv_write_t* req, int status) {
  if (status) {
    printf("Write error: %s\n", uv_strerror(status));
    exit(EXIT_FAILURE);
  }
  peer_state_t* peerstate = (peer_state_t*)req->data;
  // Flip the peer state to WAIT_FOR_MSG, and start listening for incoming data
  // from this peer.
  peerstate->state = WAIT_FOR_MSG;
  peerstate->sendbuf_end = 0;

  int rc;
  if ((rc = uv_read_start((uv_stream_t*)peerstate->client, on_alloc_buffer,on_peer_read)) < 0)
  {
    printf("uv_read_start failed: %s", uv_strerror(rc));
    exit(EXIT_FAILURE);
  }

  // Note: the write request doesn't own the peer state, hence we only free the
  // request itself, not the state.
  free(req);
}

// new connection callback
void on_peer_connected(uv_stream_t* server_stream, int status) {
  if (status < 0)
  {
    fprintf(stderr, "Peer connection error: %s\n", uv_strerror(status));
    return;
  }

  // client will represent this peer; it's allocated on the heap and only
  // released when the client disconnects. The client holds a pointer to
  // peer_state_t in its data field; this peer state tracks the protocol state
  // with this client throughout interaction.
  uv_tcp_t* client = (uv_tcp_t*)xmalloc(sizeof(*client));
  int rc;
  if ((rc = uv_tcp_init(uv_default_loop(), client)) < 0) {
    printf("uv_tcp_init failed: %s", uv_strerror(rc));
    exit(EXIT_FAILURE);
  }
  client->data = NULL;

  if (uv_accept(server_stream, (uv_stream_t*)client) == 0) {
    struct sockaddr_storage peername;
    int namelen = sizeof(peername);
    if ((rc = uv_tcp_getpeername(client, (struct sockaddr*)&peername,
                                 &namelen)) < 0) {
      printf("uv_tcp_getpeername failed: %s", uv_strerror(rc));
      exit(EXIT_FAILURE);
    }
    report_client_address(&peername);

    // Initialize the peer state for a new client: we start by sending the peer
    // the initial '*' ack.
    peer_state_t* peerstate = (peer_state_t*)xmalloc(sizeof(*peerstate));
    peerstate->state = INITIAL_ACK;
    peerstate->sendbuf[0] = '*';
    peerstate->sendbuf_end = 1;
    peerstate->client = client;
    client->data = peerstate;

    // Enqueue the write request to send the ack; when it's done,
    // on_wrote_init_ack will be called. The peer state is passed to the write
    // request via the data pointer; the write request does not own this peer
    // state - it's owned by the client handle.
    uv_buf_t writebuf = uv_buf_init(peerstate->sendbuf, peerstate->sendbuf_end);
    uv_write_t* req = (uv_write_t*)xmalloc(sizeof(*req));
    req->data = peerstate;
    if ((rc = uv_write(req, (uv_stream_t*)client, &writebuf, 1, on_wrote_init_ack)) < 0)
    {
      printf("uv_write failed: %s", uv_strerror(rc));
      exit(EXIT_FAILURE);
    }
  } else
  {
    uv_close((uv_handle_t*)client, on_client_closed);
  }
}

int main(int argc, char** argv)
{
	char* port = PORT;
	if(argc >= 2)
	{
		port = argv[1];
	}
	int rc;
	uv_tcp_t server_stream;
	if ((rc = uv_tcp_init(uv_default_loop(), &server_stream)) < 0)
	{
		printf("uv_tcp_init failed: %s", uv_strerror(rc));
		exit(EXIT_FAILURE);
	}

	struct sockaddr_in server_address;
	int portNum = atoi(port);
	if ((rc = uv_ip4_addr("0.0.0.0", portNum, &server_address)) < 0)
	{
		printf("uv_ip4_addr failed: %s", uv_strerror(rc));
		exit(EXIT_FAILURE);
	}

	if ((rc = uv_tcp_bind(&server_stream, (const struct sockaddr*)&server_address,0)) < 0)
	{
		printf("uv_tcp_bind failed: %s", uv_strerror(rc));
		exit(EXIT_FAILURE);
	}

	// Listen on the socket for new peers to connect. When a new peer connects,
	// the on_peer_connected callback will be invoked.
	if ((rc = uv_listen((uv_stream_t*)&server_stream, BACKLOG, on_peer_connected)) < 0)
	{
		printf("uv_listen failed: %s", uv_strerror(rc));
		exit(EXIT_FAILURE);
	}

	// Run the libuv event loop.
	uv_run(uv_default_loop(), UV_RUN_DEFAULT);

	// If uv_run returned, close the default loop before exiting.
	return uv_loop_close(uv_default_loop());
}
