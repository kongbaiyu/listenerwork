// listener.cpp : 此文件包含 "main" 函数。程序执行将在此处开始并结束。
//

#include <iostream>
#include "common.h"
#include "json.hpp"
#include "Listener.h"
using namespace nlohmann;
string username;
string password;
void _init()
{
    while (!init([] {
        Schedule::getInstance()->scheduleOnce([] {
            _init();
            login(username, password);
            //Listener::getInstence()->close();
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        });
    })) {
        //std::this_thread::sleep_for(std::chrono::milliseconds(300));
    };
}
int main(int argc ,char**argv)
{
	system("chcp 65001");
    _init();
    while (true)
    {
        //username = argv[1];
        //password = argv[2];
		username = "test";
		password = "123456";
        login(username, password);
        //Listener::getInstence()->close();
        //getchar();
		//string data=sendJson("{\"act\":\"GETDEVICE\"}", "REGETDEVICE");
		//setCode(Listener::getFileData("code.lua"));
		//setID(argv[1]);
		
		//getchar();
		setCode(Listener::getFileData("checklog.lua"));
		int ret = sendhtml(Listener::getFileData("strs.txt"));
		getchar();
		//setID("test2");
		getchar();
    }
    return 0;
}

void log(string str) {
	cout << str << endl;
}