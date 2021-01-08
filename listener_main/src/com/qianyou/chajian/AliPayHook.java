package com.qianyou.chajian;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.listener5.Log;

import rx.Observable;
import rx.schedulers.Schedulers;


public class AliPayHook implements IXposedHookLoadPackage{
  public static String BACK_HOME_ACTION_USERID = "com.tools.payhelper.backhomeuserid";
  
  public static String BILLRECEIVED_ACTION = "com.tools.payhelper.billreceived";
  
  public static String QRCODERECEIVED_ACTION = "com.tools.payhelper.qrcodereceived";
  
  public static String SAVEALIPAYCOOKIE_ACTION = "com.tools.payhelper.savealipaycookie";
  public static final String ALI_PACKAGE = "com.eg.android.AlipayGphone";
  public static final String APPLICATION_CLASS = "com.alipay.mobile.quinox.LauncherApplication";
  public static final String MONEY_RPC_CLASS = "com.alipay.transferprod.rpc.CollectMoneyRpc";
  public static final String ZXING_HELPER_CLASS = "com.alipay.android.phone.wallet.ZXingHelper";
  public static final String BARCODE_FORMAT_ENUM = "com.alipay.android.phone.wallet.minizxing.BarcodeFormat";
  public static final String ERROR_CORRECTION_ENUM = "com.alipay.android.phone.wallet.minizxing.ErrorCorrectionLevel";
  public static final String CONTEXT_IMPL_CLASS = "com.alipay.mobile.core.impl.MicroApplicationContextImpl";
  public static final String RPC_SERVICE_CLASS = "com.alipay.mobile.framework.service.common.RpcService";
  public static final String LAUNCHER_AGENT_CLASS = "com.alipay.mobile.framework.LauncherApplicationAgent";
  private static ClassLoader mLoader;
  private static Object mCollectMoneyRpc;
  public static Activity launcherActivity = null;
  private static AlipayBroadcast alipayBroadcast = null;
  public static Boolean isTIAOZHUAN = false;
  
  public void handleLoadPackage(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) throws Throwable {
	  if (paramLoadPackageParam.appInfo != null && (paramLoadPackageParam.appInfo.flags & 0x81) == 0) {
	      final String packageName = paramLoadPackageParam.packageName;
	      final String processName = paramLoadPackageParam.processName;
	      if ("com.eg.android.AlipayGphone".equals(packageName))
	          try {
	        	  XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.alipay.apmobilesecuritysdk.scanattack.common.ScanAttack", paramLoadPackageParam.classLoader),"getScanAttackInfo", //getAD104,getScanAttackInfo
	        			  Context.class, int.class, int.class, boolean.class, int.class, int.class, String.class, new XC_MethodHook() {
		        		  @Override
		                  protected void afterHookedMethod(MethodHookParam param) throws Throwable {
		                      super.afterHookedMethod(param);
		                      XposedBridge.log("检测："+param.getResult());
		                      param.setResult(null);
		                  }
						});
	        	  XposedHelpers.findAndHookMethod(Application.class, "attach", new Object[] { Context.class, new XC_MethodHook() {
	                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
	                      super.afterHookedMethod(param1MethodHookParam);
	                      Context context = (Context)param1MethodHookParam.args[0];
	                      ClassLoader classLoader = context.getClassLoader();
	                      if ("com.eg.android.AlipayGphone".equals(processName) ) {
	                    	//AliPayHook.this.  (AliPayHook.this, true);
//	                        AliPayHook.StartAlipayReceived startAlipayReceived = new AliPayHook.StartAlipayReceived();
//	                        IntentFilter intentFilter = new IntentFilter();
//	                        intentFilter.addAction("com.payhelper.alipay.start");
//	                        context.registerReceiver(startAlipayReceived, intentFilter);
	                        XposedBridge.log("handleLoadPackage: " + packageName);
	                        Toast.makeText(context, "绑定支付宝成功", 1).show();
	                        JSONObject jo=new JSONObject();
	                    	jo.put("action", 110);
	                    	jo.put("msg","绑定支付宝成功");
	                    	sendToMain(jo.toString(),false);
	                    	mLoader = classLoader;
	                        hook(classLoader, context);
	                      } 
	                    }
	                  } });
	            return;
	          } catch (Throwable throwable) {
	            XposedBridge.log(throwable);
	            return;
	          }  
	      if ("com.qianyou.listener5".equals(packageName))
	      {
	    	  new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JSONObject jo=new JSONObject();
					try {
						
						jo.put("action", 90);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sendToMain(jo.toString(),false);
				}
			}).start();
	    	  
	      }
	  }
  }
//解决支付宝的反hook
  private void securityCheckHook(ClassLoader classLoader) {
      try {
          Class securityCheckClazz = XposedHelpers.findClass("com.alipay.mobile.base.security.CI", classLoader);
          XposedHelpers.findAndHookMethod(securityCheckClazz, "a", String.class, String.class, String.class, new XC_MethodHook() {
              @Override
              protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                  Object object = param.getResult();
                  XposedHelpers.setBooleanField(object, "a", false);
                  param.setResult(object);
                  super.afterHookedMethod(param);
              }
          });

          XposedHelpers.findAndHookMethod(securityCheckClazz, "a", Class.class, String.class, String.class, new XC_MethodReplacement() {
              @Override
              protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                  return (byte) 1;
              }
          });
          XposedHelpers.findAndHookMethod(securityCheckClazz, "a", ClassLoader.class, String.class, new XC_MethodReplacement() {
              @Override
              protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                  return (byte) 1;
              }
          });
          XposedHelpers.findAndHookMethod(securityCheckClazz, "a", new XC_MethodReplacement() {
              @Override
              protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                  return false;
              }
          });

      } catch (Error error) {
    	  error.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
//  private void securityCheckHook(ClassLoader paramClassLoader) {
//    try {
//      Class clazz = XposedHelpers.findClass("com.alipay.mobile.base.security.CI", paramClassLoader);
//      XposedHelpers.findAndHookMethod(clazz, "a", new Object[] { String.class, String.class, String.class, new XC_MethodHook() {
//              protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                Object object = param1MethodHookParam.getResult();
//                XposedHelpers.setBooleanField(object, "a", false);
//                param1MethodHookParam.setResult(object);
//                super.afterHookedMethod(param1MethodHookParam);
//              }
//            } });
//      XposedHelpers.findAndHookMethod(clazz, "a", new Object[] { Class.class, String.class, String.class, new XC_MethodReplacement() {
//              protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                return Byte.valueOf((byte)1);
//              }
//            } });
//      XposedHelpers.findAndHookMethod(clazz, "a", new Object[] { ClassLoader.class, String.class, new XC_MethodReplacement() {
//              protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                return Byte.valueOf((byte)1);
//              }
//            } });
//      XposedHelpers.findAndHookMethod(clazz, "a", new Object[] { new XC_MethodReplacement() {
//              protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                return Boolean.valueOf(false);
//              }
//            } });
//      return;
//    } catch (Error error) {
//    
//    } catch (Exception exception) {
//    	exception.printStackTrace();
//    }
//    
//  }
  
  public void hook(final ClassLoader classLoader, final Context context) {
    securityCheckHook(classLoader);
    //hookActivityCreateFinish(context, classLoader);
    //hookAppContext();
    try {
      XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.android.phone.messageboxstatic.biz.dao.TradeDao", classLoader), "insertMessageInfo", new XC_MethodHook() {
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
              try {
                //Log.i("8888Heart", "进来了接受订单通知广播11");
                String str2 = (String)XposedHelpers.callMethod(param1MethodHookParam.args[0], "toString", new Object[0]);
                //Log.i("8888Heart", "支付宝支付" + str2);
                String str1 = getTextCenter(str2, "extraInfo='", "'");
                //XposedBridge.log("*****************start2********************");
                XposedBridge.log("总消息="+str2);
                //XposedBridge.log("str1==="+str1);
                //XposedBridge.log("*****************end2********************");
                //Log.i("8888Heart", "进来了接受订单通知广播88" + str1);
                if (str1.contains("二维码收款") || str1.contains("收到一笔转账") || str1.contains("收款金额")) {
                  //Log.i("8888Heart", "进来了接受订单通知广播44");
//                  JSONObject jSONObject = new JSONObject(str1);
//                  str1 = jSONObject.getString("content");
//                  String str = jSONObject.getString("assistMsg2");
//                  str2 = StringUtils.getTextCenter(str2, "tradeNO=", "&");
//                  XposedBridge.log("收到支付宝支付订单：" + str2 + "==" + str1 + "==" + str);
//                  //Log.i("8888Heart", "收到支付宝支付订单" + str2 + "==" + str1 + "==" + str);
//                  Intent intent = new Intent();
//                  intent.putExtra("bill_no", str2);
//                  intent.putExtra("bill_money", str1);
//                  intent.putExtra("bill_mark", str);
//                  intent.putExtra("bill_type", "alipay");
//                  intent.setAction(AliPayHook.BILLRECEIVED_ACTION);
//                  context.sendBroadcast(intent);
//                  System.out.println("支付宝日志：个人收到支付结果 mark=" + str + "  ");
                	JSONObject jo1=new JSONObject(str1);
                	JSONObject bizMonitor=new JSONObject(jo1.getString("bizMonitor"));
                	JSONObject jo=new JSONObject();
                	jo.put("action", 104);
                	jo.put("money", jo1.get("money"));
                	jo.put("id", bizMonitor.get("id"));
                	jo.put("type", "alipay");
                	jo.put("time",new Date().getTime());
                	jo.put("payer", "个人收款");
                	JSONArray content = new JSONArray(jo1.getString("content"));
                	//XposedBridge.log("content"+content);
                	for (int i = 0; i < content.length(); i++) {
						JSONObject jo2 = (JSONObject)content.get(i);
						//XposedBridge.log("jo2"+jo2);
						if(jo2.getString("title").contains("付款")){
							//XposedBridge.log("content=="+jo2.getString("content"));
							jo.put("payer", "付款方："+jo2.getString("content"));
						}
					}
                	sendToMain(jo.toString(),false);
                	
                } 
              } catch (Exception exception) {
                //Log.i("8888Heart", "进来了接受订单通知广播22");
                //XposedBridge.log(exception.getMessage());
              } 
              super.beforeHookedMethod(param1MethodHookParam);
            }
          });
      XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.android.phone.messageboxstatic.biz.dao.ServiceDao", classLoader), "insertMessageInfo", new XC_MethodHook() {
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
              //PayHelperUtils.sendmsg(context, "======支付宝商家服务订单start=========");
              try {
                String str1 = (String)XposedHelpers.callMethod(param1MethodHookParam.args[0], "toString", new Object[0]);
                String str2 = getTextCenter(str1, "extraInfo='", "'");
                //PayHelperUtils.sendmsg(context, "\"商家服务收款到账MessageInfo：\" + MessageInfo");
//                Log.i("00000Heart", "商家服务收款到账MessageInfo：" + str1);
//                Log.i("00000Heart", "商家服务收款到账content：" + str2);
                XposedBridge.log("*****************start1********************");
                XposedBridge.log("总消息="+str1);
                XposedBridge.log("str2消息==="+str2);
                if (str2.contains("二维码收款") || str2.contains("收到一笔转账") || str2.contains("收款金额")) {
                  //AliPayHook.tempCook = PayHelperUtils.getCookieStr(classLoader);
                  //Log.i("00000Heart", "商家服务收款到账");
                  //str1 = PayHelperUtils.getCookieStr(classLoader);
                  //Log.i("00000Heart", "商家服务收款到账cookie：" + str1);
                    //PayHelperUtils.sendmsg(context, "商家服务收款到账");
                    //PayHelperUtils.getTradeInfo(context, str1);
                	//JSONObject jo1=new JSONObject(str2);
                	//JSONObject bizMonitor=new JSONObject(jo1.getString("bizMonitor"));
                	//XposedBridge.log("aaaaa=="+jo1.toString());
                	XposedBridge.log("啊啊啊啊啊啊啊");
                	String money =  getTextCenter(str2, "收款金额￥", "\"");
                	XposedBridge.log("金额="+money);
                	String idString = getTextCenter(str2, "gmtValid\":", ",");
                	XposedBridge.log("id=="+idString);
                	JSONObject jo=new JSONObject();
                	jo.put("action", 104);
//                	jo.put("money", jo1.get("mainAmount"));
//                	jo.put("id", jo1.get("gmtValid"));
                	jo.put("money",money);
                	jo.put("id", idString);
                	jo.put("type", "alipay");
                	jo.put("time",new Date().getTime());
                	jo.put("payer", "商家收款");
                	XposedBridge.log("jo=="+jo.toString());
                    XposedBridge.log("*****************end1********************");
                	sendToMain(jo.toString(),false);
                	
                  //System.out.println("支付宝日志：商家收到支付结果 ");
                } 
              } catch (Exception exception) {
                //PayHelperUtils.sendmsg(context, exception.getMessage());
              } 
              super.beforeHookedMethod(param1MethodHookParam);
            }
          });
//   // hook 支付宝主界面的onCreate方法，获得主界面对象并注册广播
//      XposedHelpers.findAndHookMethod("com.alipay.mobile.quinox.LauncherActivity", classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//          @Override
//          protected void afterHookedMethod(MethodHookParam param) throws Throwable {
////              if (isTIAOZHUAN==false) {
////                  XposedBridge.log("com.alipay.mobile.quinox.LauncherActivity onCreated" + "\n");
////                  launcherActivity = (Activity) param.thisObject;
////                  Intent launcherIntent = new Intent(context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", launcherActivity.getApplicationContext().getClassLoader()));
////                  launcherActivity.startActivity(launcherIntent);
////                  isTIAOZHUAN=true;
////              }
//        	  XposedBridge.log("注册广播="+alipayBroadcast);
//        	  if (alipayBroadcast==null) {
//        		  launcherActivity = (Activity) param.thisObject;
//                  alipayBroadcast = new AlipayBroadcast();
//                  XposedBridge.log("注册成功="+alipayBroadcast);
//                  JSONObject jo=new JSONObject();
//              	  jo.put("action", 110);
//              	  jo.put("msg","可以产码");
//              	  sendToMain(jo.toString(),false);
//                  IntentFilter intentFilter = new IntentFilter();
//                  intentFilter.addAction(AlipayBroadcast.CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION);
//                  intentFilter.addAction(AlipayBroadcast.CONSULT_GET_AMOUNT);
//                  intentFilter.addAction(AlipayBroadcast.COOKIE_STR_INTENT_FILTER_ACTION);
//                  launcherActivity.registerReceiver(alipayBroadcast, intentFilter);
//        	  }
//          }
//      });
//
//   // hook 支付宝的主界面的onDestory方法，销毁广播
//      XposedHelpers.findAndHookMethod("com.alipay.mobile.quinox.LauncherActivity", classLoader, "onDestroy", new XC_MethodHook() {
//          @Override
//          protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//              XposedBridge.log("com.alipay.mobile.quinox.LauncherActivity onDestroy" + "\n");
//              if (alipayBroadcast != null) {
//                  ((Activity) param.thisObject).unregisterReceiver(alipayBroadcast);
//              }
//              launcherActivity = null;
//          }
//      });
      
      XposedHelpers.findAndHookMethod(Activity.class, "onCreate", new Object[] { Bundle.class, new XC_MethodHook() {
          protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
            super.afterHookedMethod(param1MethodHookParam);
            String str = param1MethodHookParam.thisObject.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" obj ");
            stringBuilder.append(str);
            XposedBridge.log(stringBuilder.toString());
          
          }
        } });
      
      XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
          @Override
          protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        	  XposedBridge.log("aaaaaaaaaaa啊啊啊啊啊="+isTIAOZHUAN);
        	  if (isTIAOZHUAN==false) {
        		  XposedBridge.log("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity onCreated" + "\n"+"is三十岁四十岁时");
                  Field jinErField = XposedHelpers.findField(param.thisObject.getClass(), "b");
                  final Object jinErView = jinErField.get(param.thisObject);
//                  Field beiZhuField = XposedHelpers.findField(param.thisObject.getClass(), "c");
//                  final Object beiZhuView = beiZhuField.get(param.thisObject);
                  //Intent intent = ((Activity) param.thisObject).getIntent();
                  //String jinEr = intent.getStringExtra("qr_money");
                  //String beiZu = intent.getStringExtra("beiZhu");
                  Intent intent = ((Activity) param.thisObject).getIntent();
                  String jinEr = intent.getStringExtra("qr_money");
                  XposedBridge.log("JinEr:" + jinEr + "\n");
                  //XposedBridge.log("BeiZu:" + beiZu + "\n");
                  Thread.sleep(1000);
                  XposedHelpers.callMethod(jinErView, "setText", jinEr);
                  //XposedHelpers.callMethod(beiZhuView, "setText", beiZu);
                  Thread.sleep(2000);
                  Field quRenField = XposedHelpers.findField(param.thisObject.getClass(), "e");
                  final Button quRenButton = (Button) quRenField.get(param.thisObject);
                  quRenButton.performClick();
                  isTIAOZHUAN=true;
        	  }
          }
      });
      XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", classLoader, "a", String.class,
              XposedHelpers.findClass("com.alipay.transferprod.rpc.result.ConsultSetAmountRes", classLoader), new XC_MethodHook() {
    	  protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    		  super.afterHookedMethod(param);
			  XposedBridge.log("收款："+param.args[0]);
    		  XposedBridge.log("消息="+param.args[1]);
    		  if (param.args[1]!=null&&param.args[1].toString().contains("ConsultSetAmountRes")) {
//    			  XposedBridge.log("收款："+param.args[0]);
//        		  XposedBridge.log("消息="+param.args[1]);
        		  String str1 = (String)XposedHelpers.callMethod(param.args[1], "toString", new Object[0]);
        		  XposedBridge.log(str1);
        		  String qrcode = getTextCenter(str1, "printQrCodeUrl='", "'");
        		  XposedBridge.log(qrcode);
        		  isTIAOZHUAN=false;
        		  JSONObject jo=new JSONObject();
          		  jo.put("action", 117);
              	  jo.put("money", param.args[0]);
              	  jo.put("qrcode", qrcode);
              	  XposedBridge.log("jo=="+jo.toString());
              	  sendToMain(jo.toString(),false);
    		  }
    	  }
      });
      
//      XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRNewActivity", classLoader), "b", new XC_MethodHook() {
//    	  protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//    		  if (param.args[0]!=null&&param.args[0].toString().contains("printQrCodeUrl")) {
//    			  String str1 = (String)XposedHelpers.callMethod(param.args[0], "toString", new Object[0]);
//    			 // XposedBridge.log("aaaaaaaaa"+str1);
//    			  String qrcode = getTextCenter(str1, "printQrCodeUrl='", "'");
//    			  Toast.makeText(context, "获取收款码链接成功", 1).show();
////    			  Intent launcherIntent = new Intent(context, XposedHelpers.findClass("com.eg.android.AlipayGphone.AlipayLogin", AliPayHook.launcherActivity.getApplicationContext().getClassLoader()));
////                  launcherActivity.startActivity(launcherIntent);
//                  JSONObject jo=new JSONObject();
//              	  jo.put("action", 111);
//              	  jo.put("msg",qrcode);
//              	  sendToMain(jo.toString(),false);
//    		  }
//    		  super.afterHookedMethod(param);
//    	  }
//      });
      
      XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRActivity", classLoader), "b", new XC_MethodHook() {
    	  protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    		  if (param.args[0]!=null&&param.args[0].toString().contains("printQrCodeUrl")) {
    			  String str1 = (String)XposedHelpers.callMethod(param.args[0], "toString", new Object[0]);
    			 // XposedBridge.log("aaaaaaaaa"+str1);
    			  String qrcode = getTextCenter(str1, "printQrCodeUrl='", "'");
    			  Toast.makeText(context, "获取收款码链接成功", 1).show();
                  JSONObject jo=new JSONObject();
              	  jo.put("action", 111);
              	  jo.put("msg",qrcode);
              	  sendToMain(jo.toString(),false);
    		  }
    		  super.afterHookedMethod(param);
    	  }
      });
//      XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", classLoader, "onCreate", new Object[] { Bundle.class, new XC_MethodHook() {
//              protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                XposedBridge.log("Hook支付宝开始.........");
//                Object object1 = XposedHelpers.findField(param1MethodHookParam.thisObject.getClass(), "b").get(param1MethodHookParam.thisObject);
//                Object object2 = XposedHelpers.findField(param1MethodHookParam.thisObject.getClass(), "c").get(param1MethodHookParam.thisObject);
//                Intent intent = ((Activity)param1MethodHookParam.thisObject).getIntent();
//                String str = intent.getStringExtra("mark");
//                XposedHelpers.callMethod(object1, "setText", new Object[] { intent.getStringExtra("money") });
//                XposedHelpers.callMethod(object2, "setText", new Object[] { str });
//                ((Button)XposedHelpers.findField(param1MethodHookParam.thisObject.getClass(), "e").get(param1MethodHookParam.thisObject)).performClick();
//                System.out.println("支付宝日志：调用生成二维码 mark=" + str);
//              }
//            } });
//      XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", classLoader, "a", new Object[] { XposedHelpers.findClass("com.alipay.transferprod.rpc.result.ConsultSetAmountRes", classLoader), new XC_MethodHook() {
//              protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                String str2 = (String)XposedHelpers.findField(param1MethodHookParam.thisObject.getClass(), "g").get(param1MethodHookParam.thisObject);
//                String str3 = (String)XposedHelpers.callMethod(XposedHelpers.findField(param1MethodHookParam.thisObject.getClass(), "c").get(param1MethodHookParam.thisObject), "getUbbStr", new Object[0]);
//                Object object = param1MethodHookParam.args[0];
//                System.out.println("当前数据长度：" + param1MethodHookParam.args.length);
//                String str1 = (String)XposedHelpers.findField(object.getClass(), "qrCodeUrl").get(object);
//                String str4 = (String)XposedHelpers.findField(object.getClass(), "codeId").get(object);
//                object = XposedHelpers.findField(object.getClass(), "printQrCodeUrl").get(object);
//                System.out.println("当前数据corid：" + str4 + "   " + object);
//                XposedBridge.log(str2 + "  " + str3 + "  " + str1);
//                XposedBridge.log("调用增加数据方法==>支付宝");
//                object = new Intent();
//                ((Intent) object).putExtra("money", str2);
//                ((Intent) object).putExtra("mark", str3);
//                ((Intent) object).putExtra("type", "alipay");
//                ((Intent) object).putExtra("payurl", str1);
//                ((Intent) object).setAction(AliPayHook.QRCODERECEIVED_ACTION);
//                context.sendBroadcast((Intent)object);
//                System.out.println("支付宝日志：捕捉到支付宝二维码 mark=" + str3 + "  " + str1);
//              }
//            } });
      return;
    } catch (Error error) {
    
    } catch (Exception exception) {
    	exception.printStackTrace();
    }
    
  }
  
  private void hookAppContext(){
      try {
          final Class<?> launcherClazz = mLoader.loadClass(LAUNCHER_AGENT_CLASS);//com.alipay.mobile.framework.LauncherApplicationAgent
          XposedHelpers.findAndHookMethod(launcherClazz, "init", new XC_MethodHook() {
              @Override
              protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                  super.afterHookedMethod(param);
                  XposedBridge.log("LauncherApplicationAgent init()");
                  Object launcherInstance = param.thisObject;
                  Field applicationContext = launcherClazz.getDeclaredField("mMicroApplicationContext");
                  applicationContext.setAccessible(true);
                  Object microApplicationContext = applicationContext.get(launcherInstance);
                  XposedBridge.log("get mMicroApplicationContext: " + microApplicationContext);
                  hookRpcService(microApplicationContext);
              }
          });
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }
  }
  
  private void hookRpcService(Object microApplicationContext){
	  XposedBridge.log("hookQRMoneyRpcService");
      try {
          Class<?> applicationClazz = mLoader.loadClass(CONTEXT_IMPL_CLASS);//com.alipay.mobile.core.impl.MicroApplicationContextImpl
          Class<?> collectMoneyRpcClazz = mLoader.loadClass(MONEY_RPC_CLASS);//com.alipay.transferprod.rpc.CollectMoneyRpc
          Method getRpcMethod = applicationClazz.getMethod("findServiceByInterface", String.class);
          getRpcMethod.setAccessible(true);
          XposedBridge.log("before invoke");
          Object rpcServiceInstance = getRpcMethod.invoke(microApplicationContext, RPC_SERVICE_CLASS);//com.alipay.mobile.framework.service.common.RpcService
          XposedBridge.log("after invoke"+rpcServiceInstance);
//          Method getCollectMoneyMethod = rpcServiceInstance.getClass().getDeclaredMethod("getRpcProxy", Class.class);
//          XposedBridge.log("getCollectMoneyMethod="+getCollectMoneyMethod);
//          getCollectMoneyMethod.setAccessible(true);
//          XposedBridge.log("---------get======="+collectMoneyRpcClazz);
//          Object mRpc = getCollectMoneyMethod.invoke(rpcServiceInstance, collectMoneyRpcClazz);
//          XposedBridge.log("啊啊mCollectMoneyRpc="+mRpc);
          Method getCollectMoneyMethod = rpcServiceInstance.getClass().getDeclaredMethod("getRpcProxy", Class.class);
          XposedBridge.log("getCollectMoneyMethod="+getCollectMoneyMethod);
          getCollectMoneyMethod.setAccessible(true);
          XposedBridge.log("---------get======="+collectMoneyRpcClazz);
          //mCollectMoneyRpc = collectMoneyRpcClazz.newInstance();
          //XposedBridge.log("啊啊mCollectMoneyRpc="+mCollectMoneyRpc);
      }catch (Exception e){
    	  XposedBridge.log("e=="+e.getMessage());
      }
  }
  

  public static void asyncSetQRMoney(final String des, float money){
	  XposedBridge.log("设置="+mCollectMoneyRpc);
	  XposedBridge.log("des=="+des+";money="+money);
//	  if(mCollectMoneyRpc == null){
//		  return;
//      }
      XposedBridge.log("asyncSetQRMoney");
      String consultReq = "com.alipay.transferprod.rpc.req.ConsultSetAmountReq";
      String consultRes = "com.alipay.transferprod.rpc.result.ConsultSetAmountRes";      
		try {
			 // ConsultSetAmountReq
		      Class<?> consultReqClazz = mLoader.loadClass(consultReq);
		      Class<?> collectMoneyRpcClazz = mLoader.loadClass(MONEY_RPC_CLASS);
		      Object consultReqInstance = consultReqClazz.newInstance();
		      // Field amount
		      Field amountField = consultReqClazz.getField("amount");
		      amountField.set(consultReqInstance, "" + money);
		      // Field desc
		      Field descField = consultReqClazz.getField("desc");
		      descField.set(consultReqInstance, des);
		      Log.T("aaaaaaaaaaaaa");
		      // Method consultSetAmount
//		      Method method = mCollectMoneyRpc.getClass().getMethod("consultSetAmount", consultReqClazz);
//		      method.setAccessible(true);
//		      Object resInstance = method.invoke(mCollectMoneyRpc, consultReqInstance);
//		
//		      // ConsultSetAmountRes
//		      Class<?> consultResClazz = mLoader.loadClass(consultRes);
//		      Field codeIdField = consultResClazz.getDeclaredField("codeId");
//		      codeIdField.setAccessible(true);
//		      String codeId = (String) codeIdField.get(resInstance);
//		      Field printQrCodeUrlField = consultResClazz.getDeclaredField("printQrCodeUrl");
//		      String printQrCodeUrl = (String) printQrCodeUrlField.get(resInstance);
//		      Field qrCodeUrlField;
//		      qrCodeUrlField = consultResClazz.getDeclaredField("qrCodeUrl");
//		      String qrCodeUrl = (String) qrCodeUrlField.get(resInstance);
//		      XposedBridge.log(String.format("RPC request collect money, codeId: %s, printQrCodeUrl: %s, qrCodeUrl: %s",
//		              codeId, printQrCodeUrl, qrCodeUrl));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
//  public void hookActivityCreateFinish(final Context context, final ClassLoader classLoader) {
//    try {
//      XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRActivity", classLoader, "onCreate", new Object[] { Bundle.class, new XC_MethodHook() {
//              protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                XposedBridge.log("Hook支付宝开始test11.........");
//                String str2 = (String)XposedHelpers.findField(param1MethodHookParam.thisObject.getClass(), "f").get(param1MethodHookParam.thisObject);
//                System.out.println("测试测试1== " + str2);
//                String str1 = str2;
//                if (str2 == null)
//                  str1 = ""; 
//                Intent intent = new Intent();
//                intent.putExtra("userid", str1);
//                intent.setAction(AliPayHook.BACK_HOME_ACTION_USERID);
//                context.sendBroadcast(intent);
//              }
//            } });
//      XposedHelpers.findAndHookMethod(Activity.class, "onCreate", new Object[] { Bundle.class, new XC_MethodHook() {
//              protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
//                super.afterHookedMethod(param1MethodHookParam);
//                XposedHelpers.findAndHookMethod(TextView.class, "setText", new Object[] { CharSequence.class, TextView.BufferType.class, boolean.class, int.class, new XC_MethodHook() {
//                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param2MethodHookParam) {
//                          //param2MethodHookParam.args[0] + param2MethodHookParam.thisObject.getClass().getSimpleName() + "";
//                          Log.d("开始hook", "2、获得数据" + param2MethodHookParam.args[0] + "" + param2MethodHookParam.thisObject.getClass().getSimpleName() + "  结束");
//                        }
//                      } });
//                //PayHelperUtils.sendmsg(context, ":load activity:" + param1MethodHookParam.thisObject.getClass().getPackage() + "=======" + param1MethodHookParam.thisObject.getClass().getSimpleName());
//                //Log.e(">>>>>>>>>>test0:==", ":load activity:" + param1MethodHookParam.thisObject.getClass().getPackage() + "=======" + param1MethodHookParam.thisObject.getClass().getSimpleName());
//                if (param1MethodHookParam.thisObject.getClass().getSimpleName().equals("BillListActivity_"))
//                  XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.mobile.bill.list.ui.BillListActivity_", classLoader), "a", new XC_MethodHook() {
//                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param2MethodHookParam) throws Throwable {
//                          try {
//                        	//XposedBridge.log("######################################################");
//                            //System.out.println(">>>>>>>>>>test0:" + JSON.toJSONString(param2MethodHookParam.args));
//                            boolean bool = JSON.toJSONString(param2MethodHookParam.args).contains("billByMonthJumpUrl");
//                            if (bool)
//                              try {
//                                String str = JSON.toJSONString(param2MethodHookParam.args);
//                                ArrayList<AliPayHook.Model> arrayList = new ArrayList();
//                                JSONArray jSONArray = JSON.parseArray(str);
//                                //System.out.println("抓取到的数据是:" + jSONArray.toJSONString());
//                                XposedBridge.log(jSONArray.toJSONString());
////                                ListModel listModel = (ListModel)(new Gson()).fromJson(jSONArray.getJSONObject(0).toJSONString(), ListModel.class);
////                                for (int i = 0;; i++) {
////                                  if (i < listModel.getBillListItems().size()) {
////                                    if (JSON.toJSONString(listModel.getBillListItems().get(i)).contains("bizInNo")) {
////                                      String str1 = ((ListModel.BillListItemsBean)listModel.getBillListItems().get(i)).getConsumeFee();
////                                      AliPayHook.Model model = new AliPayHook.Model();
////                                      model.tradeAmount = str1.substring(1, str1.length());
////                                      model.tradeNo = ((ListModel.BillListItemsBean)listModel.getBillListItems().get(i)).getBizInNo();
////                                      model.tradeTime = ((ListModel.BillListItemsBean)listModel.getBillListItems().get(i)).getGmtCreate() + "";
////                                      model.tradeRemark = ((ListModel.BillListItemsBean)listModel.getBillListItems().get(i)).getConsumeTitle();
////                                      arrayList.add(model);
////                                    } 
////                                  } else {
////                                    String str1 = (new Gson()).toJson(arrayList);
////                                    //Log.e("======准备回掉", str1);
////                                    //XposedBridge.log(str1);
////                                    if (arrayList.size() <= 0)
////                                      return; 
////                                    (new Intent()).putExtra("mJson", str1);
////                                    XposedHelpers.callMethod(param2MethodHookParam.thisObject, "finish", new Object[0]);
////                                    super.beforeHookedMethod(param2MethodHookParam);
////                                  } 
////                                } 
//                              } catch (Exception exception) {} 
//                          } catch (Exception exception) {}
//                          super.beforeHookedMethod(param2MethodHookParam);
//                        }
//                      }); 
//              }
//            } });
//      return;
//    } catch (Error error) {
//    
//    } catch (Exception exception) {
//    	Log.e("test临时", "hookTFAccount error:" + exception.getMessage());
//    }
//    
//  }
	public static void startSvr()
    {
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket(5458);
			    	while(true)
			    	{
			    		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			        	try {
							socket.receive(packet);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
			        	byte[] arr = packet.getData();
			        	int len = packet.getLength();
			        	String data=new String(arr,0,len);
			        	
			    		JSONObject jobject;
						try {
							jobject = new JSONObject(data);
							int act=jobject.getInt("action");
//				    		if(act==90)
//				    		{
//				    			JSONObject jo=new JSONObject();
//				    			jo.put("action", 90);
//				    			sendToMain(jo.toString(),true);
//				    		}
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		
			    		
			    	}
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//print("socket err");
				}
			}
		});
	    t.start();
    	
    }
	public static void sendToMain(final String data,final Boolean isLoop)
	{
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					DatagramSocket socket = new DatagramSocket();
					DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getByName("127.0.0.1"), 5456);
					socket.send(packet); 
					socket.close();
					//print("已就绪");
			        
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//print("未知ip");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//print("无法建立通信");
				}
			}
		});
	    t.start();
	}
  public void startActivity(Context paramContext) {
    Intent intent = new Intent(paramContext, XposedHelpers.findClass("com.alipay.mobile.bill.list.ui.BillListActivity_", paramContext.getClassLoader()));
    intent.addFlags(268435456);
    paramContext.startActivity(intent);
  }
  public static String getTextCenter(String paramString1, String paramString2, String paramString3) {
	    try {
	      int i = paramString1.indexOf(paramString2) + paramString2.length();
	      return paramString1.substring(i, paramString1.indexOf(paramString3, i));
	    } catch (Exception exception) {
	      exception.printStackTrace();
	      return "error";
	    } 
	  }
  
  public static class Model {
    public String tradeAmount;
    
    public String tradeNo;
    
    public String tradeRemark;
    
    public String tradeTime;
  }
//  class StartAlipayReceived extends BroadcastReceiver {
//	    public void onReceive(Context param1Context, Intent param1Intent) {
//	      XposedBridge.log("启动支付宝Activity=");
//	      if (param1Intent.getStringExtra("type").equals("qrset")) {
//	        Intent intent = new Intent(param1Context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", param1Context.getClassLoader()));
//	        intent.addFlags(268435456);
//	        intent.putExtra("mark", param1Intent.getStringExtra("mark"));
//	        intent.putExtra("money", param1Intent.getStringExtra("money"));
//	        param1Context.startActivity(intent);
//	        return;
//	      } 
//	      if (param1Intent.getStringExtra("type").equals("solidcode")) {
//	        param1Intent = new Intent(param1Context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRActivity", param1Context.getClassLoader()));
//	        param1Intent.addFlags(268435456);
//	        param1Context.startActivity(param1Intent);
//	        return;
//	      } 
//	    }
//	  }
	  
}


/* Location:              C:\Users\Administrator\Desktop\新实时码\classes2-dex2jar.jar!\com\tools\payhelper\AliPayHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */