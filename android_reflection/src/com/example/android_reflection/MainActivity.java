
//安卓图片倒影的实现代码
package com.example.android_reflection;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView imageView2 = (ImageView) findViewById(R.id.imageView1);
		Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.a)).getBitmap();
		imageView2.setImageBitmap(createReflectedImage(bmp));
	}

	private Bitmap createReflectedImage(Bitmap originalImage) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		Matrix matrix = new Matrix();
		// 实现图片翻转90度
		matrix.preScale(1, -1);
		// 创建倒影图片（是原始图片的一半大小）
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
		// 创建总图片（原图片 + 倒影图片）
		Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
		// 创建画布
		Canvas canvas = new Canvas(finalReflection);
		canvas.drawBitmap(originalImage, 0, 0, null);
		//把倒影图片画到画布上
		canvas.drawBitmap(reflectionImage, 0, height + 1, null);
		Paint shaderPaint = new Paint();
		//创建线性渐变LinearGradient对象
		LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, finalReflection.getHeight() + 1, 0x70ffffff,
				0x00ffffff, TileMode.MIRROR);
		shaderPaint.setShader(shader);
		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		//画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
		canvas.drawRect(0, height + 1, width, finalReflection.getHeight(), shaderPaint);
		return finalReflection;
	}

	
}
