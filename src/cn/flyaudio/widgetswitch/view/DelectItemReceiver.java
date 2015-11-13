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
		// wifi 状态改变的广播，使图片改变
		if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
			// Flog.d("lixuanwifi", "Action=" + intent.getAction());
			AppWidgetDao dao = new AppWidgetDao(context);
			List<Picture> three = dao.queryPkgName();
			Iterator<Picture> it = three.iterator();
			wifiButton = new WifiButton();

			while (it.hasNext()) {
				Picture threes = (Picture) it.next();
				int name = threes.getPkgNames();
				int index = threes.getIndexViewId();
				int appWidgetId = threes.getAppWidgetId();

				if (name == AppSelectActivity.titless2[5]) {

					RemoteViews remoteViews = SwitchWidget.initRemoteViews(
							context, appWidgetId);
					int x = wifiButton.getActualStatemy(context);
					if ((2 == x) || (4 == x) || (6 == x)) {
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor", Color.WHITE);
					} else {
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor",
								DrawableTools.getCurColorTheme());
					}

					setCommonView1(remoteViews, index, 5);
					AppWidgetManager appWidgetManager = AppWidgetManager
							.getInstance(context);
					appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
				}
			}
		}

		// 点击之后改变wifiAP状态
		if (intent.getAction().equals("cn.flyaudio.shortcut.wifiAP.picture")) {
			//Flog.d("lixuanwifiAP", "Action=" + intent.getAction());
			wifiapButton = new WifiApButton();
			wifiapButton.toggleState(context);
		}
		if (intent.getAction().equals("android.net.wifi.WIFI_AP_STATE_CHANGED")) {
		//	Flog.d("lixuanwifiAP", "Action=" + intent.getAction());
			AppWidgetDao dao = new AppWidgetDao(context);
			List<Picture> three = dao.queryPkgName();
			wifiapButton = new WifiApButton();
			Iterator<Picture> it = three.iterator();

			while (it.hasNext()) {
				Picture threes = (Picture) it.next();
				int name = threes.getPkgNames();
				int index = threes.getIndexViewId();
				int appWidgetId = threes.getAppWidgetId();

				if (name == AppSelectActivity.titless2[1]) {
					RemoteViews remoteViews = SwitchWidget.initRemoteViews(
							context, appWidgetId);
					int x = wifiapButton.getActualStatemy(context);
					if ((2 == x) || (4 == x) || (6 == x)) {
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor", Color.WHITE);
					} else {
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor",
								DrawableTools.getCurColorTheme());
					}
					setCommonView1(remoteViews, index, 1);
					AppWidgetManager appWidgetManager = AppWidgetManager
							.getInstance(context);
					appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
				}
			}
		}

		// 数据 点击之后的开关....
		if (intent.getAction().equals("cn.flyaudio.shortcut.data.picture")) {
			//Flog.d("lixuandata", "Action=" + intent.getAction());
			Log.d("lixuandata", "Action====get"+intent.getAction());
			 wifidata = new MobileDataButton(context);
			 wifidata.toggleState(context);
		}// 数据接受开和关的改变.
		if (intent.getAction().equals(
				"com.android.flyaudio.powerwidget.mobiledatabutton")) {
		//	Flog.d("lixuandata", "Action=" + intent.getAction());
			Log.d("lixuandata", "Action=====WifiChange"+intent.getAction());
			AppWidgetDao dao = new AppWidgetDao(context);
			List<Picture> three = dao.queryPkgName();
			Iterator<Picture> it = three.iterator();
			wifidata = new MobileDataButton(context);
			while (it.hasNext()) {
				Picture threes = (Picture) it.next();
				int name = threes.getPkgNames();
				int index = threes.getIndexViewId();
				int appWidgetId = threes.getAppWidgetId();
				if (name == AppSelectActivity.titless2[4]) {

					RemoteViews remoteViews = SwitchWidget.initRemoteViews(
							context, appWidgetId);
					
					 datastate = wifidata.getActualStatemy(context);
					if (!datastate) {
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor", Color.WHITE);
					} else {
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor",
								DrawableTools.getCurColorTheme());
					}
					setCommonView1(remoteViews, index, 4);
					AppWidgetManager appWidgetManager = AppWidgetManager
							.getInstance(context);
					appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
				}
			}
		}

		// 飞行模式 2个广播
		if (intent.getAction().equals("cn.flyaudio.shortcut.fly.picture")) {
			//Flog.d("lixuanflystate", "Action=" + intent.getAction());

			flymode = new FlyModel();
			flymode.toggleState(context);
		}
		if (intent.getAction().equals("android.intent.action.AIRPLANE_MODE")) {
			//Flog.d("lixuanflystate", "Action=" + intent.getAction());
			AppWidgetDao dao = new AppWidgetDao(context);
			List<Picture> three = dao.queryPkgName();
			flymode = new FlyModel();
			Iterator<Picture> it = three.iterator();

			while (it.hasNext()) {

				Picture threes = (Picture) it.next();
				int name = threes.getPkgNames();
				int index = threes.getIndexViewId();
				int appWidgetId = threes.getAppWidgetId();

				if (name == AppSelectActivity.titless2[6]) {

					RemoteViews remoteViews = SwitchWidget.initRemoteViews(
							context, appWidgetId);

					Boolean flystate = flymode.getActualState(context);

					if (!flystate) {

						remoteViews.setInt(iconIds[index],
								"setBackgroundColor", Color.WHITE);
					} else {
						//Flog.d("lixuanflystate", "设置图片的背景色 系统色" + flystate);
						remoteViews.setInt(iconIds[index],
								"setBackgroundColor",
								DrawableTools.getCurColorTheme());
					}
					setCommonView1(remoteViews, index, 6);
					AppWidgetManager appWidgetManager = AppWidgetManager
							.getInstance(context);
					appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
				}
			}
		}

	}// onRecieve结束

	private void setCommonView(RemoteViews remoteViews, int indexViewId) {
		remoteViews.setViewVisibility(shortcutIds[indexViewId], View.GONE);
		remoteViews.setViewVisibility(viewIds[indexViewId], View.VISIBLE);
	}

	private void setCommonView1(RemoteViews remoteViews, int indexViewId, int x) {

		remoteViews.setImageViewResource(iconIds[indexViewId],
				AppSelectActivity.statepic[x]);
	}
}
