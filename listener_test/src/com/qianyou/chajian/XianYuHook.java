package com.qianyou.chajian;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import android.content.Context;
import android.content.Intent;

public class XianYuHook {
	
	public static  ClassLoader xyclassLoader;
	  
	public static Context xycontext;
	
	public XianYuHook(final Context context ,final ClassLoader classLoader){
		xyclassLoader = classLoader;
		xycontext = context;
		XposedBridge.hookAllMethods(XposedHelpers.findClass("com.taobao.fleamarket.business.transferMoney.view.TransferMoneyView", classLoader), "setPayInfo", new XC_MethodHook() {
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				super.afterHookedMethod(param);
                XposedBridge.log("--------setPayInfo--------------");
                XposedBridge.log("setPayInfo："+param.args[0]);
                Object o = param.args[0];
                String sessionid = (String)XposedHelpers.getObjectField(o,"sessionId");
                String businessId = (String)XposedHelpers.getObjectField(o,"businessId");
                String payeeNick = (String)XposedHelpers.getObjectField(o,"payeeNick");
                XposedBridge.log("sessionid =="+sessionid);
                XposedBridge.log("businessId =="+businessId);
                XposedBridge.log("payeeNick =="+payeeNick);
                Intent intent = new Intent();
              	intent.putExtra("sessionid", sessionid);
              	intent.putExtra("businessId", businessId);
              	intent.putExtra("payeeNick", payeeNick);
              	intent.setAction("com.qianyou.xianyu.TransferMoneyModel");
              	context.sendBroadcast(intent);
            }
        });
	}
}
