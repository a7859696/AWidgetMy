package cn.flyaudio.widgetswitch.state;

import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class CarSetting {
	

	public static final int ON = 100;
	private FlyNavigator mFlyNavigator;

	public  void toggleState(Context context) {
		  Log.d("lixuancar", "ChejiSetting");
    mFlyNavigator = FlyNavigator.getInstance(context);
    Log.d("lixuancar", " sendFlyAppManger(126, FlyConstant.ON) ");
    context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    
    mFlyNavigator.new AdapterCenter().sendFlyAppManger(120, ON);
    }
}