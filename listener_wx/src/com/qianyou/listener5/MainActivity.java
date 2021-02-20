package com.qianyou.listener5;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qianyou.jieping.Capture;
import com.qianyou.jieping.Utils;
import com.qianyou.jieping.Capture.CallBack;
import com.qianyou.listener5.R;
import com.qianyou.nat.GetJsonData;
import com.qianyou.nat.Listener;
import com.qianyou.nat.MD5Util;
import com.qianyou.wangxin.CodeBean;
import com.qianyou.wangxin.CodeH5Bean;
import com.qianyou.wangxin.WangXinHongBao; 

import de.robv.android.xposed.XposedHelpers;

import android.Manifest;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothHidDevice.Callback;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
	public static Boolean isGETQRCODEURL = false;
	public static Boolean isBDWangXin = false;
	public static Boolean isGETZFBUID = false;
	public ListView listView,listViewLog;
	public Button button;
	private View view_custom;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    
    private Timer mTimer;
    private MyTimerTask myTimerTask;
    
    
    public static String pAYURLString="";
    public static String BDEVICEID="123456";
    
    public static int WXCOUNT=0;
	
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
	public final static int GETQRCODEURL=111;
	public final static int CHECKBDWANGXIN=112;
	public final static int GETZFBUID=113;
	public final static int GETXIANYU=114;
	public final static int CREATEWXPAM = 115;//wx产红包
	public final static int GETDEVICEID = 116;//获取id
	public final static int CREATEWXPURL = 117;//wx产h5url
	
	
	public final static int GETMSG = 555;
	
	public final static int PORT=5456;//nlservice 5457  chajian 5458 capture 5459
	
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
    public MediaProjection mMpj=null;
    public static int ImageReaderFormat;//多多模拟器0x2,小米0x1
    public static boolean captureImageReaderFormatException=false;
	
	public Map<String,JSONObject> senddatas=new HashMap<String, JSONObject>();
	public Map<String, String> msels=new HashMap<String, String>();
	public Map<String, CheckBox> mcsels=new HashMap<String, CheckBox>();
	public Map<Integer,LinearLayout> allit=new HashMap<Integer, LinearLayout>();
	public Map<String,Long> xpitems=new HashMap<String, Long>();

	  private static MainActivity mmContext;
	  private BillReceived billReceived;
	  private int breakIndex = 0;
	  private int cur = 0;
	  public String currentConId="";
	  public String currentName="";
	  public List<CodeBean> qrList = new ArrayList<CodeBean>();
	  public List<String> testList = new ArrayList<String>();
	  public int size = 1;
	
	
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
							Thread.sleep(1000*30);
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
		  mContext = MainActivity.this;
		  this.billReceived = new BillReceived();
	      IntentFilter intentFilter = new IntentFilter();
	      intentFilter.addAction("com.qianyou.wangxin.billreceived");
	      intentFilter.addAction("com.qianyou.wangxin.msgreceived");
	      intentFilter.addAction("com.qianyou.wangxin.qrcodereceived");
	      intentFilter.addAction("com.qianyou.wangxin.qrcodereceived.fail");
	      intentFilter.addAction("com.qianyou.wangxin.tradenoreceived");
	      intentFilter.addAction("com.qianyou.wangxin.current.conid");
	      intentFilter.addAction("com.qianyou.wangxin.current.account");
	      intentFilter.addAction("com.qianyou.wangxin.start.create");
	      intentFilter.addAction("com.qianyou.wangxin.back.startapp");
	      intentFilter.addAction("com.qianyou.wangxin.check.can.rob");
	      intentFilter.addAction("com.qianyou.wangxin.to.h5.back");
	      intentFilter.addAction("com.qianyou.wangxin.uplist");
	      intentFilter.addAction("com.qianyou.xianyu.TransferMoneyModel");
	      intentFilter.addAction("com.qianyou.wangxin.wangxin");
          intentFilter.addAction("com.qianyou.wangxin.rob");
          intentFilter.addAction("com.qianyou.wangxin.check.wx");
          intentFilter.addAction("com.qianyou.wangxin.to.h5");
	      registerReceiver(this.billReceived, intentFilter); 
		  //final Button button2=(Button)findViewById(R.id.button2);
		  listView=(ListView)findViewById(R.id.listView1);
		  listViewLog=(ListView)findViewById(R.id.listView2);
		  button=(Button)findViewById(R.id.button1);
		  TextView versionT=(TextView) findViewById(R.id.textViewVersion2);
		  versionT.setText("版本：" +getVersion()+"  c:" + MainActivity.instance.getResources().getString(R.string.servername));
		  
		  SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
		  tv=(TextView)findViewById(R.id.textView1);
		  
		  mTimer = new Timer(true);
		  
		  button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Log.T("开始产码");
//					Intent intent = new Intent("com.qianyou.wangxin.wangxin");
//					intent.putExtra("money", "1");
//					StringBuilder stringBuilder = new StringBuilder();
//					stringBuilder.append(System.currentTimeMillis());
//					stringBuilder.append("_");
//					stringBuilder.append(1);
//					intent.putExtra("orderid", stringBuilder.toString());
//					intent.putExtra("qunid", MainActivity.instance.currentConId);
//					intent.putExtra("type", "wangxin");
//					MainActivity.instance.sendBroadcast(intent);
//					MainActivity.instance.testList.clear();
//					for (int i = 0; i < 5; i++) {
//						CodeBean codeBean = new CodeBean();
//				          codeBean.setMoney(String.valueOf(Math.random()));
//				          codeBean.setOrderNo(String.valueOf(System.currentTimeMillis()));
//				          codeBean.setQunId(MainActivity.this.currentConId);
//				          codeBean.setPayUrl(System.currentTimeMillis()+"urlpay");
//				          codeBean.setMark(System.currentTimeMillis()+"_"+i);
//				          JSONObject jObject = new JSONObject();
//				          try {
//				        	  jObject.put("money", String.valueOf(Math.random()));
//					          jObject.put("orderNo", String.valueOf(System.currentTimeMillis()));
//					          jObject.put("mark", System.currentTimeMillis()+"_"+i);
//				          } catch (JSONException e) {
//							// TODO Auto-generated catch block
//				        	  e.printStackTrace();
//				          }
//				          MainActivity.this.testList.add(jObject.toString());
//					}
//					Log.T(MainActivity.instance.testList.toString());
//					JSONObject joJsonObject = new JSONObject();
//					try {
//						joJsonObject.put("deviceid", BDEVICEID);
//						joJsonObject.put("sign", MD5Util.getMD5Str(BDEVICEID+"cy"));
//						joJsonObject.put("code", testList.toString());
//						Log.T(joJsonObject.toString());
//					} catch (JSONException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					
					
					//-----------------------------------------------
					
					String sel = "";
					String wx=msels.get("wx");
					String alipay=msels.get("alipay");
					String bank=msels.get("bank");
					String fsj=msels.get("fsj");
					String youzan=msels.get("youzan");
					String xianyu_check=msels.get("xianyu_check");
					String alipay_xqd=msels.get("alipay_xqd");
					String alipay_scan=msels.get("alipay_scan");
					String xianyu_trans=msels.get("xianyu_trans");
					String wangxin=msels.get("wangxin");
					if(wx!=null&&!wx.equals(""))
						sel=msels.get("wx");
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
					if(wangxin!=null&&!wangxin.equals(""))
					{
						if(sel.equals(""))
							sel=wangxin;
						else
							sel=sel+","+wangxin;
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
				            editor.putString("alipay", alipay) ; 
				            editor.putString("fsj", fsj) ;
				            editor.putString("bank", bank) ; 
				            editor.putString("youzan", youzan);
				            editor.putString("xianyu_check", xianyu_check);
				            editor.putString("alipay_xqd", alipay_xqd); 
				            editor.putString("alipay_scan", alipay_scan); 
				            editor.putString("xianyu_trans", xianyu_trans);
				            editor.putString("wangxin", wangxin);
				            editor.commit() ;//提交
							String deviceid=sel;
							
							if(wx!=null&&!"".equals(wx))
								Log.T("微信　："+wx);
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
							if(wangxin!=null&&!"".equals(wangxin))
								Log.T("旺信："+wangxin);
							if(alipay_xqd!=null&&!"".equals(alipay_xqd))
								Log.T("小钱袋："+alipay_xqd);
							if(alipay_scan!=null&&!"".equals(alipay_scan))
								Log.T("支付宝扫码："+alipay_scan);
							if(listener.setID(deviceid)==1)
							{
								Listener.deviceid=deviceid;
								Log.T("接单成功,"+deviceid);
								button.setText("停止接单");
								isBand=true;
								if (!MainActivity.instance.currentConId.equals("")) {
									JSONObject jo=new JSONObject();
									try {
										jo.put("act","WXCUSE");
										jo.put("wxname", MainActivity.instance.currentName);
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									Listener.sendJson(jo.toString(), "");
									if (!MainActivity.instance.currentName.equals(deviceid)) {
										Log.T("账号名称与旺信账号不匹配");
										Toast.makeText(getApplicationContext(), "账号名称与旺信账号不匹配",Toast.LENGTH_LONG).show();
									}
								}
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
						int alipayidx=t.indexOf("alipay:",7);
						int fsjidx=t.indexOf("fsj:",7);
						int bankidx=t.indexOf("bank:",7);
						int youzanidx=t.indexOf("youzan:",7);
						int xianyu_checkidx=t.indexOf("xianyu_check:",7);
						int alipay_xqdidx=t.indexOf("alipay_xqd:",7);
						int alipay_scanidx=t.indexOf("alipay_scan:",7);
						int xianyu_transidx=t.indexOf("xianyu_trans:",7);
						int wangxinidx=t.indexOf("wangxin:",7);
						if(t.indexOf("-last-")!=-1)
						{
							wxidx=-1;
							alipayidx=-1;
							fsjidx=-1;
							bankidx=-1;
							youzanidx=-1;
							xianyu_checkidx=-1;
							alipay_xqdidx=-1;
							alipay_scanidx=-1;
							xianyu_transidx=-1;
							wangxinidx=-1;
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
						else if(wangxinidx>0)
						{
							wangxinidx+=7;
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), timeidx, wangxinidx+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(240, 140, 61)), wangxinidx+1, wangxinidx+17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
							int typeidx2=t.indexOf('\n',wangxinidx+17);
							ss.setSpan(new ForegroundColorSpan(Color.rgb(127,0,85)),wangxinidx+17, typeidx2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
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
			msels.put("alipay", sp.getString("alipay",null));
			msels.put("alipay_xqd", sp.getString("alipay_xqd",null));
			msels.put("alipay_scan", sp.getString("alipay_scan",null));
			msels.put("fsj", sp.getString("fsj",null));
			msels.put("bank", sp.getString("bank",null));
			msels.put("youzan", sp.getString("youzan", null));
			msels.put("xianyu_check", sp.getString("xianyu_check", null));
			msels.put("xianyu_trans", sp.getString("xianyu_trans", null));
			msels.put("wangxin", sp.getString("wangxin", null));
			
			String sel = "";
			String wx=msels.get("wx");
			String alipay=msels.get("alipay");
			String alipay_xqd=msels.get("alipay_xqd");
			String alipay_scan=msels.get("alipay_scan");
			String fsj=msels.get("fsj");
			String bank=msels.get("bank");
			String youzan=msels.get("youzan");
			String xianyu_check=msels.get("xianyu_check");
			String xianyu_trans=msels.get("xianyu_trans");
			String wangxin=msels.get("wangxin");
			if(wx!=null&&!wx.equals(""))
				sel=msels.get("wx");
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
			if(wangxin!=null&&!wangxin.equals(""))
			{
				if(sel.equals(""))
					sel=wangxin;
				else
					sel=sel+","+wangxin;
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
			if(alipay!=null&&!"".equals(alipay))
				Log.T("支付宝："+alipay);
			if(alipay_xqd!=null&&!"".equals(alipay_xqd))
				Log.T("小钱袋："+alipay_xqd);
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
			if(wangxin!=null&&!"".equals(wangxin))
				Log.T("旺信　："+wangxin);
			
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
        menu.findItem(R.id.xqdcj).setVisible(false);
        menu.findItem(R.id.wxhb).setVisible(false);
        menu.findItem(R.id.gmcj).setVisible(false);
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
		unregisterReceiver(this.billReceived);
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
//        if(id==R.id.test)
//        {
//        	print("你的账号 asdqqwi@qq.com 于12月29日 在其他设备登陆，如非本人，请尽快修改密码。");
//        }
        if (id==R.id.wxhb) {
//			Intent intent = new Intent(MainActivity.this, WangXinHongBao.class);
//			startActivity(intent);
        	JSONObject jObject = new JSONObject();
        	try {
//        		jObject.put("action", 117);
//				jObject.put("money", "1");
//				jObject.put("mark", "1612762750465_0");
//				jObject.put("orderNo", "12621186_1485_1612762750");
//				jObject.put("payUrl", "service=\"alipay.fund.stdtrustee.order.create.pay\"&partner=\"2088401309894080\"&_input_charset=\"utf-8\"&notify_url=\"https://wwhongbao.taobao.com/callback/alipay/notifyPaySuccess.do\"&out_order_no=\"12621186_1485_1612762750_d173edf3535aa2064e956be42b6036b4_1\"&out_request_no=\"12621186_1485_1612762750_d173edf3535aa2064e956be42b6036b4_1_p\"&product_code=\"SOCIAL_RED_PACKETS\"&scene_code=\"MERCHANT_COUPON\"&amount=\"1.00\"&pay_strategy=\"CASHIER_PAYMENT\"&receipt_strategy=\"INNER_ACCOUNT_RECEIPTS\"&platform=\"DEFAULT\"&channel=\"APP\"&order_title=\"淘宝现金红包\"&master_order_no=\"2021020810002001800589808009\"&order_type=\"DEDUCT_ORDER\"&auid=\"2088202332279574\"&extra_param=\"{\"payeeShowName\":\"淘宝现金红包\"}\"&pay_timeout=\"30m\"&order_expired_time=\"360d\"&sign=\"X43V0COeRRwtL2FsHLVH4Wpp%2F1iuq%2BoCE%2BZaxnEKYnoLtx%2F5PAHvePwThrZfbJgF2n95ejazkMOkcjPv9C3JOUvTZVdM4RnUHtJ28Bl%2BT%2BSfDIqw8AUZzckJrZNbAtKncl5sAsYtXVM3vplxH%2BUZFRa6fXhdGssbe9IRzEXHcTYu%2FgYiMFKXhF%2FAcU7RYA4WFUxV2Rv%2FUoBfwvW9jbi5cKv7hyHbIFstOyRzB3ZXU4S%2FYKi%2FMN%2BhuqhSb%2BFr%2Bw%2BndD9y9jrX4emHTSgq%2BjruLSFpkQwo7QjCaFTDN%2Biq3dVgF1v9S3nMUGcK1UPCrZhW3DBP0lXdIDlgGw2eJFasEQ%3D%3D\"&sign_type=\"RSA\"");
//				jObject.put("qunId", "1035613090");
//				jObject.put("orderid", "588953148");
        		jObject.put("action", 115);
        		jObject.put("money", 100);
        		jObject.put("num", 5);
				Utils.sendToMain(jObject.toString(), false);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        if (id==R.id.gmcj) {
//    	    Intent intent = new Intent("com.qianyou.wangxin.to.h5");
//            intent.putExtra("mark", "1612762750493_1");
//            intent.putExtra("money", "1");
//            intent.putExtra("orderNo", "12621186_2100_1612748708");
//            intent.putExtra("payurl", "service=\"alipay.fund.stdtrustee.order.create.pay\"&partner=\"2088401309894080\"&_input_charset=\"utf-8\"&notify_url=\"https://wwhongbao.taobao.com/callback/alipay/notifyPaySuccess.do\"&out_order_no=\"12621186_2100_1612748708_d173edf3535aa2064e956be42b6036b4_1\"&out_request_no=\"12621186_2100_1612748708_d173edf3535aa2064e956be42b6036b4_1_p\"&product_code=\"SOCIAL_RED_PACKETS\"&scene_code=\"MERCHANT_COUPON\"&amount=\"1.00\"&pay_strategy=\"CASHIER_PAYMENT\"&receipt_strategy=\"INNER_ACCOUNT_RECEIPTS\"&platform=\"DEFAULT\"&channel=\"APP\"&order_title=\"淘宝现金红包\"&master_order_no=\"2021020810002001380520494649\"&order_type=\"DEDUCT_ORDER\"&auid=\"2088202332279574\"&extra_param=\"{\"payeeShowName\":\"淘宝现金红包\"}\"&pay_timeout=\"30m\"&order_expired_time=\"360d\"&sign=\"bPSNke5nTHv%2FFNEqmszzs9Ct%2FrX64CHJCdqsTFvTndEZEPYSu14MBYujKkwg1giLpUkSzznVGZX8yWBEYE1OnnMr4PpP1olzU%2FxpwQtvNXr66YKkntIpFc5sZqIVd4A7eA0J6SV3poBI9u7lM1oJc7ik4tg0aeL32cN1EhMkbcjNipJmDUb4iuzFr%2FLSA%2BSEyBd3u6Z2pYQI%2FuiOa%2FppISib0L%2B0DoELrsfuV%2BWMTRDbIpjORvdWgImIrca3pwBH%2F%2B8hDuuP%2F1s6G9UmKnBYST6kaJjf958h%2FeGyFpM6tkxN%2BYXP8s8XY3sCG%2FUr4l4xlFn%2FvPT1%2FKN9pN3aIQoulg%3D%3D\"&sign_type=\"RSA\"");
//            intent.putExtra("qunId", "1035613090");
//            intent.putExtra("orderid", "588953148");
//            MainActivity.this.sendBroadcast(intent);
        	Intent intent = new Intent();
        	intent.setAction("com.qianyou.wangxin.check.wx");
			MainActivity.instance.sendBroadcast(intent);
			//createCodeAccount();
		}
        if (id==R.id.xqdcj) {
//        	Intent intent = new Intent(MainActivity.this, CreateXQDAccount.class);
//			startActivity(intent);
        	JSONObject jObject = new JSONObject();
            try {
				jObject.put("h5Url", "https://");
	            jObject.put("orderid", "15487966");
	            Log.T(jObject.toString());
	            postPayUrl(jObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

		        String url="http://store.ty157.cn/#?sign="+new String(ret);
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
		webView.setVisibility(View.VISIBLE);

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
				view.loadUrl("javascript:lindang.showSource(document.documentElement.outerHTML);");
				super.onPageFinished(view, url);
			}
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
        });
        
        
        webView.loadUrl(url);
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
	
	//自动创建个码账户
//	public void createCodeAccount(){
//		builder = new AlertDialog.Builder(mContext);
//		final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
//		view_custom = inflater.inflate(R.layout.view_createqecode, null,false);
//		builder.setView(view_custom);
//		builder.setCancelable(false);
//		alert = builder.create();
//		alert.show();
//		final EditText txt_account = view_custom.findViewById(R.id.editText_qr);
//		TextView txt_code = view_custom.findViewById(R.id.txt_qrcodeurl);
//		SharedPreferences sp = getSharedPreferences("UserData",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
//        final String qrcodeurl = sp.getString("qrcodeurl","");  
//		txt_code.setText(qrcodeurl);
//		
//		view_custom.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = getPackageManager().getLaunchIntentForPackage("com.alibaba.mobileim");
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                startActivity(intent);
//            	Intent intent = new Intent();
//            	intent.setAction("com.qianyou.wangxin.check.wx");
//				MainActivity.instance.sendBroadcast(intent);
////                Intent intent2 = new Intent((Context)stringBuilder, XposedHelpers.findClass("com.alibaba.mobileim.ui.setting.SettingAboutActivity", stringBuilder.getClassLoader()));
////                intent2.addFlags(268435456);
////                stringBuilder.startActivity(intent2);
////            	if (txt_account.getText().toString().equals("") || qrcodeurl.equals("")) {
////					Toast.makeText(mContext, "请输入账户名", 0).show();
////				} else {
////					alert.dismiss();
////				}
//            }
//        });
//		
//		view_custom.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//            }
//        });
//	}
	
	//wx创建红包
	public static void wxCreatePam(int a , int n){
		MainActivity.instance.size = n;
		MainActivity.instance.testList.clear();
		WXCOUNT = 0;
		MainActivity.instance.StartTimer();
		for (int i = 0; i < n; i++) {
			Intent intent = new Intent("com.qianyou.wangxin.wangxin");
			intent.putExtra("money", String.valueOf(a));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.currentTimeMillis());
			stringBuilder.append("_");
			stringBuilder.append(i);
			intent.putExtra("orderid", stringBuilder.toString());
			intent.putExtra("qunid", MainActivity.instance.currentConId);
			intent.putExtra("type", "wangxin");
			//Log.T("aaa="+i);
			MainActivity.instance.sendBroadcast(intent);
		}
	}
	
	public void wxCreatePUrl(String a,String b,String c,String d,String e,String f){
		MainActivity.instance.StartTimer();
		Intent intent = new Intent("com.qianyou.wangxin.to.h5");
        intent.putExtra("mark", a);
        intent.putExtra("money", b);
        intent.putExtra("orderNo", c);
        intent.putExtra("payurl", d);
        intent.putExtra("qunId", e);
        intent.putExtra("orderid", f);
        MainActivity.this.sendBroadcast(intent);
//        try {
//        	Log.T("wwwwwwwwwwwwwwww");
//        	Log.T(jobject.toString());
//			String mark = jobject.getString("mark");
//			Log.T(mark);
//			String orderNo = jobject.getString("orderNo");
//			Log.T(orderNo);
//			String qunId = jobject.getString("qunId");
//			Log.T(qunId);
//			String orderid = jobject.getString("orderid");
//			Log.T(orderid);
//			String money = jobject.getString("money");
//			Log.T(money);
//			String payUrl = jobject.getString("payUrl");
//			Log.T(payUrl);
//    		Intent intent = new Intent("com.qianyou.wangxin.to.h5");
//			intent.putExtra("mark", mark);
//	        intent.putExtra("money", money);
//	        intent.putExtra("orderNo", orderNo);
//	        intent.putExtra("payurl", payUrl);
//	        intent.putExtra("qunId", qunId);
//	        intent.putExtra("orderid", orderid);
//	        MainActivity.instance.sendBroadcast(intent);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			Log.T("wxcreate="+e.toString());
//			e.printStackTrace();
//		}
	}
	
	//上传红包参数
	public void postUrl(final String jobObject){
		new Thread(new Runnable() {
			@Override
			public void run() {
				//Log.T("jo=="+jobObject);
				String url=MainActivity.instance.getResources().getString(R.string.server)+"index/store/add_wangxin";
				JSONObject joJsonObject = new JSONObject();
				try {
					JSONArray jArray = new JSONArray(jobObject);
					joJsonObject.put("deviceId", BDEVICEID);
					joJsonObject.put("sign", MD5Util.getMD5Str(BDEVICEID+"cy"));
					joJsonObject.put("code", jArray);
					//Log.T(joJsonObject.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String dataString = GetJsonData.getJsonData(joJsonObject.toString(),url);
				//Log.T(dataString);
				try {
					JSONObject object = new JSONObject(dataString);
					//Log.T(object.getString("msg"));
					//Toast.makeText(instance, object.getString("msg"), Toast.LENGTH_SHORT).show();
					String ret="FAIL";
					int c = object.getInt("code");
					if (c == 1 || object.getString("msg").equals("执行成功")) {
						Log.T("上传成功");
						ret = "OK";
					}
					MainActivity.instance.CancelTimer();
					//Log.T(ret);
					JSONObject jo=new JSONObject();
					try {
						jo.put( "act","RECREATEWXPAM");
						jo.put("ret", ret);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Listener.sendJson(jo.toString(), "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.T("数据上传失败");
					JSONObject jo=new JSONObject();
					try {
						jo.put( "act","RECREATEWXPAM");
						jo.put("ret", "FAIL");
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Listener.sendJson(jo.toString(), "");
				}
			}
		}).start();
	}
	
	//上传红包h5参数
	public void postPayUrl(final String jobObject){
		new Thread(new Runnable() {
			@Override
			public void run() {
				//Log.T("jo=="+jobObject);
				String url=MainActivity.instance.getResources().getString(R.string.server)+"/index/ws/send_h5url";
				String dataString = GetJsonData.getJsonData(jobObject,url);
				//Log.T(dataString);
				try {
					JSONObject object = new JSONObject(dataString);
					Log.T(object.getString("msg"));
					//Toast.makeText(instance, object.getString("msg"), Toast.LENGTH_SHORT).show();
					String ret="FAIL";
					if (object.getInt("code")==1 || object.getString("msg").equals("执行成功")) {
						ret = "OK";
					}
					MainActivity.instance.CancelTimer();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.T("数据上传失败");
					
				}
			}
		}).start();
	}
	
	class BillReceived extends BroadcastReceiver {
	    
	    public void onReceive(Context param1Context, Intent param1Intent) {
	      try {
	        String str;
	        if (param1Intent.getAction().contentEquals("com.qianyou.wangxin.billreceived")) {
	          String str2 = param1Intent.getStringExtra("bill_no");
	          String str3 = param1Intent.getStringExtra("bill_money");
	          String str4 = param1Intent.getStringExtra("bill_mark");
	          str = param1Intent.getStringExtra("bill_type");
	          StringBuilder stringBuilder1 = new StringBuilder();
	          stringBuilder1.append(System.currentTimeMillis());
	          stringBuilder1.append("");
	          String str5 = stringBuilder1.toString();
	          String str1 = "";
	          if (str.equals("wangxin"))
	            str1 = "淘宝现金红包"; 
	          StringBuilder stringBuilder2 = new StringBuilder();
	          stringBuilder2.append("收到");
	          stringBuilder2.append(str1);
	          stringBuilder2.append("订单,订单号：");
	          stringBuilder2.append(str2);
	          stringBuilder2.append("金额：");
	          stringBuilder2.append(str3);
	          stringBuilder2.append("备注：");
	          stringBuilder2.append(str4);
	          Log.T(stringBuilder2.toString());
	          JSONObject jo = new JSONObject();
				jo.put("act", "CHONGZHI");
				jo.put("money", str3);
				jo.put("orderNo", str2);
				jo.put("raw",str4);
				jo.put("type", str);
				instance.listener.sendJson(jo.toString(), "");
	        } else if (param1Intent.getAction().contentEquals("com.qianyou.wangxin.qrcodereceived")) {
	          String str4 = param1Intent.getStringExtra("money");
	          String str1 = param1Intent.getStringExtra("mark");
	          String str2 = param1Intent.getStringExtra("orderNo");
	          String str3 = param1Intent.getStringExtra("type");
	          //String urlString = param1Intent.getStringExtra("url");
	          //urlString = "https:" + urlString +"%3D0";
	          //Log.T(urlString);
	          str = param1Intent.getStringExtra("payurl");
	          pAYURLString=str;
	          StringBuilder stringBuilder2 = new StringBuilder();
	          stringBuilder2.append(System.currentTimeMillis());
	          stringBuilder2.append("");
	          String str5 = stringBuilder2.toString();
	          str4 = (new DecimalFormat("0.00")).format(Double.parseDouble(str4) / 100.0D);
	          StringBuilder stringBuilder1 = new StringBuilder();
	          stringBuilder1.append("生成成功,金额:");
	          stringBuilder1.append(str4);
	          stringBuilder1.append("备注:");
	          stringBuilder1.append(str1);
	          stringBuilder1.append("红包:");
	          stringBuilder1.append(str2);
	          Log.T(stringBuilder1.toString());
//	          CodeBean codeBean = new CodeBean();
//	          codeBean.setMoney(str4);
//	          codeBean.setOrderNo(str2);
//	          codeBean.setQunId(MainActivity.this.currentConId);
//	          codeBean.setPayUrl(str);
//	          codeBean.setMark(str1);
//	          MainActivity.this.qrList.add(codeBean);
	          JSONObject jObject = new JSONObject();
	          try {
	        	  jObject.put("money", str4);
		          jObject.put("orderNo", str2);
		          jObject.put("mark", str1);
		          jObject.put("qunId", MainActivity.this.currentConId);
		          jObject.put("payUrl", str);
	          } catch (JSONException e) {
				// TODO Auto-generated catch block
	        	  e.printStackTrace();
	          }
	          MainActivity.this.testList.add(jObject.toString());
	          WXCOUNT++;
	          //Log.T("chanma="+str);
	          if (WXCOUNT == MainActivity.this.size){
	            Log.T("全部产码完毕。有"+MainActivity.this.testList.size()+"条数据,开始上传"); 
              	//WangXinHongBao.setWXLog("全部产码完毕。请点击上传数据按钮.");
	            postUrl(MainActivity.instance.testList.toString());
	          }
	        } else {
	          StringBuilder stringBuilder;
	          if (param1Intent.getAction().contains("com.qianyou.wangxin.qrcodereceived.fail")) {
	        	  param1Intent.getStringExtra("mark");
	            String str1 = param1Intent.getStringExtra("msg");
	            stringBuilder = new StringBuilder();
	            stringBuilder.append("产吗失败");
	            stringBuilder.append(str1);
	            stringBuilder.append("\n产码终止,已产");
	            stringBuilder.append(MainActivity.this.testList.size());
	            Log.T(stringBuilder.toString());
	            WXCOUNT++;
	            //stringBuilder.append("请点击上传数据按钮.");
	            Log.T(stringBuilder.toString());
	            if (WXCOUNT == MainActivity.this.size){
	            	Log.T("全部产码完毕。有"+MainActivity.this.testList.size()+"条数据,开始上传");  
	              	//WangXinHongBao.setWXLog("全部产码完毕。请点击上传数据按钮.");
	            	postUrl(MainActivity.instance.testList.toString());
		          }
                //WangXinHongBao.setWXLog(stringBuilder.toString());
	          } else if (param1Intent.getAction().contentEquals("com.qianyou.wangxin.msgreceived")) {
	            Log.T(param1Intent.getStringExtra("msg"));
	          } else if (param1Intent.getAction().contentEquals("com.qianyou.xianyu.TransferMoneyModel")) {
	            Log.T("sessionid="+param1Intent.getStringExtra("sessionid"));
	            Log.T("businessId="+param1Intent.getStringExtra("businessId"));
	            Log.T("payeeNick="+param1Intent.getStringExtra("payeeNick"));
	          } else {
	            if (param1Intent.getAction().contentEquals("com.qianyou.wangxin.current.conid")) {
	              String str1 = param1Intent.getStringExtra("conId");
	              MainActivity.this.currentConId = str1;
	              if (!TextUtils.isEmpty(MainActivity.this.currentConId)) {
	                StringBuilder stringBuilder1 = new StringBuilder();
	                stringBuilder1.append("当前目标群id：");
	                stringBuilder1.append(MainActivity.this.currentConId);
	                Log.T(stringBuilder1.toString());
	                JSONObject jo=new JSONObject();
					try {
						jo.put("act","WXCUSE");
						jo.put("wxname", MainActivity.instance.currentName);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Listener.sendJson(jo.toString(), "");
					if (!listener.deviceid.equals("")) {
						if (!MainActivity.instance.currentName.equals(listener.deviceid)) {
							Log.T("账号名称与旺信账号不匹配");
							Toast.makeText(getApplicationContext(), "账号名称与旺信账号不匹配",Toast.LENGTH_LONG).show();
						}
					}
	              } 
	            } else {
	              if (!param1Intent.getAction().contentEquals("com.qianyou.wangxin.start.create"))
	                if (param1Intent.getAction().contentEquals("com.qianyou.wangxin.current.account")) {
	                  String str1 = param1Intent.getStringExtra("currentAc");
	                  stringBuilder = new StringBuilder();
	                  stringBuilder.append("当前账号：");
	                  stringBuilder.append(str1);
	                  Log.T(stringBuilder.toString());
	                  MainActivity.instance.currentName=str1;
	                } else if (param1Intent.getAction().contentEquals("com.qianyou.wangxin.to.h5.back")) {
	                    String str1 = param1Intent.getStringExtra("money");
	                    String str3 = param1Intent.getStringExtra("mark");
	                    String str4 = param1Intent.getStringExtra("orderNo");
	                    param1Intent.getStringExtra("type");
	                    String str5 = param1Intent.getStringExtra("payurl");
	                    String str6 = param1Intent.getStringExtra("h5Url");
	                    String str2 = param1Intent.getStringExtra("qunId");
	                    String str7 = param1Intent.getStringExtra("orderid");
	                    CodeH5Bean codeH5Bean = new CodeH5Bean();
	                    codeH5Bean.setMoney(str1);
	                    codeH5Bean.setOrderNo(str4);
	                    codeH5Bean.setQunId(str2);
	                    codeH5Bean.setPayUrl(str5);
	                    codeH5Bean.setMark(str3);
	                    codeH5Bean.setH5Url(str6);
	                    //deviceId money mark orderNo payUrl qunId h5Url sign
	                    JSONObject jObject = new JSONObject();
//	                    jObject.put("money", str1);
//	                    jObject.put("mark", str3);
//	                    jObject.put("orderNo", str4);
//	                    jObject.put("payUrl",str5 );
//	                    jObject.put("qunId", str2);
	                    jObject.put("h5Url", str6);
	                    jObject.put("orderid", str7);
	                    //str1 = JSONParser.toString(codeH5Bean);
	                    StringBuilder stringBuilder1 = new StringBuilder();
	                    stringBuilder1.append("转换完成,发送数据>>");
	                    //stringBuilder1.append(codeH5Bean);
	                    Log.T(stringBuilder1.toString());
	                    //Log.T(jObject.toString());
	                    postPayUrl(jObject.toString());
	                  }  
	              return;
	            } 
	            return;
	          } 
	        } 
	        return;
	      } catch (Exception exception) {
	        exception.printStackTrace();
	        return;
	      } 
	    }
	  }
	
	
	public void StartTimer(){
		if (mTimer != null){
			if (myTimerTask != null){
		       myTimerTask.cancel();  //将原任务从队列中移除
			}
			myTimerTask = new MyTimerTask();  // 新建一个任务      
			mTimer.schedule(myTimerTask, 8000);
		}
    }
	
	public void CancelTimer(){
		if (mTimer != null){
			if (myTimerTask != null){
		       myTimerTask.cancel();  //将原任务从队列中移除
			}
		}
    }
	
	class MyTimerTask extends TimerTask{
		  @Override
		public void run() {
		// TODO Auto-generated method stub
			  Log.T("通信断开，请重新登陆铃铛和旺信");
			  Log.T("通信断开，请重新登陆铃铛和旺信");
			  Log.T("通信断开，请重新登陆铃铛和旺信");
			  try {
				Listener.diaoXian("通信断开，请重新登陆铃铛和旺信","wangxin");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }

    }
	
}
