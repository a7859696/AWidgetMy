package cn.flyaudio.widgetswitch.state;

import cn.flyaudio.widgetswitch.alltools.DrawableTools;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews.RemoteView;
import static cn.flyaudio.widgetswitch.alltools.Constants.iconIds;

public class WifiButton extends ButtonPower {

	public static final int STATE_ENABLED = 1;
	public static final int STATE_DISABLED = 2;
	public static final int STATE_TURNING_ON = 3;
	public static final int STATE_TURNING_OFF = 4;
	public static final int STATE_INTERMEDIATE = 5;
	public static final int STATE_UNKNOWN = 6;
	public static final int STATE_FOCUS = 7;

	private boolean mInTransition = false;
	private Boolean mIntendedState = null; // initially not set
	public WifiButton tochanggewifi;

	public int getActualStatemy(Context context) { // 实现 StateTracker 类的 抽象方法
													// getActualState();
													// 1作用是获得WiFi的状态
		WifiManager wifiManager = (WifiManager) context // 得到系统 wifiManager。。
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager != null) {
			Log.d("lixuanwifi", "进入getActualStatemy()");
			return wifiStateToFiveState(wifiManager.getWifiState()); // 返回WiFi
																		// 的状态，
																		// 有四种1
			// 2 3 4

		}

		return STATE_UNKNOWN; // WiFi的第五种状态
	}

	/**
	 * Converts WifiManager's state values into our Wifi/Bluetooth-common state
	 * values.
	 */
	private static int wifiStateToFiveState(int wifiState) {

		switch (wifiState) {
		case WifiManager.WIFI_STATE_DISABLED:
			return STATE_DISABLED;
		case WifiManager.WIFI_STATE_ENABLED:
			return STATE_ENABLED;
		case WifiManager.WIFI_STATE_DISABLING:
			return STATE_TURNING_OFF;
		case WifiManager.WIFI_STATE_ENABLING:
			return STATE_TURNING_ON;
		default:
			return STATE_UNKNOWN;
		}
	}

	public void toggleState(Context context) {
		int currentState = getTriState(context); // 获取目前的状态
		boolean newState = false;
		switch (currentState) {
		case ButtonPower.STATE_ENABLED:
			newState = false;
			break;
		case ButtonPower.STATE_DISABLED:
			newState = true;
			break;
		case ButtonPower.STATE_INTERMEDIATE:
			if (mIntendedState != null) {
				newState = !mIntendedState;
			}
			break;
		}
		mIntendedState = newState;
		if (mInTransition) {
		} else {
			mInTransition = true;
			requestStateChange(context, newState);
		}
	}

	public final int getTriState(Context context) {
		/*
		 * if (mInTransition) { // If we know we just got a toggle request
		 * recently // (which set mInTransition), don't even ask the //
		 * underlying interface for its state. We know we're // changing. This
		 * avoids blocking the UI thread // during UI refresh post-toggle if the
		 * underlying // service state accessor has coarse locking on its //
		 * state (to be fixed separately). return
		 * PowerButton.STATE_INTERMEDIATE; }
		 */
		switch (getActualStatemy(context)) {
		case ButtonPower.STATE_DISABLED:
			return ButtonPower.STATE_DISABLED;
		case ButtonPower.STATE_ENABLED:
			return ButtonPower.STATE_ENABLED;
		default:
			return ButtonPower.STATE_INTERMEDIATE;
		}
	}

	public void requestStateChange(Context context, // //实现 StateTracker 类的 抽象方法
			// requestStateChange(); 2
			// 作用是设置WiFi的状态
			final boolean desiredState) {
		final WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null) {
			Log.d("WifiButton", "No wifiManager.");
			return;
		}

		// Actually request the wifi change and persistent
		// settings write off the UI thread, as it can take a
		// user-noticeable amount of time, especially if there's
		// disk contention.
		new AsyncTask<Void, Void, Void>() { // AsyncTask 异步消息处理机制

			protected Void doInBackground(Void... args) { // 子线程
				/**
				 * Disable tethering if enabling Wifi
				 */

				// 需要修改
				int wifiApState = wifiManager.getWifiApState(); // 判断WiFi
				// 热点！！的状态
				if (desiredState
						&& ((wifiApState == WifiManager.WIFI_AP_STATE_ENABLING) || (wifiApState == WifiManager.WIFI_AP_STATE_ENABLED))) {
					wifiManager.setWifiApEnabled(null, false);
				}

				wifiManager.setWifiEnabled(desiredState); // 判断WiFi
				// 热点！！的状态后，，，设置WiFi的状态
				return null;
			}
		}.execute();
	}

}
