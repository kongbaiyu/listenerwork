package com.qianyou.jieping;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;

import com.qianyou.jieping.Capture.CallBack;
import com.qianyou.listener5.MainActivity;

public class Action {
	public static void doAct(int act,JSONObject jobject) throws JSONException
	{
		if(act==Capture.CAP)
		{
			String url=jobject.getString("qrcurl");
			if (jobject.has("click")) {
				MainActivity.instance.isClickBoolean = jobject.getBoolean("click");
			}
			Capture.capture.startListen(url,new CallBack() {
				@Override
				public void onResult(int ret,String money) {
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
