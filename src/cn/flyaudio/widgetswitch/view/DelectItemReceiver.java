package cn.flyaudio.widgetswitch.view;

import static cn.flyaudio.widgetswitch.alltools.Constants.KEY_APPWIDGET_ID;
import static cn.flyaudio.widgetswitch.alltools.Constants.KEY_VIEW_ID;
import static cn.flyaudio.widgetswitch.alltools.Constants.iconIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.shortcutIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.viewIds;

import java.util.Iterator;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import cn.flyaudio.widgetswitch.alltools.Constants;
import cn.flyaudio.widgetswitch.alltools.DrawableTools;
import cn.flyaudio.widgetswitch.alltools.Flog;
import cn.flyaudio.widgetswitch.alltools.SkinResource;
import cn.flyaudio.widgetswitch.db.AppWidgetDao;
import cn.flyaudio.widgetswitch.state.FlyModel;
import cn.flyaudio.widgetswitch.state.MobileDataButton;
import cn.flyaudio.widgetswitch.state.WifiApButton;
import cn.flyaudio.widgetswitch.state.WifiButton;

public class DelectItemReceiver extends BroadcastReceiver {
	// protected static boolean isEdit = false;
	public static int picture11;
	private WifiApButton wifiapButton;
	private WifiButton wifiButton;
	private FlyModel flymode;
	private MobileDataButton wifidata;
	private Boolean datastate;
	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals("cn.flyaudio.switchwidgetcut.delectitem")) {
			Flog.d(Constants.TAG, "[DelectItemReceiver onReceive]");
			int indexViewId = intent.getExtras().getInt(KEY_VIEW_ID, 0);
			int appWidgetId = intent.getExtras().getInt(KEY_APPWIDGET_ID, 0);

			RemoteViews remoteViews = SwitchWidget.initRemoteViews(context,
					appWidgetId);

			// set imageView
			setCommonView(remoteViews, indexViewId);
			// update remoteViews
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			
			appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

			new AppWidgetDao(context).delete(indexViewId, appWidgetId).close();

			SwitchWidget.dataRestore(context, appWidgetId, remoteViews, true);

		}

		if (intent.getAction().equals("cn.flyaudio.switchwidgetcut.edititem")) {
			boolean isEdit = intent.getBooleanExtra("isEdit", false);
			Flog.d(Constants.TAG, "DelectItemReceiver onReceive---isEdit="
					+ isEdit);
			int appWidgetId = intent.getExtras().getInt(KEY_APPWIDGET_ID, 0);
			RemoteViews remoteViews = SwitchWidget.initRemoteViews(context,
					appWidgetId);

			SwitchWidget.dataRestore(context, appWidgetId, remoteViews,
					isEdit);
		}

		// 点击之后wifi状态改变
		if (intent.getAction().equals("cn.flyaudio.shortcut.wifi.picture")) {
			Flog.d("lixuanwifi", "Action=" + intent.getAction());
			wifiButton = new WifiButton();
			wifiButton.toggleState(context);
		}

		// 点击之后改变wifiAP状态
		if (intent.getAction().equals("cn.flyaudio.shortcut.wifiAP.picture")) {
			//Flog.d("lixuanwifiAP", "Action=" + intent.getAction());
			wifiapButton = new WifiApButton();
			wifiapButton.toggleState(context);
		}

		// 数据 点击之后的开关....
		if (intent.getAction().equals("cn.flyaudio.shortcut.data.picture")) {
			//Flog.d("lixuandata", "Action=" + intent.getAction());
			Log.d("lixuandata", "Action====get"+intent.getAction());
			 wifidata = new MobileDataButton(context);
			 wifidata.toggleState(context);
		}

		// 飞行模式 2个广播
		if (intent.getAction().equals("cn.flyaudio.shortcut.fly.picture")) {
			//Flog.d("lixuanflystate", "Action=" + intent.getAction());

			flymode = new FlyModel();
			flymode.toggleState(context);
		}
	}
	// onRecieve结束

	
	private void setCommonView(RemoteViews remoteViews, int indexViewId) {
		remoteViews.setViewVisibility(shortcutIds[indexViewId], View.GONE);
		remoteViews.setViewVisibility(viewIds[indexViewId], View.VISIBLE);
	}
}
