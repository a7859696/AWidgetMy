package cn.flyaudio.widgetswitch.state;

import java.lang.reflect.Method;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MobileDataButton extends ButtonPower {
	private ConnectivityManager cm;
	private Boolean statedata;
	private boolean isAirPlane = false;
	private final TelephonyManager mTelephonyManager;
	private Context mContext;
	private boolean mMobileDataEnabled = false;
	private boolean isFirst = true;
	private final int MSG_MOBILE = 1;
	private final String MOBILEACTION = "com.android.flyaudio.powerwidget.mobiledatabutton";

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_MOBILE:
				boolean isMobileOpen = (Boolean) msg.obj;
				Log.d("lixuandata", " mHandler isMobileOpen : " + isMobileOpen
						+ " mHandler mMobileDataEnabled : "
						+ mMobileDataEnabled);
				mContext.sendBroadcast(new Intent(MOBILEACTION));
				break;

			default:
				break;
			}
		};
	};

	
	//AppSelectActivity用的2个类
	public Boolean getActualStatemy(Context context) {
		cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		statedata = getMobileDataStatus(cm);
		return statedata;
	}

	private boolean getMobileDataStatus(ConnectivityManager cm) {
		Boolean isOpen = null;
		try {
			isOpen = cm.getMobileDataEnabled();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOpen;
	}



	public MobileDataButton(Context context) {
		mType = BUTTON_MOBILEDATA; // toggleMobileData
		mContext = context; // context
		cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		mTelephonyManager = TelephonyManager.from(mContext);

		mMobileDataEnabled = getDataState(mContext); // 换取初始系统状态
		
		
		//开启线程．．点击速度快了会让系统卡死？？？？？？　　　修改原版，不要线程了．！
/*		Log.d("lixuandata", " MobileDataButton() mMobileDataEnabled : "
				+ mMobileDataEnabled);
		// 开启线程读取状态
		if (isFirst) {
			Log.d("lixuandata", "thread is isFirst start");
			Thread thread = new Thread(new MobileStateRunnable());
			thread.start();
			isFirst = false;
		}*/
	}

	public void toggleState(Context context) {
	/*	MobileDataButton moblilebutton = new MobileDataButton(context);*/
		Log.d("lixuandata", "toggleState");
		isAirPlane = Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 0;

		if (!isAirPlane) {
			return;
		}
		boolean enabled = getDataState(context);

		if (enabled) {
			mTelephonyManager.setDataEnabled(false);	
			context.sendBroadcast(new Intent(MOBILEACTION));
		} else {
			mTelephonyManager.setDataEnabled(true);			
			context.sendBroadcast(new Intent(MOBILEACTION));
		}
	}

	private boolean getDataState(Context context) {
		boolean state = false;
		try {
			state = cm.getMobileDataEnabled();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	private class MobileStateRunnable implements Runnable {

		boolean mobileState;

		MobileStateRunnable() {
			Log.d("lixuandata", "MobileStateRunnable() 多次创建！！！");
		}

		@Override
		public void run() {
			Log.d("lixuandata", "MobileStateRunnable running");
			while (true) {
				try {
					Thread.sleep(1000);
					mobileState = cm.getMobileDataEnabled();
					if (mobileState != mMobileDataEnabled) {
						Log.d("lixuandata",
								"mobileState != mMobileDataEnabled mobileState : "
										+ mobileState);
						mMobileDataEnabled = mobileState;
						mHandler.obtainMessage(MSG_MOBILE, mMobileDataEnabled)
								.sendToTarget();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.d("MobileContentObserver", "InterruptedException : " + e.getMessage());
				}
			}
		}
	}

}