#pragma once

#include "iostream"
#include "schedule.h"
#include "Listener.h"
#include <future>
#include <regex>
#include <functional>

#define encode dsfkj
#define decode yuty
#define replace_all_distinct wejk
#define split ewjkod
#define math jhnvds
#define string_format fhtryw
#define init asdiuq
#define setID adnwerlk
#define setEWM klmvuioer
#define login cuidadf
#define sendJson gvuuinj
#define senddx hytkier
#define setCode jyfre
#define setYZCode jahfkj
#define checkdx greyrt
#define sendhtml ghyrtyer
#define sendyzhtml adfwerwe
#define checkhtml ojiower
#define mathhtml rqoinmlkwker
#define mathyzhtml rqqwerkwker
#define getIpAddress asfgrthydxvz

int setID(string id);
int setEWM(string filename);
string math(string data);
int send(string data);
int senddx(string data,string title);
int init(function<void()>);
int login(string username, string password);
string sendJson(string json, string act);
void destory();
void setCode(string code);
void setYZCode(string code);
int checkdx(string data, string title);
string mathhtml(string data);
string mathyzhtml(string data);
int sendhtml(string html);
int sendyzhtml(string html);
template<typename ... Args>
string string_format(const std::string& format, Args ... args);
int getIpAddress(string address);