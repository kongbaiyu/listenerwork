package com.qianyou.jieping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import com.qianyou.chajian.AliPayHook;
import com.qianyou.jieping.Capture.CallBack;
import com.qianyou.listener5.Log;
import com.qianyou.listener5.MainActivity;

@SuppressLint("SimpleDateFormat") public class Action {
	public static void doAct(int act,JSONObject jobject) throws JSONException
	{
		if(act==Capture.CAP)
		{
			//Log.T("jobject="+jobject.toString());
			String url=jobject.getString("qrcurl");
			if (jobject.has("click")) {
				MainActivity.isClickBoolean = jobject.getBoolean("click");
			}
			if (jobject.has("checktype")) {
				MainActivity.CHECKTYPE = jobject.getInt("checktype");
				if (MainActivity.CHECKTYPE==1) {
					MainActivity.isClickBoolean=true;
				}else {
					MainActivity.isClickBoolean=false;
				}
			}
			//Log.T("isclick="+MainActivity.isClickBoolean);
			Date date = new Date(System.currentTimeMillis());
            String str = (new SimpleDateFormat("yyyyMMdd")).format(date);
            int today = Integer.parseInt(str);
			String timeString = str;
			if (url.equals("")) {
				Log.T("url为空");
			}else {
				int a = url.indexOf("http");
				url = url.substring(a);
				timeString = AliPayHook.getTextCenter(url, "&v=", "&sign");
				//Log.T("url="+url);
			}
            if (today>Integer.parseInt(timeString)) {
            	try {
            		JSONObject jo=new JSONObject();
    				jo.put("action",105);
    				jo.put("ret", "INV");
    				jo.put("alipayerror", "");
    				jo.put("title", "");
    				jo.put("money", "");
    				Utils.sendToMain(jo.toString(),false);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				Capture.capture.startListen(url,new CallBack() {
					@Override
					public void onResult(int ret,String money,String title) {
						// TODO Auto-generated method stub
						//Log.T("ret="+ret);
						JSONObject jo=new JSONObject();
						try {
							jo.put("action",105);
							if(ret==Capture.TRUE)
							{
								jo.put("ret", "OK");
							}
							else if(ret==Capture.FALSE)
							{
								jo.put("ret", "FAIL");
							}
							else if(ret==Capture.CANCEL)
							{
								jo.put("ret", "INV");
							}
							else if(ret==Capture.RM)
							{
								jo.put("ret", "RM");
							}
							else if(ret==Capture.OVER)
							{
								jo.put("ret", "OVER");
							}
							else if(ret==Capture.SNCH)
							{
								jo.put("ret", "SNCH");
							}
							else
							{
								jo.put("ret", "NA");
							}
							if (ret==Capture.ERRORALIPAY) {
								jo.put("alipayerror", "ERRORALIPAY");
							}
							else {
								jo.put("alipayerror", "");
							}
							if(money!=null)
							{
								money = money.replaceAll(",", ".");
								jo.put("money", money);
							}
							else
								jo.put("money", "");
							if(title!=null)
							{
								Pattern pattern = Pattern.compile("(\\s)");
								Matcher matcher = pattern.matcher(title);
								title = matcher.replaceAll("");
								title = title.replaceAll( "[-:,：，——.。@#$%^&*！~`!?？【】/[/]/(/)、（）｛｝{}_$`^=|《》；“”‘’、<>～｀＄＾＋＝｜＜＞￥×]" , "");
								jo.put("title", title);
							}
							else
								jo.put("title", "");
							Utils.sendToMain(jo.toString(),false);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}
		if(act==Capture.CAP1)
		{
			String url=jobject.getString("qrcurl");
			Capture.capture.startListenyz(url,new CallBack() {
				@Override
				public void onResult(int ret,String money,String title) {
					// TODO Auto-generated method stub
					JSONObject jo=new JSONObject();
					try {
						jo.put("action",116);
						if(ret==Capture.TRUE)
						{
							jo.put("ret", "OK");
						}
						else if(ret==Capture.FALSE)
						{
							jo.put("ret", "FAIL");
						}
						else if(ret==Capture.CANCEL)
						{
							jo.put("ret", "INV");
						}
						else
						{
							jo.put("ret", "NA");
						}
						if (ret==Capture.ERRORALIPAY) {
							jo.put("alipayerror", "ERRORALIPAY");
						}
						else {
							jo.put("alipayerror", "");
						}
						
						jo.put("money", "");
						jo.put("title", "");
						Utils.sendToMain(jo.toString(),false);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
		//支付宝个码
		if(act==Capture.CAP2)
		{
			String url=jobject.getString("url");
			Capture.capture.startListenGM(url,new CallBack() {
				@Override
				public void onResult(int ret,String money,String title) {
					// TODO Auto-generated method stub
					JSONObject jo=new JSONObject();
					try {
						jo.put("action",121);
						if(ret==Capture.TRUE)
						{
							jo.put("ret", "OK");
						}
						else if(ret==Capture.FALSE)
						{
							jo.put("ret", "FAIL");
						}
						else if(ret==Capture.CANCEL)
						{
							jo.put("ret", "INV");
						}
						else
						{
							jo.put("ret", "NA");
						}
						if (ret==Capture.ERRORALIPAY) {
							jo.put("alipayerror", "ERRORALIPAY");
						}
						else {
							jo.put("alipayerror", "");
						}
						
						jo.put("money", "");
						jo.put("title", "");
						Utils.sendToMain(jo.toString(),false);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		//吱口令
		if(act==Capture.CAP3)
		{
			Log.T("ACTIONS="+jobject.toString());
			String url=jobject.getString("qrcurl");
			Capture.capture.startListenZKL(url,new CallBack() {
				@Override
				public void onResult(int ret,String money,String title) {
					// TODO Auto-generated method stub
					JSONObject jo=new JSONObject();
					try {
						jo.put("action",105);
						if(ret==Capture.TRUE)
						{
							jo.put("ret", "OK");
						}
						else if(ret==Capture.FALSE)
						{
							jo.put("ret", "FAIL");
						}
						else if(ret==Capture.CANCEL)
						{
							jo.put("ret", "INV");
						}
						else if(ret==Capture.RM)
						{
							jo.put("ret", "RM");
						}
						else
						{
							jo.put("ret", "NA");
						}
						if (ret==Capture.ERRORALIPAY) {
							jo.put("alipayerror", "ERRORALIPAY");
						}
						else {
							jo.put("alipayerror", "");
						}
						if(money!=null)
						{
							money = money.replaceAll(",", ".");
							jo.put("money", money);
						}
						else
							jo.put("money", "");
						if(title!=null)
						{
							Pattern pattern = Pattern.compile("(\\s)");
							Matcher matcher = pattern.matcher(title);
							title = matcher.replaceAll("");
							title = title.replaceAll( "[-:,：，——.。！~`!?？【】/[/]/(/)、（）｛｝{}_$`^=|《》；“”‘’、<>～｀＄＾＋＝｜＜＞￥×]" , "");
							jo.put("title", title);
						}
						else
							jo.put("title", "");
						
						Log.T("结果="+jo.toString());
						//Utils.sendToMain(jo.toString(),false);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
	}

}
