package com.qianyou.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class Sign {

	public  static String sortAsciiJson(String json){
        String res = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            ArrayList<String> nameList = new ArrayList<>();
            Iterator keys = jsonObject.keys();
            while(keys.hasNext()) {
                String key = keys.next().toString();
                nameList.add(key);
            }
            //key排序，升序
            Collections.sort(nameList);
//            Collections.sort(nameList,new ASCIICompartor());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nameList.size(); i++) {
                String name = nameList.get(i);
                String value = jsonObject.getString(name);
                if(i != 0)sb.append("&");
                //添加键值对，区分字符串与json对象
                if(value.startsWith("{")||value.startsWith("[")){
                    sb.append(String.format("%s=%s",name,value));
                }else{
                    sb.append(String.format("%s=%s",name,value));
                }
            }
            sb.append("&key=cy");
            res = sb.toString();
 
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
 
 
        return getMD5Str(res);
	}

	public static String getMD5Str(String str) {
		System.out.println(str);
        byte[] digest = null;
        try {
            digest  = MessageDigest.getInstance("md5").digest(
                    str.getBytes());;
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    // 计算md5函数
                    md.update(str.getBytes());
            		System.out.println(new BigInteger(1, md.digest()).toString(16));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        System.out.println(md5Str);
        for (int i = 0; i < 32 - md5Str.length(); i++) {
            md5Str = "0" + md5Str;
        }
        return md5Str;
    }
	
}
