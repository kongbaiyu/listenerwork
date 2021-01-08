package com.qianyou.wangxin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.qianyou.listener5.MainActivity;
import com.qianyou.listener5.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class WangXinHongBao extends Activity{

	private Button btn_plcm, btn_commit, btn_close;
	private EditText edt_accout, edt_wxmoney;
	private List<msgMessage> mData = null;
    private Context mContext;
    private static MsgAdapter mAdapter = null;
    private ListView listView;
    private static msgMessage msg_5 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wangxinhongbao);
		
		btn_plcm = findViewById(R.id.wxbtn_plcm);
		btn_commit = findViewById(R.id.wxbtn_commit);
		btn_close = findViewById(R.id.wxbtn_close);
		
		edt_accout = findViewById(R.id.wxedt_account);
		edt_wxmoney = findViewById(R.id.wxedt_wxmoney);
		
		
		listView = findViewById(R.id.listView_wx);
		
		mContext=this;
		mData = new LinkedList<>();
		mAdapter = new MsgAdapter((LinkedList<msgMessage>) mData,mContext);
        listView.setAdapter(mAdapter);
		btn_plcm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = edt_wxmoney.getText().toString();
				if (TextUtils.isEmpty(str)) {
					setWXLog("请输入金额");
					Toast.makeText(getApplicationContext(), "请输入金额", Toast.LENGTH_SHORT).show();
					return;
				}
				setWXLog("开始产码...");
				int i =0;
				while (i<MainActivity.instance.size) {
            	 	Intent intent = new Intent("com.qianyou.wangxin.wangxin");
					intent.putExtra("money", str);
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(System.currentTimeMillis());
					stringBuilder.append("_");
					stringBuilder.append(i);
					intent.putExtra("orderid", stringBuilder.toString());
					intent.putExtra("qunid", MainActivity.instance.currentConId);
					intent.putExtra("type", "wangxin");
					MainActivity.instance.sendBroadcast(intent);
					setWXLog(stringBuilder.toString());
					i++;
				}
			}
		});
		
		btn_commit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str = edt_accout.getText().toString();
				if (TextUtils.isEmpty(str)) {
					setWXLog("请输入账户名称");
					return;
				}
				setWXLog("开始上传。。。");
				setWXLog(MainActivity.instance.qrList.toString());
			}
		});
		
		btn_close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WangXinHongBao.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@SuppressLint("SimpleDateFormat") public static void setWXLog(String data){
        Date date = new Date(System.currentTimeMillis());
        String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        msg_5 = new msgMessage(str+": "+data);
        mAdapter.add(0,msg_5 );
		
	}
	
}
