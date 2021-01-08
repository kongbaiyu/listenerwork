package com.qianyou.listener5;

import com.qianyou.jieping.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateXQDAccount extends Activity{

	private EditText edt_account;
	private static EditText edt_UID;
	private Button btn_1,btn_2,btn_3;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_createxqd);
		context = this;
		
		edt_account = findViewById(R.id.editText_uu);
		edt_UID = findViewById(R.id.txt_uu);

		SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String zfbuid = sp.getString("zfbuid","");
		edt_UID.setText(zfbuid);  
		btn_1 = findViewById(R.id.btn_get_uu);
		btn_1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainActivity.instance.isGETZFBUID = false;
				String urlString = "http://auth.xxpay.vip/api/alipay";
				Utils.openZFB(urlString);
			}
		});
		
		btn_2 = findViewById(R.id.btn_commit_uu);
		btn_2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (edt_account.getText().toString().equals("") || edt_UID.getText().toString().equals("")) {
					Toast.makeText(context, "请输入账户名称或获取UID", Toast.LENGTH_SHORT).show();
				} else {
					create();
				}
			}
		});
		
		btn_3 = findViewById(R.id.btn_close_uu);
		btn_3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateXQDAccount.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	protected void create(){
        String uid = edt_UID.getText().toString();
        if (uid.startsWith("2088")) {
			Toast.makeText(context, "创建成功", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(context, "UID错误", Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void setUID(String msg){
		edt_UID.setText(msg);
	}
}
