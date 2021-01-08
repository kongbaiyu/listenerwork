package com.qianyou.other;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Util {
  public static Map<String, String> StrToMap(String paramString1, String paramString2) {
    String[] arrayOfString = paramString1.split(paramString2);
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++) {
      String[] arrayOfString1 = arrayOfString[i].split("=");
      if (arrayOfString1.length >= 3) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int k = 1; k < arrayOfString1.length; k++) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(arrayOfString1[k]);
          stringBuilder1.append("=");
          stringBuilder.append(stringBuilder1.toString());
        } 
        hashMap.put(arrayOfString1[0], stringBuilder.substring(0, stringBuilder.length() - 1));
      } else if (arrayOfString1.length == 2) {
        hashMap.put(arrayOfString1[0], arrayOfString1[1]);
      } else if (arrayOfString1.length == 1) {
        hashMap.put(arrayOfString1[0], "");
      } 
    } 
    return (Map)hashMap;
  }
  
  public static void e(String paramString1, String paramString2) {
    Log.e(paramString1, paramString2);
  }
  
  public static int getLocalVersion(Context paramContext) {
    int i = 0;
    try {
      int j = (paramContext.getApplicationContext().getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionCode;
      i = j;
      StringBuilder stringBuilder = new StringBuilder();
      i = j;
      stringBuilder.append("当前版本号:");
      i = j;
      stringBuilder.append(j);
      i = j;
      Log.d("TAG", stringBuilder.toString());
      return j;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return i;
    } 
  }
  
  public static String getLocalVersionName(Context paramContext) {
    String str = "";
    try {
      String str1 = (paramContext.getApplicationContext().getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionName;
      str = str1;
      StringBuilder stringBuilder = new StringBuilder();
      str = str1;
      stringBuilder.append("当前版本名称:");
      str = str1;
      stringBuilder.append(str1);
      str = str1;
      Log.d("TAG", stringBuilder.toString());
      return str1;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return str;
    } 
  }
  
  public static int getRandom(int paramInt) {
    return (new Random()).nextInt(paramInt);
  }
  
  public static String getRandomCode(int paramInt1, int paramInt2) {
    StringBuffer stringBuffer1 = null;
    StringBuffer stringBuffer2 = new StringBuffer();
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    switch (paramInt2) {
      case 7:
        stringBuffer2.append(UUID.randomUUID().toString().replaceAll("-", ""));
        break;
      case 6:
        stringBuffer1 = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        stringBuffer2.append(stringBuffer1.charAt(random.nextInt(stringBuffer1.length() - 10)));
        paramInt1--;
        break;
      case 5:
        stringBuffer1 = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        break;
      case 4:
        stringBuffer1 = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        break;
      case 3:
        stringBuffer1 = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
        break;
      case 2:
        stringBuffer1 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        break;
      case 1:
        stringBuffer1 = new StringBuffer("abcdefghijklmnopqrstuvwxyz");
        break;
      case 0:
        stringBuffer1 = new StringBuffer("0123456789");
        break;
    } 
    if (paramInt2 != 7) {
      int i = stringBuffer1.length();
      for (paramInt2 = 0; paramInt2 < paramInt1; paramInt2++)
        stringBuffer2.append(stringBuffer1.charAt(random.nextInt(i))); 
    } 
    return stringBuffer2.toString();
  }
  
  public static void showLargeLog(String paramString1, String paramString2, int paramInt) {
    if (paramString2.length() > paramInt) {
      e(paramString1, paramString2.substring(0, paramInt));
      if (paramString2.length() - paramInt > paramInt) {
        showLargeLog(paramString1, paramString2.substring(paramInt, paramString2.length()), paramInt);
      } else {
        e(paramString1, paramString2.substring(paramInt, paramString2.length()));
      } 
      return;
    } 
    e(paramString1, paramString2);
  }
}
