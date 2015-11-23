package cn.flyaudio.widgetswitch.alltools;

import static cn.flyaudio.widgetswitch.alltools.Constants.iconIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.lableIds;

import java.util.Iterator;
import java.util.List;

import cn.flyaudio.widgetswitch.db.AppWidgetDao;
import cn.flyaudio.widgetswitch.state.BrightnessButton;
import cn.flyaudio.widgetswitch.view.AppSelectActivity;
import cn.flyaudio.widgetswitch.view.Picture;
import cn.flyaudio.widgetswitch.view.SwitchWidget;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class PicturesService extends Service{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
        try {       
                     
            MyReceiver myReceiver = new MyReceiver();  
            IntentFilter filter = new IntentFilter();  
            filter.addAction("com.android.systemui.updatebrightnessstate");  
            filter.addAction("cn.flyaudio.systemui.changebrightness");
            registerReceiver(myReceiver, filter);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	private class MyReceiver extends BroadcastReceiver {

		private BrightnessButton brightness;

		@Override
		public void onReceive(Context context, Intent intend) {
			// TODO Auto-generated method stub
			if (intend.getAction().equals("com.android.systemui.updatebrightnessstate")) {
				Log.d("lixuanbright", "intend=" + intend.getAction());
				brightness = new BrightnessButton();						
				brightness.changeCurrentBrightness(context, intend);
				int x =brightness.getActualState(context);
				Log.d("lixuanbright", "intend= =  =  =  x      " + x);
				AppWidgetDao dao = new AppWidgetDao(context);
				List<Picture> three = dao.queryPkgName();
				Iterator<Picture> it = three.iterator();

				while (it.hasNext()) {
					//Flog.d("lixuanbright", "进入while循环");
					Picture threes = (Picture) it.next();
					int name = threes.getPkgNames();
					int index = threes.getIndexViewId();
					int appWidgetId = threes.getAppWidgetId();

					if (name == AppSelectActivity.titless2[0]) {
						RemoteViews remoteViews = SwitchWidget.initRemoteViews(
								context, appWidgetId);
						if (0 == x||1==x) {
							remoteViews.setImageViewResource(iconIds[index],
									AppSelectActivity.statepic_light[0]);
							Log.d("lixuanbright", "进入x===="+x);
							remoteViews
									.setTextViewText(
											lableIds[index],
											SkinResource.getSkinStringById(AppSelectActivity.titless_bright[0])); // 
						} else if (2 == x) {
						//	Flog.d("lixuanbright", "x====2");
							remoteViews.setImageViewResource(iconIds[index],
									AppSelectActivity.statepic[0]);
							remoteViews
									.setTextViewText(
											lableIds[index],
											SkinResource.getSkinStringById(AppSelectActivity.titless_bright[1]));
						} else if (3 == x) {
							remoteViews.setImageViewResource(iconIds[index],
									AppSelectActivity.statepic_light[1]);
							remoteViews
									.setTextViewText(
											lableIds[index],
											SkinResource.getSkinStringById(AppSelectActivity.titless_bright[2]));
						}
						AppWidgetManager appWidgetManager = AppWidgetManager
								.getInstance(context);
						appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

					}
				}
			}
			
			if (intend.getAction().equals("cn.flyaudio.systemui.changebrightness")) {
				Log.d("lixuanbright", "intend=" + intend.getAction());
			
				brightness = new BrightnessButton();
				brightness.changeCurrentBrightness(context, intend);
			}
			
			
			
		}  
		
	}
}
