package cn.flyaudio.widgetswitch.state;

import java.lang.reflect.Method;
import java.util.List;

import cn.flyaudio.widgetswitch.alltools.SkinResource;

//import cn.flyaudio.widgetswitch.R;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RecycleMemoryButton {

	private Context mContext;
	private boolean enabled = false;
	private RamInfo mRaminfo;
	int i = 0;
	int j = 0;
	int saveMemory = 0;
	int saveTask = 0;
	public static final String TAG = "PowerButton";

	public void toggleState(Context context) {
		onClickMethod(context);
	}

	private void onClickMethod(Context context) {
		// TODO Auto-generated method stub

		mContext = context;
		mRaminfo = new RamInfo(context);
		Long usedMemory_start = mRaminfo.getUesdMemory();
		// Log.d(TAG,"usedMemory_start = "+usedMemory_start);
		i = mRaminfo.getRunningAppSize();

		killRunnintApp(context);
		enabled = true;
		// Log.d(TAG,"com.android.recycle.memory  before.........sendbroadcast()...");
		Message msg = new Message();
		mHandler.sendMessageDelayed(msg, 2000);

		j = mRaminfo.getRunningAppSize();

		Log.d(TAG, "i = " + i + " j = " + j);

		Long usedMemory_end = mRaminfo.getUesdMemory();
		saveMemory = (int) ((usedMemory_start - usedMemory_end) / 1024);
		saveTask = (i - j);
		Log.d(TAG, "usedMemory_end = " + usedMemory_end);
		Toast_RecycleMemory(context);

		context.sendBroadcast(new Intent()
				.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

	}

	public void killRunnintApp(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> mRunningProcess = mActivityManager
				.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess) {
			if (amProcess.processName.startsWith("cld.navi.")
					|| amProcess.processName.equals("com.mapabc.android")
					|| amProcess.processName.equals("android.process.media")
					|| amProcess.processName.equals("cn.flyaudio.media")
					|| amProcess.processName.equals("cn.flyaudio.flyaudioram")
					|| amProcess.processName
							.equals("com.flyaudio.proxyservice")) {
				Log.i(TAG,
						"lyl------killRunnintApp--------amProcess.processName="
								+ amProcess.processName);
				continue; // 过滤不杀的应用
			} else {
				if (!amProcess.processName.equals("com.tencent.qqmusic"))
					mActivityManager
							.killBackgroundProcesses(amProcess.processName);
				else
					forceKillApp(mActivityManager, amProcess.processName); // 强制关闭进程
			}
			Log.d(TAG, amProcess.processName);
		}

	}

	protected void Toast_RecycleMemory(Context context) {
		String toastText = null;
		if (saveMemory <= 0) {

			// toastText =SkinUtil.getStringFromSkin(Launcher.skinContext,
			// "toastnone");
			toastText =SkinResource.getSkinStringByName("toastnone");
		} else {
			String temp;
			temp = SkinResource.getSkinStringByName("toasttext");
			toastText = String.format(temp, saveTask, saveMemory);

		}
		Log.d("AAA", toastText);
		// LayoutInflater inflater =
		// (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View toastRoot = inflater.inflate(R.layout.fly_toast, null);

	/*	View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.fly_toast, null);*/
		View	toastRoot = SkinResource.getSkinLayoutViewByName("fly_toast");
		Toast toast = new Toast(context);
		toast.setView(toastRoot);
		//TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
		TextView tv = (TextView) toastRoot.findViewById(SkinResource.getSkinResourceId("TextViewInfo", "id"));
		tv.setText(toastText);
		
		toast.show();
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.d(TAG, "com.android.recycle.memory  sendbroadcast()...");
			enabled = false;
			mContext.sendBroadcast(new Intent()
					.setAction("com.android.recycle.memory"));

		}
	};

	// 强制关闭进程，这个方法需要添加android.permission.FORCE_STOP_PACKAGES权限，及加入系统进程
	// android:sharedUserId="android.uid.system"
	public void forceKillApp(ActivityManager am, String packageName) {
		Method forceStopPackage = null;
		try {
			forceStopPackage = am.getClass().getDeclaredMethod(
					"forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(am, packageName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
