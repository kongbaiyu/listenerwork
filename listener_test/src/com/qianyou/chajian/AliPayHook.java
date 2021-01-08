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
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.listener5.MainActivity;


public class AliPayHook implements IXposedHookLoadPackage{
  public static String BACK_HOME_ACTION_USERID = "com.tools.payhelper.backhomeuserid";
  
  public static String BILLRECEIVED_ACTION = "com.tools.payhelper.billreceived";
  
  public static String QRCODERECEIVED_ACTION = "com.tools.payhelper.qrcodereceived";
  
  public static String SAVEALIPAYCOOKIE_ACTION = "com.tools.payhelper.savealipaycookie";
  
  
  
  public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam paramLoadPackageParam) throws Throwable {
	  if (paramLoadPackageParam.appInfo != null && (paramLoadPackageParam.appInfo.flags & 0x81) == 0) {
	      final String packageName = paramLoadPackageParam.packageName;
	      final String processName = paramLoadPackageParam.processName;
	      if ("com.eg.android.AlipayGphone".equals(packageName))
	          try {
	        	  XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.alipay.apmobilesecuritysdk.scanattack.common.ScanAttack", paramLoadPackageParam.classLoader),"getScanAttackInfo", //getAD104
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
	                       // XposedBridge.log("handleLoadPackage: " + packageName);
	                        Toast.makeText(context, "绑定支付宝成功", 1).show();
	                        JSONObject jo=new JSONObject();
	                    	jo.put("action", 110);
	                    	jo.put("msg","绑定支付宝成功5");
	                    	sendToMain(jo.toString(),false);
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
	      if ("com.alibaba.mobileim".equals(packageName) && !MainActivity.instance.WANGXIN_ISHOOK)
	          try {
	            XposedHelpers.findAndHookMethod(Application.class, "attach", new Object[] { Context.class, new XC_MethodHook() {
	                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
	                      super.afterHookedMethod(param1MethodHookParam);
	                      Context context = (Context)param1MethodHookParam.thisObject;
	                      MainActivity.instance.WANGXIN_ISHOOK=true;
	                      XposedBridge.log("旺信hook成功");
                    	  JSONObject jo=new JSONObject();
                    	  jo.put("action", 112);
                    	  jo.put("msg","绑定旺信成功");
                    	  sendToMain(jo.toString(),false);
                          Toast.makeText(context, "绑定旺信宝成功b", 1).show();
	                      new MobileImHook(context, paramLoadPackageParam.classLoader);
	                    }
	                  } });
	            return;
	          } catch (Throwable throwable) {} 
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
  
  public void hook(final ClassLoader classLoader, final Context context) {
    securityCheckHook(classLoader);
    //hookActivityCreateFinish(context, classLoader);
    try {
      XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.android.phone.messageboxstatic.biz.dao.TradeDao", classLoader), "insertMessageInfo", new XC_MethodHook() {
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
              try {
                //Log.i("8888Heart", "进来了接受订单通知广播11");
                String str2 = (String)XposedHelpers.callMethod(param1MethodHookParam.args[0], "toString", new Object[0]);
                //Log.i("8888Heart", "支付宝支付" + str2);
                String str1 = getTextCenter(str2, "extraInfo='", "'");
//                XposedBridge.log("*****************start2********************");
//                XposedBridge.log("总消息="+str2);
//                XposedBridge.log(str1);
//                XposedBridge.log("*****************end2********************");
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
//                XposedBridge.log("*****************start1********************");
//                XposedBridge.log("总消息"+str1);
//                XposedBridge.log(str2);
                if (str2.contains("二维码收款") || str2.contains("收到一笔转账") || str2.contains("收款金额")) {
                  //AliPayHook.tempCook = PayHelperUtils.getCookieStr(classLoader);
                  //Log.i("00000Heart", "商家服务收款到账");
                  //str1 = PayHelperUtils.getCookieStr(classLoader);
                  //Log.i("00000Heart", "商家服务收款到账cookie：" + str1);
                    //PayHelperUtils.sendmsg(context, "商家服务收款到账");
                    //PayHelperUtils.getTradeInfo(context, str1);
                	JSONObject jo1=new JSONObject(str2);
                	JSONObject bizMonitor=new JSONObject(jo1.getString("bizMonitor"));
                	JSONObject jo=new JSONObject();
                	jo.put("action", 104);
                	jo.put("money", jo1.get("mainAmount"));
                	jo.put("id", bizMonitor.get("id"));
                	jo.put("type", "alipay");
                	jo.put("time",new Date().getTime());
                	jo.put("payer", "商家收款");
                    //XposedBridge.log("*****************end1********************");
                	sendToMain(jo.toString(),false);
                  //System.out.println("支付宝日志：商家收到支付结果 ");
                } 
              } catch (Exception exception) {
                //PayHelperUtils.sendmsg(context, exception.getMessage());
              } 
              super.beforeHookedMethod(param1MethodHookParam);
            }
          });
      
      XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRActivity", classLoader), "b", new XC_MethodHook() {
    	  protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    		  //XposedBridge.log("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
    		  Object consultSetAmountRes = param.args[0];
    		  String consultSetAmountResString = "";
    		  if (param.args[0]!=null) {
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
      return;
    } catch (Error error) {
    
    } catch (Exception exception) {
    	exception.printStackTrace();
    }
    
  }

  

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
