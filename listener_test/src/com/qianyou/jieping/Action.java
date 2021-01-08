package com.qianyou.jieping;

import java.text.SimpleDateFormat;
import java.util.Date;

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
			String url=jobject.getString("qrcurl");
			if (jobject.has("click")) {
				MainActivity.isClickBoolean = jobject.getBoolean("click");
			}
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
            	JSONObject jo=new JSONObject();
				jo.put("action",105);
				jo.put("ret", "INV");
				jo.put("alipayerror", "");
				jo.put("money", "");
				Utils.sendToMain(jo.toString(),false);
			} else {
				Capture.capture.startListen(url,new CallBack() {
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
								jo.put("money", money);
							}
							else
								jo.put("money", "");
							if(title!=null)
							{
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
	}

}
