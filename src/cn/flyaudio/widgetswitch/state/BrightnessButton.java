package cn.flyaudio.widgetswitch.state;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

public class BrightnessButton extends ButtonPower {

	// Auto-backlight level
	private static final int CLOSE_BACKLIGHT = 0; // 关屏
	private static final int LOW_BACKLIGHT = 1;// 暗淡
	private static final int MID_BACKLIGHT = 2;// 中等亮度
	private static final int HIGH_BACKLIGHT = 3;// 高亮

	private static final int[] BACKLIGHTS = new int[] { CLOSE_BACKLIGHT,
			LOW_BACKLIGHT, MID_BACKLIGHT, HIGH_BACKLIGHT };

	// 屏幕的背光亮度　，在0到255之间
	private static final Uri BRIGHTNESS_URI = Settings.System
			.getUriFor(Settings.System.SCREEN_BRIGHTNESS);

	// 控制是否启用自动亮度模式。
	private static final Uri BRIGHTNESS_MODE_URI = Settings.System
			.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);

	private static final List<Uri> OBSERVED_URIS = new ArrayList<Uri>();

	static {
		OBSERVED_URIS.add(BRIGHTNESS_URI);
		OBSERVED_URIS.add(BRIGHTNESS_MODE_URI);
	}

	private boolean mAutoBrightnessSupported = false;
	
	private int mCurrentBacklightIndex = 0;

	private int[] mBacklightValues = new int[] { 0, 1, 2, 3 };

	public BrightnessButton() {
		mType = BUTTON_BRIGHTNESS; // 切换亮度
	}

	// 切换状态，控制屏幕亮度
	int brightness = 0;

	// mCurrentBrightness 点击如何取到是问题. ? ? ? ? ?初始化也就要取到mCurrentBrightness

	public void toggleState(Context context) {
		Log.d("lixuanbright", "toggleState=");
		PowerManager power = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		ContentResolver resolver = context.getContentResolver();

		int br = Settings.System.getInt(resolver,
				Settings.System.SCREEN_BRIGHTNESS, -1);
		Log.d("lixuanbright", "br = " + br);
		Intent i = new Intent("cn.flyaudio.systemui.changebrightness");
		mCurrentBacklightIndex = br;
		mCurrentBacklightIndex++; // 0++

		if (mCurrentBacklightIndex > mBacklightValues.length - 1) {
			mCurrentBacklightIndex = 0;
		}
		int backlightIndex = mBacklightValues[mCurrentBacklightIndex];
		brightness = BACKLIGHTS[backlightIndex];
		Log.d("lixuanbright", " brightness =  " + brightness);
		if (brightness == CLOSE_BACKLIGHT) {
			// Settings.System.putInt(resolver,
			// Settings.System.SCREEN_BRIGHTNESS_MODE, //自动　－－改为关屏
			// Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
			i.putExtra("value", 0);
		} else {
			if (mAutoBrightnessSupported) {
				Settings.System.putInt(resolver,
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			}
			if (power != null) {
				// power.setBacklightBrightness(brightness);
				// if(brightness == BRIGHT_BACKLIGHT)
				// {
				// i.putExtra("value", 3);
				// Log.d("DDD", "BRIGHT_BACKLIGHT........");
				// }
				if (brightness == HIGH_BACKLIGHT) {
					i.putExtra("value", 3);
					Log.d("DDD", "HIGH_BACKLIGHT........");
				} else if (brightness == MID_BACKLIGHT) {
					i.putExtra("value", 2);
					Log.d("DDD", "MID_BACKLIGHT.....");
				} else if (brightness == LOW_BACKLIGHT) {
					i.putExtra("value", 1);
					Log.d("DDD", "LOW_BACKLIGHT.....");
				}
			}
		}

		Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS,
				brightness);
		context.sendBroadcast(i);

		int br1 = Settings.System.getInt(resolver,
				Settings.System.SCREEN_BRIGHTNESS, -1);
		Log.d("lixuanbright", "  br =1 " + br1);
	}
	
    //获取系统亮度值
    public int getActualState(Context context){
    	PowerManager power = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        ContentResolver resolver = context.getContentResolver();
    	int br1 = Settings.System.getInt(resolver,
                Settings.System.SCREEN_BRIGHTNESS, -1);  	
    	return br1;
    }
}
