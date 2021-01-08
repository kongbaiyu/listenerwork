package com.qianyou.chajian;

import android.content.Context;
import android.content.Intent;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import de.robv.android.xposed.XposedBridge;

public class GetHongBaoCallBackProxy implements InvocationHandler {
  private Context context;
  
  private String hongbaoid;
  
  private String orderid;
  
  public GetHongBaoCallBackProxy(Context paramContext, String paramString1, String paramString2) {
    this.context = paramContext;
    this.orderid = paramString1;
    this.hongbaoid = paramString2;
  }
  
  public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    paramObject = new StringBuilder();
    ((StringBuilder) paramObject).append("拆包接口回调----invoke GetHongBaoCallBackProxy method: ");
    ((StringBuilder) paramObject).append(paramMethod.getName());
    XposedBridge.log(paramObject.toString());
    XposedBridge.log("paramArray="+paramArrayOfObject);
    if (paramArrayOfObject != null) {
      paramObject = new StringBuilder();
      ((StringBuilder) paramObject).append("拆包接口回调----GetHongBaoCallBackProxy method params = ");
      ((StringBuilder) paramObject).append(Arrays.toString(paramArrayOfObject));
      XposedBridge.log(paramObject.toString());
    } 
    try {
      if (paramMethod.getName().contains("onSuccess")) {
        paramObject = (new Gson()).toJson(paramArrayOfObject);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("拆包接口返回数据---");
        stringBuilder2.append((String)paramObject);
        XposedBridge.log(stringBuilder2.toString());
        paramObject = paramArrayOfObject[0];
        paramObject = (new Gson()).toJson(paramObject);
        XposedBridge.log("啊啊啊啊啊="+(new Gson()).toJson(paramObject));
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("红包内容---");
        stringBuilder1.append((String)paramObject);
        XposedBridge.log(stringBuilder1.toString());
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("支付回传--红包id：");
        stringBuilder1.append(this.hongbaoid);
        XposedBridge.log(stringBuilder1.toString());
        paramObject = (new JSONArray((String)paramObject)).get(0);
        int i = ((JSONObject) paramObject).optInt("status");
        double d = ((JSONObject) paramObject).optDouble("amount");
        paramObject = fen2yuan((int)d);
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(" onSuccess ");
        stringBuilder1.append(i);
        stringBuilder1.append("----");
        stringBuilder1.append(d);
        XposedBridge.log(stringBuilder1.toString());
        if (i == 2) {
          Intent intent = new Intent();
          intent.putExtra("bill_no", this.hongbaoid);
          intent.putExtra("bill_money", (String)paramObject);
          intent.putExtra("bill_mark", this.orderid);
          intent.putExtra("bill_type", "wangxin");
          intent.setAction("com.tools.payhelper.billreceived");
          this.context.sendBroadcast(intent);
          //MobileImHook.dataMap.remove(this.hongbaoid);
        } 
      } 
    } catch (Exception exception) {
      XposedBridge.log(exception.getMessage());
      exception.printStackTrace();
    } 
    if (paramMethod.getName().contains("toString")) {
      paramObject = new StringBuilder();
      ((StringBuilder) paramObject).append(getClass());
      ((StringBuilder) paramObject).append("@12649");
      return paramObject.toString();
    } 
    return null;
  }
  public static String fen2yuan(int paramInt) {
	    return BigDecimal.valueOf(Long.valueOf(paramInt).longValue()).divide(new BigDecimal(100)).toString();
	  }
}
