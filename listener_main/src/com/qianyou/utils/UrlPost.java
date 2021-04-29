package com.qianyou.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.chajian.AliPayHook;
import com.qianyou.listener5.Log;
import com.qianyou.nat.GetJsonData;
import com.qianyou.nat.Listener;

public class UrlPost {

	//考拉商品信息查询
	public static void KLGoodsQuery(final JSONObject data){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
	        	try {
	        		//Log.T("data");
					String dString = GetJsonData.getPost(data, "https://buy.kaola.com/personal/my_order_data.html");
					//Log.T(dString);
					JSONObject object2 = new JSONObject(dString);
					JSONArray array = new JSONArray(object2.getString("gorderList"));
					JSONObject object3 = array.getJSONObject(0);
					String detailDisId = object3.getString("detailDisId");
					Log.T("orderID="+detailDisId);
					//Log.T(object3.toString());
					String status = AliPayHook.getTextCenter(object3.toString(), "\"orderStatus\":", ",");
					String money = AliPayHook.getTextCenter(object3.toString(), "\"gpayAmount\":", ",");
					//Log.T(money);
					//Log.T(AliPayHook.getTextCenter(object3.toString(), "\"gpayAmount\":", "gpayAmountChangeBefore"));
					String ret = "NA";
					String cardNo = "";
					String codeNo = "";
					if (status.equals("5")) {
						Log.T("订单过期");
						ret="INV";
					}else if (status.equals("4")) {
						Log.T("订单退款");
						ret="INV";
					}else if (status.equals("0")) {
						Log.T("还未付款");
						ret="FAIL";
					}else if (status.equals("3")) {
						Log.T("已付款");
						data.put("detailDisId", detailDisId);
						String dataString = GetJsonData.getRechargeInfos(data);
						JSONObject object = new JSONObject(getCard(dataString));
						cardNo = object.getString("cardNo");
						codeNo = object.getString("codeNo");
						ret="OK";
					}else {
						Log.T("检测未知");
						ret="NA";
					}
					
					JSONObject jo=new JSONObject();
					try {
						jo.put("act","REKAOLAJC");
						jo.put("orderid",data.getString("orderid"));
						jo.put("cardNum", cardNo);
						jo.put("codeNum",codeNo);
						jo.put("money", money);
						jo.put("ret",ret);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Log.T(jo.toString());
					Listener.sendJson(jo.toString(), "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.T("json="+e.toString());
					JSONObject jo=new JSONObject();
					try {
						jo.put("act","REKAOLAJC");
						jo.put("orderid",data.getString("orderid"));
						jo.put("cardNum", "");
						jo.put("codeNum","");
						jo.put("money", "");
						jo.put("ret","NA");
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//Log.T(jo.toString());
					Listener.sendJson(jo.toString(), "");
				}
			}
		}).start();
	}
	
	public static String getCard(String data){
		JSONObject aObject = new JSONObject();
		try {
			JSONObject object = new JSONObject(data);
			JSONObject object2 = new JSONObject(object.getString("body"));
			JSONObject object3 = new JSONObject(object2.getString("rechargeResult"));
			JSONArray array = new JSONArray(object3.getString("rechargeInfos"));
			JSONObject object4 = array.getJSONObject(0);
			String cardNo = object4.getString("value");
			JSONObject object5 = array.getJSONObject(1);
			String codeNo = object5.getString("value");
			aObject.put("cardNo", cardNo);
			aObject.put("codeNo", codeNo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return aObject.toString();
	}

}
