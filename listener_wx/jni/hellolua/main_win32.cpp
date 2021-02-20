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

		/*string a = "service=\"alipay.fund.stdtrustee.order.create.pay\"&partner=\"2088401309894080\"&_input_charset=\"utf-8\"&notify_url=\"https://wwhongbao.taobao.com/callback/alipay/notifyPaySuccess.do\"&out_order_no=\"12621186_2100_1612748708_d173edf3535aa2064e956be42b6036b4_1\"&out_request_no=\"12621186_2100_1612748708_d173edf3535aa2064e956be42b6036b4_1_p\"&product_code=\"SOCIAL_RED_PACKETS\"&scene_code=\"MERCHANT_COUPON\"&amount=\"1.00\"&pay_strategy=\"CASHIER_PAYMENT\"&receipt_strategy=\"INNER_ACCOUNT_RECEIPTS\"&platform=\"DEFAULT\"&channel=\"APP\"&order_title=\"淘宝现金红包\"&master_order_no=\"2021020810002001380520494649\"&order_type=\"DEDUCT_ORDER\"&auid=\"2088202332279574\"&extra_param=\"{\"payeeShowName\":\"淘宝现金红包\"}\"&pay_timeout=\"30m\"&order_expired_time=\"360d\"&sign=\"bPSNke5nTHv%2FFNEqmszzs9Ct%2FrX64CHJCdqsTFvTndEZEPYSu14MBYujKkwg1giLpUkSzznVGZX8yWBEYE1OnnMr4PpP1olzU%2FxpwQtvNXr66YKkntIpFc5sZqIVd4A7eA0J6SV3poBI9u7lM1oJc7ik4tg0aeL32cN1EhMkbcjNipJmDUb4iuzFr%2FLSA%2BSEyBd3u6Z2pYQI%2FuiOa%2FppISib0L%2B0DoELrsfuV%2BWMTRDbIpjORvdWgImIrca3pwBH%2F%2B8hDuuP%2F1s6G9UmKnBYST6kaJjf958h%2FeGyFpM6tkxN%2BYXP8s8XY3sCG%2FUr4l4xlFn%2FvPT1%2FKN9pN3aIQoulg%3D%3D\"&sign_type=\"RSA\"";
		cout << a << endl;
		cout << "--------------------------------------------" << endl;
		int b = a.find("out_order_no");
		int c = a.find("sign_type");
		int b1 = a.find("pay_strategy");
		string a1 = a.substr(b + 14, b1 -b - 14 - 2);
		cout << a1 << endl;
		cout << "--------------------------------------------" << endl;
		int b2 = a.find("master_order_no");
		string a2 = a.substr(b2+17,28);
		cout << "a2=" << a2 << endl;
		cout << "--------------------------------------------" << endl;
		int b3 = a.find("sign=");
		string a3 = a.substr(b3+6,c-b3-6-2);
		cout << a3 << endl;
		cout << "--------------------------------------------" << endl;
		cout << a1.length() << "	" << a2.length() << "	" << a3.length() << endl;
		cout << a1.length() + a2.length() + a3.length() << endl;
		string u = "service=\"alipay.fund.stdtrustee.order.create.pay\"&partner=\"2088401309894080\"&_input_charset=\"utf-8\"&notify_url=\"https://wwhongbao.taobao.com/callback/alipay/notifyPaySuccess.do\"&out_order_no=\""+a1+"\"&pay_strategy=\"CASHIER_PAYMENT\"&receipt_strategy=\"INNER_ACCOUNT_RECEIPTS\"&platform=\"DEFAULT\"&channel=\"APP\"&order_title=\"淘宝现金红包\"&master_order_no=\""+a2+"\"&order_type=\"DEDUCT_ORDER\"&auid=\"2088202332279574\"&extra_param=\"{\"payeeShowName\":\"淘宝现金红包\"}\"&pay_timeout=\"30m\"&order_expired_time=\"360d\"&sign=\""+a3+"\"&sign_type=\"RSA\"";
		cout << u << endl;
		cout << (a==u) << endl;
		cout << a.length() << "	" << u.length() << endl;
		if (a == u) {
			cout << "true" << endl;
		}
		else
		{
			cout << "false" << endl;
		}*/
		/*int e = c - b-15;
		string d = a.substr(b+14, e);
		cout << "a=" << a.length() << endl;
		cout << "b=" << b << endl;
		cout << "c=" << c << endl;
		cout << c - b << endl;
		cout << a.substr(b + 14, c-b-15) << endl;
		cout << d << endl;
		cout << d.length() << endl;
		cout << e << endl;*/
		//setCode(Listener::getFileData("checklog.lua"));
		//int ret = sendhtml(Listener::getFileData("strs.txt"));
		getchar();
		//setID("test2");
		getchar();
    }
    return 0;
}

void log(string str) {
	cout << str << endl;
}