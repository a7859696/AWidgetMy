package cn.flyaudio.widgetswitch.alltools;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Flog {

	public static String TAG = "widgetswitch";  //须根据应用更改
	public static boolean DEBUG = false;
	static boolean INFO = false;
	public static Context mContext;
	
	private final String DEBUG_ACTION_ALL = "cn.flyaudio.debug";
	
	private String DEBUG_SHAREPREFERENCE_NAME = "debug";

	public static void e(String Tag, String msg) {
		if (INFO)
			Log.e(TAG + "-" + Tag, msg);
	}

	public static void e(String msg) {
		if (INFO)
			Log.e(TAG, msg);
	}

	public static void d(String Tag, String msg) {
		if (DEBUG)
			Log.d(TAG + "-" + Tag, msg);
	}

	public static void d(String msg) {
		if (DEBUG)
			Log.d(TAG, msg);
	}

	public static void i(String msg) {
		if (INFO)
			Log.i(TAG, msg);
	}
	
	public Flog() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * register the receiver of debug state change
	 * @param context
	 * @param action : please pass in the package name
	 */
	public void registerDebugBrocastReceiver(Context context, String action){
		mContext = context;
		DEBUG = INFO = restoreDebug();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(action);
		intentFilter.addAction(DEBUG_ACTION_ALL);
		mContext.registerReceiver(new DebugReceiver(action),intentFilter);
	}

	/**
	 * 
	 * @author qqm
	 * debug state receiver
	 */
	class DebugReceiver extends BroadcastReceiver{

		private String action = "";
		
		public DebugReceiver(String action) {
			// TODO Auto-generated constructor stub
			this.action = action;
		}
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String intentAction = intent.getAction();
			if (intentAction.equals(action) || intentAction.equals(DEBUG_ACTION_ALL)) {
				setDebug(intent.getBooleanExtra("debug", false));
			}
		}
		
	}
	
	private void setDebug(boolean debug){
		this.DEBUG  = this.INFO = debug;
		storeDebug(debug);
	}
	
	private void storeDebug(boolean debug){
		if (mContext != null) {
			SharedPreferences preferences = mContext.getSharedPreferences(DEBUG_SHAREPREFERENCE_NAME,Context.MODE_PRIVATE);
			if (preferences != null) {
				Editor editor = preferences.edit();
				editor.putBoolean("debug", debug);
				editor.commit();
			}
		}
	}
	
	private boolean restoreDebug(){
		boolean debug = false;
		if (mContext != null) {
			SharedPreferences preferences = mContext.getSharedPreferences(DEBUG_SHAREPREFERENCE_NAME,Context.MODE_PRIVATE);
			if (preferences != null) {
				debug = preferences.getBoolean("debug", false);
			}
		}
		return debug;
	}
	
}
