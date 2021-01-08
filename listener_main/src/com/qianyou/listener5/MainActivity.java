package com.qianyou.listener5;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.qianyou.chajian.AliPayHook;
import com.qianyou.chajian.AlipayBroadcast;
import com.qianyou.jieping.Capture;
import com.qianyou.jieping.Utils;
import com.qianyou.listener5.R;
import com.qianyou.nat.GetJsonData;
import com.qianyou.nat.Listener;

import android.Manifest;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;

import android.support.v4.app.NotificationCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


 @SuppressLint("NewApi") public class MainActivity extends Activity {
	public Photo photo=null;
	public Capture capture = null;
	public static MainActivity instance;
	public TextView tv=null;
	public Messenger mRemoteMessenger;
	public static Boolean isBand=false;
	public static Boolean isCheck=false;
	public static Boolean isCaputreOk=false;
	public static Boolean isClickBoolean=false;
	public static Boolean isBDZFBoolean = false;
	public static Boolean isCHANGEWEB =false;
	public static int CHECKTYPE = 0;
	public ListView listView,listViewLog;
	public Button button;
	public Button btnUP,btnDown;
	public static String TIMEString=null;
	
	public final static int OK=90;
	public final static int EXIT=91;
	public final static int CLEARALLNOTIFICATION=92;
	public final static int LOG_T=98;
	public final static int LOG_CLEAR=99;
	public final static int LOGIN=100;
	public final static int SETID=101;
	public final static int GETDEVICE=102;
	public final static int SEND=103;
	public final static int XPSEND=104;
	public final static int XYSEND=105;
	public final static int CHECK=106;
	public final static int CHECKCAPTUREISOK=107;
	public final static int CAPTUREOK=108;
	public final static int CHECKXY=109;
	public final static int CHECKBDZHF=110;
	public final static int CHECKDD=111;
	public final static int CHECKWEBXY=112;
	public final static int CHECKYZ=113;
	public final static int REYOUZANPAY=114;
	public final static int REYOUZANPAY1=115;	
	public final static int YZSEND=116;			
	public final static int QRCODESEND=117;		//固码产码完成
	public final static int ALGEMAPRO=118;		//固码产码
	public final static int OPENCVMSG=119;
	public final static int CHECKZFBGM = 120; //支付宝个码检测
	public final static int ZFBGMSEND = 121; //支付宝个码检测结果
	public final static int CHECKZKL = 122; //吱口令
	
	public final static int PORT=5456;//nlservice 5457  chajian 5458 capture 5459
	public final static String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";
	public Listener listener;
	public NetDoActions netDoActions;
	private WebView webView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int PHOTO_REQUEST = 100;
    private final static int VIDEO_REQUEST = 120;
    
	public static int REQUEST_CODE=101;
	public static MediaProjectionManager mMpmngr;
	
    private boolean videoFlag = false;
    private boolean checkUpdateRet=false;
    public MenuItem updateMenu;
    public String host;
    public static int deviceID = 1;
    public MediaProjection mMpj=null;
    public static int ImageReaderFormat;//多多模拟器0x2,小米0x1
    public static boolean captureImageReaderFormatException=false;
	
    public int qrnum =1;
    public static String zfbqrcode ="http";
    
	public Map<String,JSONObject> senddatas=new HashMap<String, JSONObject>();
	public Map<String, String> msels=new HashMap<String, String>();
	public Map<String, CheckBox> mcsels=new HashMap<String, CheckBox>();
	public Map<Integer,LinearLayout> allit=new HashMap<Integer, LinearLayout>();
	public Map<String,Long> xpitems=new HashMap<String, Long>();

	public List<String> logList=new ArrayList<String>();
	public Handler handler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try
			{
				String t=(String)msg.obj;
				logList.add(t);
				if(logList.size()>1000)
				{
					logList.remove(0);
				}
				if(listViewLog!=null)
				{
					((BaseAdapter)(listViewLog.getAdapter())).notifyDataSetChanged();
					listViewLog.invalidate();
				}
			}
			catch(Exception e)
			{
				
			}
			return false;
		}
	});
	
	public Handler shandler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			netDoActions.doAct((String)msg.obj);
			return false;
		}
	});
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        instance=this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        webView=null;
        netDoActions=new NetDoActions(this);
        login();
        requestPower();
		if (isNotificationListenersEnabled()==false)
		{
		  	gotoNotificationAccessSetting();
		}
		MainActivity.this.toggleNotificationListenerService(MainActivity.this);
		
		if(listener==null)
		{
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener=new Listener();
				}
			});
		    t.start();
		}
		
		{
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Utils.startMainSvr();
				}
			});
		    t.start();
		}
		{
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					host=MainActivity.instance.getResources().getString(R.string.server).split("://")[1];
			        host=host.replaceAll("/", "");
			        try {
						host=InetAddress.getByName(host).getHostAddress();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					while(true)
					{
						
						if(DownLoadAndInstallApk.CheckUpdata()==true)
						{
							checkUpdateRet=true;
							break;
						}
						try {
							Thread.sleep(1000*60);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
		}
    }
    

	public static void print(final String str)
	{
		instance.handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final NotificationManager manager = (NotificationManager) instance.getSystemService(NOTIFICATION_SERVICE);
				String contentTitle = "铃铛管家"; //标题
		        String contentText = str;//内容
		        Notification notification = new NotificationCompat.Builder(instance).setContentTitle(contentTitle)
		                .setContentText(contentText).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_launcher)
		                .setLargeIcon(BitmapFactory.decodeResource(instance.getResources(), R.drawable.ic_launcher)).build();
		        manager.notify(1, notification);
			}
		});
	}
	public void diaoxian()
	{
		MainActivity.instance.handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(button!=null)
					button.setEnabled(false);
			}
		});
		
	}
	public void zaixian()
	{
		MainActivity.instance.handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(button!=null)
					button.setEnabled(true);
			}
		});
	}
	public void stopJieDan()
	{
		if(isBand==true)
		{
			if(button!=null)
				button.callOnClick();
		}
	}
	public void startJieDan()
	{
		if(isBand==false)
		{
			if(button!=null)
				button.callOnClick();
		}
	}
	public void login()
    {
    	setContentView(R.layout.login);
    	Log.loadLog();
    	Button loginbt=(Button) findViewById(R.id.login);
    	loginbt.setText("初始化中,请稍后...");
    	loginbt.setEnabled(false);
    	final EditText et_account=(EditText) findViewById(R.id.editText1);
    	final EditText et_password=(EditText) findViewById(R.id.editText01);
    	TextView versionT=(TextView) findViewById(R.id.textViewVersion);
		versionT.setText("版本：" +getVersion()+"  c:" + MainActivity.instance.getResources().getString(R.string.servername) );
    	SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String account = sp.getString("account","");  
        String password = sp.getString("password","");  
        ImageReaderFormat = sp.getInt("imagereaderformat",0x2);  
        et_account.setText(account);
        et_password.setText(password);
    	loginbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				
				String account=et_account.getText().toString();
				String password=et_password.getText().toString();
				Pattern pattern = Pattern.compile("(\\s)");
				Matcher matcher = pattern.matcher(account);
				account = matcher.replaceAll("");
				Matcher m2 = pattern.matcher(password);
				password = m2.replaceAll("");
				Listener.account=account;
				Listener.password=password;
				int rlogin;
				if(listener!=null)
					rlogin=listener.login(account, password);
				else
					rlogin=2;
				if(rlogin==1)
				{
					Log.Clear();
					Log.T("登陆成功");
					main();
				}
				else if(rlogin==2)
				{
					Toast.makeText(MainActivity.this, "无法连接服务器", Toast.LENGTH_LONG).show();
				}
				else if(rlogin==-1)
				{
					Toast.makeText(MainActivity.this, "登陆超时", Toast.LENGTH_LONG).show();
				}
				else if(rlogin==-2)
				{
					Toast.makeText(MainActivity.this, "该账号已被封禁", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(MainActivity.this, "账号密码不正确", Toast.LENGTH_LONG).show();
				}
				
				SharedPreferences sp = getSharedPreferences("UserData", Activity.MODE_PRIVATE);//创建sp对象
	            SharedPreferences.Editor editor = sp.edit() ;
	            editor.putString("account", et_account.getText().toString()) ; 
	            editor.putString("password", et_password.getText().toString()) ; 
	            editor.commit() ;//提交
			}
		});
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
		    	JSONObject jo=new JSONObject();
		    	try {
					jo.put("action", MainActivity.OK);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	Utils.sendToService(jo.toString());
		    	try {
					Thread.sleep(1000*5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	Utils.sendToMain(jo.toString(), false);
		    	Utils.sendToService(jo.toString());
			}
		}).start();
    }
    public void main()
    {
		  setContentView(R.layout.activity_main);
		  photo=new Photo(MainActivity.this);
		  
		  //final Button button2=(Button)findViewById(R.id.button2);
		  listView=(ListView)findViewById(R.id.listView1);
		  listViewLog=(ListView)findViewById(R.id.listView2);
		  button=(Button)findViewById(R.id.button1);
		  btnUP=(Button)findViewById(R.id.up);
		  btnDown=(Button)findViewById(R.id.down);
		  btnDown.setOnTouchListener(new View.OnTouchListener() {
	            @Override
	            public boolean onTouch(View view, MotionEvent motionEvent) {
	                switch (motionEvent.getAction()){
	                    case MotionEvent.ACTION_DOWN:
	                        listScrollDown();
	                        break;
	                    case MotionEvent.ACTION_UP:
	                        listScrollOff();
	                        break;
	                }
	                return true;
	            }
	        });
		  btnUP.setOnTouchListener(new View.OnTouchListener() {
	            @Override
	            public boolean onTouch(View view, MotionEvent motionEvent) {
	                switch (motionEvent.getAction()){
	                    case MotionEvent.ACTION_DOWN:
	                        listScrollUp();
	                        break;
	                    case MotionEvent.ACTION_UP:
	                        listScrollOff();
	                        break;
	                }
	                return true;
	            }
	        });
		  
		  btnDown.setVisibility(View.INVISIBLE);
		  btnUP.setVisibility(View.INVISIBLE);
		  TextView versionT=(TextView) findViewById(R.id.textViewVersion2);
		  versionT.setText("版本：" +getVersion()+"  c:" + MainActivity.instance.getResources().getString(R.string.servername));
		  
		  SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
		  tv=(TextView)findViewById(R.id.textView1);
		  button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String sel = "";
					String wx=msels.get("wx");
					String wx_skd = msels.get("wx_skd");
					String alipay=msels.get("alipay");
					String bank=msels.get("bank");
					String fsj=msels.get("fsj");
					String youzan=msels.get("youzan");
					String xianyu_check=msels.get("xianyu_check");
					String alipay_xqd=msels.get("alipay_xqd");
					String alipay_guma=msels.get("alipay_guma");
					String alipay_scan=msels.get("alipay_scan");
					String xianyu_trans=msels.get("xianyu_trans");
					if(wx!=null&&!wx.equals(""))
						sel=msels.get("wx");
					if(wx_skd!=null&&!wx_skd.equals(""))
					{
						if(sel.equals(""))
							sel=wx_skd;
						else
							sel=sel+","+wx_skd;
					}
					if(alipay!=null&&!alipay.equals(""))
					{
						if(sel.equals(""))
							sel=alipay;
						else
							sel=sel+","+alipay;
					}
					if(alipay_xqd!=null&&!alipay_xqd.equals(""))
					{
						if(sel.equals(""))
							sel=alipay_xqd;
						else
							sel=sel+","+alipay_xqd;
					}
					if(alipay_guma!=null&&!alipay_guma.equals(""))
					{
						if(sel.equals(""))
							sel=alipay_guma;
						else
							sel=sel+","+alipay_guma;
					}
					if(alipay_scan!=null&&!alipay_scan.equals(""))
					{
						if(sel.equals(""))
							sel=alipay_scan;
						else
							sel=sel+","+alipay_scan;
					}
					if(fsj!=null&&!fsj.equals(""))
					{
						if(sel.equals(""))
							sel=fsj;
						else
							sel=sel+","+fsj;
					}
					if(youzan!=null&&!youzan.equals(""))
					{
						if(sel.equals(""))
							sel=youzan;
						else
							sel=sel+","+youzan;
					}
					if(xianyu_check!=null&&!xianyu_check.equals(""))
					{
						if(sel.equals(""))
							sel=xianyu_check;
						else
							sel=sel+","+xianyu_check;
					}
					if(xianyu_trans!=null&&!xianyu_trans.equals(""))
					{
						if(sel.equals(""))
							sel=xianyu_trans;
						else
							sel=sel+","+xianyu_trans;
					}
					if(bank!=null&&!bank.equals(""))
					{
						if(sel.equals(""))
							sel=bank;
						else
							sel=sel+","+bank;
					}
					
					if(isBand==false )
					{
						if(sel.equals(""))
						{
							Toast.makeText(MainActivity.this, "尚未选择", Toast.LENGTH_SHORT).show();
						}
						else
						{
							SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
							SharedPreferences.Editor editor = sp.edit() ;
				            editor.putBoolean("isbind", true) ; 
				            editor.putString("wx", wx) ; 
				            editor.putString("wx_skd", wx_skd) ; 
				            editor.putString("alipay", alipay) ; 
				            editor.putString("fsj", fsj) ;
				            editor.putString("bank", bank) ; 
				            editor.putString("youzan", youzan);
				            editor.putString("xianyu_check", xianyu_check);
				            editor.putString("alipay_xqd", alipay_xqd); 
				            editor.putString("alipay_guma", alipay_guma); 
				            editor.putString("alipay_scan", alipay_scan); 
				            editor.putString("xianyu_trans", xianyu_trans);
				            editor.commit() ;//提交
							String deviceid=sel;
							
							if(wx!=null&&!"".equals(wx))
								Log.T("微信　："+wx);
							if(wx_skd!=null&&!"".equals(wx_skd))
								Log.T("微信1　："+wx_skd);
							if(alipay!=null&&!"".equals(alipay))
								Log.T("支付宝："+alipay);
							if(fsj!=null&&!"".equals(fsj))
								Log.T("丰收家："+fsj);
							if(bank!=null&&!"".equals(bank))
								Log.T("银行　："+bank);
							if(youzan!=null&&!"".equals(youzan))
								Log.T("有赞："+youzan);
							if(xianyu_check!=null&&!"".equals(xianyu_check))
								Log.T("检测："+xianyu_check);
							if(xianyu_trans!=null&&!"".equals(xianyu_trans))
								Log.T("闲鱼："+xianyu_trans);
							if(alipay_xqd!=null&&!"".equals(alipay_xqd))
								Log.T("小钱袋："+alipay_xqd);
							if(alipay_guma!=null&&!"".equals(alipay_guma))
								Log.T("支付宝固码："+alipay_guma);
							if(alipay_scan!=null&&!"".equals(alipay_scan))
								Log.T("支付宝扫码："+alipay_scan);
							//Log.T("device="+deviceid);
							if(listener.setID(deviceid)==1)
							{
								Listener.deviceid=deviceid;
								Log.T("接单成功");
								button.setText("停止接单");
								isBand=true;
							}
							else
							{
								button.setText("开始接单");
								Log.T("接单失败");
								msels.clear();
								isBand=false;
							}
							
							
							
							
							
						}
						
					}
					else
					{
						button.setText("开始接单");
						isBand=false;
						SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
						SharedPreferences.Editor editor = sp.edit() ;
			            editor.putBoolean("isbind", false) ; 
			            editor.commit() ;//提交
			            try {
							if(listener.rmDevices())
							{
								Log.T("停止接单成功");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					setEnable(!isBand);
				}
			});
		ListAdapter adapter=new BaseAdapter() {
			
			@Override
			public View getView(int idx, View view, ViewGroup arg2) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) view;
				if(tv==null)
				{
					tv=new TextView(MainActivity.this);
					LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					tv.setLayoutParams(params);
					
					
				}
				String t=(String) this.getItem(idx);
				
				if (t!=null)
				{
					SpannableString ss=new SpannableString(t);
					try{
						int timeidx= t.indexOf(']',0)+1;
						ss.setSpan(new ForegroundColorSpan(Color.GRAY), 0,timeidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
						int wxidx=t.indexOf("wx:",7);
						int wx_skdidx=t.indexOf("wx_skd:",7);
						int alipayidx=t.indexOf("alipay:",7);
						int fsjidx=t.indexOf("fsj:",7);
						int bankidx=t.indexOf("bank:",7);
						int youzanidx=t.indexOf("youzan:",7);
						int xianyu_checkidx=t.indexOf("xianyu_check:",7);
						int alipay_xqdidx=t.indexOf("alipay_xqd:",7);
						int alipay_gumaidx=t.indexOf("alipay_guma:",7);
						int alipay_scanidx=t.indexOf("alipay_scan:",7);
						int xianyu_transidx=t.indexOf("xianyu_trans:",7);
						if(t.indexOf("-last-")!=-1)
						{
							wxidx=-1;
							wx_skdidx=-1;
							alipayidx=-1;
							fsjidx=-1;
							bankidx=-1;
							youzanidx=-1;
							xianyu_checkidx=-1;
							alipay_xqdidx=-1;
							alipay_gumaidx=-1;
							alipay_scanidx=-1;
							xianyu_transidx=-1;
						}
						if(wxidx>0)
						{
							wxidx+=2;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, wxidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), wxidx+1, wxidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',wxidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(0,0,192)), wxidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							if(strArr.length>0)
							{
								int moneyIdx=strArr[0].length();
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, t.indexOf("元",moneyIdx), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						else if(wx_skdidx>0)
						{
							wx_skdidx+=6;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, wx_skdidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							//ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), wx_skdidx+1, wx_skdidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							//int typeidx2=t.indexOf('\n',wx_skdidx+17);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(0,0,192)), wx_skdidx+1, moneyIdx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
								ss.setSpan(new ForegroundColorSpan(Color.BLACK), yuanidx+1, t.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}

						}
						else if(alipayidx>0)
						{
							alipayidx+=6;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, alipayidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), alipayidx+1, alipayidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',alipayidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), alipayidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
							
						}
						else if(alipay_xqdidx>0)
						{
							alipay_xqdidx+=10;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, alipay_xqdidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), alipay_xqdidx+1, alipay_xqdidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',alipay_xqdidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), alipay_xqdidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
							
						}
						else if(alipay_gumaidx>0)
						{
							alipay_gumaidx+=11;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, alipay_gumaidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), alipay_gumaidx+1, alipay_gumaidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',alipay_gumaidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), alipay_gumaidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
							
						}
						else if(alipay_scanidx>0)
						{
							alipay_scanidx+=11;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, alipay_scanidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), alipay_scanidx+1, alipay_scanidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',alipay_scanidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), alipay_scanidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
							
						}
						else if(fsjidx>0)
						{
							fsjidx+=3;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, fsjidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), fsjidx+1, fsjidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',fsjidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), fsjidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						else if(youzanidx>0)
						{
							youzanidx+=6;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, youzanidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), youzanidx+1, youzanidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',youzanidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), youzanidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						else if(xianyu_checkidx>0)
						{
							xianyu_checkidx+=12;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, xianyu_checkidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), xianyu_checkidx+1, xianyu_checkidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',xianyu_checkidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), xianyu_checkidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						else if(xianyu_transidx>0)
						{
							xianyu_transidx+=12;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, xianyu_transidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), xianyu_transidx+1, xianyu_transidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',xianyu_transidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)),xianyu_transidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						else if(bankidx>0)
						{
							bankidx+=4;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, bankidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), bankidx+1, bankidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',bankidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)), bankidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							String[] strArr=t.split("([0-9\\.]+)元.*");
							int moneyIdx=strArr[0].length();
							int yuanidx=t.indexOf("元",moneyIdx);
							if(yuanidx!=-1)
							{
								ss.setSpan(new ForegroundColorSpan(Color.rgb(0,182,17)), moneyIdx, yuanidx, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						else
						{
							ss.setSpan(new ForegroundColorSpan(Color.GRAY), timeidx, t.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
						//Log.T("[11:52] wx:2019-12-16 11:49    微信支付\n[6条]微信支付: 微信支付收款0.01元(朋友到店)");
					}catch(Exception e)
					{
						
					}
					
					tv.setText(ss);
				}
				return tv;
			}
			
			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int idx) {
				// TODO Auto-generated method stub
				if(logList.size()>idx)
					return logList.get(logList.size()-1-idx);
				else
					return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return logList.size();
			}
		};  
		listViewLog.setAdapter(adapter);
		if(sp.getBoolean("isbind", false))
		{
			msels.put("wx", sp.getString("wx",null));
			msels.put("wx_skd", sp.getString("wx_skd",null));
			msels.put("alipay", sp.getString("alipay",null));
			msels.put("alipay_xqd", sp.getString("alipay_xqd",null));
			msels.put("alipay_guma", sp.getString("alipay_guma",null));
			msels.put("alipay_scan", sp.getString("alipay_scan",null));
			msels.put("fsj", sp.getString("fsj",null));
			msels.put("bank", sp.getString("bank",null));
			msels.put("youzan", sp.getString("youzan", null));
			msels.put("xianyu_check", sp.getString("xianyu_check", null));
			msels.put("xianyu_trans", sp.getString("xianyu_trans", null));
			
			String sel = "";
			String wx=msels.get("wx");
			String wx_skd=msels.get("wx_skd");
			String alipay=msels.get("alipay");
			String alipay_xqd=msels.get("alipay_xqd");
			String alipay_guma=msels.get("alipay_guma");
			String alipay_scan=msels.get("alipay_scan");
			String fsj=msels.get("fsj");
			String bank=msels.get("bank");
			String youzan=msels.get("youzan");
			String xianyu_check=msels.get("xianyu_check");
			String xianyu_trans=msels.get("xianyu_trans");
			if(wx!=null&&!wx.equals(""))
				sel=msels.get("wx");
			if(wx_skd!=null&&!wx_skd.equals(""))
			{
				if(sel.equals(""))
					sel=wx_skd;
				else
					sel=sel+","+wx_skd;
			}
			if(alipay!=null&&!alipay.equals(""))
			{
				if(sel.equals(""))
					sel=alipay;
				else
					sel=sel+","+alipay;
			}
			if(alipay_xqd!=null&&!alipay_xqd.equals(""))
			{
				if(sel.equals(""))
					sel=alipay_xqd;
				else
					sel=sel+","+alipay_xqd;
			}
			if(alipay_guma!=null&&!alipay_guma.equals(""))
			{
				if(sel.equals(""))
					sel=alipay_guma;
				else
					sel=sel+","+alipay_guma;
			}
			if(alipay_scan!=null&&!alipay_scan.equals(""))
			{
				if(sel.equals(""))
					sel=alipay_scan;
				else
					sel=sel+","+alipay_scan;
			}
			if(fsj!=null&&!fsj.equals(""))
			{
				if(sel.equals(""))
					sel=fsj;
				else
					sel=sel+","+fsj;
			}

			if(youzan!=null&&!youzan.equals(""))
			{
				if(sel.equals(""))
					sel=youzan;
				else
					sel=sel+","+youzan;
			}

			if(xianyu_check!=null&&!xianyu_check.equals(""))
			{
				if(sel.equals(""))
					sel=xianyu_check;
				else
					sel=sel+","+xianyu_check;
			}
			if(xianyu_trans!=null&&!xianyu_trans.equals(""))
			{
				if(sel.equals(""))
					sel=xianyu_trans;
				else
					sel=sel+","+xianyu_trans;
			}
			if(bank!=null&&!bank.equals(""))
			{
				if(sel.equals(""))
					sel=bank;
				else
					sel=sel+","+bank;
			}
			
			
			
			String deviceid=sel;
			
			if(wx!=null&&!"".equals(wx))
				Log.T("微信　："+wx);
			if(wx_skd!=null&&!"".equals(wx_skd))
				Log.T("微信1　："+wx_skd);
			if(alipay!=null&&!"".equals(alipay))
				Log.T("支付宝："+alipay);
			if(alipay_xqd!=null&&!"".equals(alipay_xqd))
				Log.T("小钱袋："+alipay_xqd);
			if(alipay_guma!=null&&!"".equals(alipay_guma))
				Log.T("支付宝固码："+alipay_guma);
			if(alipay_scan!=null&&!"".equals(alipay_scan))
				Log.T("支付宝扫码："+alipay_scan);
			if(fsj!=null&&!"".equals(fsj))
				Log.T("丰收家："+fsj);
			if(bank!=null&&!"".equals(bank))
				Log.T("银行　："+bank);
			if(youzan!=null&&!"".equals(youzan))
				Log.T("有赞　："+youzan);
			if(xianyu_check!=null&&!"".equals(xianyu_check))
				Log.T("检测　："+xianyu_check);
			if(xianyu_trans!=null&&!"".equals(xianyu_trans))
				Log.T("闲鱼　："+xianyu_trans);
			
			Log.T("自动接单"+deviceid);
			if(listener.setID(deviceid)==1)
			{
				Listener.deviceid=deviceid;
				button.setText("停止接单");
				Log.T("接单成功");
				isBand=true;
			}
			else
			{
				button.setText("开始接单");
				Log.T("接单失败");
				msels.clear();
				isBand=false;
			}
			
			
		}	 
		String data="";
		try {
			data=listener.getDevices();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jobject=new JSONObject();
		try {
			jobject.put("action", MainActivity.GETDEVICE);
			jobject.put("data", data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Message msg=shandler.obtainMessage();
		msg.obj=jobject.toString();
		shandler.sendMessage(msg);
		
		//Log.T("正在获取后台数据");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        updateMenu=menu.findItem(R.id.update);
        updateMenu.setVisible(checkUpdateRet);
        return true;
    }
    public static String getVersion()
    {
    	try {
    	    PackageInfo pInfo = MainActivity.instance.getPackageManager().getPackageInfo(instance.getPackageName(), 0);
    	    String version = pInfo.versionName ;
    	    return version;
    	    }
    	catch (PackageManager.NameNotFoundException e) {
    	    e.printStackTrace();
    	    }
    	return "";
    }
    public void setEnable(Boolean enable)
    {
    	for (Map.Entry<Integer, LinearLayout> it : allit.entrySet()) {
			it.getValue().findViewById(R.id.checkBox1).setEnabled(enable);
		}
    }
    public void refrush()
    {
    	mcsels.clear();
    	allit.clear();
		if(capture == null){
	    	String data="";
			try {
				data=listener.getDevices();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jobject=new JSONObject();
			try {
				jobject.put("action", MainActivity.GETDEVICE);
				jobject.put("data", data);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg=shandler.obtainMessage();
			msg.obj=jobject.toString();
			shandler.sendMessage(msg);
			//Log.T("重新加载数据中...");
		} else {
			//Log.T("capture为空");
		}
    }
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refrush();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		listener.destory();
		if(mMpj!=null)mMpj.stop();
		try{
			for(int i=0;i<Utils.threads.size();i++)
			{
				Utils.threads.get(i).stop();
			}
			Utils.threads.clear();
		}
		catch(Exception e){
			
		}
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public boolean onMenuOpened(int featureId,  Menu menu) {
		// TODO Auto-generated method stub
		updateMenu.setVisible(checkUpdateRet);
		return super.onMenuOpened(featureId, menu);
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.set)
        {
        	toSelfSetting(this);
        }
        if(id==R.id.update)
        {
        	DownLoadAndInstallApk.showDownLoadTip();
        }
        if (id==R.id.quit) {
        	instance.finish();  
        	System.exit(1);  
        	android.os.Process.killProcess(android.os.Process.myPid());
		}
//        if(id==R.id.test)
//        {
//        	print("你的账号 asdqqwi@qq.com 于12月29日 在其他设备登陆，如非本人，请尽快修改密码。");
//        }
        if (id == R.id.htgl) {
        	Intent intent= new Intent();
			intent.setAction("android.intent.action.VIEW");
			JSONObject jo=new JSONObject();
			SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
	        String account = sp.getString("account","");  
	        String password = sp.getString("password","");  
			try {
				jo.put("n", account);
				jo.put("p", encrypt(password));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String sign=jo.toString();
			byte[] ret = Base64.encode(sign.getBytes(),Base64.DEFAULT);
			
		    final String MIME = "image/*";


		    final int FILECHOOSER_RESULTCODE = 1;
			
			webView = (WebView)findViewById(R.id.webView1);
			if(webView!=null)
			{
				webView.setVisibility(View.VISIBLE);
		        webView.setWebViewClient(new WebViewClient() {
		            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
		            @Override
		            public boolean shouldOverrideUrlLoading(WebView view, String url) {
		            	if(!url.startsWith("http")){
		            		try {
		            			Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			            		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			            		MainActivity.instance.startActivity(intent);
							} catch (Exception e) {
								// TODO: handle exception
								return false;
							}
		            		
		            	}
		            	else
		            		view.loadUrl(url);
		                return true;
		            }
		        });
		        
		        WebSettings settings = webView.getSettings();
		        settings.setUseWideViewPort(true);
		        settings.setLoadWithOverviewMode(true);
		        settings.setDomStorageEnabled(true);
		        settings.setDefaultTextEncodingName("UTF-8");
		        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
		        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
		        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
		        settings.setAllowFileAccessFromFileURLs(false);
		        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
		        settings.setAllowUniversalAccessFromFileURLs(false);
		        //开启JavaScript支持
		        settings.setJavaScriptEnabled(true);
		        // 支持缩放
		        settings.setSupportZoom(true);
		        //辅助WebView处理图片上传操作
		        webView.setWebChromeClient(new MyChromeWebClient());
		        //加载地址

		        String url="http://store.youxin123.cn/#?sign="+new String(ret);
		        webView.loadUrl(url);

				//Uri content_url = Uri.parse("http://pay.qian178.com/user.php/Login/single_login?sign="+new String(ret));
				//intent.setData(content_url);
				//startActivity(intent);
	            
			}
			else
			{
				Toast.makeText(this, "尚未登陆", Toast.LENGTH_LONG).show();
			}
			return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static String encrypt(String dataStr) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(dataStr.getBytes("UTF8"));
			byte s[] = m.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
            Toast.makeText(this,
                    "授权成功", Toast.LENGTH_SHORT).show();
            mMpj = MainActivity.mMpmngr.getMediaProjection(MainActivity.RESULT_OK, data);
            capture=new Capture(mMpj);
        }
        if (requestCode == PHOTO_REQUEST) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else if (requestCode == VIDEO_REQUEST) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;

            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                if (resultCode == RESULT_OK) {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{result});
                    mUploadCallbackAboveL = null;
                } else {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{});
                    mUploadCallbackAboveL = null;
                }

            } else if (mUploadMessage != null) {
                if (resultCode == RESULT_OK) {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                } else {
                    mUploadMessage.onReceiveValue(Uri.EMPTY);
                    mUploadMessage = null;
                }

            }
        }else{
    		this.photo.onResult(requestCode, resultCode, data);
        }	
	}

	public boolean isNotificationListenersEnabled() {
        String pkgName = getPackageName();
        final String flat = android.provider.Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    protected boolean gotoNotificationAccessSetting() {
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;

        } catch (ActivityNotFoundException e) {//普通情况下找不到的时候需要再特殊处理找一次
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                startActivity(intent);
                return true;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            Toast.makeText(this, "对不起，您的手机暂不支持", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }
    private void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, com.qianyou.listener5.NLService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(context, com.qianyou.listener5.NLService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
    @Override  
    public boolean dispatchKeyEvent(KeyEvent event) {  
    	
      if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {  
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) { 
        	//dialog();
        	if(webView==null)
        	{
        		//Log.T("alipay:2019-12-14 16:50 支付宝通知\nmc123小前卫通过扫码向你付款1元");
        		//Log.T("fsj:2020-02-10 11:04    丰收家商户\n微信收款0.01元");
        		//print(String.format("%f", Math.random()));
        		
        		moveTaskToBack(false);  
        	}
        	else
        	{
        		if (webView.canGoBack()) {
                    while(webView.canGoBack())
                    	webView.goBack();
                    webView.setVisibility(View.INVISIBLE);
                } else {
                	
                	if(webView.getVisibility() == View.VISIBLE)
                	{
                		webView.setVisibility(View.INVISIBLE);
                	}
                	else
                	{     
                		moveTaskToBack(false);            	          		
                	}
                }
        		refrush();
        	}
//        	for(int i=0;i<100;i++)
//        	{
//        	Log.T("wx:2019-12-16 11:49    微信支付\n[6条]微信支付: 微信支付收款0.01元(朋友到店)");
//        	Log.T("alipay:2019-12-14 16:50 支付宝通知\nmc小前卫通过扫码向你付款500.00元");
//        	}
        }  
        return true;  
      }  
      return super.dispatchKeyEvent(event);  
    } 
    protected void dialog() {  
        AlertDialog.Builder builder = new Builder(this);  
        builder.setMessage("确定要退出吗?");  
        builder.setTitle("提示");  
        builder.setPositiveButton("确认",  
        new android.content.DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                
            }  
        });  
        builder.setNegativeButton("取消",  
        new android.content.DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	dialog.dismiss();  
            }  
        });  
        builder.create().show();  
    }
//    public void safeExit()
//    {
//
//    	Intent intent=new Intent();
//		intent.setAction("com.qianyou.listener5.TSMsg");
//		intent.putExtra("action",MainActivity.EXIT);
//		sendBroadcast(intent);
//    }
    public static void toSelfSetting(Context context) {
    	Intent mIntent = new Intent(); 
    	mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    	if (Build.VERSION.SDK_INT >= 9) { 
    		mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS"); 
    		mIntent.setData(Uri.fromParts("package", context.getPackageName(), null)); 
		} else if (Build.VERSION.SDK_INT <= 8) { 
			mIntent.setAction(Intent.ACTION_VIEW); 
			mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails"); 
			mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName()); 
		} 
    	context.startActivity(mIntent); 
    }  
 
    private Uri imageUri;

    //自定义 WebChromeClient 辅助WebView处理图片上传操作【<input type=file> 文件上传标签】
    public class MyChromeWebClient extends WebChromeClient {
        // For Android 3.0-
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            if (videoFlag) {
                //recordVideo();
            } else {
                photo();
            }

        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            if (videoFlag) {
                //recordVideo();
            } else {
                photo();
            }
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            if (videoFlag) {
                //recordVideo();
            } else {
                photo();
            }
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            if (videoFlag) {
                //recordVideo();
            } else {
                photo();
            }
            return true;
        }
    }

    private void photo(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"),PHOTO_REQUEST);
    }



    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != PHOTO_REQUEST || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }
    /**
     * 动态权限申请
     */
    @SuppressLint("NewApi") public void requestPower() {
        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,android.os.Process.myPid(), android.os.Process.myUid()) != PackageManager.PERMISSION_GRANTED) {
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Toast.makeText(this, "需要读写权限，请打开设置开启对应的权限", Toast.LENGTH_LONG).show();
            }
            else
            {
            	 requestPermissions(
                         this,
                         new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                 Manifest.permission.READ_EXTERNAL_STORAGE},
                         1);
            }
        }
    }
    public interface RequestPermissionsRequestCodeValidator {
        public void validateRequestPermissionsRequestCode(int requestCode);
    }
    public static void requestPermissions(final  Activity activity,
            final  String[] permissions, final int requestCode) {
        if (activity instanceof RequestPermissionsRequestCodeValidator) {
            ((RequestPermissionsRequestCodeValidator) activity)
                    .validateRequestPermissionsRequestCode(requestCode);
        }
        activity.requestPermissions(permissions, requestCode);
    }
    @SuppressLint("NewApi") public static boolean checkInstallPermission()
    {
    	if (Build.VERSION.SDK_INT >= 26) {
			boolean haveInstallPermission = instance.getPackageManager().canRequestPackageInstalls();
	        if(!haveInstallPermission){
	            Uri packageURI = Uri.parse("package:"+instance.getPackageName());
	            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
	            instance.startActivityForResult(intent, 10086);
	            return false;
	        }
    	}
        return true;
    }

	public void startCapture() {
		mMpmngr = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
		Intent captureIntent = mMpmngr.createScreenCaptureIntent();
		startActivityForResult(captureIntent, REQUEST_CODE);
    }
	interface JS {
		@JavascriptInterface
	    @SuppressWarnings("unused")
	    public void showSource(String html) ;
	}
	
	public void openXYPage(String url,JS js){
		WebView webView = findViewById(R.id.webView1);
		isCHANGEWEB=false;
		webView.setVisibility(View.INVISIBLE);

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);
        //辅助WebView处理图片上传操作
        webView.setWebChromeClient(new MyChromeWebClient());
        //加载地址
        webView.addJavascriptInterface(js, "lindang");
        webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				if (isCHANGEWEB==false) {
					view.loadUrl("javascript:lindang.showSource(document.documentElement.outerHTML);");
					try {
						Thread.sleep(500);
						view.loadUrl("file:///android_asset/test.html");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isCHANGEWEB=true;
				}
				super.onPageFinished(view, url);
			}
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
        });
        
        
        webView.loadUrl(url);
	}
	
	public void fixedAmount(String amount){
		Intent intent = getPackageManager().getLaunchIntentForPackage(ALIPAY_PACKAGE_NAME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        AliPayHook.isTIAOZHUAN=false;
        qrnum = qrnum+1;
        Intent broadCastIntent = new Intent();
        Random random = new Random();
        broadCastIntent.putExtra("qr_money",amount );//String.valueOf(random.nextInt(100) + 1)
        broadCastIntent.putExtra("beiZhu", "测试");
        broadCastIntent.putExtra("num", String.valueOf(qrnum));
        broadCastIntent.setAction(AlipayBroadcast.CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION);
        sendBroadcast(broadCastIntent);
		
//        Intent broadCastIntent = new Intent();
//        broadCastIntent.setAction(AlipayBroadcast.CONSULT_GET_AMOUNT);
//        sendBroadcast(broadCastIntent);
	}
	
	public void post(final JSONObject jo){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Listener.sendJson(jo.toString(), "");
				//Log.T("jobject2="+jo.toString());
				String dataString = GetJsonData.getJsonData(jo,"http://cy.youxin123.cn/index/store/add_alipay_guma");
				//Log.T("data="+dataString);
				try {
					JSONObject object = new JSONObject(dataString);
					Log.T(object.getString("msg"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.T("数据上传失败");
				}
			}
		}).start();
		
	}
	
	public void warnZFB(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("data", "支付宝没有响应");
					String daString = GetJsonData.getJsonData(jsonObject, "http://cy.youxin123.cn/index/log/listening");
					JSONObject object = new JSONObject(daString);
					Log.T(object.getString("msg"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(500);
					JSONObject job = new JSONObject();
					job.put("action", 91);
					job.put("msg", "alipay error");
					Utils.sendToMain(job.toString(), false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	//执行shell命令
	public void execShellCmd(String cmd) {
		 
		try {
			// 申请获取root权限，这一步很重要，不然会没有作用
			Process process = Runtime.getRuntime().exec("su");
			// 获取输出流
			OutputStream outputStream = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			dataOutputStream.writeBytes(cmd);
			dataOutputStream.flush();
			dataOutputStream.close();
			outputStream.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	//检测出现问题重新打开一下支付宝
	public void openz(){
		Intent intent = new Intent();
	 	PackageManager packageManager = this.getPackageManager();
	 	intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
	 	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
	 	startActivity(intent);
	 	//Log.T("qqqq");

	}
	
	public void copy(String data){
		//获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", data);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
	}
	
	public static void openSDK(final String url){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Log.T("bbbb");
				String tradeString = AliPayHook.getTextCenter(url, "biz_no=", "_");
				String Info="trade_no="+tradeString+"&biz_sub_type=peerpay_trade&presessionid=&app=tb&channel=&type2=gulupay&bizcontext={\"presessionid2\":\"qogir\",\"method2\":\"alipay.trade.app.pay\",\"format2\":\"json\",\"charset2\":\"utf-8\",\"sign_type2\":\"RSA2\",\"sign2\":\"\",\"biz_type\":\"share_pp_pay\",\"timestamp2\":\"\",\"version\":\"1.0\",\"app_auth_token2\":\"token1\",\"key2\":\"alipay.trade\",\"timeout_express2\":\"100\",\"merchant_order_no2\":\"1606047822125\"}";
				(new PayTask(MainActivity.instance)).payV2(Info, true);
				//Log.T("vccc");
			}
		}).start();
	}
	
	//支付宝吱口令打开
	public boolean openZKL(String data){
		
		Intent intent = new Intent();
	 	PackageManager packageManager = this.getPackageManager();
	 	intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
	 	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
	 	startActivity(intent);
	 	//Log.T("qqqq");
	 	return true;
	}
	
	Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handler.removeCallbacks(run_scroll_down);
            handler.removeCallbacks(run_scroll_up);
        }
    };

    public void listScrollUp() {
        listScrollOff();
        mhandler.postDelayed(run_scroll_up, 0);
    }
    public void listScrollDown() {
        listScrollOff();
        mhandler.postDelayed(run_scroll_down, 0);
    }
    public void listScrollOff() {
        mhandler.removeCallbacks(run_scroll_down);
        mhandler.removeCallbacks(run_scroll_up);
    }

    Runnable run_scroll_up = new Runnable() {
        @Override
        public void run() {
            listView.smoothScrollBy(15, 10);//这里可以调整滑动的速度，向上滑就是正数。
            mhandler.postDelayed(run_scroll_up, 10);
        }
    };
    Runnable run_scroll_down = new Runnable() {
        @Override
        public void run() {
            listView.smoothScrollBy(-15, 10);//这里调整向下滑的速度，向下滑是负数
            mhandler.postDelayed(run_scroll_down, 10);
        }
    };
	//获取点击坐标
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//	    int rawX= (int) ev.getRawX();
//	    int rawY= (int) ev.getRawY();
//	    Log.T("position_X"+ String.valueOf(rawX)+",position_Y"+String.valueOf(rawY));
//	    return super.dispatchTouchEvent(ev);
//	}
}
