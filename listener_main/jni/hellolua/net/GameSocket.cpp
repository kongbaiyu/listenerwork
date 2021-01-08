

#include "GameSocket.h"

extern void log(string str);

CGameSocket::CGameSocket()
{
}

void CGameSocket::closeSocket()
{
#ifdef WIN32
	closesocket(m_sockClient);
	WSACleanup();
#else
	close(m_sockClient);
#endif
}
#ifdef WIN32
bool getSocketAndConnectByIPV6(string pszServerIP, int nServerPort ,SOCKET &sock)
{
    return false;
#else
bool getSocketAndConnectByIPV6(string pszServerIP, int nServerPort ,int &sock)
{
    struct addrinfo hints, *res, *res0;
    int error, s;
    const char *cause = NULL;
    
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET6;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_DEFAULT;
    error = getaddrinfo(pszServerIP.c_str(), NULL, &hints, &res0);
    if (error) {
        return false;
        /*NOTREACHED*/
    }
    s = -1;
    for (res = res0; res; res = res->ai_next) {
        s = socket(res->ai_family, res->ai_socktype,
                   res->ai_protocol);
        if (s < 0) {
            cause = "socket";
            continue;
        }
        
        ((struct sockaddr_in *)res->ai_addr)->sin_port=htons(nServerPort);
        if (connect(s, res->ai_addr, res->ai_addrlen) < 0) {
            cause = "connect";
            close(s);
            s = -1;
            continue;
        }
        
        break;  /* okay we got one */
    }
    if (s < 0) {
        return false;
        /*NOTREACHED*/
    }
    fcntl(s, F_SETFL, O_NONBLOCK);
    sock=s;
    freeaddrinfo(res0);
    return true;
#endif

    
}
bool CGameSocket::Create(string pszServerIP, int nServerPort, int nBlockSec, bool bKeepAlive /*= FALSE*/,bool useIpV6 /* = FALSE */)
{
	// ºÏ≤È≤Œ ˝
	if (pszServerIP.length() > 15) {
		return false;
	}
    if (!useIpV6)
    {
#ifdef WIN32
        WSADATA wsaData;
        WORD version = MAKEWORD(2, 0);
        int ret = WSAStartup(version, &wsaData);//win sock start up
        if (ret != 0) {
			
            return false;
        }
#endif
        // ¥¥Ω®÷˜Ã◊Ω”◊÷
        m_sockClient = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
        if (m_sockClient == INVALID_SOCKET) {
			log(string("错误代码:") + to_string(errno));
            closeSocket();
            return false;
        }
        // …Ë÷√SOCKETŒ™KEEPALIVE
        if (bKeepAlive)
        {
            int		optval = 1;
            if (setsockopt(m_sockClient, SOL_SOCKET, SO_KEEPALIVE, (char *)&optval, sizeof(optval)))
            {
				log(string("错误代码:") + to_string(errno));
                closeSocket();
                return false;
            }
        }

        
        unsigned int serveraddr = inet_addr(pszServerIP.c_str());
        if (serveraddr == INADDR_NONE)	// ºÏ≤ÈIPµÿ÷∑∏Ò Ω¥ÌŒÛ
        {
			log(string("错误代码:") + to_string(errno));
            closeSocket();
            return false;
        }
        sockaddr_in	addr_in;
        memset((void *)&addr_in, 0, sizeof(addr_in));
        addr_in.sin_family = AF_INET;
        addr_in.sin_port = htons(nServerPort);
        addr_in.sin_addr.s_addr = serveraddr;
        if (connect(m_sockClient, (sockaddr *)&addr_in, sizeof(addr_in)) == SOCKET_ERROR) {
			log(string("错误代码:") + to_string(errno));
            closeSocket();
            return false;
        }
		
#ifdef WIN32
        DWORD nMode = 1;
        int nRes = ioctlsocket(m_sockClient, FIONBIO, &nMode);
        if (nRes == SOCKET_ERROR) {
			log(string("错误代码:") + to_string(errno));
            closeSocket();
            return false;
        }
#else
        // …Ë÷√Œ™∑«◊Ë»˚∑Ω Ω
        fcntl(m_sockClient, F_SETFL, O_NONBLOCK);
#endif

    }
    else
    {
        if(getSocketAndConnectByIPV6(pszServerIP,nServerPort,m_sockClient)==false)//ipv6链接失败
        {
            return this->Create(pszServerIP, nServerPort,nBlockSec,bKeepAlive,false);//尝试ipv4重新连接
        }
        
    }
    struct linger so_linger;
    so_linger.l_onoff = 1;
    so_linger.l_linger = 500;
    setsockopt(m_sockClient, SOL_SOCKET, SO_LINGER, (const char*)&so_linger, sizeof(so_linger));
    return true;
}

bool CGameSocket::SendMsg(string pBuf)
{
	if ( m_sockClient == INVALID_SOCKET) {
		return false;
	}
	m_bufOutput = m_bufOutput + pBuf;
	return true;

}
string CGameSocket::ReceiveMsg()
{
	string ret;
	if (recvFromSock())
	{	
		ret = m_bufInput;
		m_bufInput = "";
		return ret;
	}
	else
	{
		return ret;
	}
}

bool CGameSocket::hasError()
{
#ifdef WIN32
	int err = WSAGetLastError();
	if (err != WSAEWOULDBLOCK) {
#else
	int err = errno;

	
	if (!(err == EINPROGRESS || err == EAGAIN || err == 0)) {
#endif
		log(string("错误代码:") + to_string(errno));
		return true;
	}
	
	return false;
}

bool CGameSocket::recvFromSock(void)
{
	if (m_sockClient == INVALID_SOCKET) {
		return false;
	}
	char buf[1024] = { 0 };
	int inlen = recv(m_sockClient, buf, 1024, 0);
	if (inlen > 0) {
		m_bufInput = m_bufInput + string(buf, inlen);
	}
	else if (inlen == 0) {
		Destroy();
		return false;
	}
	else {
		// ¡¨Ω”“—∂œø™ªÚ’ﬂ¥ÌŒÛ£®∞¸¿®◊Ë»˚£©
		if (hasError()) {

			Destroy();
			return false;
		}
	}

	return true;
}

bool CGameSocket::Flush(void)		//? »Áπ˚ OUTBUF > SENDBUF ‘Ú–Ë“™∂‡¥ŒSEND£®£©
{
	if (m_sockClient == INVALID_SOCKET) {
		return false;
	}

	if (m_bufOutput.length() == 0) {
		return true;
	}

	// ∑¢ÀÕ“ª∂Œ ˝æ›
	int	outsize;

	outsize = send(m_sockClient, m_bufOutput.c_str(), m_bufOutput.length(), 0);
	if (outsize > 0) {
		m_bufOutput = m_bufOutput.substr(outsize, m_bufOutput.length() - outsize);
	}
	else {
		if (hasError()) {
			Destroy();
			return false;
		}
	}

	return true;
}

bool CGameSocket::Check(void)
{
	// ºÏ≤È◊¥Ã¨
	if (m_sockClient == INVALID_SOCKET) {
		return false;
	}

	char buf[1];
	int	ret = recv(m_sockClient, buf, 1, MSG_PEEK);
	if (ret == 0) {
		Destroy();
		return false;
	}
	else if (ret < 0) {
		if (hasError()) {
			Destroy();
			return false;
		}
		else {	// ◊Ë»˚
			return true;
		}
	}
	else {	// ”– ˝æ›
		return true;
	}

	return true;
}
void CGameSocket::SetOnCloseFunc(function<void()> _onclose)
{
	m__onclose = _onclose;
}
void CGameSocket::Destroy(void)
{
	//以下代码注释 因安卓长时间断线 重连时卡住会黑屏bug
	
	struct linger so_linger;
	so_linger.l_onoff = 0;
	so_linger.l_linger = 0;
	int ret = setsockopt(m_sockClient, SOL_SOCKET, SO_LINGER, (const char*)&so_linger, sizeof(so_linger));
	

	closeSocket();
	if (m__onclose)m__onclose();
	m_sockClient = INVALID_SOCKET;
}
