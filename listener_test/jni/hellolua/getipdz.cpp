#include <iostream>
#include <getipdz.h>
#include <winsock.h>

using namespace std;
#pragma comment(lib, "ws2_32.lib")


string getIPAddress(string argc)
{
	WORD version = MAKEWORD(2, 2);
	WSADATA wd = { 0 };
	WSAStartup(version, &wd);

	hostent* he;
	he = gethostbyname("cy.xjt365.cn");
	if (he == nullptr)
	{
		printf("gethostbyname error");
		return 0;
	}
	in_addr addr;
	addr.S_un.S_addr = *(ULONG*)he->h_addr;		//正确的转换方法


	WSACleanup();
	return inet_ntoa(addr);
}