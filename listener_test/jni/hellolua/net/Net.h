
#include "string"
#include "GameSocket.h"
#include "schedule.h"
#define Net vjekr
#define ctor dgret
#define connect fghrty
#define close bvcfh
#define send avcgtr
#define setOnRecv rtyert
#define setOnClose dfhert
#define setIpV6Enable bhreta
#define receive zxcdkiyu
#define checkData gfyrtbf

using namespace std;
class Net
{
private:
	string host;
	int port;
	int timeout;
	bool keepalive;
    bool useIpV6;
	CGameSocket *gamesock;
	function<void(bool)> onConnect;
    function<void()> onClose;
	string recvdata;
    function<void(string)> onRecv;
public:
	Net();
	~Net();
	void ctor(string host, int port, function<void(bool)> onConnect);
	void connect();
	void close();
	void send(string str);
	void setOnRecv(function<void(string)> onRecv);
	void setOnClose(function<void()> onClose);
    void setIpV6Enable(bool enable);
	void receive();
    virtual bool checkData(string &data) = 0;
};