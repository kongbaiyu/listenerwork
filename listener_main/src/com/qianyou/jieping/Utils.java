package com.qianyou.jieping;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.listener5.Log;
import com.qianyou.listener5.MainActivity;
import com.qianyou.listener5.NLService;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.widget.Toast;

public class Utils {
	public static List<Thread> threads=new ArrayList<Thread>();
	public static void startCaptureSvr()
    {
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket(5459);
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
							Action.doAct(act,jobject);
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
	    threads.add(t);
    }
	public static void startMainSvr()
    {
    	
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
					
					try {
						DatagramSocket svrsocket = new DatagramSocket(MainActivity.PORT);
				    	while(true)
				    	{
				    		DatagramPacket packet = new DatagramPacket(new byte[2048], 2048);
				        	try {
				        		svrsocket.receive(packet);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								break;
							}
				        	byte[] arr = packet.getData();
				        	int len = packet.getLength();
				        	String data=new String(arr,0,len);
				        	
				    		Message msg=MainActivity.instance.shandler.obtainMessage();
				    		msg.obj=data;
				    		MainActivity.instance.shandler.sendMessage(msg);
				    		
				    	}
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						//Toast.makeText(MainActivity.instance, "无法打开端口,请退出再重新打开", Toast.LENGTH_LONG).show();
						e1.printStackTrace();
					}
					//print("已就绪");
			}
		});
	    t.start();
	    threads.add(t);
    }
    public static void sendToService(final String data)
	{
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					DatagramSocket socket = new DatagramSocket();
					DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getByName("127.0.0.1"), NLService.PORT);
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
			        
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    t.start();
	}
	public static void sendToCapture(final String data,final Boolean isLoop)
	{
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					DatagramSocket socket = new DatagramSocket();
					DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getByName("127.0.0.1"), 5459);
					socket.send(packet); 
					socket.close();
			        
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    t.start();
	}
	public static boolean openZFB(String url)
	{
		//Log.T(url);
		boolean ret=false;
		try {
			Uri uri = Uri.parse("alipays://platformapi/startapp?appId=20000067&url="+URLEncoder.encode(url,"utf-8"));
		    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    MainActivity.instance.startActivity(intent);
		    ret=true;
		}catch(Exception e){
			ret=false;
		}
		return ret;
	}
	public static boolean openZFB2(String url)
	{
		//Log.T(url);
		boolean ret=false;
		try {
			Uri uri = Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+URLEncoder.encode(url,"utf-8"));
		    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    MainActivity.instance.startActivity(intent);
		    ret=true;
		}catch(Exception e){
			ret=false;
		}
		return ret;
	}
	public static boolean openyzZFB(String url)
	{
		//Log.T(url);
		boolean ret=false;
		try {
			Uri uri = Uri.parse(url);
		    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    MainActivity.instance.startActivity(intent);
		    ret=true;
		}catch(Exception e){
			ret=false;
		}
		return ret;
	}
	public static void openURL(String uri){
        Intent intent = new Intent(); //Intent
        Uri content_url = Uri.parse(uri);
        intent = new Intent(Intent.ACTION_VIEW, content_url);
        intent.setAction("android.intent.action.VIEW");
        intent.setData(content_url); 
        MainActivity.instance.startActivity(intent);
    }
    public static void clearNotification()
    {
    	JSONObject jo=new JSONObject();
    	try {
			jo.put("action", MainActivity.CLEARALLNOTIFICATION);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Utils.sendToService(jo.toString());
    }
}
