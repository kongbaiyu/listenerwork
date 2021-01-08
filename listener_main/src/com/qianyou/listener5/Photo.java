package com.qianyou.listener5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

public class Photo {
	public Activity instance=null;
	public final int RESULT_LOAD_IMAGE=100;
	public final int REQUEST_CODE_CUT_PHOTO=101;
	public final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=1000;
	public Uri uritempFile;
	private Delegate delegate=null;
	public interface Delegate
	{
		public void onSel(Uri uri);
	}
	public Photo(MainActivity instance)
	{
		this.instance=instance;
	}
	public void open()
	{
	    Intent i = new Intent(  
	            Intent.ACTION_PICK,   
	            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);    
	    instance.startActivityForResult(i,RESULT_LOAD_IMAGE); 
	}
	public void onResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == MainActivity.RESULT_OK && null != data) {    
			delegate.onSel(data.getData());
        }   
		if (requestCode == REQUEST_CODE_CUT_PHOTO)
		{
			
		}
	}
	/**
     * 转换图片成圆形
     *
     * @param bitmap
     *            传入Bitmap对象
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
    /**
     * 保存图片
     *
     * @param bitmap
     * @param fileName
     * @param baseFile
     */
    public static void saveBitmap(Bitmap bitmap, String fileName, File baseFile) {
        FileOutputStream bos = null;
        File imgFile = new File(baseFile, "/" + fileName);
        try {
            bos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = instance.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        instance.startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO);
    }
    private void doPhoto(Intent data) {
        if (data != null) {

            // 这个方法是根据Uri获取Bitmap图片的静态方法

            // 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
            Uri mImageCaptureUri = data.getData();
            // 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
            if (mImageCaptureUri != null) {
                startPhotoZoom(mImageCaptureUri);
            } else {
                // android拍照获得图片URI为空的处理方法http://www.xuebuyuan.com/1929552.html
                // 这样做取得是缩略图,以下链接是取得原始图片
                // http://blog.csdn.net/beyond0525/article/details/8940840
                Bundle extras = data.getExtras();
                if (extras != null) {
                    // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                    // Bitmap imageBitmap =
                    // extras.getParcelable("data");
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ///
                    mImageCaptureUri = Uri.parse(MediaStore.Images.Media.insertImage(
                                    instance.getContentResolver(), imageBitmap,
                                    null, null));
                    startPhotoZoom(mImageCaptureUri);
                    //*/
                }
            }
        }
    }
    public static Bitmap compressImage(Bitmap image) {  
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 250;  
        while ( baos.toByteArray().length / 1024>15) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
            if (options<=0)
            	break;
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }  
    public static String bitmapToBase64(Bitmap bitmap) {  
    	  
        String result = null;  
        ByteArrayOutputStream baos = null;  
        try {  
            if (bitmap != null) {  
                
            	bitmap=compressImage(bitmap);
                
                baos = new ByteArrayOutputStream();  
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
      
                baos.flush();  
                baos.close(); 
                
                byte[] bitmapBytes = baos.toByteArray();  
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (baos != null) {  
                    baos.flush();  
                    baos.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
  //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) 
    {  
        Bitmap BitmapOrg = bitmap;  
        int width = BitmapOrg.getWidth();  
        int height = BitmapOrg.getHeight();  
        int newWidth = w;  
        int newHeight = h;  
 
        float scaleWidth = ((float) newWidth) / width;  
        float scaleHeight = ((float) newHeight) / height;  
 
        Matrix matrix = new Matrix();  
        matrix.postScale(scaleWidth, scaleHeight);  
        // if you want to rotate the Bitmap   
        // matrix.postRotate(45);   
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,  
                        height, matrix, true);  
        return resizedBitmap;  
    }
    public void setDelegate(Delegate d)
    {
    	delegate=d;
    }
}
