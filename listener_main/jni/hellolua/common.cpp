#include "common.h"

#include "json.hpp"
#include "kaguya.hpp"
extern "C" {
#include <lua.h>  
#include <lualib.h>  
#include <lauxlib.h>  
}

#include "string"
#include "sstream"
#include "list"

#include "string"
#include "iostream"
#include "UdpSocket.h"

#ifdef WIN32
#define  LOGD(...)  ;
#else
#include <android/log.h>
#define  LOG_TAG    "main"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#endif
//#define  LOGD(...) 

using namespace std;
using namespace nlohmann;
time_t lastXinTiao;
bool ignorext=false;
extern void log(string str);

string encode(string data);
string decode(string data);
map<string, function<void(json)>> actions;

string luacode;
string yzluacode;
string ipaddress;
lua_State* L = 0;
void initLua()
{
	if (L == 0)
	{
		L = lua_open();
		luaL_openlibs(L);//open all previous libraries
	}
}

string&   replace_all_distinct(string&   str, const   string&   old_value, const   string&   new_value)
{
    for (string::size_type pos(0); pos != string::npos; pos += new_value.length()) {
        if ((pos = str.find(old_value, pos)) != string::npos)
            str.replace(pos, old_value.length(), new_value);
        else   break;
    }
    return   str;
}
list<string> split(const std::string& s, char delimiter)
{
    list<string> tokens;
    string token;
    istringstream tokenStream(s);
    while (std::getline(tokenStream, token, delimiter))
    {
        tokens.push_back(token);
    }
    return tokens;
}
int setID(string id)
{
    //{"act":"SETID","id":id}
    //list<string> dids = split(id, ',');
    json jdata = {
        {"act","BINDDEVICE"},
        {"devicename",id}
    }; 
    promise<int> pret;
    future<int> fret = pret.get_future();
    actions["REBINDDEVICE"] = [&](json data) {
        if (data["ret"] == "OK")
        {
            pret.set_value(1);
            json xt = {
				{"action",222},
				{"data",data["data"]}
			};
			if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
			{
				log(string("REBINDDEVICE 错误代码:") + to_string(errno));
			}
        }
        else
        {
            pret.set_value(0);
			if (data.contains("msg"))
			{
				log(data["msg"].get<string>());
			}
			
        }
    };
	lastXinTiao = time(0);
    Listener::getInstence()->sendString(encode(jdata.dump()));
    int ret = 0;
    if (fret.wait_for(chrono::seconds(15))==future_status::ready)
    {
        ret = fret.get();
    }
    actions["REBINDDEVICE"] = nullptr;
    cout << "ret:" << ret << endl;
    return ret;
}
int setEWM(string filename)
{
    //{"act":"SETEWM", "filename" : filename}
    int idx = filename.find_last_of('/')+1;
    string _filename;
    if (idx>0)
    {
        _filename =  filename.substr(idx, filename.length() - idx);
    }
    else
    {
        _filename =  filename;
    }
    _filename = "ewm_" + _filename;
    json jdata = {
        {"act","SETEWM"},
        {"filename",_filename}
    };
	lastXinTiao = time(0);
    Listener::getInstence()->sendString(jdata.dump());

    string data = Listener::getFileData(filename);
    Listener::getInstence()->sendFile(_filename, data);
    return 0;
}
string math2(string data)//匹配2
{
	data = replace_all_distinct(data, "\n", "");
	data = replace_all_distinct(data, "\r", "");
	std::smatch matchResult;
	string yuan("\xE5\x85\x83\x00", 4);//utf8 元
	std::regex_match(data, matchResult, std::regex(string(".*?([0-9\\.]+)") + yuan + ".*"));
	if (matchResult.size() > 1)
	{
		cout << matchResult[1] << yuan << endl;

		return matchResult[1].str();
	}

	return "";
}

string math(string data)//匹配
{
    data=replace_all_distinct(data, "\n", "");
    data=replace_all_distinct(data, "\r", "");
    std::smatch matchResult;
    string yuan("\xE5\x85\x83\x00",4);//utf8 元
    std::regex_match(data, matchResult, std::regex(string(".*?([0-9\\.]+)")+yuan+".*"));
    if (matchResult.size() > 1)
    {
		string tmp = matchResult[1];
		if (math2(replace_all_distinct(data, tmp, "")) != "")
		{
			return "";
		}
        
        return tmp.c_str();
    }

    return "";
}
void setCode(string code)
{
	//log("脚本获取");
	//log(code);
	luacode = code;
}

void setYZCode(string code)
{
	//log("有赞脚本获取");
	yzluacode = code;
}

string mathdx(string data,string title)
{
	kaguya::State state(L);
	state.dostring(luacode);
	kaguya::LuaFunction lua_mathdx = state["mathdx"];
	if (lua_mathdx.isNilref())
		return "";
	data = replace_all_distinct(data, "\n", "");
	data = replace_all_distinct(data, "\r", "");
	string ret = lua_mathdx(data,title);
	return ret;
}
int checkdx(string data, string title)
{
	if (mathdx(data, title) != "")
		return 1;
	return 0;
}
string mathhtml(string data)
{
	kaguya::State state(L);
	state.dostring(luacode);
	kaguya::LuaFunction lua_mathhtml = state["mathhtml"];
	if (lua_mathhtml.isNilref())
		return "";
	data = replace_all_distinct(data, "\n", "");
	data = replace_all_distinct(data, "\r", "");
	string ret = lua_mathhtml(data);
	return ret;
}
string mathyzhtml(string data)
{
	kaguya::State state(L);
	state.dostring(yzluacode);
	kaguya::LuaFunction lua_mathhtml = state["mathhtml"];
	if (lua_mathhtml.isNilref())
		return "";
	data = replace_all_distinct(data, "\n", "");
	data = replace_all_distinct(data, "\r", "");
	string ret = lua_mathhtml(data);
	return ret;
}
int checkhtml(string data)
{
	if (mathhtml(data) != "")
		return 1;
	return 0;
}
int send(string rawdata)
{
    //{"act":"CHONGZHI","money":"","raw":rawdata}
    string data;
    string money = math(rawdata);
    auto t = split(rawdata, ':');
    auto type = t.front();
    json jdata = {
        {"act","CHONGZHI"},
        {"money",money},
        {"raw",rawdata},
        {"type",type}
    };
	lastXinTiao = time(0);
    if(money!="")
        Listener::getInstence()->sendString(encode(jdata.dump()));
    return 0;
}
int senddx(string rawdata,string title)
{
	//{"act":"CHONGZHI","money":"","raw":rawdata}
	string data;
	string money = mathdx(rawdata,title);
	auto t = split(rawdata, ':');
	auto type = t.front();
	json jdata = {
		{"act","CHONGZHI"},
		{"money",money},
		{"raw",rawdata},
		{"type",type}
	};
	lastXinTiao = time(0);
	//log(jdata.dump());
	if (money != "")
		Listener::getInstence()->sendString(encode(jdata.dump()));
	return 0;
}
string sendstr(string str,string act)
{
	lastXinTiao = time(0);
    Listener::getInstence()->sendString(encode(str));
    return str;
}
int sendhtml(string html)
{
	string data = mathhtml(html);
	try
	{
		json r = json::parse(data);
		json jdata = {
			{"action",112},
			{"act","REXYPAY"},
			{"ret",r["type"]},
			{"money",r["money"]},
			{"title",r["title"]}
		};
		lastXinTiao = time(0);
		Listener::getInstence()->sendString(encode(jdata.dump()));
		if (-1 == sendTo("127.0.0.1", 5456, jdata.dump()))
		{
			log(string("REXYPAY 错误代码:") + to_string(errno));
		}
	}
	catch (const std::exception&e)
	{
		log(e.what());
	}
	return 0;
}
int sendyzhtml(string html)
{
	string data = mathyzhtml(html);
	try
	{
		json r = json::parse(data);
		json jdata = {
			{"action",114},
			{"act","REYOUZANPAY"},
			{"ret",r["type"]},
			{"money",r["money"]},
			{"title",r["title"]}
		};
		lastXinTiao = time(0);
		Listener::getInstence()->sendString(encode(jdata.dump()));
		if (-1 == sendTo("127.0.0.1", 5456, jdata.dump()))
		{
			log(string("REXYPAY 错误代码:") + to_string(errno));
		}
	}
	catch (const std::exception&e)
	{
		log(e.what());
	}
	return 0;
}
int login(string username, string password)
{
    json jdata = {
        {"act","LOGIN"},
        {"account",username},
        {"password",password}
    };
    promise<int> pret;
    future<int> fret= pret.get_future();
    actions["RELOGIN"] = [&](json data) {
        if (data["ret"] == "OK")
        {
            pret.set_value(1);
			if (data["stype"] == "CHECK")
			{
				//ignorext = true;
				sendTo("127.0.0.1", 5456, "{\"action\":106}");
				sendTo("127.0.0.1", 5456, "{\"action\":106}");
			}
        }
        else if (data["ret"] == "FREEZE")
        {
            pret.set_value(-2);
        }
        else
        {
            pret.set_value(0);
        }
        lastXinTiao = time(0);
    };
	actions["REGF"] = [&](json data) {
		json r = data["data"];
		for (auto& iter : data["data"])
		{
			if (iter["id"]==10)
			{
				setYZCode(iter["func"]);
			}
			else
			{
				setCode(iter["func"]);
			}
		}
        lastXinTiao = time(0);
	};
    actions["XINTIAO"] = [&](json data) {
        json xt = {
            {"act","RXINTIAO"},
            {"data",data["data"]}
        };
        //log("RXINTIAO");
        Listener::getInstence()->sendString(encode(xt.dump()));
        lastXinTiao = time(0);
    };
	actions["XYPAY"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",107},
			{"qrcurl",data["qrcurl"]}
		};
		if (data["click"].is_boolean())
		{
			xt["click"] = data["click"];
		}
		if (data["checktype"].is_number())
		{
			xt["checktype"] = data["checktype"];
		}
		
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("XYPAY 错误代码:") + to_string(errno));
		}
        lastXinTiao = time(0);
	};
	actions["XYPAY2"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",109},
			{"market_url",data["market_url"]}
		};
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("XYPAY2 错误代码:") + to_string(errno));
		}
        lastXinTiao = time(0);
	};
	actions["YOUZANPAY"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",113},
			{"qrcurl",data["qrcurl"]}
		};
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("YOUZANPAY 错误代码:") + to_string(errno));
		}
        lastXinTiao = time(0);
	};
	actions["YOUZANPAY1"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",115},
			{"qrcurl",data["qrcurl"]}
		};
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("YOUZANPAY 错误代码:") + to_string(errno));
		}
        lastXinTiao = time(0);
	};
	actions["ASCHECK"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",120},
			{"url",data["qrcurl"]}
		};
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("YOUZANPAY 错误代码:") + to_string(errno));
		}
		lastXinTiao = time(0);
	};
	actions["ALGEMAPRO"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",118},
			{"device_id",data["device_id"]},
			{"money",data["money"]}
		};
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("YOUZANPAY 错误代码:") + to_string(errno));
		}
		lastXinTiao = time(0);
	};

	actions["CREATEWXPAM"] = [&](json data) {
		//log(data.dump());
		json xt = {
			{"action",123},
			{"num",data["num"]},
			{"money",data["money"]}
		};
		if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
		{
			log(string("YOUZANPAY 错误代码:") + to_string(errno));
		}
		lastXinTiao = time(0);
	};
	actions["DOUYINJC"] = [&](json data) {
			//log(data.dump());
			json xt = {
				{"action",124},
				{"qrcurl",data["qrcurl"]}
			};
			if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
			{
				log(string("YOUZANPAY 错误代码:") + to_string(errno));
			}
			lastXinTiao = time(0);
		};
	actions["KAOLAJC"] = [&](json data) {
				//log(data.dump());
				json xt = {
					{"action",126},
					{"cookie",data["param"]},
					{"orderid",data["outorderid"]}
				};
				if (-1 == sendTo("127.0.0.1", 5456, xt.dump()))
				{
					log(string("KOALA 错误代码:") + to_string(errno));
				}
				lastXinTiao = time(0);
			};
	lastXinTiao = time(0);
	//log("xintiao");
	Schedule::getInstance()->schedule([] {
		if (ignorext == true)//忽略心跳
			return;
		time_t now = time(0);
		if ((now - lastXinTiao) > 130)
		{
			log("心跳超时");
			LOGD("xin tiao chaoshi");
			Listener::getInstence()->close();
			thread t([] {
				destory();
			});
			t.detach();
		}
	}, "xintiao");

	lastXinTiao = time(0);
    Listener::getInstence()->sendString(encode(jdata.dump()));

    int ret = -1;
    if (fret.wait_for(chrono::seconds(15)) == future_status::ready)
    {
        ret = fret.get();
    }
    actions["RELOGIN"] = nullptr;
    cout << "ret:" << ret << endl;//-1超时，-2封号，0密码错误,1成功，2无法连接服务器
    return ret;
}
string sendJson(string sjson, string ract)
{
	lastXinTiao = time(0);
    Listener::getInstence()->sendString(encode(sjson));
	if (ract == "")
		return "";
    promise<string> pret;
    future<string> fret = pret.get_future();
    actions[ract] = [&](json data) {
        pret.set_value(data.dump());
    };
    string ret ;
    if (fret.wait_for(chrono::seconds(15)) == future_status::ready)
    {
        ret = fret.get();
    }
    actions[ract] = nullptr;
    return ret;
}

int getIpAddress(string address)
{
	ipaddress = address;
	return 0;
}

int init(function<void()> call)
{
	initLua();
    bool state=false;
	Listener::getInstence()->setOnClose(call);
    Listener::getInstence()->ctor(ipaddress, 9990, [&](bool _state) {
        state = _state;
    });
    if (state == false)Listener::clear();
    Listener::getInstence()->setOnRecv([](int type,string _data) {
        if (type == 0)
        {
			try
			{
				json data = json::parse(decode(_data));
				auto func = actions[data["act"]];
				if (func)
				{
					func(data);
				}
				else
				{
					cout << "act:" << data["act"] << " not found" << endl;
				}
			}
			catch (const std::exception&e)
			{
				log(e.what());
			}
            
			
        }
    });
    return state;
}

void destory()
{
    Schedule::destory();
}

template<typename ... Args>
string string_format(const std::string& format, Args ... args)
{
    size_t size = snprintf(nullptr, 0, format.c_str(), args ...) + 1; // Extra space for '\0'
    unique_ptr<char[]> buf(new char[size]);
    snprintf(buf.get(), size, format.c_str(), args ...);
    return string(buf.get(), buf.get() + size - 1); // We don't want the '\0' inside
}

vector<unsigned char> key = { 35,234,12,15,85,45,24,2,5,23,1,57,43,233,76 };
vector<unsigned char> table = { 
    224,1,181,145,179,101,36,86,253,167,221,242,
    32,68,82,143,146,208,151,17,8,47,160,34,104,7,110,94,
    187,115,135,170,172,126,113,230,123,163,239,16,241,217,
    14,157,24,71,174,88,171,72,132,33,21,182,41,159,223,
    248,173,177,247,232,194,209,5,18,244,106,252,195,74,
    125,119,111,92,141,138,162,229,240,251,213,62,197,9,
    175,127,216,140,79,176,60,129,212,153,67,26,30,164,
    205,204,231,228,238,97,210,50,137,218,191,6,203,2,
    95,199,156,27,4,37,152,89,98,61,25,250,15,196,198,
    42,75,0,80,234,96,78,245,180,222,154,69,19,38,201,
    51,65,49,133,40,207,147,243,103,155,131,214,105,
    117,178,12,236,22,99,81,233,246,45,206,165,186,166,52,
    215,188,193,83,59,235,122,93,102,29,56,168,91,53,107,28,
    120,128,44,118,84,139,85,66,73,211,148,100,121,185,46,
    249,43,31,190,254,184,54,108,124,35,237,39,149,20,189,
    161,10,3,169,144,192,202,158,87,58,114,130,55,226,116,
    134,219,63,48,183,13,90,200,64,220,142,11,23,112,57,227,
    77,109,255,150,76,136,70,225 
};
vector<unsigned char> tableindex = {
    130, 01, 112, 219, 117, 64, 110, 25, 20, 84, 218, 243, 158, 237, 42, 125, 39, 19, 65, 140, 215, 52,
    160, 244, 44, 123, 96, 116, 186, 180, 97, 204, 12, 51, 23, 211, 06, 118, 141, 213, 147, 54, 128, 203,
    189, 165, 201, 21, 235, 145, 106, 143, 170, 184, 208, 229, 181, 246, 226, 175, 91, 122, 82, 234, 240,
    144, 194, 95, 13, 139, 254, 45, 49, 195, 70, 129, 252, 248, 134, 89, 131, 162, 14, 174, 191, 193, 07,
    225, 47, 120, 238, 183, 74, 178, 27, 113, 133, 104, 121, 161, 198, 05, 179, 151, 24, 155, 67, 185, 209,
    249, 26, 73, 245, 34, 227, 29, 231, 156, 190, 72, 187, 199, 177, 36, 210, 71, 33, 86, 188, 92, 228,
    153, 50, 146, 232, 30, 253, 107, 76, 192, 88, 75, 242, 15, 221, 03, 16, 149, 197, 214, 251, 18, 119, 94,
    138, 152, 115, 43, 224, 55, 22, 217, 77, 37, 98, 167, 169, 9, 182, 220, 31, 48, 32, 58, 46, 85, 90,
    59, 157, 04, 136, 02, 53, 236, 207, 200, 168, 28, 172, 216, 205, 109, 222, 173, 62, 69, 126, 83, 127,
    114, 239, 142, 223, 111, 100, 99, 166, 148, 17, 63, 105, 196, 93, 81, 154, 171, 87, 41, 108, 233, 241,
    10, 137, 56, 00, 255, 230, 247, 102, 78, 35, 101, 61, 163, 132, 176, 159, 212, 103, 38, 79, 40, 11, 150,
    66, 135, 164, 60, 57, 202, 124, 80, 68, 8, 206, 250
};

string encode(string data)
{
    int idx = 0;
    string out;
    for (char c : data)
    {
        char k = (key.at(idx%key.size()) + idx) >> 1;
        unsigned char t = table[(unsigned char)((unsigned char)k + (unsigned char)c)];
        out.push_back(t);
        idx++;
    }
    return out;
}
string decode(string data)
{
    int idx = 0;
    string out;
    for (char c : data)
    {
        char k = (key.at(idx%key.size()) + idx) >> 1;
        out.push_back(tableindex[(unsigned char)data[idx]] - (unsigned char)k);
        idx++;
    }
    return out;
}


