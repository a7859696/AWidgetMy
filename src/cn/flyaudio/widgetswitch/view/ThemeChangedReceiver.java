package cn.flyaudio.widgetswitch.view;

import cn.flyaudio.widgetswitch.alltools.Constants;
import cn.flyaudio.widgetswitch.alltools.SkinResource;
import cn.flyaudio.widgetswitch.db.AppWidgetDao;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class ThemeChangedReceiver extends BroadcastReceiver {

	RemoteViews remoteViews = null;
	AppWidgetManager appWidgetManager = null;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(Constants.TAG, "[ColorThemeChangedReceiver onReceive]---intent.getAction()="+intent.getAction());
		if(intent.getAction().equals("action.flyaudio.colortheme"))
		{	
			Log.e("lixuanbright", "action.flyaudio.colortheme====");
//			AppWidgetDao dao = new AppWidgetDao(context);
//			List<Integer> appWidgetIds = dao.getAppWidgetIds();
			int[] appWidgetIds = AppWidgetDao.getAllAppWidgetId(context);
			appWidgetManager = AppWidgetManager.getInstance(context);
			for (int appWidgetId : appWidgetIds) {
				 remoteViews =  SwitchWidget.initRemoteViews(context, appWidgetId);
				//  restore shortcut had been set
				SwitchWidget.dataRestore(context, appWidgetId , remoteViews, false);
				
				appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
			}
//			dao.close();
		}
		if(Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction()))
		{
			Log.e("lixuanbright", "主题:Intent.ACTION_LOCALE_CHANGED===");
/*			int[] appWidgetIds = AppWidgetDao.getAllAppWidgetId(context);
			appWidgetManager = AppWidgetManager.getInstance(context);
			for (int appWidgetId : appWidgetIds) {
			 remoteViews =SwitchWidget.initRemoteViews(context, appWidgetId);
				//  restore shortcut had been set
				SwitchWidget.dataRestore(context, appWidgetId , remoteViews, false);
				
				appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
			}*/
		}
	}
}
