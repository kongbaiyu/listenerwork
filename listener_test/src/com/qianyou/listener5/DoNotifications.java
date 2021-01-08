package com.qianyou.listener5;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.nat.Listener;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class DoNotifications {
    //private FileLog fileLog;
	Random r;
	DoNotifications(){
		//fileLog=new FileLog(Environment.getExternalStorageDirectory().getPath()+"/Notification.sav");
		r=new Random(new Date().getTime());
	}
	public void doN(String pkg,String title,String text,String time)
	{
		//Log.T("pkg:"+pkg+",title:"+title+",text:"+text);
		if(pkg.equals("com.tencent.mm"))
		{
			
			String str=time+"    "+title+"\n"+text;
			//SLog.T(str);
			send("wx:"+str,"wx",title);
		}
		if(pkg.equals("com.eg.android.AlipayGphone"))
		{
			String str=time+"    "+title+"\n"+text;
			//SLog.T(str);
			send("alipay:"+str,"alipay",title);
		}
		if(pkg.equals("com.buybal.buybalpay.nxy.fkepay"))
		{
			String str=time+"    "+title+"\n"+text;
			//SLog.T(str);
			send("bank:"+str,"bank",title);
		}
		if(pkg.equals("com.csii.jsnx"))
		{
			String str=time+"    "+title+"\n"+text;
			//SLog.T(str);
			send("bank:"+str,"bank",title);
		}
		if(pkg.equals("com.csii.zjrcm"))
		{
			String str=time+"    "+title+"\n"+text;
			//SLog.T(str);
			send("fsj:"+str,"fsj",title);
		}
		if(pkg.equals("com.newland.satrpos.starposmanager"))
		{
			String str=time+"    "+title+"\n"+text;
			//SLog.T(str);
			send("bank:"+str,"bank",title);
		}
		
		if(pkg.equals("com.android.mms"))
		{
			String str=time+"    "+title+"\n"+text;
			//SLog.T(pkg+":"+time+"    "+title+"\n"+text);
			send("bank:"+str,"bank",title);
		}
		if(pkg.equals("com.qianyou.listener5"))
		{
			//SLog.T(title+"\n"+text);
			String str=time+"    "+title+"\n"+text;
			send("bank:"+str,"bank",title);
		}
		if (pkg.equals("com.qima.kdt")) {
			String str=time+"    "+title+"\n"+text;
			//Log.T("有赞："+str);
			send("youzan:"+str, "youzan", title);
		}
		NLService.clearNotification();
	}
	public void send(final String data,final String type,final String title)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jo=new JSONObject();
				long time=new Date().getTime();
				try {
					jo.put("action", MainActivity.SEND);
					jo.put("data",data);
					jo.put("type",type);
					jo.put("title",title);
					jo.put("time", time );
					jo.put("rid",String.format("%d%d", time,r.nextInt()));
					NLService.sendToMain(jo.toString(),false);
					Thread.sleep(1000);
					NLService.sendToMain(jo.toString(),false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}).run();
		
	}
}
