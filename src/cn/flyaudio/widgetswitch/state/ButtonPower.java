package cn.flyaudio.widgetswitch.state;

import android.view.View;

public class ButtonPower {
	public static final String TAG = "PowerButton";
	
    public static final int STATE_ENABLED = 1;
    public static final int STATE_DISABLED = 2;
    public static final int STATE_TURNING_ON = 3;
    public static final int STATE_TURNING_OFF = 4;
    public static final int STATE_INTERMEDIATE = 5;
    public static final int STATE_UNKNOWN = 6;
    public static final int STATE_FOCUS = 7;

    public static final String BUTTON_WIFI = "toggleWifi";
    public static final String BUTTON_GPS = "toggleGPS";
    public static final String BUTTON_BLUETOOTH = "toggleBluetooth";
    public static final String BUTTON_BRIGHTNESS = "toggleBrightness";
    public static final String BUTTON_SOUND = "toggleSound";
    public static final String BUTTON_SYNC = "toggleSync";
    public static final String BUTTON_WIFIAP = "toggleWifiAp";
    public static final String BUTTON_SCREENTIMEOUT = "toggleScreenTimeout";
    public static final String BUTTON_MOBILEDATA = "toggleMobileData";
    public static final String BUTTON_LOCKSCREEN = "toggleLockScreen";
    public static final String BUTTON_NETWORKMODE = "toggleNetworkMode";
    public static final String BUTTON_AUTOROTATE = "toggleAutoRotate";
    public static final String BUTTON_AIRPLANE = "toggleAirplane";
    public static final String BUTTON_FLASHLIGHT = "toggleFlashlight";
    public static final String BUTTON_SLEEP = "toggleSleepMode";
    public static final String BUTTON_MEDIA_PLAY_PAUSE = "toggleMediaPlayPause";
    public static final String BUTTON_MEDIA_PREVIOUS = "toggleMediaPrevious";
    public static final String BUTTON_MEDIA_NEXT = "toggleMediaNext";
    public static final String BUTTON_LTE = "toggleLte";
    public static final String BUTTON_WIMAX = "toggleWimax";
    public static final String BUTTON_UNKNOWN = "unknown";
    public static final String BUTTON_MEMORY = "toogme";
    public static final String BUTTON_RESET = "toogReset";
    public static final String BUTTON_SETTINGS = "toogSettings";
    protected int mIcon;
    protected int mIconTag;
//    protected int mTextColor;
    protected int mText;
    protected int mState;
    protected View mView;
    protected int color;
    protected String mType = BUTTON_UNKNOWN;
}
