package com.qianyou.listener5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.qianyou.nat.GetJsonData;
import com.qianyou.nat.Listener;
import com.qianyou.utils.UrlPost;

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
			JSONObject jobject=new JSONObject((String) js);
			int act=jobject.getInt("action");
			//Log.T("js="+js);
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
								if(type.equals("wx_scan"))
								{
									qudao="微信　：";
									if(name.equals(instance.msels.get("wx_scan")))
									{
										cb.setChecked(true);
									}
								}
								if(type.equals("wx_skd"))
								{
									qudao="微信1　：";
									if(name.equals(instance.msels.get("wx_skd")))
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
								else if(type.equals("alipay_guma"))
								{
									qudao="支付宝固码：";
									if(name.equals(instance.msels.get("alipay_guma")))
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
								else if(type.equals("douyin"))
								{
									qudao="抖音：";
									if(name.equals(instance.msels.get("douyin")))
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
				//instance.listener.sendJson(jo.toString(), "");
				//Log.T(jo.toString());
				String ret = jobject.getString("ret");
				String alipayerror = jobject.getString("alipayerror");
				//if (!ret.equals("NA")) {
					instance.listener.sendJson(jo.toString(), "");
				//}
				//Log.T(ret);
				if(ret.equals("OK"))
				{
					Log.T("已经付款,"+MainActivity.TIMEString);
				}
				if(ret.equals("OVER"))
				{
					Log.T("超过代付限额,"+MainActivity.TIMEString);
				}
				if(ret.equals("SNCH"))
				{
					Log.T("店铺收款风控,"+MainActivity.TIMEString);
				}
				if(ret.equals("RM"))
				{
					Log.T("订单风控,"+MainActivity.TIMEString);
				}
				if(ret.equals("NA"))
				{
					NANum++;
					Log.T("检测出现问题，情况未知,"+MainActivity.CHECKTYPE+","+MainActivity.TIMEString);
//					try {
//						Thread.sleep(2000);
//						instance.openz();
//						//Log.T("aaa");
//						Thread.sleep(3000);
//						//Log.T("bbbb");
//						SystemHelper.setTopApp(instance);
//						instance.listener.sendJson(jo.toString(), "");
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
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
					Log.T("还没付款，可以支付,"+MainActivity.TIMEString);
				}
				if(ret.equals("INV"))
				{
					Log.T("无效的订单，订单已取消或其他情况,"+MainActivity.TIMEString);
				}
				if (alipayerror.equals("ERRORALIPAY")) {
					Log.T("支付宝出现问题");
					MainActivity.instance.warnZFB();
					//System.exit(0);
				}
			}
			if(act==MainActivity.SEND && instance.isBand==true)
			{
//				Log.T(jobject.toString());
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
					if(type.equals("wx_scan")&&(title.equals("微信支付")||title.equals("微信收款助手")))
					{
						Log.T("微信收款"+AliPayHook.getTextCenter(data, "支付收款", "元")+"元");
						JSONObject jo=new JSONObject();
						jo.put("act", "CHONGZHI");
						jo.put("raw", data);
						jo.put("money", AliPayHook.getTextCenter(data, "支付收款", "元"));
						jo.put("type", type);
						instance.listener.sendJson(jo.toString(), "");
						//instance.listener.send(data);
						isapp=true;
					}
					if(type.equals("wx_scan")&&title.equals("微信收款商业版"))
					{
						Log.T(data);
						String pattern = "(\\d+)(\\.)(\\d+)";
						Pattern r = Pattern.compile(pattern);
					    Matcher m = r.matcher(data);
					    String money = "0";
					    if (m.find()) {
							money = m.group(0);
						}
						JSONObject jo=new JSONObject();
						jo.put("act", "CHONGZHI");
						jo.put("raw", data);
						jo.put("money", money);
						jo.put("type", type);
						//Log.T(jo.toString());
						instance.listener.sendJson(jo.toString(), "");
						//instance.listener.send(data);
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
//					Log.T(type+isapp);
					if(isapp==false&&type.equals("bank"))
					{
						//Log.T(jobject.toString());
//						if(!data.equals(MainActivity.BANKDATA)) //双重接收保证udp接收成功率
//						{
//							Log.T("check="+instance.listener.checkdx(data,title));
//							if(instance.listener.checkdx(data,title)==1)
//							{
//								MainActivity.BANKDATA=data;
//								Log.T(data);
//								instance.listener.senddx(data,title);
//							}else {
//								instance.listener.sendJson(jobject.toString(), "");
//							}
//							
//						}
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
				//Log.T(jobject.toString());
				String url=jobject.getString("market_url");
				//Log.T("链接="+url);
				instance.openXYPage(url,new JS() {
					
					@Override
					@JavascriptInterface
					public void showSource(String html) {
						// TODO Auto-generated method stub
						//Log.T(html);
						Listener.sendHtml(html);
					}
				});
			}
			if(act==MainActivity.CHECKWEBXY)
			{
				//Log.T(jo.toString());
				String ret = jobject.getString("ret");
				//Log.T(ret);
				if(ret.equals("OK"))
				{
					Log.T("闲鱼:已经付款");
				}
				if(ret.equals("NA"))
				{
					Log.T("闲鱼:检测出现问题，情况未知");
				}
				if(ret.equals("FAIL"))
				{
					Log.T("闲鱼:还没付款，可以支付");
				}
				if(ret.equals("INV"))
				{
					Log.T("闲鱼:无效的订单，订单已取消或其他情况");
				}
			}
			if(act==MainActivity.CHECKYZ)
			{
				//Log.T(jobject.toString());
				String url=jobject.getString("qrcurl");
				//Log.T("链接="+url);
				instance.openXYPage(url,new JS() {
					
					@Override
					@JavascriptInterface
					public void showSource(String html) {
						// TODO Auto-generated method stub
						instance.listener.sendYZHtml(html);
					}
				});
			}
			if(act==MainActivity.REYOUZANPAY)
			{
				//Log.T(jo.toString());
				String ret = jobject.getString("ret");
				//Log.T(ret);
				if(ret.equals("OK"))
				{
					Log.T("有赞:已经付款");
				}
				if(ret.equals("NA"))
				{
					Log.T("有赞:检测出现问题，情况未知");
				}
				if(ret.equals("FAIL"))
				{
					Log.T("有赞:还没付款，可以支付");
				}
				if(ret.equals("INV"))
				{
					Log.T("有赞:无效的订单，订单已取消或其他情况");
				}
			}
			if (act==MainActivity.CHECKBDZHF) {
				String msg = jobject.getString("msg");
				instance.print("绑定支付宝成功");
				if (instance.isBDZFBoolean==false) {
					Log.T(msg);
					instance.isBDZFBoolean=true;
				}
				if (msg.equals("可以产码")) {
					JSONObject jo=new JSONObject();
					jo.put("act","GEMACHANMA");
					instance.listener.sendJson(jo.toString(), "");
					Log.T("c:1");
				}
			}
			if(act==MainActivity.REYOUZANPAY1)
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
					jobject.put("action", 111);
					Utils.sendToCapture(jobject.toString(),false);
				}
			}
			if (act==MainActivity.CHECKDD) {
				Log.T(jobject.toString());
				String msg = jobject.getString("msg");
				Log.T(msg);
			}
			if(act==MainActivity.YZSEND)
			{
				SystemHelper.setTopApp(instance);
				JSONObject jo=new JSONObject();
				jo.put("act", "REYOUZANPAY1");
				jo.put("ret", jobject.get("ret"));
				jo.put("money", jobject.get("money"));
				jo.put("title", jobject.get("title"));
				instance.listener.sendJson(jo.toString(), "");
				//Log.T(jo.toString());
				String ret = jobject.getString("ret");
				String alipayerror = jobject.getString("alipayerror");
				//Log.T(ret);
				if(ret.equals("OK"))
				{
					Log.T("yz:已经付款");
				}
				if(ret.equals("NA"))
				{
					NANum++;
					Log.T("yz:检测出现问题，情况未知");
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
					Log.T("yz:还没付款，可以支付");
				}
				if(ret.equals("INV"))
				{
					Log.T("yz:无效的订单，订单已取消或其他情况");
				}
				if (alipayerror.equals("ERRORALIPAY")) {
					Log.T("yz:支付宝出现问题");
					System.exit(0);
				}
			}
			if (act==MainActivity.QRCODESEND) {
				String qrcode = jobject.getString("qrcode"); 
				String money = jobject.getString("money");
				//Log.T("个码获取="+jobject.toString());
				//Log.T("boolean="+qrcode.equals(MainActivity.zfbqrcode));
				//Log.T("MainActivity.zfbqrcode="+MainActivity.zfbqrcode);
				if (!MainActivity.zfbqrcode.equals(qrcode)) {
					SystemHelper.setTopApp(instance);
					MainActivity.zfbqrcode = qrcode;
					JSONObject jObject2 = new JSONObject();
					jObject2.put("money", money);
					jObject2.put("content", qrcode);
					jObject2.put("device_id", MainActivity.deviceID);
					jObject2.put("act", "REALGEMAPRO");
					jObject2.put("ret", "OK");
					instance.post(jObject2);
					
				}
			}	
			if (act==MainActivity.ALGEMAPRO) {
				//Log.T(jobject.toString());
				String money = jobject.getString("money");
				int device_id = jobject.getInt("device_id");
				MainActivity.deviceID = device_id;
				//instance.fixedAmount(money);
			}
			if (act==MainActivity.CHECKZFBGM) {
				//Log.T(jobject.toString());
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
					jobject.put("action", 112);
					Utils.sendToCapture(jobject.toString(),false);
				}
			}

			if (act==MainActivity.CHECKZKL) {
				Log.T(jobject.toString());
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
					MainActivity.instance.copy(jobject.getString("qrcurl"));
					jobject.put("action", 113);
					Utils.sendToCapture(jobject.toString(),false);
				}
			}
			if (act==MainActivity.ZFBGMSEND) {
				SystemHelper.setTopApp(instance);
				//Log.T(jobject.toString());
				String ret = jobject.getString("ret");
				//Log.T(ret);
				JSONObject jo = new JSONObject();
				jo.put("act", "REASCHECK");
				jo.put("ret", jobject.get("ret"));
				//Log.T(jo.toString());
				Listener.sendJson(jo.toString(), "");
				if(ret.equals("OK"))
				{
					Log.T("个码:已经付款");
				}
				if(ret.equals("NA"))
				{
					Log.T("个码:检测出现问题，情况未知");
				}
				if(ret.equals("FAIL"))
				{
					Log.T("个码:正常");
				}
				if(ret.equals("INV"))
				{
					Log.T("个码:有问题");
				}
			}
			
			if (act==MainActivity.OPENCVMSG) {
				Log.T(jobject.toString());
			}
			//抖音产码
			if (act==MainActivity.DOUYINPCODE) {
				MainActivity.DYMONEY = jobject.getString("money");
				int num = jobject.getInt("num");
				MainActivity.DYTOAL = num;
				MainActivity.DYNUM=1;
				Log.T("开始产码,金额"+MainActivity.DYMONEY+"元"+num+"个");
				MainActivity.instance.orderDouYin();
			}
			//抖音回调
			if (act==MainActivity.DOUYINRESULT) {
				String url = jobject.getString("qrcurl");
				MainActivity.instance.DYCheck(url);
			}
			//获取deviceid
			if (act==MainActivity.REBINDDEVICE) {
				//Log.T(jobject.toString());
				String deviedId = AliPayHook.getTextCenter(jobject.toString(), "deviceid\":", ",");
				MainActivity.BDEVICEID = deviedId;
			}
			if (act==MainActivity.BANKRESULT) {
				//Log.T(jobject.toString());
				String data=jobject.getString("data");
				String title=jobject.getString("title");
				if(!data.equals(MainActivity.BANKDATA)) //双重接收保证udp接收成功率
				{
					//Log.T("check="+instance.listener.checkdx(data,title));
					if(instance.listener.checkdx(data,title)==1)
					{
						MainActivity.BANKDATA=data;
						Log.T(data);
						instance.listener.senddx(data,title);
					}else {
						instance.listener.sendJson(jobject.toString(), "");
					}
					
				}
			}
			if (act==MainActivity.KAOLAGOODQUERY) {
				//Log.T(jobject.toString());
				String orderid = jobject.getString("orderid");
//				JSONObject jo=new JSONObject();
//				try {
//					jo.put("act","REKAOLAJC");
//					jo.put("orderid",orderid);
//					jo.put("cardNum", "123456789");
//					jo.put("codeNum","987654321");
//					jo.put("money", "9.1");
//					jo.put("ret","OK");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Log.T(jo.toString());
//				Listener.sendJson(jo.toString(), "");
				if (orderid.isEmpty()) {
					Log.T("orderid为空");
					JSONObject jo=new JSONObject();
					try {
						jo.put("act","REKAOLAJC");
						jo.put("orderid",orderid);
						jo.put("cardNum", "");
						jo.put("codeNum","");
						jo.put("money", "");
						jo.put("ret","NA");
						jo.put("raw","orderid为空");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.T(jo.toString());
					Listener.sendJson(jo.toString(), "");
				}else {
					UrlPost.KLGoodsQuery(jobject);
				}
			}
			if(act==MainActivity.EXIT)
			{
				Log.T(jobject.toString());
				instance.finish();  
		         System.exit(1);  
		         android.os.Process.killProcess(android.os.Process.myPid());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
