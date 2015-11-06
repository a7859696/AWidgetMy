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

public class ScrennTurnoff {
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

	// 切换状态，控制屏幕亮度
	int brightness = 0;

	public void toggleStateturnoff(Context context) {
		Log.d("lixuanscreen", "toggleState=");
		PowerManager power = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		ContentResolver resolver = context.getContentResolver();

		int br = Settings.System.getInt(resolver,
				Settings.System.SCREEN_BRIGHTNESS, -1);
		Log.d("lixuanscreen", "br = " + br);
		Intent i = new Intent("cn.flyaudio.systemui.changebrightness");
		mCurrentBacklightIndex = 0;

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
				i.putExtra("value", 0);
			}
		}
		Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, 0);
		context.sendBroadcast(i);

		int br1 = Settings.System.getInt(resolver,
				Settings.System.SCREEN_BRIGHTNESS, -1);
		Log.d("lixuanscreen", "  br =1 " + br1);
	}
}
