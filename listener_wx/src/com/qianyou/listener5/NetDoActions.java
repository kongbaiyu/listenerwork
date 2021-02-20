package com.qianyou.listener5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.CaseMap.Title;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.qianyou.chajian.AliPayHook;
import com.qianyou.jieping.Capture;
import com.qianyou.jieping.Utils;
import com.qianyou.listener5.MainActivity.JS;
import com.qianyou.nat.Listener;

public class NetDoActions {
	MainActivity instance;
	int NANum=0;
	
	public NetDoActions(MainActivity instance)
	{
		this.instance=instance;
	}
	public void doAct(String js)
	{
		try {
//			if (js.contains("116")) {
//				Log.T("js="+js.length());
//			}
			JSONObject jobject=new JSONObject(js);
			int act=jobject.getInt("action");
			//Log.T("jsssssssssssss="+act);
			if(act==MainActivity.GETDEVICE)
			{
				String data=jobject.getString("data");
				try {
					JSONObject job = new JSONObject(data);
					if(job.has("dlist")==false)
					{
						if(instance.listView!=null)instance.listView.setAdapter(null);
					}
					final JSONArray ja=job.getJSONArray("dlist");
					
				
					ListAdapter adapter=new BaseAdapter() {
						
						@Override
						public View getView(int position, View convertView, ViewGroup parent) {
							// TODO Auto-generated method stub
							LinearLayout ll=instance.allit.get(position);
							if(ll==null)
							{
								ll=(LinearLayout)LayoutInflater.from(instance).inflate(R.layout.item, null);
								instance.allit.put(position, ll);
							}
							
							try {
								final JSONObject jo=ja.getJSONObject(position);
								final CheckBox cb=(CheckBox) ll.findViewById(R.id.checkBox1);
								final String type=jo.getString("type");
								final String name=jo.getString("name");
								final int freeze=jo.getInt("freeze");
								if(freeze==0)
								{
									cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
										
										@Override
										public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
											// TODO Auto-generated method stub
											if(isChecked)
											{
												
												if(instance.mcsels.get(type)!=null)
												{
													instance.mcsels.get(type).setChecked(false);
												}
												instance.mcsels.put(type, cb);
												instance.msels.put(type, name);
											}
											else
											{
												instance.msels.put(type, "");
												instance.mcsels.put(type, null);
											}
											
										}
									});
								}
								if(instance.isBand==true)
								{
									cb.setEnabled(false);
								}
								String qudao="";
								if(type.equals("wx"))
								{
									qudao="微信　：";
									if(name.equals(instance.msels.get("wx")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("alipay"))
								{
									qudao="支付宝：";
									if(name.equals(instance.msels.get("alipay")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("alipay_scan"))
								{
									qudao="支付宝扫码：";
									if(name.equals(instance.msels.get("alipay_scan")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("alipay_xqd"))
								{
									qudao="小钱袋：";
									if(name.equals(instance.msels.get("alipay_xqd")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("fsj"))
								{
									qudao="丰收家：";
									if(name.equals(instance.msels.get("fsj")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("youzan"))
								{
									qudao="有赞：";
									if(name.equals(instance.msels.get("youzan")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("xianyu_trans"))
								{
									qudao="闲鱼：";
									if(name.equals(instance.msels.get("xianyu_trans")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("wangxin"))
								{
									qudao="旺信：";
									if(name.equals(instance.msels.get("wangxin")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("bank"))
								{
									qudao="银行　：";
									if(name.equals(instance.msels.get("bank")))
									{
										cb.setChecked(true);
									}
								}
								else if(type.equals("xianyu_check"))
								{
									qudao="检测　：";
									if(name.equals(instance.msels.get("xianyu_check")))
									{
										cb.setChecked(true);
									}
								}
								if(freeze==0)
								{
									cb.setText(qudao+"  "+name);
									cb.setClickable(true);
								}
								else
								{
									cb.setClickable(false);
									String str=qudao+"  "+name + " (封禁)";
									SpannableString ss=new SpannableString(str);
									ss.setSpan(new ForegroundColorSpan(Color.GRAY), 0, str.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
									cb.setText(ss);
								}
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							return ll;
						}
						
						@Override
						public long getItemId(int position) {
							// TODO Auto-generated method stub
							return position;
						}
						
						@Override
						public Object getItem(int position) {
							// TODO Auto-generated method stub
							JSONObject ret=null;
							try {
								ret=ja.getJSONObject(position);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return ret;
						}
						
						@Override
						public int getCount() {
							// TODO Auto-generated method stub
							return ja.length();
						}
					};
					instance.listView.setAdapter(adapter);
					//Log.T("数据加载完成");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(act==MainActivity.LOG_T)
			{
				Log.T(jobject.getString("text"));
			}
			if(act==MainActivity.CHECK)
			{
				if(instance.isCheck==false)
				{
					Log.T("开启文字识别模式");
					instance.isCheck=true;
					instance.startCapture();
				}
			}
			if(act==MainActivity.CHECKCAPTUREISOK)
			{
				if(instance.isCaputreOk==false)
				{
					JSONObject jo=new JSONObject();
					jo.put("act","REXYPAY");
					jo.put("ret", "NA");
					instance.listener.sendJson(jo.toString(), "");
					Log.T("服务未开启");
				}
				else
				{
					jobject.put("action", 110);
					Utils.sendToCapture(jobject.toString(),false);
				}
			}
			if(act==MainActivity.LOG_CLEAR)
			{
				Log.Clear();
			}
			if(act==MainActivity.XPSEND && instance.isBand==true)
			{
				String id=jobject.getString("id");
				if(!instance.xpitems.containsKey(id))
				{
					instance.xpitems.put(id,Long.parseLong(jobject.getString("time")));
					String money=jobject.getString("money");
					String type=jobject.getString("type");
					String payer=jobject.getString("payer");
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                Date date = new Date();
					Log.T(type+": 收款"+money+"元");
					if(type.equals("alipay"))
					{
						JSONObject jo = new JSONObject();
						jo.put("act", "CHONGZHI");
						jo.put("money", money);
						jo.put("raw",ft.format(date)+"	"+ payer+",xposed-alipay:"+money+"元");
						jo.put("type", type);
						instance.listener.sendJson(jo.toString(), "");
						//listener.send("xponse-支付宝收款："+money+"元");
					}
					List<String> rms=new ArrayList<String>();
					for (Map.Entry<String,Long> it : instance.xpitems.entrySet()) {
						long ret=new Date().getTime()-it.getValue();
						if((ret/1000)>10) //超过10秒的直接清理
						{
							rms.add(it.getKey());
						}
					}
					for (int i = 0; i < rms.size(); i++) {
						instance.xpitems.remove(rms.get(i));
					}
					
				}
			}
			if(act==MainActivity.XYSEND)
			{
				SystemHelper.setTopApp(instance);
				JSONObject jo=new JSONObject();
				jo.put("act", "REXYPAY");
				jo.put("ret", jobject.get("ret"));
				jo.put("money", jobject.get("money"));
				jo.put("title", jobject.get("title"));
				instance.listener.sendJson(jo.toString(), "");
				Log.T(jo.toString());
				String ret = jobject.getString("ret");
				String alipayerror = jobject.getString("alipayerror");
				//Log.T(ret);
				if(ret.equals("OK"))
				{
					Log.T("已经付款");
				}
				if(ret.equals("NA"))
				{
					NANum++;
					Log.T("检测出现问题，情况未知	"+MainActivity.instance.isClickBoolean);
					if(NANum>=4)
					{
						NANum=0;
						instance.capture=new Capture(instance.mMpj);
					}
					else
					{
						SharedPreferences sp = instance.getSharedPreferences("UserData", Activity.MODE_PRIVATE);//创建sp对象
			            SharedPreferences.Editor editor = sp.edit() ;
			            editor.putInt("imagereaderformat", instance.ImageReaderFormat) ; 
			            editor.commit() ;//提交
						if (MainActivity.captureImageReaderFormatException==true)
						{
							NANum=0;
							instance.capture=new Capture(instance.mMpj);
						}
					}
				}
				else
				{
					NANum=0;
				}
				if(ret.equals("FAIL"))
				{
					Log.T("还没付款，可以支付");
				}
				if(ret.equals("INV"))
				{
					Log.T("无效的订单，订单已取消或其他情况");
				}
				if (alipayerror.equals("ERRORALIPAY")) {
					Log.T("支付宝没有响应");
					System.exit(0);
				}
			}
			if(act==MainActivity.SEND && instance.isBand==true)
			{
				String data=jobject.getString("data");
				String rid=jobject.getString("rid");
				if(instance.senddatas.containsKey(rid)) //双重接收保证udp接收成功率
				{
					instance.senddatas.remove(rid);//第二次接收到了重复的数据直接移除掉
				}
				else
				{
					instance.senddatas.put(rid, jobject);
					
					String type=jobject.getString("type");
					String title=jobject.getString("title");

					boolean isapp=false;
					if(type.equals("wx")&&title.equals("微信支付"))
					{
						Log.T(data);
						instance.listener.send(data);
						isapp=true;
					}
					if(type.equals("fsj")&&title.equals("丰收家商户"))
					{
						Log.T(data);
						instance.listener.send(data);
						isapp=true;
					}
//					if (type.equals("youzan")&&(title.equals("有赞微商城")||title.equals("收款提醒"))) {
//						Log.T(data);
//						instance.listener.send(data);
//						isapp=true;
//					}
					if(type.equals("bank")&&title.equals("交易提醒"))
					{
						Log.T(data);
						instance.listener.send(data);
						isapp=true;
					}
					if(type.equals("alipay")&&instance.isBDZFBoolean==false)
					{
						//Log.T("dddddd="+jobject.toString());
						if(instance.listener.checkdx(data, title)==1)
						{
							Log.T(data);
							instance.listener.senddx(data,title);
							isapp=true;
						}
					}
					if(type.equals("youzan"))
					{
						if(instance.listener.checkdx(data, title)==1)
						{
							Log.T(data);
							instance.listener.senddx(data,title);
							isapp=true;
						}
					}
					if(isapp==false&&type.equals("bank"))
					{
						if(instance.listener.checkdx(data,title)==1)
						{
							Log.T(data);
							instance.listener.senddx(data,title);
						}
					}
					if(title.equals("支付宝账号在其他设备登录")) //支付宝账号在其他设备登录
					{
						instance.listener.diaoXian(data,type);
						Log.T(data);
						instance.handler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								instance.stopJieDan();
							}
						});
					}
				}
				//以下是对只接收到一次的数据的清理
				List<String> rms=new ArrayList<String>();
				for (Map.Entry<String,JSONObject> it : instance.senddatas.entrySet()) {
					long ret=new Date().getTime()-it.getValue().getLong("time");
					if((ret/1000)>10) //超过10秒的直接清理
					{
						rms.add(it.getKey());
					}
				}
				for (int i = 0; i < rms.size(); i++) {
					instance.senddatas.remove(rms.get(i));
				}
				
			}
			if(act==MainActivity.OK)
			{
				Button loginbt=(Button) instance.findViewById(R.id.login);
				if(loginbt!=null)
				{
					Utils.clearNotification();
					loginbt.setText("登陆");
			    	loginbt.setEnabled(true);
				}
		    	
			}
			if(act==MainActivity.CAPTUREOK)
			{
				instance.isCaputreOk=true;
		    	
			}
			if(act==MainActivity.CHECKXY)
			{
				String url=jobject.getString("url");
				instance.openXYPage(url,new JS() {
					
					@Override
					@JavascriptInterface
					public void showSource(String html) {
						// TODO Auto-generated method stub
						instance.listener.sendHtml(html);
					}
				});
			}
			if (act==MainActivity.CHECKBDZHF) {
				String msg = jobject.getString("msg");
				if (instance.isBDZFBoolean==false) {
					Log.T(msg);
					instance.isBDZFBoolean=true;
				}
			}
			if (act==MainActivity.GETQRCODEURL) {
				String msg = jobject.getString("msg");
				if (instance.isGETQRCODEURL==false) {
					Log.T(msg);	
					SharedPreferences sp = instance.getSharedPreferences("UserData", Activity.MODE_PRIVATE);//创建sp对象
		            SharedPreferences.Editor editor = sp.edit() ;
		            editor.putString("qrcodeurl", msg) ; 
		            editor.commit() ;//提交
					instance.isGETQRCODEURL=true;
				}
			}
			if (act==MainActivity.GETZFBUID) {
				String msg = jobject.getString("msg");
				if (instance.isGETZFBUID==false) {
					SystemHelper.setTopApp(instance);
					Log.T("UID:"+msg);	
					CreateXQDAccount.setUID(msg);
					SharedPreferences sp = instance.getSharedPreferences("UserData", Activity.MODE_PRIVATE);//创建sp对象
		            SharedPreferences.Editor editor = sp.edit() ;
		            editor.putString("zfbuid", msg) ; 
		            editor.commit() ;//提交
		            instance.isGETZFBUID=true;
				}
			}
			if (act==MainActivity.CHECKBDWANGXIN) {
				String msg = jobject.getString("msg");
				if (instance.isBDWangXin==false) {
					Log.T(msg);
					instance.isBDWangXin=true;
				}
			}
			if (act==MainActivity.GETMSG) {
				//Log.T(jobject.toString());
				String mark = jobject.getString("mark");
				Log.T("mark="+mark);
				String orderNo = jobject.getString("orderNo");
				Log.T(orderNo);
				String qunId = jobject.getString("qunId");
				Log.T(qunId);
				String orderid = jobject.getString("orderid");
				Log.T(orderid);
				String money = jobject.getString("money");
				Log.T(money);
				String payUrl = jobject.getString("payUrl");
				Log.T(payUrl);
			}
			if (act==MainActivity.GETXIANYU) {
				String sessionid = jobject.getString("sessionid");
				String businessId = jobject.getString("businessId");
				String payeeNick = jobject.getString("payeeNick");
				Log.T("sessionid="+sessionid);
				Log.T("businessId="+businessId);
				Log.T("payeeNick="+payeeNick);
			}
			if (act==MainActivity.CREATEWXPAM) {
				Log.T("开始创建红包");
				int money = jobject.getInt("money");
				int num = jobject.getInt("num");
				MainActivity.wxCreatePam(money, num);
			}
			if (act==MainActivity.GETDEVICEID) {
				//Log.T("id获取="+jobject.toString());
				String deviedId = AliPayHook.getTextCenter(jobject.toString(), "deviceid\":", ",");
				MainActivity.BDEVICEID = deviedId;
				//Log.T(deviedId);
			}
			if (act==MainActivity.CREATEWXPURL) {
	        	//Log.T(jobject.toString());
				String mark = jobject.getString("mark");
				//Log.T("mark="+mark);
				String orderNo = jobject.getString("orderNo");
				//Log.T("orderNo"+orderNo);
				String qunId = jobject.getString("qunId");
				//Log.T("qunId"+qunId);
				String orderid = jobject.getString("orderid");
				//Log.T("orderid"+orderid);
				String money = jobject.getString("money");
				//Log.T("money"+money);
//				String a1 = jobject.getString("payUrl1");
//				String a2 = jobject.getString("payUrl2");
//				String a3 = jobject.getString("payUrl3");
				//MainActivity.wxCreatePUrl(jobject);
				//String payUrl = "service=\"alipay.fund.stdtrustee.order.create.pay\"&partner=\"2088401309894080\"&_input_charset=\"utf-8\"&notify_url=\"https://wwhongbao.taobao.com/callback/alipay/notifyPaySuccess.do\"&out_order_no=\""+a1+"\"&pay_strategy=\"CASHIER_PAYMENT\"&receipt_strategy=\"INNER_ACCOUNT_RECEIPTS\"&platform=\"DEFAULT\"&channel=\"APP\"&order_title=\"淘宝现金红包\"&master_order_no=\""+a2+"\"&order_type=\"DEDUCT_ORDER\"&auid=\"2088202332279574\"&extra_param=\"{\"payeeShowName\":\"淘宝现金红包\"}\"&pay_timeout=\"30m\"&order_expired_time=\"360d\"&sign=\""+a3+"\"&sign_type=\"RSA\"";
				String payUrl = jobject.getString("payUrl");
				//Log.T("payUrl"+payUrl);
				MainActivity.instance.wxCreatePUrl(mark, money, orderNo, payUrl, qunId, orderid);
			}
			if(act==MainActivity.EXIT)
			{
				instance.finish();  
		         System.exit(1);  
		         android.os.Process.killProcess(android.os.Process.myPid());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.T("error="+e.toString());
		}
	}
}
