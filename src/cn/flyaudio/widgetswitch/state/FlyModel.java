package cn.flyaudio.widgetswitch.state;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

public class FlyModel extends ButtonPower {
	private Boolean state;

//前2个方法获得状态
	public Boolean getActualState(Context context) {

		return state = getAirplaneModeStatus(context);
	}

	private boolean getAirplaneModeStatus(Context context) {
		boolean status = Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false;

		Log.d("lixuanfly", "status=" + status);
		return status;
	}

	/**
	 * 切换状态
	 */
	public void toggleState(Context context) {
	boolean mAirplaneModeState = getDataState(context);
	Log.d("airp", "	toggleState mAirplaneModeState:"+mAirplaneModeState);
	if (mAirplaneModeState) {
            setAirplaneModeState(false,context);
        } else {
            setAirplaneModeState(true,context);
        }
	}
	
	/**
	 * 把广播发出去，改变飞行模式的状态
	 * @param enabled
	 * @param context
	 */
	private void setAirplaneModeState(boolean enabling,Context context) {
	 // Change the system setting
        Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 
                                enabling ? 1 : 0);
        // Post the intent
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enabling);
        context.sendBroadcastAsUser(intent, UserHandle.ALL);
//	context.sendBroadcast(intent);
    }
	
	/**
	 * 得到目前飞行模式的状态
	 */
	private boolean getDataState(Context context){
		return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;	
	}
}
