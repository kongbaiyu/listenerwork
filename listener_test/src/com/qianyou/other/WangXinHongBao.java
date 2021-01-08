package com.qianyou.other;

import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.chajian.MobileImHook;
import com.qianyou.listener5.MainActivity;
import com.qianyou.listener5.R;

import de.robv.android.xposed.XposedBridge;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WangXinHongBao extends Activity{

	private Button btn_plcm, btn_commit, btn_close;
	private EditText edt_accout, edt_wxmoney;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wangxinhongbao);
		
		btn_plcm = findViewById(R.id.wxbtn_plcm);
		btn_commit = findViewById(R.id.wxbtn_commit);
		btn_close = findViewById(R.id.wxbtn_close);
		
		edt_accout = findViewById(R.id.wxedt_account);
		edt_wxmoney = findViewById(R.id.wxedt_wxmoney);
		
		edt_accout.setText(MainActivity.instance.converstationid);
		
		btn_plcm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = edt_wxmoney.getText().toString();
				if (TextUtils.isEmpty(str)) {
					Toast.makeText(getApplicationContext(), "请输入金额", Toast.LENGTH_SHORT).show();
					return;
				}
				int i =0;
				while (i<1) {
            	 	Intent intent = new Intent("com.tools.payhelper.wangxin");
					intent.putExtra("money", str);
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(System.currentTimeMillis());
					stringBuilder.append("_");
					stringBuilder.append(i);
					intent.putExtra("orderid", stringBuilder.toString());
					intent.putExtra("qunid", MainActivity.instance.currentConId);
					intent.putExtra("type", "wangxin");
					MainActivity.instance.sendBroadcast(intent);
					i++;
				}
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
	
	
}
