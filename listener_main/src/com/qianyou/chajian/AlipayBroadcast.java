package com.qianyou.chajian;


import android.R.integer;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

/**
 * Created by dell on 2018/4/4.
 */

public class AlipayBroadcast extends BroadcastReceiver{
    public static String CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION = "com.hhly.pay.alipay.info.consultSetAmountResString";

    public static String CONSULT_GET_AMOUNT = "com.qianyou.alipay.payActivity";
    
    public static String COOKIE_STR_INTENT_FILTER_ACTION = "com.hhly.pay.alipay.info.cookieStr";
    
    private String qrnumString;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().contentEquals(CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION)) {
            String qr_money = intent.getStringExtra("qr_money");
            String beiZhu = intent.getStringExtra("beiZhu");
            String num = intent.getStringExtra("num");
            //log("AlipayBroadcast onReceive " + qr_money + " " + beiZhu + "\n");
            //log("num="+num+";qrnum="+qrnumString+";qq=");
            if (!qr_money.contentEquals("")) {
            	qrnumString = num;
            	//log("aaaaaa="+qrnumString);
                Intent launcherIntent = new Intent(context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", AliPayHook.launcherActivity.getApplicationContext().getClassLoader()));
                launcherIntent.putExtra("qr_money", qr_money);
                launcherIntent.putExtra("beiZhu", beiZhu);
                AliPayHook.launcherActivity.startActivity(launcherIntent);
            }
        }
        if (intent.getAction().contentEquals(CONSULT_GET_AMOUNT)) {
            log("AlipayBroadcast onReceive"+CONSULT_GET_AMOUNT);
            //if (!qr_money.contentEquals("")) {
                Intent launcherIntent = new Intent(context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRNewActivity", AliPayHook.launcherActivity.getApplicationContext().getClassLoader()));
                
                AliPayHook.launcherActivity.startActivity(launcherIntent);
            //}
        }
    }
}

