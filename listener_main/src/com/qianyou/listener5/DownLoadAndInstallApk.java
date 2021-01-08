package com.qianyou.listener5;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;


import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DownLoadAndInstallApk {
	private Activity activity;
	private static String newVersion;
	private static String downloadUrl;
	Handler handler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Runnable rb=(Runnable) msg.obj;
			rb.run();
			return false;
		}
	});
	DownLoadAndInstallApk(Activity activity)
	{
		this.activity=activity;
	}
	public static String getRequest(String urlStr) throws Exception {
            URL url = new URL(urlStr);
            String ret="";
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();
            int rc=httpURLConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == rc) {
                //调用getInputStream后才开始正式发起请求
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                ret=stringBuilder.toString();
                //关闭流对像
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } else {
            	throw new Exception();
            }
			return ret;
    }
	public static boolean CheckUpdata() 
	{
		boolean ret=false;
//		try {
//    		String config=getRequest(MainActivity.instance.getResources().getString(R.string.server)+"/update/config.txt");
//			final JSONObject jo = new JSONObject(config);
//			final String version=jo.getString("version");
//			if(!version.equals(MainActivity.getVersion()))
//            {
//				ret=true;
//				MainActivity.instance.handler.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						final String url;
//						try {
//							url = jo.getString("url");
//							newVersion=version;
//							downloadUrl=url;
//							showDownLoadTip();
//							
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//            }
//            
//		} catch (Exception e) {
//			// TODO: handle exception
//		} 
		return ret;
	}
	private boolean isSDcardExist(){  
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  
	}
    private String getDowloadPath(){  
        return activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();  
    }  
    public static void showDownLoadTip()
    {
		AlertDialog.Builder builder =new Builder(MainActivity.instance);
    	builder.setTitle("版本更新");
    	builder.setMessage("请更新到版本:"+newVersion);
    	builder.setIcon(R.drawable.ic_launcher);
     	builder.setCancelable(false);
    	builder.setPositiveButton("直接下载", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DownLoadAndInstallApk dlaia=new DownLoadAndInstallApk(MainActivity.instance);
				dlaia.downLoad(downloadUrl);
			}
		});
    	builder.setNeutralButton("浏览器下载", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setData(Uri.parse(downloadUrl));//Url 就是你要打开的网址
				intent.setAction(Intent.ACTION_VIEW);
				MainActivity.instance.startActivity(intent); //启动浏览器
			}
		});
    	AlertDialog dlg=builder.create();
    	dlg.show();
    }
    /** 
     * 自己写的一个单线程下载 
     * 如果想要刷新要发handler（保证在UI线程中）， 
     * 利用状态isDownloading或isCancel来判断当前是否停止下载等操作 
     */  
    public void downLoad(String url){  

		final View view=RelativeLayout.inflate(this.activity, R.layout.download, null);
		final AlertDialog dlg=new AlertDialog.Builder(this.activity).setView(view).setCancelable(false).show();
		
        final String downloadUrl = url; 
    	SharedPreferences sp = activity.getSharedPreferences("UserData",Activity.MODE_PRIVATE);
    	if(sp.getBoolean("candownload", true)==false)
    	{
    		Intent intent=new Intent();
			intent.setData(Uri.parse(downloadUrl));//Url 就是你要打开的网址
			intent.setAction(Intent.ACTION_VIEW);
			activity.startActivity(intent); //启动浏览器
    		return;
    	}
        final String filePath = getDowloadPath()  +"/tmp.apk";  
        //自己开线程下载  
        new Thread(){  
            private InputStream inputStream;  
            private FileOutputStream fos;  
            public void run() {  
                try {  
                    final URL url = new URL(downloadUrl);  
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
                    conn.setRequestMethod("GET");  
                    conn.setConnectTimeout(30000);  
                    //http请求不要gzip压缩，否则获取的文件大小可以小于文件的实际大小  
                    conn .setRequestProperty("Accept-Encoding", "identity");   
                    int responseCode = conn.getResponseCode();  
                    if(responseCode == 200){  
                        inputStream = conn.getInputStream();  
                        File file = new File(filePath);  
                        fos = new FileOutputStream(file);  
                        int contentLength = conn.getContentLength();  
                        System.out.println("文件的大小：：" + contentLength);  
                        final double fileLengthFromHeader = Integer.parseInt(conn.getHeaderField("Content-Length"));  
                        System.out.println("根据头文件获取文件的大小：：" + fileLengthFromHeader);  
                          
                        byte[] buffer = new byte[1024000];  
                        int len = 0;  
                        while((len = inputStream.read(buffer)) != -1 ){   
                            fos.write(buffer, 0, len);  
                            
                            final double curlength = (double) file.length();   
                            //System.out.println("downed" + curlength/1024.0/1024.0+","+fileLengthFromHeader/1024.0/1024.0);
                            Message msg=handler.obtainMessage();
                            msg.obj=new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									TextView tv=(TextView) view.findViewById(R.id.textView2);
									tv.setText(""+((double)((int)(10000*curlength/fileLengthFromHeader)))/100.0+"%");
									ProgressBar pb=(ProgressBar) view.findViewById(R.id.firstBar);
									pb.setProgress((int)(100*curlength/fileLengthFromHeader));
									TextView tv2=(TextView) view.findViewById(R.id.textView3);
									tv2.setText(""+((double)((int)(100.0*curlength/1024.0/1024.0)))/100.0+"M/"+((double)((int)(100.0*fileLengthFromHeader/1024.0/1024.0)))/100.0+"M");
								}
							};
							handler.sendMessage(msg);
                        }  
                          
                        if(file.length() == contentLength){  
                            //下载完成   
                        	Message msg=handler.obtainMessage();
                            msg.obj=new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										DownLoadAndInstallApk.this.InstallAPK(filePath);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										SharedPreferences sp = activity.getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
										SharedPreferences.Editor editor = sp.edit() ;
							            editor.putBoolean("candownload", false) ;  
							            editor.commit() ;//提交
										Intent intent=new Intent();
										intent.setData(Uri.parse(downloadUrl));//Url 就是你要打开的网址
										intent.setAction(Intent.ACTION_VIEW);
										activity.startActivity(intent); //启动浏览器
										e.printStackTrace();
									}
								}
							};
							handler.sendMessage(msg);
                        }  
                    }else{  
                        Toast.makeText(activity, "访问服务器失败", 1).show();   
                    }  
                } catch (MalformedURLException e) {  
                    e.printStackTrace();  
                    System.out.println("MalformedURLException:" + e.getMessage());  
                } catch (IOException e2) {  
                    e2.printStackTrace();  
                    System.out.println("IOException:" + e2.getMessage());  
                }finally{  
                    try {  
                        if(inputStream != null){  
                            inputStream.close();  
                        }  
                          
                        if(fos != null){  
                            fos.close();  
                        }  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                        System.out.println("IOException:" + e.getMessage());  
                    }  
                }  
            };  
        }.start();  
          
    }  
    /** 
     * 安装apk 
     * @param filePath 
     * @throws Exception 
     */  
    private void InstallAPK(final String filePath) throws Exception{  
    	try {
    		Intent intent = new Intent(Intent.ACTION_VIEW);   
            intent.setDataAndType(Uri.parse("file://" + filePath),"application/vnd.android.package-archive");   
            activity.startActivity(intent); 
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

    } 

    @SuppressLint("NewApi")
	public boolean check()
    {
    	return activity.getPackageManager().canRequestPackageInstalls();
    	
    }
}
