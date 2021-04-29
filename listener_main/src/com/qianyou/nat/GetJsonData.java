package com.qianyou.nat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Properties;

import org.json.JSONObject;

import com.qianyou.chajian.AliPayHook;
import com.qianyou.listener5.Log;

public class GetJsonData {


	public static String ip="121.231.223.100";
	public static int port=4276;
	
    public static String getJsonData(JSONObject jsonParam, String urlString) {
    	String urls = "http://192.168.1.119:81/index/store/add_alipay_guma"; 
        urls = urlString;
    	StringBuffer sb=new StringBuffer();
        //Log.T("sb=="+sb.toString());
        try {
        	//Log.T("aaaa="+jsonParam.toString());
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
          //在你发起Http请求之前设置一下属性
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Log.T("conn="+conn.toString());
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
            //Log.T("aaaaaaaaaaaaa="+conn.getResponseCode());
            System.out.println(conn.getResponseCode());
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
        	e.printStackTrace();
        	Log.T("exception="+e);
        }

        return sb.toString();

    }
    
    public static String getaddressData(String urlString) {
    	StringBuffer sb=new StringBuffer();
    	try {
            // 创建url资源
            URL url = new URL(urlString);
            //Log.T("url=="+url);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Log.T("conn="+conn);
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
           // byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            //conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("content-type", "application/json");
            // 开始连接请求
            conn.connect();
            //Log.T("connect");
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
            //Log.T("out=="+out);
            // 写入请求的字符串
            //out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();

            //System.out.println(conn.getResponseCode());
            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                //Log.T("连接成功");
                // 请求返回的数据
                InputStream in1 = conn.getInputStream();
                try {
                    BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
                    String readLine = responseReader.readLine();
                    //Log.T(readLine);
//                    sb.append(responseReader.readLine());
//                    while((readLine=responseReader.readLine())!=null){
//                        sb.append(readLine).append("\n");
//                    }
                    responseReader.close();
                    //System.out.println(sb.getClass().toString());
                    //Log.T("get:"+readLine);
                    return readLine;
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Log.T("e1=="+e1);
                }
            } else {
                Log.T("请求失败");

            }

        } catch (Exception e) {
        	e.printStackTrace();
        	Log.T("e=="+e);
        }

        return sb.toString();

    }
    
    public static String getPost( JSONObject param,String defURL){
    	String reginid="1";
    	String cityid="2";
    	//"http://webapi.http.zhimacangku.com/getip?num=1&type=1&pro="+reginid+"&city="+cityid+"&yys=0&port=11&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";
    	//wapi.http.linkudp.com/index/index/save_white?neek=192528&appkey=41b368b1e9e6f79a3f1af405c1b23ff9&white=
    	final String urladdress = "http://webapi.http.zhimacangku.com/getip?num=1&type=1&yys=0&port=11&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";
        StringBuffer sbf = new StringBuffer();
        //defURL = "http://pv.sohu.com/cityjson?ie=utf-8";
    	try {
    		String address;
    		address =  getaddressData(urladdress);
    		Log.T("address="+address);
    		if (address.contains("请重新提取")) {
    			address =  getaddressData(urladdress);
    			//Log.T("a="+address);
    		}
    		if (address.contains("请添加白名单")) {
    			String data = getaddressData("http://wapi.http.linkudp.com/index/index/save_white?neek=192528&appkey=41b368b1e9e6f79a3f1af405c1b23ff9&white="+AliPayHook.getTextCenter(address, "请添加白名单", "\""));
    			//Log.T(data);
    			if (data.contains("保存成功")) {
					Thread.sleep(2000);
	    			address =  getaddressData(urladdress);
	    			//Log.T("a="+address);
				}
    		}
    		int index = address.indexOf(":");
    		//Log.T("index="+index);
    		if (index>10 && !address.contains("false")) {
    			ip = address.substring(0, index);
    			port = Integer.parseInt(address.substring(index+1));
			}
    		InetSocketAddress addr = new InetSocketAddress(ip, port);
    		Proxy.Type proxyType = Proxy.Type.HTTP;
    		Proxy proxy = new Proxy(proxyType, addr);
//    		Properties systemProperties =System.getProperties();
//	        systemProperties.setProperty("http.proxyHost",ip);
//
//	        systemProperties.setProperty("http.proxyPort",String.valueOf(port));
    		URL url = new URL(defURL);
            // 打开和URL之间的连接
            HttpURLConnection con = (HttpURLConnection)url.openConnection(proxy);
            con.setRequestMethod("POST");//请求post方式
            con.setUseCaches(false); // Post请求不能使用缓存
            con.setDoInput(true);// 设置是否从HttpURLConnection输入，默认值为 true
            con.setDoOutput(true);// 设置是否使用HttpURLConnection进行输出，默认值为 false
            //设置header内的参数 connection.setRequestProperty("健, "值");
            //con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("isTree", "true");
            con.setRequestProperty("isLastPage", "true");
            String cookie = param.getString("cookie");
            con.setRequestProperty("cookie", cookie);
            //con.setRequestProperty("Cookie", "kaola_user_key=d95f5f64-f8d4-426a-a51b-bec7c27cc953; cna=/uK8Fw+dL30CATFCFlKUfaa8; JSESSIONID-WKL-8IO=VWq6Vn%2BUQqX8UmiqcLUi5sPq%5CbgnXA6pJdNiiIIwKZHxrYRrZ5iqzexer5lCjCn81et1Vk8ylT%5CE4b4Mlfyxt6mNZieN%2Fg4tStG57NoDJvZwb61E8%5C5P6i01OA%2B%2FejPJT8jHulOutWMLS77IwQYJhtmum1ibOWKRkz5vH%2BU4xoY%5C%5C8Gl%3A1618900401971; _klhtxd_=31; _samesite_flag_=true; cookie2=1382f2293dd169c0c4994674490b9f76; t=cc17693df513e36c8f83b1d08ad0c053; _tb_token_=f310a10685399; xlly_s=1; __da_ntes_utma=2525167.1050686010.1618814039.1618814039.1618814039.1; davisit=1; __da_ntes_utmb=2525167.1.10.1618814039; KAOLA_NEW_USER_COOKIE=yes; __da_ntes_utmfc=1; __da_ntes_utmz=1; csg=ed06a82f; NTES_OSESS=09a4102f06ee4052ab5aa43e650c8f51; KAOLA_USER_ID=109999078941221888; KAOLA_MAIN_ACCOUNT=161845874486019406@pvkaola.163.com; unb=2211382567415; kaola_csg=f2d97088; kaola-user-beta-traffic=18618169218; firstLogin=0; KAOLA_USER_ID.sig=TScRYEJu3g-qEOpmrNsZkwuklZ5UewWE7dEI4JFfjWk; ucn=center; hb_MA-AE38-1FCC6CD7201B_source=account.kaola.com.hk; __wpkreporterwid_=60412f8d-e9c0-45f4-32c3-15a368a81571; l=eBaSz3LgjcF8zc4DBO5Cnurza77ToBdb8sPzaNbMiInca6ZlBejzqNCQRRIWvdtjgtCH3etPbQoCndFyra4d9xDDBefcjF6K3xvO.; tfstk=cRYAB3xF9YDDAK5JTnnu1qELpSqAaKMOFS6gWjHLU7t8GxUYls29-eKHJm1_HahR.; isg=BKWllE69Kdk2pk2FMwpwD0nwtGHf4ll0dPo3SKeBDVzXvsYwaTCaRP4YSGJtvnEs");
            // 建立实际的连接
            con.connect();
            Log.T("connect");
            // 得到请求的输出流对象
            //OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            //writer.append(param.toString());
            String searchCond = param.getString("orderid");
            String dataString = "searchCond="+searchCond+"&pageSize=15&pageNo=1";
            writer.write(dataString);
            writer.flush();
            
//            System.getProperties().remove("http.proxyHost");
//
//            System.getProperties().remove("http.proxyPort");
            
            writer.close();
            // 获取服务端响应，通过输入流来读取URL的响应

            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            //Log.T("返回的数据="+sbf.toString());
            reader.close();

            // 关闭连接
            con.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
			Log.T("getpost="+e.toString());
		}
    	
        return sbf.toString();
    }
    
    
    public static String getRechargeInfos(JSONObject jsonParam) {
    	String urls = "https://gw.kaola.com/gw/order/anti/verify?version=1.0"; 
    	StringBuffer sb=new StringBuffer();
        //Log.T("sb=="+sb.toString());
        try {
        	//Log.T("aaaa="+jsonParam.toString());
    		InetSocketAddress addr = new InetSocketAddress(ip, port);
    		Proxy.Type proxyType = Proxy.Type.HTTP;
    		Proxy proxy = new Proxy(proxyType, addr);
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
          //在你发起Http请求之前设置一下属性
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            //Log.T("conn="+conn.toString());
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

            conn.setRequestProperty("gw-request-type", "wap");
            String cookie = jsonParam.getString("cookie");
            conn.setRequestProperty("cookie", cookie);
            //conn.setRequestProperty("cookie", "kaola_user_key=9e92b8a0-3d7e-459c-81bf-ad3ceea6cd7e; cna=YhEEGU8x80kCATFCFSb2Q0jl; failCount=1; kl_newpopup_update=1; JSESSIONID-WKL-8IO=Nq%5Cns%5CpZpnixhe%2F5%2FsYmt%5CryZg%2FQUa7lx%5C%2Fflww%5CJUpn1OG%2BRLUMHuK7u7l5wfwxMDNcIh%5CUfVpAs8%2FuHztY3GRxc5hmJp4XrtpjgwaLJiUl0w%5CLVczHteUWcU3czHqVtCDVduIsG2op6hCc90KWHm9eyT9znNZrDCOwAxnheasJ%2B%2FiM%3A1619184328413; _klhtxd_=31; _samesite_flag_=true; t=16b3e38f3ae9394e57a2458d048d8d28; _tb_token_=e9b337ebbe335; xlly_s=2; csg=b967ddab; NTES_OSESS=260d2087fdae454fb5b3e31258b77d1d; KAOLA_MAIN_ACCOUNT=161908495426267379@pvkaola.163.com; cookie2=1e1515798303120bafe3bb4a358fae0d; kaola_csg=9af9cdc1; unb=2211420048180; kaola-user-beta-traffic=11418171740; firstLogin=0; l=eBglDjGRjBcI3pHYBOfZlurza779SCOOguPzaNbMiOCP_3CH53sNW61XW3LMCnGRnseDY3Js8DwBBk8L7yURQxv9-eM_z4VrnddC.; tfstk=cvNABd1hbP-seF388-Bk5tOMdIbhaKXxXOiy6ZQBlQeF4Fd91sUh-OJHgx0rwDf..; isg=BDEx7ISi9UHGK1mpoHIebIKdS7RnDqef63VDnhNGKfgXOlmMSG4kYEPLWoj58j3I; EGG_SESS=c0WRXgD5un8UQbaG-ppC_bapzRG1S9uY8zIXSnsKMjZWThguT2hQ1BOBfoLwSNqVJeL0WauqXdI9c5KPssoyV2NogdE5NzEycxmUI2PrH_SeOx4YqBi3XbeblQFfqWNTwAWJckynUGo7_W9YgZOWK9ZhRjOplcGXX02rPmXYrX2oEcM5JjhgP-drB1yWjcsb8uP3cljqAcO-GZ7soT1sTAdUBVLBQ74Ildzn6HgV3MJP0hAo3XzgkCz-wHQTey2B05GHjDcCZZfljwjSR4WqhCin0Bo-ZW-JUL61vYIi_bVYQsDKZrPTBnXHJa-ADjq460PvG5S3ygBSpDlS_ZdUaWBRrs_IwaM5-Zfdzz7BX-Y_jjQdtVDNsQtVoAMHcpLD8cnGoITZds9-1WPhTBacVM894XQik8zUvhAQ_xHIij8=; KAOLA_USER_ID.sig=KZmclp-KLCNwGnGhBa2bALVUNo9uyXTGMDxdxDX4eis; KAOLA_USER_ID=109999078941474016");
            // 开始连接请求
            conn.connect();
            String aa= "{\"rechargeAntispamParam\": {\"orderId\": \""+jsonParam.getString("detailDisId")+"\"}}";
//            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
//            // 写入请求的字符串
//            out.write((jsonParam.toString()).getBytes());
//            out.flush();
//            out.close();
            
    		OutputStreamWriter out = new OutputStreamWriter(
    				conn.getOutputStream(), "UTF-8");
    		out.write(aa);
    		out.flush();
    		out.close();
            //Log.T("aaaaaaaaaaaaa="+conn.getResponseCode());
            System.out.println(conn.getResponseCode());
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
        	e.printStackTrace();
        	Log.T("exception="+e);
        }

        return sb.toString();

    }
}



