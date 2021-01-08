package com.qianyou.jieping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.ResponseResult;
import com.baidu.ocr.sdk.model.Word;
import com.baidu.ocr.sdk.model.WordSimple;
import com.qianyou.listener5.FileLog;
import com.qianyou.listener5.Log;
import com.qianyou.listener5.MainActivity;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.res.Configuration;

public class Capture  {
	private static final String TAG = "bqt";
	public final static int CAP = 110;
	public static Capture capture;
	public Image image=null;
	
	private ImageReader mImageReader;
	private int screenDensity;
	private int windowWidth;
	private int windowHeight;
	private VirtualDisplay mVirtualDisplay;
	private WindowManager wm;
	private static final String mImagePath = Environment
			.getExternalStorageDirectory().getPath() + "/screenshort/";
	private CallBack lcb = null;

	public final static int FALSE = 0;// 失败
	public final static int TRUE = 1; // 成功
	public final static int CANCEL = 2; // 订单已经取消
	public final static int ERRBITMAP = 3; // 创建bitmap失败
	public final static int ERRWRITEFILE = 4; // 写入图片失败
	public final static int ERROCR = 5; // 无法解析图片
	public final static int ERRLISTENER = 6; // 上次的尚未结束
	public final static int ERROPENZFB = 7; // 上次的尚未结束
	public final static int ERREXP = 8; // 异常
	public final static int ERRUNKNOW = 9; // 未知页面
	public final static int ERRORALIPAY = 10; //支付宝没有响应


	public interface CallBack {
		public void onResult(int ret,String money);
	}

	public  Capture(MediaProjection mMpj) {
		capture=this;
		wm = (WindowManager) MainActivity.instance.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		windowWidth = metric.widthPixels;
		windowHeight = metric.heightPixels;
		screenDensity = metric.densityDpi;
		
		mImageReader = ImageReader.newInstance(windowWidth, windowHeight, MainActivity.ImageReaderFormat,1);
		
		
		mVirtualDisplay = mMpj.createVirtualDisplay("capture_screen",windowWidth, windowHeight, screenDensity,DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,mImageReader.getSurface(), null, null);
		
		MainActivity.instance.handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				OCR.getInstance(MainActivity.instance).initAccessToken(
					new OnResultListener<AccessToken>() {

						@Override
						public void onResult(AccessToken arg0) {
							// TODO Auto-generated method stub
							JSONObject jo = new JSONObject();
							try {
								jo.put("action", MainActivity.CAPTUREOK);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Utils.sendToMain(jo.toString(), false);
						}

						@Override
						public void onError(OCRError arg0) {
							// TODO Auto-generated method stub
						}
				}, MainActivity.instance);
				
			}
		});

		
		Utils.startCaptureSvr();
	}

	Handler backgroundHandler;

    private Handler getBackgroundHandler() {
        if (backgroundHandler == null) {
            HandlerThread backgroundThread =
                    new HandlerThread("catwindow", android.os.Process
                            .THREAD_PRIORITY_BACKGROUND);
            backgroundThread.start();
            backgroundHandler = new Handler(backgroundThread.getLooper());
        }
        return backgroundHandler;
    }
	@SuppressWarnings("unchecked")
	private void startCapture(final CallBack cb) {
		Bitmap bitmap=null;
		try{
			image=mImageReader.acquireLatestImage();
			if(image==null)
			{
				cb.onResult(ERRBITMAP,null);
				return;
			}
			int width = image.getWidth();
	        int height = image.getHeight();
	        final Image.Plane[] planes = image.getPlanes();
	        final ByteBuffer buffer = planes[0].getBuffer();
	        int pixelStride = planes[0].getPixelStride();
	        int rowStride = planes[0].getRowStride();
	        int rowPadding = rowStride - pixelStride * width;
	        bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
	        bitmap.copyPixelsFromBuffer(buffer);
	        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
	        image.close();
		}catch(Exception e)
		{
			MainActivity.ImageReaderFormat=0x1;
			MainActivity.captureImageReaderFormatException=true;
			com.qianyou.listener5.Log.T(e.getMessage());
			cb.onResult(ERREXP,null);
			return;
		}
		if (bitmap == null)
			cb.onResult(ERRBITMAP,null);
		else {
			try {
				File fileFolder = new File(mImagePath);
				if (!fileFolder.exists())
					fileFolder.mkdirs();
				File file = new File(mImagePath, "tmp.jpg");
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();

				GeneralBasicParams param = new GeneralBasicParams();
				param.setDetectDirection(true);
				param.setImageFile(file);

				// 调用通用文字识别服务（含位置信息版）
				OCR.getInstance(MainActivity.instance).recognizeGeneralBasic(param,
						new OnResultListener() {
							@Override
							public void onResult(Object result) {
								int kkzf = 0;// 慷慨支付
								int dffk = 0;// 代付付款
								int dfje = 0;// 代付金额
								int yyrbtfk = 0;// 已有人帮他付款
								int ddyqx = 0;// 订单已取消
								int dfddxx = 0;// 代付订单信息
								int dbjy = 0;// 担保交易
								int dfsm = 0;// 代付说明
								int xzfkfs = 0;//选择付款方式
								int tjyhkfk = 0;//添加银行卡付款
								int zhyezfgn = 0;//账户余额支付功能
								int zfbmyxy = 0;//支付宝没有响应
								String money=null;
								for (WordSimple wordSimple : ((GeneralResult) result)
										.getWordList()) {
									String str = wordSimple.getWords();
									if (str.contains("慷慨付款")) {
										kkzf++;
									}
									if (str.equals("代付付款") || str.equals("<代付付款")) {
										dffk++;
									}
									if (str.equals("代付金额")) {
										dfje++;
									}
									if (str.equals("已有人帮TA付款")) {
										yyrbtfk++;
									}
									if (str.equals("订单已取消") || str.contains("代付单据查询失败")) {
										ddyqx++;
									}
									if (str.equals("代付订单信息")) {
										dfddxx++;
									}
									if (str.startsWith("担保交易")) {
										dbjy++;
									}
									if (str.startsWith("代付说明")) {
										dfsm++;
									}
									if (str.contains("选择付款方式")) {
										xzfkfs++;
									}
									if (str.contains("添加银行卡付款")) {
										tjyhkfk++;
									}
									if (str.contains("账户余额支付功能关闭")) {
										zhyezfgn++;
									}
									if (str.contains("支付宝没有响应") || str.contains("关闭应用")) {
										zfbmyxy++;
									}
									if(dfje==1&&str.startsWith("￥")&&money==null)
									{
										money=str.replaceAll("￥", "");
									}
								}
								if (kkzf >= 1 && ddyqx==0 && xzfkfs == 0 && zhyezfgn==0)// 尚未支付&& MainActivity.instance.isClickBoolean == false
								{
									if (MainActivity.instance.isClickBoolean == true){
										MainActivity.instance.execShellCmd("input tap 360 1180");
										cb.onResult(ERRUNKNOW,null);
									} else {
										cb.onResult(FALSE,money);
									}
									//cb.onResult(FALSE,money);
								} else if (xzfkfs >= 1 || tjyhkfk >= 1 || zhyezfgn>=1) {
									cb.onResult(FALSE, money);
								} else if (ddyqx >= 1) {
									cb.onResult(CANCEL,money);
								}else if (zfbmyxy >= 1) {
									cb.onResult(ERRORALIPAY, money);
								} else {
									 String str=((GeneralResult)result).getJsonRes();
									if (dffk == 1 && dfje == 1 && yyrbtfk == 1
											&& dfddxx == 1 && dbjy == 1
											&& dfsm == 1)
										cb.onResult(TRUE,money);
									else {
										cb.onResult(ERRUNKNOW,null);
									}
								}
							}

							@Override
							public void onError(OCRError error) {
								// 调用失败，返回OCRError对象
								cb.onResult(ERROCR,null);
							}
						});
			} catch (IOException e) {
				cb.onResult(ERRWRITEFILE,null);
				e.printStackTrace();
			}

		}
		
	}


	public void capture(final CallBack cb) {
		MainActivity.instance.handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startCapture(cb);
			}
		});
	}

	public static void c6(final int n) {
		if (n > 4) // 超过6次定为失败
		{
			capture.lcb.onResult(ERRUNKNOW,null);
			capture.lcb = null;
			return;
		}
		try {
			if(n==1)
				Thread.sleep(6666);
			else{			
				if (MainActivity.instance.isClickBoolean == true) {
					Thread.sleep(4000);
				}else {
					Thread.sleep(2000);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		capture.capture(new CallBack() {
			@Override
			public void onResult(int ret,String money) {
				// TODO Auto-generated method stub
				if (ret == TRUE || ret == FALSE || ret == CANCEL || ret == ERRORALIPAY) {
					capture.lcb.onResult(ret,money);
					capture.lcb = null;
				} else {
					int step=1;
					if(ret == ERREXP)step=6;
					c6(n + step);
				}
			}
		});
	}

	public void startListen(final String url, CallBack cb) {
		if (this.lcb != null) {
			// 正在监听中
			cb.onResult(ERRLISTENER,null);
		} else {
			this.lcb = cb;
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (Utils.openZFB(url)) {
						c6(1);// 检测3次
					} else {
						capture.lcb.onResult(ERROPENZFB,null);
					}
				}
			}).start();
		}
	}

}