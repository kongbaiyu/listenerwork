
#include "schedule.h"
#include "iostream"
#include "string"
#include "functional"
#include "unordered_map"
#include "thread"
#include "list"
using namespace std;
extern void log(string l);


Schedule *gshedule = nullptr;

Schedule::Schedule()
{
    isRunning = true;
    _t = thread([&] {
        int times = 0;
        while (isRunning)
        {
            lock_guard<recursive_mutex> lock(mx);
            list<string> keys;
            for (auto func : m_funmap)
            {
                times++;
                if (func.second)
                    func.second();
                else
                    keys.push_back(func.first);
            }
            for (auto k : keys)
            {
                m_funmap.erase(k);
            }
            if(m_funmap.size()==0)
                std::this_thread::sleep_for(std::chrono::milliseconds(10));
            else
                std::this_thread::sleep_for(std::chrono::milliseconds(10));
        }
    });
}


Schedule::~Schedule()
{
}

Schedule * Schedule::getInstance()
{
    if (gshedule == nullptr)
    {
        gshedule = new Schedule;
    }
    return gshedule;
}

void Schedule::schedule(function<void()> func, string key)
{
    lock_guard<recursive_mutex> lock(mx);
    m_funmap[key] = func;
}

void Schedule::scheduleOnce(function<void()> func)
{
    int r = rand();

    schedule([=] {
        func(); 
        unschedule(string((char*)&r, 4));
    }, string((char*)&r, 4));
}

void Schedule::scheduleOnce(function<void()> func, int delay)//delay ∫¡√Î
{
    int r = rand();
    string key=string((char*)&r, 4);
    m_timemap[key] = delay / 10;
    schedule([=] {
        int t = m_timemap[key];
        if (t == 0)
        {
            func();
            unschedule(key);
            m_timemap.erase(key);
        }
        else
        {
            m_timemap[key] = t - 1;
        }
    }, key);
}

void Schedule::unschedule(string key)
{
    lock_guard<recursive_mutex> lock(mx);
    m_funmap[key]=function<void()>();
}

void Schedule::destory()
{
    gshedule->isRunning = false;
    gshedule->_t.join();
    delete gshedule;
    gshedule = nullptr;
}
