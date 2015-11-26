package cn.flyaudio.widgetswitch.state;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

	private int mCurrentBrightness;

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
		Log.d("lixuanbright", "toggleState=       mCurrentBacklightIndex="
				+ "       " + mCurrentBacklightIndex);
		SharedPreferences state = context.getSharedPreferences(
				"brightness_state", 0);
		mCurrentBrightness = state.getInt("currentstate", 3);
		mCurrentBacklightIndex = mCurrentBrightness;
		Intent intent = new Intent("cn.flyaudio.systemui.changebrightness");
		mCurrentBacklightIndex++;
		Log.d("lixuanbright", "mCurrentBacklightIndex    ++ 之后===="
				+ mCurrentBacklightIndex);
		// if(mCurrentBacklightIndex==1){mCurrentBacklightIndex++;}
		// mCurrentBrightness++;
		// mCurrentBacklightIndex=mCurrentBrightness;
		// Log.d("lixuanbright",
		// "mCurrentBrightness=        mCurrentBacklightIndex="+"  "+mCurrentBrightness+"       "+mCurrentBacklightIndex);
		ContentResolver resolver = context.getContentResolver();
		if (mCurrentBacklightIndex > mBacklightValues.length - 1) {
			mCurrentBacklightIndex = 0;
		}
		brightness = BACKLIGHTS[mCurrentBacklightIndex];
		if (brightness == CLOSE_BACKLIGHT) {
			intent.putExtra("value", 0);
			context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
		} else {
			if (brightness == LOW_BACKLIGHT) {
				intent.putExtra("value", 1);

			} else if (brightness == MID_BACKLIGHT) {
				intent.putExtra("value", 2);

			} else if (brightness == HIGH_BACKLIGHT) {
				intent.putExtra("value", 3);
			}
		}
		Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS,
				brightness);
		context.sendBroadcast(intent);
		/*
		 * mCurrentBrightness=brightness; Log.d("lixuanbright",
		 * " mCurrentBrightness =  " + mCurrentBrightness);
		 * SharedPreferences.Editor edit =
		 * context.getSharedPreferences("brightness_state", 0).edit();
		 * edit.putInt("currentstate", mCurrentBrightness); edit.commit();
		 */
	}

	/*
	 * //获取系统亮度值 public int getActualState(Context context){ PowerManager power
	 * = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	 * ContentResolver resolver = context.getContentResolver();
	 * Log.d("lixuanbright1", "进入getActualState----bright"); int br2 =
	 * Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS, -1);
	 * Log.d("lixuanbright1", "br2====="+br2); return br2; }
	 */

	public int getActualState(Context context) {

		SharedPreferences state = context.getSharedPreferences("brightness_state", 0);
		mCurrentBrightness = state.getInt("currentstate", 3);

		Log.d("lixuanbright", " mCurrentBrightness =  = = = 当前亮度值:"
				+ mCurrentBrightness);
		return mCurrentBrightness;
	}

	public void changeCurrentBrightness(Context context, Intent intent) {
		int i = intent.getIntExtra("value", 0);
		// updateState(context);
		mCurrentBrightness = i;

		SharedPreferences.Editor edit = context.getSharedPreferences("brightness_state", 0).edit();
		edit.putInt("currentstate", mCurrentBrightness);
		edit.commit();
		Log.d("lixuanbright", " changeCurrentBrightness =  " + mCurrentBrightness);
	}

	public void startCurrentBrightness(Context context) {
		SharedPreferences.Editor edit = context.getSharedPreferences("brightness_state", 0).edit();
		edit.putInt("currentstate", 3);
		edit.commit();
		Log.d("lixuanbright"," startCurrentBrightness =  ="+ context.getSharedPreferences("brightness_state", 0).getInt("currentstate", 3));
	}
	
}
