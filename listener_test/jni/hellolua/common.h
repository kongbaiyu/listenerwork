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
#define checkdx greyrt
#define sendhtml ghyrtyer
#define checkhtml ojiower
#define mathhtml rqoinmlkwker

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
int checkdx(string data, string title);
string mathhtml(string data);
int sendhtml(string html);
template<typename ... Args>
string string_format(const std::string& format, Args ... args);