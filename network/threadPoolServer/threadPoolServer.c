/*
 * A thread pooled server
 * refer from:https://eli.thegreenplace.net/2017/concurrent-servers-part-1-introduction/
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
#include <pthread.h>
#include <assert.h>
#include "threadpool.h"

#define PORT "9090"  // the port users will be connected to
#define BACKLOG 10  // how many pending connections queue will hold
#define THREAD 2
#define QUEUE 256

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

void report_client_address(struct sockaddr_storage* client_addr)
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
	char s[INET6_ADDRSTRLEN];
	inet_ntop(client_addr->ss_family, get_in_addr((struct sockaddr*)client_addr), s, sizeof s);
	printf("server: got connection from %s at timeStamp=%ld\n", s, tv.tv_sec);
}

typedef enum { WAIT_FOR_MSG, IN_MSG } ProcessingState;
typedef struct { int sockfd; } thread_config_t;

void serve_connection(int sockfd)
{
  // Clients attempting to connect and send data will succeed even before the
  // connection is accept()-ed by the server. Therefore, to better simulate
  // blocking of other clients while one is being served, do this "ack" from the
  // server which the client expects to see before proceeding.
  if (send(sockfd, "*", 1, 0) < 1)
  {
    perror("send");
    exit(EXIT_FAILURE);
  }

  ProcessingState state = WAIT_FOR_MSG;

  while (1)
  {
    uint8_t buf[1024];
    int len = recv(sockfd, buf, sizeof buf, 0);
    if (len < 0)
    {
      perror("recv");
      exit(EXIT_FAILURE);
    }
    else if (len == 0)
    {
      break;
    }

    for (int i = 0; i < len; ++i)
    {
      switch (state) {
      case WAIT_FOR_MSG:
        if (buf[i] == '^')
        {
          state = IN_MSG;
        }
        break;
      case IN_MSG:
        if (buf[i] == '$')
        {
          state = WAIT_FOR_MSG;
        }
        else
        {
          buf[i] += 1;
          if (send(sockfd, &buf[i], 1, 0) < 1)  // echo back buf content
          {
            perror("send error");
            close(sockfd);
            return;
          }
        }
        break;
      }
    }
  }

  close(sockfd);
}

void server_thread(void *arg)
{
	thread_config_t* config = (thread_config_t*)arg;
	int sockfd = config->sockfd;
	free(config);

	 // This cast will work for Linux, but in general casting pthread_id to an
	 // integral type isn't portable.
	 unsigned long id = (unsigned long)pthread_self();
	 printf("Thread %lu created to handle connection with socket %d\n", id,sockfd);
	 serve_connection(sockfd);
	 printf("Thread %lu done\n", id);
}

int main(int argc, char** argv)
{
	int sockfd, new_fd;
	struct sockaddr_storage their_addr; // client's address information
	socklen_t addr_len;
	char* port = PORT;
	if(argc >= 2)
	{
		port = argv[1];
	}
	int thread_num = THREAD;
	if(argc >= 3)
	{
		port = argv[1];
		thread_num = atoi(argv[2]);
	}
	sockfd = listen_for_port(port);

	// init thread pool
	threadpool_t *pool;
	assert((pool = threadpool_create(thread_num, QUEUE, 0)) != NULL);
	fprintf(stderr, "Pool started with %d threads and queue size of %d\n", thread_num, QUEUE);
	while(1)
	{
		addr_len = sizeof(their_addr);
		new_fd = accept(sockfd, (struct sockaddr*)&their_addr, &addr_len);
		if(new_fd == -1)
		{
			perror("accept");
			continue;
		}
		report_client_address(&their_addr);
		// add task to thread pool
		thread_config_t* config = (thread_config_t*)malloc(sizeof(*config));
		if (!config) {
		  perror("OOM");
		  exit(EXIT_FAILURE);
		}
		config->sockfd = new_fd;
		threadpool_add(pool, server_thread, config, 0);
	}
	assert(threadpool_destroy(pool, 0) == 0);
	return 0;
}
