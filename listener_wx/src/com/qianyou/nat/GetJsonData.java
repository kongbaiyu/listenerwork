package com.qianyou.nat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetJsonData {

    public static String getJsonData(String jsonParam, String urlString) {
    	String urls = urlString;
    	StringBuffer sb=new StringBuffer();
        //Log.T("sb=="+sb.toString());
        try {
        	//Log.T("aaaa="+jsonParam.toString());
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            byte[] data = jsonParam.getBytes();
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setRequestProperty("content-type", "application/json");
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
            out.write(jsonParam.getBytes());
            out.flush();
            out.close();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                InputStream in1 = conn.getInputStream();
                try {
                    String readLine;
                    BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
                    while((readLine=responseReader.readLine())!=null){
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
                    //Log.T("get:"+sb.toString());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {

            }

        } catch (Exception e) {
        }

        return sb.toString();

    }
}



