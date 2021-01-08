package com.qianyou.listener5;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.nat.Listener;

public class NLService extends NotificationListenerService {
        private String TAG="NLService";
        private String posturl=null;
        public static NLService service=null;
        public Boolean isok=false;
        private DoNotifications doNotifications=new DoNotifications();
        public static final int PORT = 5457;
		public Handler rhandler=new Handler(new Handler.Callback() {
				
				@Override
				public boolean handleMessage(Message msg) {
					// TODO Auto-generated method stub
				((Runnable)msg.obj).run();
				return false;
			}
		});
	
		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			service=this;
			
			JSONObject jo=new JSONObject();
			try {
				this.isok=true;
				jo.put("action", MainActivity.OK);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendToMain(jo.toString(),true);
			startSvr();
			print("已就绪");
			return super.onBind(intent);
		}



		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		}
		public static void startSvr()
	    {
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					DatagramSocket socket = null;
					try {
						socket = new DatagramSocket(NLService.PORT);
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
					    		if(act==MainActivity.OK)
					    		{
					    			JSONObject jo=new JSONObject();
					    			jo.put("action", MainActivity.OK);
					    			sendToMain(jo.toString(),true);
					    		}
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
						DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getByName("127.0.0.1"), MainActivity.PORT);
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
		public static void print(final String str)
		{

			Message msg=service.rhandler.obtainMessage();
			msg.obj=new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					final NotificationManager manager = (NotificationManager) service.getSystemService(NOTIFICATION_SERVICE);
					String contentTitle = "铃铛管家"; //标题
			        String contentText = str;//内容
			        NotificationCompat.Builder builder=new NotificationCompat.Builder(service);
			        builder.setContentTitle(contentTitle);
			        builder.setContentText(contentText);
			        builder.setWhen(System.currentTimeMillis());
			        builder.setSmallIcon(R.drawable.ic_launcher);
			        builder.setLargeIcon(BitmapFactory.decodeResource(service.getResources(), R.drawable.ic_launcher));
			        Notification notification=builder.build();
			        manager.notify(1, notification);
				}
			};
			service.rhandler.sendMessage(msg);
		}
		public static void doMsg(String msg)
		{
			JSONObject jobject=new JSONObject();
			int act=0;
			try {
				act=jobject.getInt("action");
				if(act==MainActivity.OK)
				{
					if(service.isok==true)
					{
						JSONObject jo=new JSONObject();
						try {
							
							jo.put("action", MainActivity.OK);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sendToMain(jo.toString(),false);
					}
				}
				if(act==MainActivity.CLEARALLNOTIFICATION)
				{
					clearNotification();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public class MyReceiver extends BroadcastReceiver {
		     @Override
		     public void onReceive(Context context, Intent intent) {
					Bundle bundle=intent.getExtras();
					int act=bundle.getInt("action");

					if(act==MainActivity.OK)
					{
						Intent intent2=new Intent();
						intent2.putExtra("action", MainActivity.OK);
						intent2.setAction("com.qianyou.listener5.TMMsg");
						NLService.service.sendBroadcast(intent2);
					}
					if(act==MainActivity.EXIT)
					{
						Intent intent2=new Intent();
						intent2.putExtra("action", MainActivity.EXIT);
						intent2.setAction("com.qianyou.listener5.TMMsg");
						NLService.service.sendBroadcast(intent2);
						
					}
					
				}
		    }

        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {
                //        super.onNotificationPosted(sbn);
                //这里只是获取了包名和通知提示信息，其他数据可根据需求取，注意空指针就行
                
        		
                Notification notification = sbn.getNotification();
                String pkg = sbn.getPackageName();
                if (notification == null) {
                        return;
                }

                Bundle extras = notification.extras;
                if(extras==null)
                        return;

                String time=getNotitime(notification);
                String title=getNotiTitle(extras);
                String text=getNotiContent(extras);
                
                doNotifications.doN(pkg,title,text,time);
        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {
//                if (Build.VERSION.SDK_INT >19)
//                        super.onNotificationRemoved(sbn);
        }
        
        private void sendToast(String msg){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }

        private String getNotitime(Notification notification){

                long when=notification.when;
                Date date=new Date(when);
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String notitime=format.format(date);
                return notitime;
        }

        private String getNotiTitle(Bundle extras){
                String title=null;
                // 获取通知标题
                title = extras.getString(Notification.EXTRA_TITLE, "");
                return title;
        }

        private String getNotiContent(Bundle extras){
                String content=null;
                // 获取通知内容
                content = extras.getString(Notification.EXTRA_TEXT, "");
                return content;
        }

        private void printNotify(String notitime,String title,String content){
                Log.d(TAG,notitime);
                Log.d(TAG,title);
                Log.d(TAG,content);
        }

        @SuppressLint("NewApi")
		public static void clearNotification()
        {
        	StatusBarNotification sbn[]=service.getActiveNotifications();
        	int num=sbn.length;
        	if(num>=30)
        	{
            	new Thread(new Runnable() {
    				
    				@Override
    				public void run() {
    					// TODO Auto-generated method stub
    					try {
    						Thread.sleep(5000);
    			        	service.rhandler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									service.cancelAllNotifications();
								}
							});
    					} catch (InterruptedException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    				}
    			}).start();
        	}

        }

}
