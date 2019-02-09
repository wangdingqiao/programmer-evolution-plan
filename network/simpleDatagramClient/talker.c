/*
 * talker.c
 * refer from: beej's guide
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

#define PORT "4950"   // the port client will be connecting to

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

int main(int argc, char *argv[])
{
	int sockfd, numbytes;
	struct addrinfo hints, *serverinfo, *p;
	int rv;

	if(argc != 3)
	{
		fprintf(stderr, "usage: client hostname message\n");
		exit(1);
	}

	// get server address
	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_DGRAM;
	if((rv = getaddrinfo(argv[1], PORT, &hints, &serverinfo)) != 0)
	{
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
		return 1;
	}

	// loop through all results and connect to the first one we can
	for(p = serverinfo; p != NULL;p = p->ai_next)
	{
		if((sockfd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1)
		{
			perror("client: socket");
			continue;
		}

//		if(connect(sockfd, p->ai_addr, p->ai_addrlen) == -1)
//		{
//			close(sockfd);
//			perror("client: connect");
//			continue;
//		}
		break;
	}

	if(p == NULL)
	{
		fprintf(stderr, "talker: failed to connect\n");
		return 2;
	}
	// send to with specific address
	if((numbytes = sendto(sockfd, argv[2], strlen(argv[2]), 0, p->ai_addr, p->ai_addrlen)) == -1)
	{
		perror("talker: sendto");
		exit(1);
	}

	freeaddrinfo(serverinfo);
	printf("talker: send %d bytes to '%s'\n", numbytes, argv[1]);
	close(sockfd);

	return 0;
}

