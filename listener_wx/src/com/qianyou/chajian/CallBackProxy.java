package com.qianyou.chajian;

import android.content.Context;
import android.content.Intent;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import de.robv.android.xposed.XposedBridge;

public class CallBackProxy implements InvocationHandler {
  private String amount;
  
  private Context context;
  
  private String orderid;
  
  public CallBackProxy(Context paramContext, String paramString, long paramLong) {
    this.context = paramContext;
    this.orderid = paramString;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramLong);
    stringBuilder.append("");
    this.amount = stringBuilder.toString();
  }
  
  @SuppressWarnings("deprecation")
public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
	  XposedBridge.log("啊啊啊="+paramArrayOfObject);
	  XposedBridge.log("铃铛invoke CallBackProxy method: "+paramMethod.getName());
    if (paramArrayOfObject != null) {
      XposedBridge.log("铃铛CallBackProxy method params = "+Arrays.toString(paramArrayOfObject));
      int i;
      for (i = 0; i < paramArrayOfObject.length; i++) {
        paramObject = paramArrayOfObject[i];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("铃铛index:");
        stringBuilder.append(i);
        stringBuilder.append("=====");
        stringBuilder.append((new Gson()).toJson(paramObject));
        XposedBridge.log(stringBuilder.toString());
      } 
    } 
    if (paramMethod.getName().contains("onError")) {
      if (!"0".equals(this.amount)) {
//    	JSONObject jo=new JSONObject();
//    	jo.put("action", 120);
//        jo.put("money", this.amount);
//        jo.put("mark", this.orderid);
//        jo.put("type", "wangxin");
//        jo.put("msg", Arrays.toString(paramArrayOfObject));
//        jo.put("actiontype","com.qianyou.wangxin.qrcodereceived.fail");
        Intent intent = new Intent();
        intent.putExtra("money", this.amount);
        intent.putExtra("mark", this.orderid);
        intent.putExtra("type", "wangxin");
        intent.putExtra("msg", Arrays.toString(paramArrayOfObject));
        intent.setAction("com.qianyou.wangxin.qrcodereceived.fail");
        this.context.sendBroadcast(intent);
      } 
    } else if (paramMethod.getName().contains("onSuccess")) {
//      paramObject = paramArrayOfObject[0];
//      XposedBridge.log("啊啊啊啊=="+paramArrayOfObject[0]);
//      paramObject = (new Gson()).toJson(paramObject);
//      XposedBridge.log("成功获取消息===="+paramObject);
//      StringBuilder stringBuilder1 = new StringBuilder();
//      stringBuilder1.append(" onSuccess ");
//      stringBuilder1.append((String)paramObject);
//      XposedBridge.log(stringBuilder1.toString());
//      paramObject = (new JSONArray((String)paramObject)).get(0);
//      JSONObject jSONObject = paramObject.getJSONObject("alipayParam");
//      paramObject = paramObject.optString("hongbaoId");
//      String str = jSONObject.optString("url");
//      StringBuilder stringBuilder2 = new StringBuilder();
//      stringBuilder2.append("url =");
//      stringBuilder2.append(str);
//      stringBuilder2.append(" hongbaoId =");
//      stringBuilder2.append(paramObject.getString("hongbaoId"));
    	paramObject = paramArrayOfObject[0];
    	XposedBridge.log("啊啊啊=="+paramObject);
        paramObject = (new Gson()).toJson(paramObject);
        //XposedBridge.log("aaaaa="+paramObject);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(" onSuccess ");
        stringBuilder1.append((String)paramObject);
        //XposedBridge.log(stringBuilder1.toString());
        JSONObject paramObject1 = (JSONObject) (new JSONArray((String)paramObject)).get(0);
        XposedBridge.log("唉唉唉="+paramObject1);
        JSONObject jSONObject = paramObject1.getJSONObject("alipayParam");
        paramObject = paramObject1.optString("hongbaoId");
        //String templateData = paramObject1.optString("templateData");
        //String urlString=AliPayHook.getTextCenter(templateData, "https:", "%3D0");
        //XposedBridge.log(urlString);
        //XposedBridge.log(templateData);
        String str = jSONObject.optString("url");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("url =");
        stringBuilder2.append(str);
        stringBuilder2.append(" hongbaoId =");
        stringBuilder2.append((String)paramObject);
        //XposedBridge.log(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("啊啊URLDecoder :");
        stringBuilder2.append(URLDecoder.decode(str));
        XposedBridge.log(stringBuilder2.toString());
        Intent intent = new Intent();
        intent.putExtra("money", this.amount);
        intent.putExtra("mark", this.orderid);
        intent.putExtra("orderNo", (String)paramObject);
        intent.putExtra("type", "wangxin");
        intent.putExtra("payurl", str);
        //intent.putExtra("url", urlString);
        intent.setAction("com.qianyou.wangxin.qrcodereceived");
        this.context.sendBroadcast(intent);
    } 
    if (paramMethod.getName().contains("toString")) {
      paramObject = new StringBuilder();
      ((StringBuilder) paramObject).append(getClass());
      ((StringBuilder) paramObject).append("@12549");
      return paramObject.toString();
    } 
    return null;
  }
}

