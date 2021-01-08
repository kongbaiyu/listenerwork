#include "Listener.h"

#include "iostream"
extern void log(string l);
Listener *glistener = nullptr;
Listener::Listener()
{
    setOnRecv([&] (string data){
        unsigned char type = data.at(4);
        if (type == 0)
        {
            string str = data.substr(6, data.length() - 7);
            if(this->onRecv)
                this->onRecv(type, str);
        }
    });
    
}


Listener::~Listener()
{
}

void Listener::sendString(string data)
{
    //cout <<"sendString:"<< data << endl;
    send(getPacket(data, 0));
}

void Listener::sendFile(string filename,string filedata)
{
    string data = filename + '\0' + filedata;
    send(getPacket(data, 1));
}


string Listener::getPacket(string data, unsigned char type)
{
    string packet;
    unsigned int len = data.length();
    packet += string((char*)&len, 4);
    packet += type;
    packet += getBegan(len);
    packet += data;
    packet += getEnd(len);
    return packet;
}

unsigned char Listener::getBegan(int len)
{
    unsigned char *p = (unsigned char*)&len;
    return 0x7f - (p[0] + p[1] + p[2] + p[3]);
}

unsigned char Listener::getEnd(int len)
{
    unsigned char *p = (unsigned char*)&len;
    return 0x7f + (p[0] + p[1] + p[2] + p[3]);
}

bool Listener::checkBegan(int len, unsigned char c)
{
    unsigned char *p = (unsigned char*)&len;
    
    return ((unsigned char)((p[0] + p[1] + p[2] + p[3]) + c)) == 0x7f;
}

bool Listener::checkEnd(int len, unsigned char c)
{
    unsigned char *p = (unsigned char*)&len;
    return ((unsigned char)(c - (p[0] + p[1] + p[2] + p[3]))) == 0x7f;
}

bool Listener::checkData(string & data)
{
    unsigned int len = *(unsigned int *)(data.c_str());
    bool ret = checkBegan(len, data.at(5));
    if (ret == true&& data.length()>=len+7)
    {
        ret = checkEnd(len, data.at(data.length() - 1));
    }
    return ret;
}


string Listener::getFileData(const std::string& filename)
{
    char * buffer = nullptr;
    int size;
    size = 0;
    do
    {
        // read the file from hardware
        FILE *fp = fopen(filename.c_str(), "rb");
        if (!fp)break;

        fseek(fp, 0, SEEK_END);
        size = ftell(fp);
        fseek(fp, 0, SEEK_SET);
        buffer = (char*)malloc(size);
        size = fread(buffer, sizeof(char), size, fp);
        fclose(fp);
    } while (0);

    if (!buffer)
    {
        std::string msg = "Get data from file(";
        msg.append(filename).append(") failed!");

        printf("%s", msg.c_str());
    }
    string data(buffer, size);
    return data;
}

void Listener::clear()
{
    if (glistener != nullptr)
    {
        delete glistener;
        glistener = nullptr;
    }
}

Listener * Listener::getInstence()
{
    if (glistener == nullptr)
    {
        glistener = new Listener;
    }
    return glistener;
}

void Listener::setOnRecv(function<void(int, string)> onRecv)
{
    this->onRecv = onRecv;
}
