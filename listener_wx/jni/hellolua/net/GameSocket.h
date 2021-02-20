#pragma once

#ifdef WIN32
#include <windows.h>
#include <WinSock.h>
#else
#include <sys/socket.h>
#include <fcntl.h>
#include <errno.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "unistd.h"

#include <netdb.h>
#include "err.h"
#define SOCKET int
#define SOCKET_ERROR -1
#define INVALID_SOCKET -1

#endif
#include "string"
#include "functional"
using namespace std;
#ifndef CHECKF
#define CHECKF(x) \
	do \
	{ \
	if (!(x)) { \
	printf("CHECKF", #x, __FILE__, __LINE__); \
	return 0; \
		} \
	} while (0)
#endif

#define BLOCKSECONDS	30			// INIT��������ʱ��
/*
#define _MAX_MSGSIZE 16 * 1024		// �ݶ�һ����Ϣ���Ϊ16k
#define INBUFSIZE	(128*1024)		//?	����ߴ�������汨�����  �������ݵĻ���
#define OUTBUFSIZE	(16*1024)		//? ����ߴ�������汨������� �������ݵĻ��棬��������8Kʱ��FLUSHֻ��ҪSENDһ��
*/


#define CGameSocket ajyrtnh
#define Create juiyuen
#define SendMsg nutyurt
#define ReceiveMsg dfhytuj
#define Flush ktyvdf
#define Check vswertn
#define Destroy yfghdfg
#define SetOnCloseFunc bnrtert
#define GetSocket nyhrtew
#define recvFromSock vsdfdsf
#define hasError dsfhnbrfd
#define closeSocket ruwerfds


class CGameSocket {
public:
	CGameSocket(void);
	bool	Create(string pszServerIP, int nServerPort, int nBlockSec = BLOCKSECONDS, bool bKeepAlive = false , bool useIpV6 = false);
	bool	SendMsg(string pBuf);
	string	ReceiveMsg();
	bool	Flush(void);
	bool	Check(void);
	void	Destroy(void);
	void	SetOnCloseFunc(function<void()> _onclose);
	SOCKET	GetSocket(void) const { return m_sockClient; }
private:
	bool	recvFromSock(void);		// �������ж�ȡ�����ܶ������
	bool    hasError();			// �Ƿ�������ע�⣬�첽ģʽδ��ɷǴ���
	void    closeSocket();

	SOCKET	m_sockClient;
	function<void()> m__onclose;
	// �������ݻ���
	string  m_bufOutput;
	// ���ջ�����
	string	m_bufInput;
};
