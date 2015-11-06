package cn.flyaudio.widgetswitch.skinresource;


import cn.flyaudio.widgetswitch.alltools.Flog;
import cn.flyaudio.widgetswitch.alltools.SkinResource;
import android.app.Application;
import android.content.Context;

import android.util.Log;

public class FlyShortcutApplication extends Application {
	
	public static final String TAG = "FlyShortcutApplication";
	private static Context mContext;

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "FlyShortcutApplication onCreate");
		mContext = getApplicationContext();
		SkinResource.initSkinResource(mContext, "com.flyaudio.flyaudiowidgetswitchskin");
		
		Flog flog = new Flog();
		flog.registerDebugBrocastReceiver(mContext, "cn.flyaudio.widgetswitch.debug");
	}

	public static Context getContext(){
		return mContext;
	}

}
