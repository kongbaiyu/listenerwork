#pragma once
#include "string"
#include "functional"
#include "unordered_map"
#include "thread"
#include "mutex"
using namespace std;
#define Schedule asdjkfgdf
#define getInstance gferre
#define schedule bnyeefg
#define scheduleOnce hsfrty
#define unschedule bddg
#define destory grehyt
class Schedule
{
    unordered_map<string, function<void()>> m_funmap;
    unordered_map<string, float> m_timemap;
    thread _t;
    recursive_mutex mx;
    bool isRunning;
public:
    Schedule();
    ~Schedule();
    static Schedule*getInstance();
    void schedule(function<void()> func, string key);
    void scheduleOnce(function<void()> func);
    void scheduleOnce(function<void()> func,int delay);
    void unschedule(string key);
    static void destory();
};

