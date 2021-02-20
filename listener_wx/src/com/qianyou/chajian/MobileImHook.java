package com.qianyou.chajian;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import com.qianyou.listener5.Log;
import com.qianyou.listener5.MainActivity;
import com.qianyou.wangxin.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import java.net.URLDecoder;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class MobileImHook {
	  public static String account;
	  
	  public static String appkey = "12621186";
	  
	  public static String cMark;
	  
	  public static String cMoney;
	  
	  public static String cOrderNo;
	  
	  public static String cPayUrl;
	  
	  public static String orderid;
	  
	  public static boolean canRob;
	  
	  public static  ClassLoader wxclassLoader;
	  
	  public static Context wxcontext;
	  
	  public static String currentQunId;
	  
	  public static Map<String, String> dataMap = new HashMap<String, String>();
	  
	  public static Activity mActivity;
	  
	  public static String qunId;
	  
	  public MyReceiver myReceiver;
	  
	  static {
	    canRob = false;
	  }
	  
	  public MobileImHook(final Context context, final ClassLoader classLoader) {
		  wxclassLoader = classLoader;
		  wxcontext = context;
	    XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alibaba.mobileim.YWAPI", classLoader), "createIMCore", new XC_MethodHook() {
	          protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
	            super.beforeHookedMethod(param1MethodHookParam);
	            if (TextUtils.isEmpty(MobileImHook.account)) {
	              MobileImHook.account = (String)param1MethodHookParam.args[0];
	              StringBuilder stringBuilder = new StringBuilder();
	              stringBuilder.append("当前账户：");
	              stringBuilder.append(MobileImHook.account);
	              XposedBridge.log(stringBuilder.toString());
	              Intent intent = new Intent();
	              intent.putExtra("currentAc", MobileImHook.account);
	              intent.setAction("com.qianyou.wangxin.current.account");
	              context.sendBroadcast(intent);
	            } 
	          }
	        });
	    XposedHelpers.findAndHookMethod("com.alibaba.mobileim.lib.presenter.message.MessageList", classLoader, "pushMessage", new Object[] { String.class, List.class, new XC_MethodHook() {
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(" 收到消息 ");
              stringBuilder.append(param1MethodHookParam.args[0]);
              //XposedBridge.log(stringBuilder.toString());
              //Toast.makeText(context,"收到红包消息",0).show();
              List list = (List)param1MethodHookParam.args[1];
              //XposedBridge.log("数组=="+list);
              for(int i=0;i<list.size();i++){
            	  Object o = list.get(i);
            	  //XposedBridge.log("object"+i+"=="+o);
            	  String str = (String)XposedHelpers.callMethod(o, "toString", new Object[0]);
            	  //XposedBridge.log("atrr=="+str);
            	  str = str.replace("MessageItem","");
            	  //XposedBridge.log("aaaaaatrr=="+str);
            	  JSONObject jobject = new JSONObject(str);
            	  //XposedBridge.log("转为String类型=="+str);
            	  //XposedBridge.log("JSON数据="+jobject);
            	  //XposedBridge.log("JSON数据="+jobject.getString("msgType"));
                if (jobject.getString("msgType").equals("211")) {
                  str = jobject.getString("content");
                  StringBuilder stringBuilder2 = new StringBuilder();
                  stringBuilder2.append("红包消息体：");
                  stringBuilder2.append(str);
                  //XposedBridge.log(stringBuilder2.toString());
                  str = (new JSONObject(str)).optJSONObject("template").optJSONObject("data").optJSONObject("body").optJSONArray("ac").getString(0);
                  stringBuilder2 = new StringBuilder();
                  stringBuilder2.append(" 红包连接 ");
                  stringBuilder2.append(str);
                  //XposedBridge.log(stringBuilder2.toString());
                  String str4 = URLDecoder.decode(str);
                  StringBuilder stringBuilder1 = new StringBuilder();
                  stringBuilder1.append(" 红包连接 decode");
                  stringBuilder1.append(str4);
                  //XposedBridge.log(stringBuilder1.toString());
                  Uri uri = Uri.parse(str4);
                  String str3 = uri.getQueryParameter("ActionExtraParam");
                  String str1 = uri.getQueryParameter("sender");
                  String str2 = uri.getQueryParameter("note");
                  str3 = Uri.parse(str3.replace("wangwang", "http")).getQueryParameter("hongbaoId");
                  StringBuilder stringBuilder4 = new StringBuilder();
                  stringBuilder4.append("获取红包发送人：");
                  stringBuilder4.append(str1);
                  stringBuilder4.append("备注：");
                  stringBuilder4.append(str2);
                  //XposedBridge.log(stringBuilder4.toString());
                  if (str1 == null) {
                    str1 = str4.substring(str4.indexOf("sender=") + "sender=".length(), str4.length());
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append("s = ");
                    stringBuilder5.append(str1);
                    //XposedBridge.log(stringBuilder5.toString());
                    str1 = str1.split("&")[0];
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append("获取红包发送人null：");
                    stringBuilder5.append(str1);
                    //XposedBridge.log(stringBuilder5.toString());
                  } 
                  StringBuilder stringBuilder3 = new StringBuilder();
                  stringBuilder3.append(" 红包信息 =");
                  stringBuilder3.append(str3);
                  stringBuilder3.append(" sender=");
                  stringBuilder3.append(str1);
                  //XposedBridge.log(stringBuilder3.toString());
                  //XposedBridge.log("少时诵诗书context="+context+"; str1="+str1+"; str3="+str3+"; str2="+str2);
                  //if (MobileImHook.canRob)
                  MobileImHook.tryGetHongbao(context, str1, str3, str2); 
                } 
              } 
            }
          } });
	    XposedHelpers.findAndHookMethod(Activity.class, "onCreate", new Object[] { Bundle.class, new XC_MethodHook() {
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
              Intent intent;
              IntentFilter intentFilter;
              super.afterHookedMethod(param1MethodHookParam);
              String str = param1MethodHookParam.thisObject.toString();
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(" obj ");
              stringBuilder.append(str);
              XposedBridge.log(stringBuilder.toString());
              if (str.contains("MainTabActivity")) {
                if (MobileImHook.mActivity == null)
                  MobileImHook.mActivity = (Activity)param1MethodHookParam.thisObject; 
//                 String str1 = (String)XposedHelpers.callMethod(XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.gingko.WangXinApi", classLoader), "getInstance", new Object[0]), "getAccount", new Object[0]), "getAccount", new Object[0]);
//                 StringBuilder stringBuilder1 = new StringBuilder();
//                 stringBuilder1.append(" 账号 ");
//                 stringBuilder1.append(str1);
//                 stringBuilder1.append("---");
//                 XposedBridge.log(stringBuilder1.toString());
            	  if (MobileImHook.this.myReceiver == null) {
                      XposedBridge.log("注册hook广播");
                      MobileImHook.this.myReceiver = new MobileImHook.MyReceiver();
                      intentFilter = new IntentFilter();
                      intentFilter.addAction("com.qianyou.wangxin.wangxin");
                      intentFilter.addAction("com.qianyou.wangxin.rob");
                      intentFilter.addAction("com.qianyou.wangxin.check.wx");
                      intentFilter.addAction("com.qianyou.wangxin.to.h5");
                      ((Activity)param1MethodHookParam.thisObject).registerReceiver(MobileImHook.this.myReceiver, intentFilter);
                      intent = new Intent();
                      intent.setAction("com.qianyou.wangxin.check.can.rob");
                      context.sendBroadcast(intent);
                      return;
                    } 
              } else if (str.contains("WxChattingActvity")) {
                String str1;
                String str2=((Activity)((XC_MethodHook.MethodHookParam)param1MethodHookParam).thisObject).getIntent().getStringExtra("conversationId").replace("tribe", "");
                MobileImHook.qunId = str2;
                XposedBridge.log("MobileImHook.qunId==="+str2);
//                try {
//             	JSONObject jo=new JSONObject();
//      	  		 jo.put("action", 114);
//          	  	 jo.put("msg",str2);
//          	  	 Utils.sendToMain(jo.toString(),false);
//	                XposedBridge.log("Moaaaaa1"+jo.toString());
//				} catch (Exception e) {
//					Toast.makeText(context, "报错", 0).show();
//	                XposedBridge.log("MobileImHook.qunId11111111111111"+MobileImHook.qunId);
//				}
                //Context context = context.getApplicationContext();
                if (MobileImHook.canRob) {
                  str1 = "开启抢包";
                } else {
                  str1 = "未开启抢包";
                } 
                //Toast.makeText(context, str1, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent1.putExtra("conId", str2);
                intent1.setAction("com.qianyou.wangxin.current.conid");
                context.sendBroadcast(intent1);
              } 
            }
          } });
	    XposedBridge.hookAllConstructors(XposedHelpers.findClass("com.alipay.sdk.e.b", classLoader), new XC_MethodHook() {
	          protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
	            try {
	            	XposedBridge.log("commmmmmmmmmmmmmmmm="+param1MethodHookParam.args.length);
	            	XposedBridge.log(param1MethodHookParam.toString());
	              if (param1MethodHookParam.args != null && param1MethodHookParam.args.length > 0)
	                for (int i = 0;i<param1MethodHookParam.args.length; i++) {
	                  if (i < param1MethodHookParam.args.length) {
	                	  //Toast.makeText(context, "开始转换", Toast.LENGTH_SHORT).show();
	                    String str = (String)param1MethodHookParam.args[i];
	                    StringBuilder stringBuilder = new StringBuilder();
	                    stringBuilder.append("转换后");
	                    stringBuilder.append(i);
	                    stringBuilder.append("====respJson=");
	                    stringBuilder.append(str);
	                    XposedBridge.log(stringBuilder.toString());
	                    if (!TextUtils.isEmpty(str)) {
	                      JSONObject jSONObject1 = new JSONObject(str);
	                      String str2 = jSONObject1.optString("action");
	                      String str1 = jSONObject1.optString("external_info");
	                      String str3 = jSONObject1.optString("biztype");
	                      JSONObject jSONObject2 = jSONObject1.optJSONObject("form");
	                      if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1)) {
	                        XposedBridge.log("先拦截参数--------");
	                        str2 = Util.getRandomCode(64, 3);
	                        str3 = Util.getRandomCode(24, 6);
	                        str1 = (String)Util.StrToMap(str1, "&").get("out_order_no");
	                        jSONObject1.put("tid", str2);
	                        jSONObject1.put("utdid", str3);
	                        jSONObject1.put("new_client_key", Util.getRandomCode(10, 3));
	                        param1MethodHookParam.args[i] = jSONObject1.toString();
	                      } else if (!TextUtils.isEmpty(str3) && jSONObject2 != null) {
	                        String str4 = AliPayHook.getTextCenter(jSONObject2.toString(), "wappay('", "')\"").replace("\\", "");
	                        StringBuilder stringBuilder1 = new StringBuilder();
	                        stringBuilder1.append("转换完成");
	                        stringBuilder1.append(str4);
	                        stringBuilder1.append("\n");
	                        stringBuilder1.append(MobileImHook.cMark);
	                        stringBuilder1.append(MobileImHook.cOrderNo);
	                        XposedBridge.log(stringBuilder1.toString());
	                        //Toast.makeText(context, "转换完成", Toast.LENGTH_SHORT).show();
	                        Intent intent = new Intent();
	                        intent.putExtra("money", MobileImHook.cMoney);
	                        intent.putExtra("mark", MobileImHook.cMark);
	                        intent.putExtra("orderNo", MobileImHook.cOrderNo);
	                        intent.putExtra("payurl", MobileImHook.cPayUrl);
	                        intent.putExtra("qunId", MobileImHook.currentQunId);
	                        intent.putExtra("orderid", MobileImHook.orderid);
	                        intent.putExtra("h5Url", str4);
	                        intent.setAction("com.qianyou.wangxin.to.h5.back");
	                        context.sendBroadcast(intent);
	                      } 
	                    } 
	                    //continue;
	                  } 
	                  XposedBridge.log("结束");
	                  super.beforeHookedMethod(param1MethodHookParam);
	                }  
	              super.beforeHookedMethod(param1MethodHookParam);
	            } catch (Exception exception) {
	              exception.printStackTrace();
	            } 
	            super.beforeHookedMethod(param1MethodHookParam);
	          }
	          
//	          protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//	        	  XposedBridge.log("q轻轻巧巧QQ去");
//	        	  for (int j = 0; j < param1MethodHookParam.args.length; j++) {
//					XposedBridge.log("after"+j+"="+param1MethodHookParam.args[j]);
//				}
//	        	  super.afterHookedMethod(param1MethodHookParam);
//	          }
	        });
	    XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.sdk.app.H5PayActivity", classLoader), "onCreate", new XC_MethodHook() {
	          protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
	            ((Activity)param1MethodHookParam.thisObject).finish();
	            super.beforeHookedMethod(param1MethodHookParam);
	          }
	        });
	  }
	  
	  
	  
	  public static void createQunHonginfo(Context paramContext,final long money, final String qunid, final String title) {
		  	XposedBridge.log(""+paramContext);
		    XposedBridge.log("aaaaaaaaaaaa"+money+"------"+qunid+"----"+title+"------"+wxclassLoader+"-----"+account+"-----"+appkey);
		    Object object = (Object)XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.YWAPI", wxclassLoader), "getIMKitInstance", new Object[] { account, appkey });
		   //XposedBridge.log("ccccccccc"+object);
		    StringBuilder stringBuilder2 = new StringBuilder();
		    stringBuilder2.append("mIMKit ");
		    stringBuilder2.append(object);
		    //XposedBridge.log(stringBuilder2.toString());
		    final Object WxAccount = XposedHelpers.callMethod(XposedHelpers.callMethod(object, "getIMCore", new Object[0]), "getWxAccount", new Object[0]);
		    Object object2 = WxAccount;
		    //XposedBridge.log("WxAccount=="+WxAccount);
		    final Object HongbaoManager = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.lib.presenter.hongbao.HongbaoManager", wxclassLoader), "getInstance", new Object[0]);
		    Object object3 = HongbaoManager;
		    //XposedBridge.log("object3=="+object3);
		    final String createHongbaoId = (String)XposedHelpers.callMethod(object3, "createHongbaoId", new Object[] { object2 });
		    object = (Object)new StringBuilder();
		    String str = createHongbaoId;
		    //XposedBridge.log("红包id=="+str);
		    final long uuid = ((Long)XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.channel.util.WXUtil", wxclassLoader), "getUUID", new Object[0])).longValue();
		    object = (Object)new StringBuilder();
//		    object.append("uuid ");
//		    object.append(l);
		    //XposedBridge.log("uuid==="+uuid);
		    try {
		      object = (Object)wxclassLoader.loadClass("com.alibaba.mobileim.channel.event.IWxCallback");
		    } catch (ClassNotFoundException classNotFoundException) {
		      XposedBridge.log(classNotFoundException);
		      classNotFoundException = null;
		    } 
		    CallBackProxy callBackProxy = new CallBackProxy(paramContext,title, money);
		    final Object param9 = Proxy.newProxyInstance(wxclassLoader, new Class[] { XposedHelpers.findClass("com.alibaba.mobileim.channel.event.IWxCallback", wxclassLoader) }, (InvocationHandler)callBackProxy);
		    StringBuilder stringBuilder1 = new StringBuilder();
		    Object object1 = param9;
		    //XposedBridge.log("object1==="+object1);
		    stringBuilder1 = new StringBuilder();
		    stringBuilder1.append("创建红包完毕...");
		    stringBuilder1.append(str);
		    stringBuilder1.append("-----");
		    stringBuilder1.append(title);
		    XposedBridge.log(stringBuilder1.toString());
		    stringBuilder1 = new StringBuilder();
		    stringBuilder1.append("创建回传--红包id：");
		    stringBuilder1.append(str);
		    XposedBridge.log(stringBuilder1.toString());
		    //dataMap.put(str, title);
		    if (object1 != null) {
		    	(new Thread(new Runnable() {
		            public void run() {
		            	//XposedBridge.log("================================createhongbao");
		            	XposedBridge.log(WxAccount+"---crestehongbaoid="+createHongbaoId+"---money="+Long.valueOf(money)+";	int="+Integer.valueOf(1)+";--title="+title+";  qunid="+qunid+";  uuid="+Long.valueOf(uuid)+";  param9="+param9);
		            	XposedHelpers.callMethod(HongbaoManager, "createHongbao", new Object[] { WxAccount, createHongbaoId, Long.valueOf(money), Integer.valueOf(1), Integer.valueOf(1), title, qunid, Long.valueOf(uuid), param9 });
		            }
		          })).start();
		    	return;
		    } 
		    XposedBridge.log("创建红包失败");
		  }
	  
	  public static void getWangXinH5(final String payUrl, final String qunId) {
		    try {
		    	XposedBridge.log("链接："+payUrl);
		      final Object localObject2 = XposedHelpers.callMethod(XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.YWAPI", wxclassLoader), "getIMKitInstance", new Object[] { account, appkey }), "getIMCore", new Object[0]), "getWxAccount", new Object[0]);
		      final Object localObject3 = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.lib.presenter.hongbao.HongbaoManager", wxclassLoader), "getInstance", new Object[0]);
		      final String hongbaoId = (String)XposedHelpers.callMethod(localObject3, "createHongbaoId", new Object[] { localObject2 });
		      XposedBridge.log("l2="+localObject2+";lo3="+localObject3+";id="+hongbaoId);
		      final long l = ((Long)XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.channel.util.WXUtil", wxclassLoader), "getUUID", new Object[0])).longValue();
		      XposedBridge.log("ll="+l);
		      Class<?> clazz = wxclassLoader.loadClass("com.alibaba.mobileim.channel.event.IWxCallback");
		      XposedBridge.log("clazz="+clazz);
		      final CallBackProxy o_d = new CallBackProxy(wxcontext, "", 0L);
		      XposedBridge.log("oooo="+o_d);
		      final Object callBackProxy = XposedHelpers.findClass("com.alipay.sdk.app.PayTask", wxclassLoader).getConstructor(new Class[] { Activity.class }).newInstance(new Object[] { mActivity });
		      XposedBridge.log("call="+callBackProxy);
		      final Object mobj = Proxy.newProxyInstance(wxclassLoader, new Class[] { clazz }, (InvocationHandler)o_d);
		      XposedBridge.log("mobj="+mobj);
		      if (mobj != null)
		        (new Thread(new Runnable() {
		              public void run() {
		                XposedHelpers.callMethod(localObject3, "createHongbao", new Object[] { localObject2, hongbaoId, Long.valueOf("0"), Integer.valueOf(1), Integer.valueOf(1), "123", qunId, Long.valueOf(l), mobj, Integer.valueOf(0) });
		                XposedHelpers.callMethod(callBackProxy, "a", new Object[] { payUrl });
		              }
		            })).start(); 
		    } catch (IllegalAccessException illegalAccessException) {
		      illegalAccessException.printStackTrace();
		      XposedBridge.log("报错iiii");
		    } catch (NoSuchMethodException noSuchMethodException) {
		      noSuchMethodException.printStackTrace();
		      XposedBridge.log("报错nnnn");
		    } catch (InvocationTargetException invocationTargetException) {
		      invocationTargetException.printStackTrace();
		      XposedBridge.log("报错iiiiinvo");
		    } catch (InstantiationException instantiationException) {
		      instantiationException.printStackTrace();
		      XposedBridge.log("报错is");
		    } catch (ClassNotFoundException classNotFoundException) {
		      classNotFoundException.printStackTrace();
		      XposedBridge.log("报错ccccc");
		      return;
		    } 
		  }
	  
	  public static void tryGetHongbao(Context paramContext, final String sender, final String hongbao_id, String paramString3) {
		    try {
		      //String str = dataMap.get(hongbao_id);
		      StringBuilder stringBuilder = new StringBuilder();
		      stringBuilder.append("当前红包备注");
		      //stringBuilder.append(str);
		      stringBuilder.append("______");
		      stringBuilder.append(paramString3);
		      stringBuilder.append("hongbao_id:");
		      stringBuilder.append(hongbao_id);
		      //XposedBridge.log(stringBuilder.toString());
		      Object object = (Object)XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.YWAPI", wxclassLoader), "getIMKitInstance", new Object[] { account, appkey });
		      stringBuilder = new StringBuilder();
		      stringBuilder.append("mIMKit ");
		      stringBuilder.append(object);
		      //XposedBridge.log(stringBuilder.toString());
		      final Object WxAccount = XposedHelpers.callMethod(XposedHelpers.callMethod(object, "getIMCore", new Object[0]), "getWxAccount", new Object[0]);
		      object = (Object)new StringBuilder();
		      ((StringBuilder) object).append("WxAccount ");
		      ((StringBuilder) object).append(WxAccount);
		      //XposedBridge.log(object.toString());
		      final Object HongbaoManager = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alibaba.mobileim.lib.presenter.hongbao.HongbaoManager", wxclassLoader), "getInstance", new Object[0]);
			  //XposedBridge.log("HongbaoManager==="+HongbaoManager);
		      try {
			      object = (Object)wxclassLoader.loadClass("com.alibaba.mobileim.channel.event.IWxCallback");
			    } catch (ClassNotFoundException classNotFoundException) {
			      XposedBridge.log(classNotFoundException);
			      classNotFoundException = null;
			    } 
		      GetHongBaoCallBackProxy getHongBaoCallBackProxy = new GetHongBaoCallBackProxy(paramContext, paramString3, hongbao_id);
		      final Object param4 = Proxy.newProxyInstance(wxclassLoader, new Class[] { XposedHelpers.findClass("com.alibaba.mobileim.channel.event.IWxCallback", wxclassLoader) }, (InvocationHandler)getHongBaoCallBackProxy);
			  //XposedBridge.log("param4=="+param4);  
			  (new Thread(new Runnable() {
				
				  @Override
				  public void run() {
					// TODO Auto-generated method stub
					  try {
			              Thread.sleep(2000);
			            } catch (InterruptedException interruptedException) {
			              interruptedException.printStackTrace();
			            } 
			            //XposedBridge.log("WxAccount="+WxAccount+"; sender="+sender+";hongbao_id="+hongbao_id+"; param4="+param4);
			            XposedHelpers.callMethod(HongbaoManager, "tryGetHongbao", new Object[] { WxAccount, sender, hongbao_id, param4 });
					}
			  })).start();
//		      Runnable runnable = new Runnable() {
//		          public void run() {
//		            try {
//		              Thread.sleep(2000);
//		            } catch (InterruptedException interruptedException) {
//		              interruptedException.printStackTrace();
//		            } 
//		            XposedBridge.log("WxAccount="+WxAccount+"; sender="+sender+";hongbao_id="+hongbao_id+"; param4="+param4);
//		            XposedHelpers.callMethod(HongbaoManager, "tryGetHongbao", new Object[] { WxAccount, sender, hongbao_id, param4 });
//		          }
//		        };
		      return;
		    } catch (Exception exception) {
		      StringBuilder stringBuilder = new StringBuilder();
		      stringBuilder.append("拆包异常");
		      stringBuilder.append(sender);
		      exception.printStackTrace();
		      return;
		    } 
		  }

	  public static class MyReceiver extends BroadcastReceiver {
		    public void onReceive(Context param1Context, Intent param1Intent) {
		      StringBuilder stringBuilder;
		      String str;
		      Intent intent = param1Intent;
		      if (param1Intent.getAction().equals("com.qianyou.wangxin.wangxin")) {
		        XposedBridge.log("收到广播");
		    	  String str2 = param1Intent.getStringExtra("money");
		    	  String str1 = param1Intent.getStringExtra("qunid");
		        str = param1Intent.getStringExtra("orderid");
		        StringBuilder stringBuilder2 = new StringBuilder();
		        stringBuilder2.append("收到广播 创建红包 money=");
		        stringBuilder2.append(str2);
		        stringBuilder2.append("  qunid=");
		        stringBuilder2.append(str1);
		        stringBuilder2.append("备注=");
		        stringBuilder2.append(str);
		        XposedBridge.log(stringBuilder2.toString());
//		        DecimalFormat m = new DecimalFormat("0.00");
//				String aString = m.format(Double.parseDouble(str2));
		        int i = yuan2fen(Double.parseDouble(str2));
		        StringBuilder stringBuilder1 = new StringBuilder();
		        stringBuilder1.append("金额为:");
		        stringBuilder1.append(i);
		        XposedBridge.log(stringBuilder1.toString());
		        MobileImHook.createQunHonginfo(param1Context, i, str1, str);
		        return;
		      } 
		      if (param1Intent.getAction().equals("com.qianyou.wangxin.rob")) {
		        MobileImHook.canRob = param1Intent.getBooleanExtra("canRob", false);
		        stringBuilder = new StringBuilder();
		        stringBuilder.append("是否抢包");
		        stringBuilder.append(MobileImHook.canRob);
		        XposedBridge.log(stringBuilder.toString());
		        return;
		      }
		      if (param1Intent.getAction().equals("com.qianyou.wangxin.check.wx")) {
		    	  XposedBridge.log("qqqqqqqqqqqqqqqqq3");
//		    	  Intent intent2 = new Intent(Intent.ACTION_MAIN);
//		    	  /**知道要跳转应用的包命与目标Activity  com.alibaba.mobileim.ui.tab.MainTabActivity*/
//		    	  ComponentName componentName = new ComponentName("com.alibaba.mobileim", "com.alibaba.mobileim.ui.WxChattingActvity");
//		    	  intent2.setComponent(componentName);
//		    	  //intent2.putExtra("payurl", param1Intent.getStringExtra("url"));//这里Intent传值
//		    	  MainActivity.instance.startActivity(intent2);
		    	  intent = new Intent(wxcontext, XposedHelpers.findClass("com.alibaba.mobileim.ui.setting.SettingAboutActivity", wxclassLoader));
//		          intent.addFlags(268435456);
		          wxcontext.startActivity(intent);
		          XposedBridge.log("aaasdfasdfasdf");
		          return;
		        } 
		      if (intent.getAction().equals("com.qianyou.wangxin.to.h5")) {
		        MobileImHook.cMoney = intent.getStringExtra("money");
		        MobileImHook.cMark = intent.getStringExtra("mark");
		        MobileImHook.cOrderNo = intent.getStringExtra("orderNo");
		        MobileImHook.cPayUrl = intent.getStringExtra("payurl");
		        MobileImHook.currentQunId = intent.getStringExtra("qunId");
		        MobileImHook.orderid = intent.getStringExtra("orderid");
		        stringBuilder = new StringBuilder();
		        stringBuilder.append("开始转换---");
		        stringBuilder.append(MobileImHook.cMark);
		        stringBuilder.append("\n");
		        stringBuilder.append(MobileImHook.cOrderNo);
		        stringBuilder.append("\n");
		        stringBuilder.append(MobileImHook.currentQunId);
		        XposedBridge.log(stringBuilder.toString());
		        MobileImHook.getWangXinH5(MobileImHook.cPayUrl, MobileImHook.currentQunId);
		      } 
		    }

		  }
	  
	  public static int yuan2fen(double paramDouble) {
		    return (int)(100.0D * Double.valueOf((new DecimalFormat("#.00")).format(paramDouble)).doubleValue());
		  }
	  
}
