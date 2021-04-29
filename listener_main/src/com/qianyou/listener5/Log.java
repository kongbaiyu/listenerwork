package com.qianyou.listener5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Environment;
import android.os.Message;

public class Log {
	public static void T(final String str)
	{
		System.out.println(str);
		Message msg=MainActivity.instance.handler.obtainMessage();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[DD HH:mm:ss] ");// HH:mm:ss
		//获取当前时间
		Date date = new Date(System.currentTimeMillis());
		String logstr=simpleDateFormat.format(date)+str;
		msg.obj=logstr;
		MainActivity.instance.handler.sendMessage(msg);
		String logfile=MainActivity.instance.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/listener.log";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(logfile,true));
			pw.println(logstr);      // 换行
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void T2(final String str)
	{
		Message msg=MainActivity.instance.handler.obtainMessage();
		msg.obj=" -last- "+str;
		MainActivity.instance.handler.sendMessage(msg);
		String logfile=MainActivity.instance.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/listener.log";
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(logfile,true));
			pw.println(str);      // 换行
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void loadLog()
	{
		List<String> datas=new ArrayList<String>();
		String logfile=MainActivity.instance.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/listener.log";
		try {
			BufferedReader br=new BufferedReader(new FileReader(logfile));
			String line;
			while ((line = br.readLine()) != null) {
				datas.add(line);
				if(datas.size()>1000)
					datas.remove(0);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new File(logfile).delete();
		for(int i=0;i<datas.size();i++)
		{
			Log.T2(datas.get(i));
		}
	}
	public static void Clear()
	{
		MainActivity.instance.handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(MainActivity.instance.tv!=null)
					MainActivity.instance.tv.setText("");
			}
		});
	}
}
