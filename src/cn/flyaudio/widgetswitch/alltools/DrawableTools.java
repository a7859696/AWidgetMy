package cn.flyaudio.widgetswitch.alltools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemProperties;

public class DrawableTools {

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap drawableToIcon(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(100, 100,
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, 100, 100);
		drawable.draw(canvas);
		return bitmap;
	}
	
	private static final int DEFAULT_COLOR_THEME = Color.RED;
	public final static String COLOR_PROPERTIES_KEY = "persist.fly.colortheme";
	public static int getCurColorTheme() {
		String color = SystemProperties.get(COLOR_PROPERTIES_KEY, "");
		if (color != null && !color.equals("")) {
			return Integer.parseInt(color);
		}
		return DEFAULT_COLOR_THEME;
	}
	   //lyl+
	public static Bitmap colorChange(Bitmap src, Drawable whiteBg) {
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap newb = Bitmap.createBitmap(w, h, android.graphics.Bitmap.Config.ARGB_8888);
		Canvas cv = new Canvas(newb);
		if (whiteBg != null) {
			Drawable temp = whiteBg;
			temp.setColorFilter(getCurColorTheme(), Mode.MULTIPLY);
			cv.drawBitmap(drawableToBitmap(temp), 0, 0, null);
		}
		cv.drawBitmap(src, 0, 0, null);
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}
	
	public static Drawable getCurColorTheme(Context context, int resId) {
		Drawable filter = context.getResources().getDrawable(resId);
		filter.setColorFilter(getCurColorTheme(), Mode.SRC_IN);		
		return filter;
	}

	   /**
     * 将bitmap转换为Drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap)
    {
    	BitmapDrawable bd = new BitmapDrawable(bitmap);
    	return bd;
    }
	
}
