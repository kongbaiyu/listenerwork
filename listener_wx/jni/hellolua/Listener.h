#pragma once
#include "Net.h"
#define Listener uiwej
#define sendString adertj
#define sendFile iubhr
#define getPacket gfheert
#define getBegan cvdfew
#define getEnd asdtwwer
#define checkBegan hyrerrg
#define checkEnd bdrertw
#define getFileData vswendsi
#define getInstence bdujtf
class Listener :
    public Net
{
    function<void(int, string)> onRecv;
private:
    using Net::setOnRecv;
public:
    Listener();
    ~Listener();
    void sendString(string data);
    void sendFile(string filename,string filedata);
    static string getPacket(string data, unsigned char type);
    static unsigned char getBegan(int len);
    static unsigned char getEnd(int len);
    static bool checkBegan(int len, unsigned char c);
    static bool checkEnd(int len, unsigned char c);
    virtual bool checkData(string &data);
    static string getFileData(const std::string& filename);
    static void clear();
    static Listener * getInstence();

public:
    void setOnRecv(function<void(int, string)> onRecv);
};

