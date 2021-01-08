package com.qianyou.listener5;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;

public class SLog {
	public static void T(final String str)
	{
		JSONObject jo=new JSONObject();
		try {
			jo.put("action", MainActivity.LOG_T);
			jo.put("text",str);
			NLService.sendToMain(jo.toString(),false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void Clear()
	{
		JSONObject jo=new JSONObject();
		try {
			jo.put("action", MainActivity.LOG_CLEAR);
			NLService.sendToMain(jo.toString(),false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
