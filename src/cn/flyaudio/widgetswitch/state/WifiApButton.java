package cn.flyaudio.widgetswitch.state;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

public class WifiApButton extends ButtonPower {

	private Boolean mIntendedState = null; // initially not set
	private boolean mInTransition = false;

	public int getActualStatemy(Context context) { // 实现 StateTracker 类的 抽象方法
													// getActualState();
													// 1作用是获得WiFi的状态
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager != null) {
			return wifiApStateToFiveState(getWifiApState(wifiManager)); // 返回WiFi
																		// 的状态，
																		// 有四种1
																		// 2 3 4
		}

		return STATE_UNKNOWN; // WiFi的第五种状态
	}

	/**
	 * Converts WifiManager's state values into our Wifi/WifiAP/Bluetooth-common
	 * state values.
	 */
	private static int wifiApStateToFiveState(int wifiState) {
		switch (wifiState) {
		case 11:
			return STATE_DISABLED;
		case 13:
			return STATE_ENABLED;
		case 10:
			return STATE_TURNING_OFF;
		case 12:
			return STATE_TURNING_ON;
		default:
			return STATE_UNKNOWN;
		}
	}

	private int getWifiApState(WifiManager wifiManager) {
		int tmp;
		tmp = wifiManager.getWifiApState();
		return tmp;
	}

	public final void toggleState(Context context) {
		int currentState = getTriState(context); // 获取 目前的状态
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

		switch (getActualStatemy(context)) {
		case ButtonPower.STATE_DISABLED:
			return ButtonPower.STATE_DISABLED;
		case ButtonPower.STATE_ENABLED:
			return ButtonPower.STATE_ENABLED;
		default:
			return ButtonPower.STATE_INTERMEDIATE;
		}
	}

	protected void requestStateChange(Context context,
			final boolean desiredState) {

		final WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null) {
			Log.d("WifiAPManager", "No wifiManager.");
			return;
		}
		Log.i("WifiAp", "Setting: " + desiredState);
		new AsyncTask<Void, Void, Void>() {

			protected Void doInBackground(Void... args) {
				/**
				 * Disable Wif if enabling tethering
				 */
				int wifiState = wifiManager.getWifiState();
				if (desiredState
						&& ((wifiState == WifiManager.WIFI_STATE_ENABLING) || (wifiState == WifiManager.WIFI_STATE_ENABLED))) {
					wifiManager.setWifiEnabled(false);
				}
				wifiManager.setWifiApEnabled(null, desiredState);
				Log.i("WifiAp", "Async Setting: " + desiredState);
				return null;
			}
		}.execute();
	}

}
