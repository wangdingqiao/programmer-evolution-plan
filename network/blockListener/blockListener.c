/*
 * A blocked listener
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
#include <stdint.h>
#include <inttypes.h>

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

void report_client_address(struct sockaddr_storage* client_addr)
{
	char s[INET6_ADDRSTRLEN];
	inet_ntop(client_addr->ss_family, get_in_addr((struct sockaddr*)client_addr), s, sizeof s);
	printf("server: got connection from %s at timeStamp=%" PRId64 "\n", s, getTimeStamp());
}

void serve_connection(int sockfd)
{
	while (1)
	{
	    uint8_t buf[1024];
	    printf("Calling recv at timeStamp=%" PRId64 "\n", getTimeStamp());
	    int len = recv(sockfd, buf, sizeof buf, 0);
	    if (len < 0)
	    {
	      perror("recv");
	      exit(EXIT_FAILURE);
	    } else if (len == 0)
	    {
	      printf("Peer disconnected; I'm done.\n");
	      break;
	    }
	    printf("recv returned %d bytes\n", len);
	}

  close(sockfd);
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
	sockfd = listen_for_port(port);
	addr_len = sizeof(their_addr);
	new_fd = accept(sockfd, (struct sockaddr*)&their_addr, &addr_len);
	if(new_fd == -1)
	{
		perror("accept");
		exit(EXIT_FAILURE);
	}
	report_client_address(&their_addr);
	serve_connection(new_fd);
	close(sockfd);
	return 0;
}
