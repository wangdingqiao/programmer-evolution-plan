/*
 * listener.c
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

#define PORT "4950"  // the port users will be connected to
#define MAXBUFLEN 100

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


int main()
{
	int sockfd;
	struct addrinfo hints, *serverinfo, *p;
	struct sockaddr_storage their_addr; // client's address information
	socklen_t addr_len;
	char s[INET6_ADDRSTRLEN];
	char buf[MAXBUFLEN];
	int numbytes;
	int rv;

	// get server local address information

	memset(&hints, 0 , sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_DGRAM; // change socket type
	hints.ai_flags = AI_PASSIVE;   // fill with my ip

	if((rv = getaddrinfo(NULL, PORT, &hints, &serverinfo)) != 0)
	{
		fprintf(stderr, "getaddrinfo:%s\n", gai_strerror(rv));
		return 1;
	}

	// loop through all the ip information and bind to the first one we can
	for(p = serverinfo; p != NULL; p = p->ai_next)
	{
		if((sockfd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1)
		{
			perror("listener: socket");
			continue;
		}
		if(bind(sockfd, p->ai_addr, p->ai_addrlen) == -1)
		{
			close(sockfd);
			perror("listener: bind");
			continue;
		}
		break;
	}
	if(p == NULL)
	{
		fprintf(stderr, "listener: failed to bind.");
		return 2;
	}
	freeaddrinfo(serverinfo);

	// there is no listen and accept steps.

	printf("listener: waiting for recvfrom...\n");

	addr_len = sizeof(their_addr);
	if((numbytes = recvfrom(sockfd, buf, MAXBUFLEN-1, 0, (struct sockaddr *)&their_addr, &addr_len)) == -1)
	{
		perror("recvfrom");
		exit(1);
	}
	buf[numbytes] = '\0';
	inet_ntop(their_addr.ss_family, get_in_addr((struct sockaddr*)&their_addr), s, sizeof s);
	printf("listener: got packet from %s\n, packet is %d bytes long\n, packet contains=%s\n",
			s, numbytes, buf);
	close(sockfd);

	return 0;
}
