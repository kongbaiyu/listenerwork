package com.qianyou.nat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.R.integer;

import com.qianyou.listener5.Log;

public class GetJsonData {

    public static String getJsonData(JSONObject jsonParam, String urlString) {
    	String urls = "http://cy.youxin123.cn/index/store/add_alipay_guma"; //"http://192.168.1.119:81/index/store/add_alipay_guma";
        urls = urlString;
    	StringBuffer sb=new StringBuffer();
        //Log.T("sb=="+sb.toString());
        try {
        	//Log.T("aaaa="+jsonParam.toString());
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            //conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("content-type", "application/json");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
            // 写入请求的字符串
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();
            //Log.T("aaaaaaaaaaaaa");
            //System.out.println(conn.getResponseCode());
            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                //Log.T("连接成功");
                // 请求返回的数据
                InputStream in1 = conn.getInputStream();
                try {
                    String readLine;
                    BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
                    while((readLine=responseReader.readLine())!=null){
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
                    System.out.println(sb.getClass().toString());
                    //Log.T("get:"+sb.toString());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                Log.T("请求失败");

            }

        } catch (Exception e) {
        	Log.T("exception="+e);
        }

        return sb.toString();

    }
}



