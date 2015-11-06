package cn.flyaudio.widgetswitch.view;

import static cn.flyaudio.widgetswitch.alltools.Constants.delectIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.shortcutIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.viewIds;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
//import cn.flyaudio.widgetswitch.R;


import cn.flyaudio.widgetswitch.alltools.AppWidgetService;
import cn.flyaudio.widgetswitch.alltools.Constants;
import cn.flyaudio.widgetswitch.alltools.DrawableTools;
import cn.flyaudio.widgetswitch.alltools.Flog;
import cn.flyaudio.widgetswitch.alltools.FlyShortcutXMLParser;
import cn.flyaudio.widgetswitch.alltools.SetConmmonView;
import cn.flyaudio.widgetswitch.alltools.SkinResource;
import cn.flyaudio.widgetswitch.db.AppWidgetDao;

public class SwitchWidget extends AppWidgetProvider {

	public static final String TAG = Constants.TAG;
	public static AppWidgetManager appWidgetManager = null;
	public static Map<String, String> flyShortcutNeedShow = null;
	private static Drawable whiteBg = null;
	private static SetConmmonView set1;

	public static int[] shuzi = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public static String[] broadcast = new String[] {
			"cn.flyaudio.shortcut.bright.picture",
			"cn.flyaudio.shortcut.wifiAP.picture",
			"cn.flyaudio.shortcut.jiasu.picture",
			"cn.flyaudio.shortcut.reset.picture",
			"cn.flyaudio.shortcut.data.picture",
			"cn.flyaudio.shortcut.wifi.picture",
			"cn.flyaudio.shortcut.fly.picture",
			"cn.flyaudio.shortcut.system.picture",
			"cn.flyaudio.shortcut.car.picture",
			"cn.flyaudio.shortcut.screen.picture" };

	@Override
	// 周期更新时调用
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		RemoteViews remoteViews = null;
		if (SwitchWidget.appWidgetManager == null) {
			SwitchWidget.appWidgetManager = appWidgetManager;
		}

		flyShortcutNeedShow = initFlyApp(); // 得到解析XML数据 data

		Flog.d(TAG, "lyl-------onUpdate-------flyShortcutNeedShow="
				+ flyShortcutNeedShow);
		for (int appWidgetId : appWidgetIds) { // appWidgetIds
			Flog.d(TAG, "appWidgetId:" + appWidgetId);
			AppWidgetDao.saveAppWidgetId(context, appWidgetId); // 对appWidgetId存储
			remoteViews = initRemoteViews(context, appWidgetId); // restore
																	// shortcut
																	// had been
																	// set

			dataRestore(context, appWidgetId, remoteViews, false);
			appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
		}

	}

	/**
	 * initialize a remoteViews set click pendingIntent with start
	 * 初始化一个remoteViews，具有点击事件，4个PendingInten，当点击插件4个+加好型的图标，启动AppSelectActivity
	 * 同时有 发送cn.flyaudio.shortcut.edititem广播，，， 具有编辑功能 设置好Widget 的背景！
	 * AppSelectActivity
	 * 
	 * @param context
	 * @param appWidgetId
	 * @return
	 */
	public static RemoteViews initRemoteViews(Context context, int appWidgetId) {
		//RemoteViews remoteViews;
		//remoteViews = new RemoteViews(context.getPackageName(),
		//		R.layout.widget_layout); // 插件布局
		
		RemoteViews remoteViews = new RemoteViews(SkinResource.getSkinContext().getPackageName(),
				SkinResource.getSkinLayoutIdByName("widget_layout"));
		if (flyShortcutNeedShow == null) {
			flyShortcutNeedShow = initFlyApp();
		}
		for (int index = 0; index < Constants.viewIds.length; index++) { // 4个PendingInten，当点击插件4个图标，
			Intent intent = new Intent(context, AppSelectActivity.class); // 启动AppSelectActivity
			intent.putExtra(Constants.KEY_VIEW_ID, index); // viewId
			intent.putExtra(Constants.KEY_APPWIDGET_ID, appWidgetId); // appWidgetId
			// the second parameter must be set to distinguish different
			// PendingIntent
			PendingIntent selectIntent = PendingIntent.getActivity(context,
					appWidgetId * Constants.viewIds.length // viewIds.length=4
							+ index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(Constants.viewIds[index],
					selectIntent);
			if (Constants.DEBUG)
				Flog.d(TAG, "[ShortcutWidget initRemoteViews] appWidgetId:"
						+ appWidgetId + " viewId:" + Constants.viewIds[index]);

		}

		/*
		 * Intent intent_text = new Intent(context, AppSelectActivity.class);
		 * intent_text.putExtra("titlesId", 0);
		 * intent_text.putExtra(Constants.KEY_APPWIDGET_ID, appWidgetId);
		 * PendingIntent textIntent = PendingIntent.getActivity(context,
		 * appWidgetId * Constants.titles.length + 0, intent_text,
		 * PendingIntent.FLAG_UPDATE_CURRENT);
		 * remoteViews.setOnClickPendingIntent(Constants.titles[0], textIntent);
		 */

		Intent intent_edit = new Intent("cn.flyaudio.shortcut.edititem"); // 发送
																			// 点击编辑的Intent,,,,
		intent_edit.putExtra("titlesId", 1);
		intent_edit.putExtra("isEdit", true);
		intent_edit.putExtra(Constants.KEY_APPWIDGET_ID, appWidgetId);
		PendingIntent editIntent = PendingIntent.getBroadcast(context,
				appWidgetId * Constants.titles.length + 1, intent_edit,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(Constants.titles[1], editIntent);

		// set background color theme

		whiteBg = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("color_changed_background"));
		Drawable drawable = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("widget_layout_bg"));
		
		Bitmap bitmap = DrawableTools.drawableToBitmap(drawable);
		bitmap = DrawableTools.colorChange(bitmap, whiteBg);
		remoteViews.setImageViewBitmap(SkinResource.getSkinResourceId("widget_background", "id"), bitmap);
		return remoteViews;
	}

	/**
	 * restore the remoteViews app which have set from database
	 * 
	 * @param context
	 * @param appWidgetId
	 * @param view
	 */
	public static void dataRestore(Context context, int appWidgetId,
			RemoteViews view, boolean isEdit) {
		Flog.d(TAG, "lyl-----------dataRestore:appWidgetId=" + appWidgetId);
		// data persistence:restore from database
		set1 = new SetConmmonView();
		AppWidgetDao dao = new AppWidgetDao(context);
		int[] pkgNames = dao.query2(appWidgetId); // 查询数据库。得到pkgNames的值，存到数组中
		int[] iconAll = dao.query1(appWidgetId);

		boolean flag = false;

		for (int i = 0; i < Constants.viewIds.length; i++) {
			
			if (pkgNames.length == 0 || pkgNames[i] == 0) {
				Flog.d("lixuanupdata", "onupdate==pkgNames   - ------");
				continue;
			}
			flag = true;
			int name = pkgNames[i];
			try {
				for (int x : shuzi) {
					// set click event //设置点击有程序图标的点击事件。。。。
					if (name == (AppSelectActivity.titless2[x])) {
						if (x != 8) {
							Intent intent_d = new Intent(broadcast[x]);
							intent_d.putExtra(Constants.KEY_VIEW_ID, i);
							intent_d.putExtra(Constants.KEY_APPWIDGET_ID,
									appWidgetId);

							PendingIntent pendingIntent_d = PendingIntent
									.getBroadcast(context, appWidgetId
											* Constants.viewIds.length + i,
											intent_d,
											PendingIntent.FLAG_UPDATE_CURRENT);
	
							view.setOnClickPendingIntent(
									Constants.shortcutIds[i], pendingIntent_d);
						} else {
							Intent intent = new Intent().setClass(context,
									AppWidgetService.class);
							intent.putExtra(Constants.KEY_FLYAPP, 120);
							PendingIntent pendingIntent = PendingIntent
									.getService(context, (appWidgetId
											* viewIds.length + i) * 10, intent,
											PendingIntent.FLAG_UPDATE_CURRENT);
							view.setOnClickPendingIntent(shortcutIds[i],
									pendingIntent);
						}

						if (isEdit) // 是否可编辑
						{
							editModel(context, appWidgetId, view, i);

						} else {
							view.setViewVisibility(delectIds[i], View.GONE);
				
							Drawable icon = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("widget_edit"));
							view.setImageViewBitmap(Constants.titles[1], DrawableTools.drawableToBitmap(icon));
						}

					} else {
						continue;
					}
					if (name == (AppSelectActivity.titless2[0])) {
						set1.setCommonView2(context, view, i, iconAll[i],
								pkgNames[i]);
					} else {
						
						set1.setCommonView(context, view, i, iconAll[i],
								pkgNames[i]);
					}
				} // for循环结束
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Flog.d("lixuan", "55" + i);
				e.printStackTrace();
			}
		} // 一个Constants.viewIds 的for结束

		if (!flag) { // widget上面的编辑图标 titles【1】
			Drawable icon = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("widget_edit"));
			view.setImageViewBitmap(Constants.titles[1], DrawableTools.drawableToBitmap(icon));	
		}
		// update remoteViews
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		appWidgetManager.updateAppWidget(appWidgetId, view);
		dao.close();
	}

	private static void editModel(Context context, int appWidgetId,
			RemoteViews view, int i) {
		view.setViewVisibility(delectIds[i], View.VISIBLE);
		Intent intent_d = new Intent("cn.flyaudio.shortcut.delectitem");
		intent_d.putExtra(Constants.KEY_VIEW_ID, i);
		intent_d.putExtra(Constants.KEY_APPWIDGET_ID, appWidgetId);
		PendingIntent pendingIntent_d = PendingIntent.getBroadcast(context,
				appWidgetId * Constants.viewIds.length + i, intent_d,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// view.setOnClickPendingIntent(delectIds[i], pendingIntent_d);
		view.setOnClickPendingIntent(Constants.shortcutIds[i], pendingIntent_d);
		// String text =
		// context.getResources().getString(R.string.widget_Canceledit);
/*		Drawable icon = context.getResources().getDrawable(
				R.drawable.widget_canceledit);*/
		
		Drawable icon = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("widget_canceledit"));
	
		
		view.setImageViewBitmap(Constants.titles[1],
				DrawableTools.drawableToBitmap(icon));
		Flog.d(TAG, "editModel----------:appWidgetId=" + appWidgetId);

		for (int index = 0; index < Constants.viewIds.length; index++) {
			Intent intent_edit = new Intent("cn.flyaudio.shortcut.edititem");

			intent_edit.putExtra("titlesId", 1);
			intent_edit.putExtra("isEdit", false);
			intent_edit.putExtra(Constants.KEY_APPWIDGET_ID, appWidgetId);
			PendingIntent editIntent = PendingIntent.getBroadcast(context,
					appWidgetId * Constants.titles.length + 1, intent_edit,
					PendingIntent.FLAG_UPDATE_CURRENT);
			view.setOnClickPendingIntent(Constants.viewIds[index], editIntent);
		}

	}

	private static Map<String, String> initFlyApp() {
		InputStream f = null;
		String path = SystemProperties.get("persist.fly.car.select", "default"); // 通过这个接口可以对系统的属性进行读取
		path = "flysystem/flyconfig/" + path
				+ "/uiconfig/flyshortcutconfig.xml";
		try {
			f = new FileInputStream(new File(path));
		} catch (Exception e) {
			f = null;
		}
		Flog.d(TAG, "lyl-------initFlyApp()-------f=" + f);
		FlyShortcutXMLParser parser = new FlyShortcutXMLParser();
		return ((FlyShortcutXMLParser) cn.flyaudio.widgetswitch.alltools.XMLTool.parse(f,
				parser)).getData(); // 得到解析XML数据 data
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds) {
			Log.d(TAG, "[ShortcutWidget onDeleted] appWidgetId:" + appWidgetId);
			// data persistence:deleted
			new AppWidgetDao(context).delete(appWidgetId).close();
			AppWidgetDao.removeAppWidgetId(context, appWidgetId);
		}
	}

}
