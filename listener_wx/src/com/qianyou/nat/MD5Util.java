package com.qianyou.nat;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

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
