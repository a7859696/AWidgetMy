package cn.flyaudio.widgetswitch.alltools;

import com.flyaudio.proxyservice.aidl.IProxyConnet;

import android.app.Service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import static cn.flyaudio.widgetswitch.alltools.Constants.*;

/**
 * content agency to start flyaudio application
 * 
 * @author moxiyong Jul 23, 2015
 */
public class AppWidgetService extends Service {

	private boolean isBind = false;

	public IProxyConnet mIProxyConnet = null;
	private ServiceConnection conn = new ServiceConnection() {
		@Override
		synchronized public void onServiceConnected(ComponentName name,
				IBinder service) {
			Log.d("test", " ServiceConnection   ");
			mIProxyConnet = IProxyConnet.Stub.asInterface(service);
			isBind = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mIProxyConnet = null;
			isBind = false;
		}
	};

	public void unbindService() {
		if (isBind)
			unbindService(conn);
		isBind = false;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		isBind = true;
		Intent mIntent = new Intent("com.flyaudio.nativeservice.IProxyService")
				.setPackage("com.flyaudio.proxy.service");
		bindService(mIntent, conn, Service.BIND_AUTO_CREATE);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		try {
			int key = intent.getIntExtra(KEY_FLYAPP, -1);
			Log.d(TAG, "[AppWidgetService onStartCommand] intent:" + key);
			if (key != -1 && mIProxyConnet != null) {
				Log.d(TAG,
						"[AppWidgetService onStartCommand] iffifififififififif:");
				mIProxyConnet.sendFlyAppManger(key, FlyConstant.ON);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unbindService();
		Log.e(Constants.TAG, "[AppWidgetService onDestroy]");
		super.onDestroy();
	}

}
