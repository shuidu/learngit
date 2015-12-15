
//��׿ͼƬ��Ӱ��ʵ�ִ���
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
		// ʵ��ͼƬ��ת90��
		matrix.preScale(1, -1);
		// ������ӰͼƬ����ԭʼͼƬ��һ���С��
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
		// ������ͼƬ��ԭͼƬ + ��ӰͼƬ��
		Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
		// ��������
		Canvas canvas = new Canvas(finalReflection);
		canvas.drawBitmap(originalImage, 0, 0, null);
		//�ѵ�ӰͼƬ����������
		canvas.drawBitmap(reflectionImage, 0, height + 1, null);
		Paint shaderPaint = new Paint();
		//�������Խ���LinearGradient����
		LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, finalReflection.getHeight() + 1, 0x70ffffff,
				0x00ffffff, TileMode.MIRROR);
		shaderPaint.setShader(shader);
		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		//����������תͼƬ��С����Ȼ��ѽ���Ч���ӵ����У��ͳ�����ͼƬ�ĵ�ӰЧ����
		canvas.drawRect(0, height + 1, width, finalReflection.getHeight(), shaderPaint);
		return finalReflection;
	}

	
}
