package com.qianyou.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import com.qianyou.listener5.Log;
import com.qianyou.nat.GetJsonData;

/**
 * 
 * @author librabin
 *
 */
public class ClientProxyBasicHttp {

	public static void clientHttp() throws Exception {
		// 目标地址
		String targetUrl = "http://httpbin.org/get";

		// 代理服务器
		String proxyHost = "xxx";
		int proxyPort = 0;

		// http代理: Proxy.Type.HTTP, socks代理: Proxy.Type.SOCKS
		Proxy.Type proxyType = Proxy.Type.HTTP;
		
		// 代理验证
		String proxyUser = "13417870508";
		String proxyPwd = "qaz123456";


    	final String urladdress = "http://webapi.http.zhimacangku.com/getip?num=1&type=1&yys=0&port=11&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";
		
		try {
			String address;
    		String ip="";
    		int port=1234;
    		address =  GetJsonData.getaddressData(urladdress);
    		Log.T("address="+address);
    		if (address.contains("请重新提取")) {
    			address =  GetJsonData.getaddressData(urladdress);
    			Log.T("a="+address);
    		}
    		int index = address.indexOf(":");
    		//Log.T("index="+index);
    		if (index>10 && !address.contains("false")) {
    			ip = address.substring(0, index);
    			port = Integer.parseInt(address.substring(index+1));
			}
			// 设置验证
			Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPwd));
			
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(proxyType, addr);
			// 访问目标网页
			URL url = new URL(targetUrl);
			URLConnection con = url.openConnection(proxy);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("isTree", "true");
            con.setRequestProperty("isLastPage", "true");
            con.setRequestProperty("Cookie", "kaola_user_key=d95f5f64-f8d4-426a-a51b-bec7c27cc953; cna=/uK8Fw+dL30CATFCFlKUfaa8; JSESSIONID-WKL-8IO=VWq6Vn%2BUQqX8UmiqcLUi5sPq%5CbgnXA6pJdNiiIIwKZHxrYRrZ5iqzexer5lCjCn81et1Vk8ylT%5CE4b4Mlfyxt6mNZieN%2Fg4tStG57NoDJvZwb61E8%5C5P6i01OA%2B%2FejPJT8jHulOutWMLS77IwQYJhtmum1ibOWKRkz5vH%2BU4xoY%5C%5C8Gl%3A1618900401971; _klhtxd_=31; _samesite_flag_=true; cookie2=1382f2293dd169c0c4994674490b9f76; t=cc17693df513e36c8f83b1d08ad0c053; _tb_token_=f310a10685399; xlly_s=1; __da_ntes_utma=2525167.1050686010.1618814039.1618814039.1618814039.1; davisit=1; __da_ntes_utmb=2525167.1.10.1618814039; KAOLA_NEW_USER_COOKIE=yes; __da_ntes_utmfc=1; __da_ntes_utmz=1; csg=ed06a82f; NTES_OSESS=09a4102f06ee4052ab5aa43e650c8f51; KAOLA_USER_ID=109999078941221888; KAOLA_MAIN_ACCOUNT=161845874486019406@pvkaola.163.com; unb=2211382567415; kaola_csg=f2d97088; kaola-user-beta-traffic=18618169218; firstLogin=0; KAOLA_USER_ID.sig=TScRYEJu3g-qEOpmrNsZkwuklZ5UewWE7dEI4JFfjWk; ucn=center; hb_MA-AE38-1FCC6CD7201B_source=account.kaola.com.hk; __wpkreporterwid_=60412f8d-e9c0-45f4-32c3-15a368a81571; l=eBaSz3LgjcF8zc4DBO5Cnurza77ToBdb8sPzaNbMiInca6ZlBejzqNCQRRIWvdtjgtCH3etPbQoCndFyra4d9xDDBefcjF6K3xvO.; tfstk=cRYAB3xF9YDDAK5JTnnu1qELpSqAaKMOFS6gWjHLU7t8GxUYls29-eKHJm1_HahR.; isg=BKWllE69Kdk2pk2FMwpwD0nwtGHf4ll0dPo3SKeBDVzXvsYwaTCaRP4YSGJtvnEs");
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            //writer.append(param.toString());
            String dataString = "searchCond=202104201352GORDER35574492&pageSize=15&pageNo=1";
            writer.write(dataString);
            writer.flush();
            
            writer.close();
			// 读取返回数据
			InputStream in = con.getInputStream();
			// 将返回数据转换成字符串
			Log.T(IO2String(in));
			System.out.println(IO2String(in));
			

		} catch (Exception e) {
			e.printStackTrace();
			Log.T(e.toString());
		}

	}

	/**
	 * 将输入流转换成字符串
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static String IO2String(InputStream inStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inStream.read(buffer)) != -1) {
			result.write(buffer, 0, len);
		}
		String str = result.toString(StandardCharsets.UTF_8.name());
		return str;
	}
	
	static class ProxyAuthenticator extends Authenticator {
		private String authUser, authPwd;
		
		public ProxyAuthenticator(String authUser, String authPwd) {
			this.authUser = authUser;
			this.authPwd = authPwd;
		}
		
        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication(authUser, authPwd.toCharArray()));
        }
    }
}

