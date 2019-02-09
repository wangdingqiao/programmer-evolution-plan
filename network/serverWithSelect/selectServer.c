/*
 * server.c
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

#define PORT "9034"  // the port users will be connected to
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


int main()
{
	int sockfd, new_fd;
	struct addrinfo hints, *serverinfo, *p;
	struct sockaddr_storage their_addr; // client's address information
	socklen_t addr_len;
	int yes=1;
	char s[INET6_ADDRSTRLEN];
	int rv;
	int numbytes;
	char buf[256];

	// master to keep all connections and listener socket, read_fds for temp use.
	fd_set master;
	fd_set read_fds;
	int fdmax;

	// get server local address information

	memset(&hints, 0 , sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
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
			perror("server: socket");
			continue;
		}
		if(setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
		{
			perror("setsockopt");
			exit(1);
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
		fprintf(stderr, "server: failed to bind.");
		return 2;
	}
	freeaddrinfo(serverinfo);

	// listen on socket
	if(listen(sockfd, BACKLOG) == -1)
	{
		perror("listen");
		exit(1);
	}

	// add listener to the master set
	FD_SET(sockfd, &master);
	fdmax = sockfd; // keep track of max file descriptor

	while(1)
	{
		read_fds = master; // copy set
		if(select(fdmax + 1, &read_fds, NULL, NULL, NULL) == -1)
		{
			perror("select");
			exit(4);
		}

		// run through the existing connections looking for data to read
		for(int i = 0; i <= fdmax; ++i)
		{
			if(FD_ISSET(i, &read_fds))
			{
				if(i == sockfd)   // handle new connections
				{
					addr_len = sizeof their_addr;
					new_fd = accept(sockfd, (struct sockaddr *)&their_addr, &addr_len);
					if(new_fd == -1)
					{
						perror("accept");
					}
					else
					{
						FD_SET(new_fd, &master);
						if(new_fd > fdmax)
						{
							fdmax = new_fd;
						}
						inet_ntop(their_addr.ss_family, get_in_addr((struct sockaddr*)&their_addr), s, sizeof s);
						printf("server: got connection from %s\n", s);
					}
				}
				else   // handle data from a client
				{
					if((numbytes = recv(i, buf, sizeof buf, 0)) <= 0) // failed to read data
					{
						if(numbytes == 0) // connection closed
						{
							printf("selectserver: socket %d hung up\n", i);
						}
						else
						{
							perror("recv");
						}
						close(i);
						FD_CLR(i, &master);
					}
					else  // get some data and broadcast to others
					{
						for(int j = 0; j <= fdmax; ++j)
						{
							if(j == sockfd || j == i)
							{
								continue;
							}
							if(FD_ISSET(j, &master))
							{
								if(send(j, buf, numbytes, 0) == -1)
								{
									perror("send");
								}
							}
						}
					}
				}
			}
		}
	}
	return 0;
}
