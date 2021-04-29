package com.qianyou.nat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;

import com.qianyou.chajian.AliPayHook;
import com.qianyou.listener5.Log;
import com.qianyou.listener5.MainActivity;
import com.qianyou.listener5.R;
import com.qianyou.listener5.R.string;
import com.qianyou.listener5.SLog;

public class Listener {
	
	public static int dengludjs=0;
	public static String account=null;
	public static String password=null;
	public static String deviceid=null;
	public static Boolean isDuanKai=false;
	static {
		System.loadLibrary("listener");
		}
	public static native int init();
	public static native int send(String data);
	public static native int senddx(String data,String title);
	public static native int setEWM(String imgpath);
	public static native int setID(String id);
	public static native int login(String account,String password);
	public static native int checkdx(String data,String title);
	public static native int sendHtml(String html);
	public static native int sendYZHtml(String html);
	public static native void destory();
	public static native String sendJson(String json,String ract);
	public static native int getIpAddress(String address);
	public Listener(){
		_init();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
				{
					if(dengludjs==0)
					{
						dl();
					}
					else
					{
						if(dengludjs>0)
						{
							Log.T(String.valueOf(dengludjs)+"秒后自动登陆，请稍后");
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					dengludjs--;
				}
				
				
			}
		}).start();
	}
	public void _init()
	{
		while(true)
		{
			String ipaddress = parseHostGetIPAddress();
			//Log.T("aaaaaaa="+ipaddress);
			int aa = getIpAddress(ipaddress);
			int r=init(); //第一次连接，连接成功则继续
			boolean ret=(r==1);
			if(ret)
			{
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.T("服务器连接成功");
				
				isDuanKai=false;
				if(account!=null&&password!=null)
				{
					while(true)
					{
						int ret=login(account,password);
						if(ret==1)
						{
							Log.T("登陆成功");
							if(deviceid!=null)
							{
								Log.T("登陆成功后自动接单:"+deviceid);
								int jd=setID(deviceid);
								if(jd==1)
								{
									Log.T("自动接单成功");
								}
								else
								{
									Log.T("自动接单失败");
								}
							}
							break;
						}
						else if(ret==0)
						{
							Log.T("密码错误，请退出应用后重新登陆");
							break;
						}
						else if(ret==-1)
						{
							Log.T("登陆超时，正在尝试重新登陆");
							
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							destory();
							_init();
							break;
						}
						else if(ret==-2)
						{
							Log.T("该账号被封禁，请退出应用后重新登陆");
							break;
						}
						else
						{
							Log.T("未知错误");
							break;
						}
					}
					
				}
			}
		}).start();
	}
	public static void wantdl()
	{
		Random r = new Random();
        int b = r.nextInt(6) + 6;
		dengludjs=b;
	}
	public static void dl()
	{
		if(account!=null&&password!=null)
		{
			while(true)
			{
				int ret=login(account,password);
				if(ret==1)
				{
					Log.T("登陆成功");
					MainActivity.instance.zaixian();
					if(deviceid!=null)
					{
						Log.T("断线重连后自动接单:"+deviceid);
						int jd=setID(deviceid);
						if(jd==1)
						{
							Log.T("自动接单成功");
						}
						else
						{
							Log.T("自动接单失败");
						}
					}
					break;
				}
				else if(ret==0)
				{
					Log.T("密码错误，请退出应用后重新登陆");
					break;
				}
				else if(ret==-1)
				{
					Log.T("登陆超时");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				else if(ret==-2)
				{
					Log.T("该账号被封禁，请退出应用后重新登陆");
					break;
				}
				else
				{
					Log.T("未知错误");
					break;
				}
			}
			
		}
	}
	public static void onClose()
	{
		MainActivity.instance.diaoxian();
		
		Runnable rb=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				isDuanKai=true;
				boolean connectret=false;
				Log.T("服务器已经断开，正在重连中。。。");
				String ipaddress = parseHostGetIPAddress();
				int aaString = getIpAddress(ipaddress);
				connectret=(init()==1);
				if(connectret==false)
				{
					//Log.T("connectret false");
					try {
						Random r = new Random();
				        int b = r.nextInt(10) + 6;
						Thread.sleep(b*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					onClose();
					return;
				}
				
				Log.T("服务器连接成功");
				isDuanKai=false;
				wantdl();
			}
		};
		new Thread(rb).start();
		
	}
//	public static void check1(){
//		try {
//			Log.T(executeCmd("ping -c 1 -i 0.2 baidu.com"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
	public static void check2(){
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					String host=MainActivity.instance.host;
//					Log.T(executeCmd("ping -c 3 -i 0.2 "+host));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}).start();
		
    }
	private static String executeCmd(final String cmd) throws IOException,
		    InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec(cmd);
		// 一定要调用waitFor,因为ping的返回不是即时的。
		int wF = proc.waitFor();
		BufferedReader bufferedreader = new BufferedReader(
		        new InputStreamReader(proc.getInputStream()));
		BufferedReader errorbuffer = new BufferedReader(new InputStreamReader(
		        proc.getErrorStream()));
		String line = "";
		StringBuilder sb = new StringBuilder(line);
		while ((line = bufferedreader.readLine()) != null) {
		    sb.append(line);
		    sb.append('\n');
		}
		while ((line = errorbuffer.readLine()) != null) {
		    sb.append(line);
		    sb.append('\n');
		}
		return sb.toString();
	}
	public static void log(String l)
	{
		Log.T(l);
	}
	public static String getDevices() throws JSONException
	{
		JSONObject jo=new JSONObject();
		jo.put("act", "GETDEVICE");
		String json=jo.toString();
		String data=sendJson(json,"REGETDEVICE");
		return data;
	}
	public static Boolean rmDevices() throws JSONException
	{
		JSONObject jo=new JSONObject();
		jo.put("act", "DELDEVICE");
		String data=sendJson(jo.toString(), "REDELDEVICE");
		Listener.deviceid=null;
		JSONObject jret=new JSONObject(data);
		if("OK".equals(jret.getString("ret")))
		{
			return true;
		}
		return false;
	}
	public static void diaoXian(String str,String type) throws JSONException
	{
		JSONObject jo=new JSONObject();
		JSONObject jod=new JSONObject();
		jo.put("act", "DIAOXIAN");
		jod.put("type", type);
		jod.put("info", str);
		jo.put("data", jod);
		sendJson(jo.toString(), "REDIAOXIAN");
	}
	public static String parseHostGetIPAddress() {
		String host = MainActivity.instance.getResources().getString(R.string.server);
		host = AliPayHook.getTextCenter(host, "//", "/");
		//Log.T(host);
        String[] ipAddressArr = null;
        try {
            InetAddress[] inetAddressArr = InetAddress.getAllByName(host);
            if (inetAddressArr != null && inetAddressArr.length > 0) {
                ipAddressArr = new String[inetAddressArr.length];
                for (int i = 0; i < inetAddressArr.length; i++) {
                    ipAddressArr[i] = inetAddressArr[i].getHostAddress();
                    //Log.T("iiiiiip"+i+":=="+ipAddressArr[i]);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        //Log.T("qq="+ipAddressArr[0]);
        //return ipAddressArr[0];
        //return "192.168.1.106";
        //return "159.138.132.57"; //23ee
        return "119.8.51.214";  //xf
    }

}
