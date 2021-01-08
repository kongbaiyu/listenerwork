#include "UdpSocket.h"

#ifndef WIN32
#include <stdio.h>   
#include <string.h>   
#include <errno.h>   
#include <stdlib.h>   
#include <unistd.h>   
#include <sys/types.h>   
#include <sys/socket.h>   
#include <netinet/in.h>   
#include <arpa/inet.h>   


int sendTo(const char * ip ,int port,string data)
{
	/* socket文件描述符 */
	int sock_fd;

	/* 建立udp socket */
	sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
	if (sock_fd < 0)
	{
		return -1;
	}

	/* 设置address */
	struct sockaddr_in addr_serv;
	int len;
	memset(&addr_serv, 0, sizeof(addr_serv));
	addr_serv.sin_family = AF_INET;
	addr_serv.sin_addr.s_addr = inet_addr(ip);
	addr_serv.sin_port = htons(port);
	len = sizeof(addr_serv);


	int send_num;

	send_num = sendto(sock_fd, data.c_str(), data.length(), 0, (struct sockaddr *)&addr_serv, len);

	if (send_num < 0)
	{
		return -1;
	}


	close(sock_fd);

	return send_num;
}
#else
int sendTo(const char * ip, int port, string data)
{
	return -1;
}
#endif // WIN32


