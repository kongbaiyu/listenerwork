#include "Net.h"
#include "schedule.h"


#define LOGD printf
extern void log(string str);

Net::Net()
{
	this->gamesock = new CGameSocket();
	this->useIpV6 = false;
}
Net::~Net()
{
	delete this->gamesock;
}
void Net::ctor(string host, int port, function<void(bool)> onConnect)
{
	this->onConnect = onConnect;
	
	this->gamesock->SetOnCloseFunc([=]{
        LOGD("closed\n");
		Schedule::getInstance()->unschedule("connect");
		if (this->onClose)
			this->onClose();
	});
	this->host = host;
	this->port = port;
	this->timeout = 8;
	this->keepalive = false;
	this->connect();
}
void Net::connect()
{
	if (this->gamesock->Create(this->host, this->port, this->timeout, this->keepalive,this->useIpV6))
	{
        //log("connect true\n");
		this->onConnect(true);
        
        Schedule::getInstance()->schedule([=](){
			this->gamesock->Flush();
			this->receive();
		},"connect");
	}
	else
	{
		//log("connect false\n");
		this->onConnect(false);
	}
}
void Net::close()
{
	this->gamesock->Destroy();
}
void Net::send(string str)
{
	this->gamesock->SendMsg(str);
}
void Net::setOnRecv(function<void(string)> onRecv)
{
	this->onRecv = onRecv;
}
void Net::setOnClose(function<void()> onClose)
{
	this->onClose = onClose;
}
void Net::receive()
{
	string recvdata = this->gamesock->ReceiveMsg();
	if (recvdata.length() > 0)
	{
		this->recvdata += recvdata;
	}
	int datalen = this->recvdata.length();
	if (datalen > 6)
	{
		unsigned int len=*((unsigned int*)this->recvdata.c_str());
        string pack = string(this->recvdata.c_str(), len);
        if (checkData(pack) == false)
            this->close();
        else
        {
            if (datalen >= len + 7)
            {
                if (checkData(pack) == false)
                    this->close();
                else
                {
                    this->onRecv(this->recvdata.substr(0, len + 7));
                    this->recvdata = this->recvdata.substr(len + 7, datalen - (len + 7));
                }
                
            }
        }
		
	}
}
void Net::setIpV6Enable(bool enable)
{
    this->useIpV6=enable;
}