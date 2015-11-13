package cn.flyaudio.widgetswitch.view;

import static cn.flyaudio.widgetswitch.alltools.Constants.KEY_APPWIDGET_ID;
import static cn.flyaudio.widgetswitch.alltools.Constants.KEY_VIEW_ID;
import static cn.flyaudio.widgetswitch.alltools.Constants.iconIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.lableIds;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.flyaudio.widgetswitch.alltools.DrawableTools;
import cn.flyaudio.widgetswitch.alltools.Flog;
import cn.flyaudio.widgetswitch.alltools.SetConmmonView;
import cn.flyaudio.widgetswitch.alltools.SkinResource;
import cn.flyaudio.widgetswitch.db.AppWidgetDao;
import cn.flyaudio.widgetswitch.state.BrightnessButton;
import cn.flyaudio.widgetswitch.state.RecycleMemoryButton;
import cn.flyaudio.widgetswitch.state.ScrennTurnoff;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

public class SysandchejiBroadcast extends BroadcastReceiver {

	BrightnessButton brightness;
	ScrennTurnoff scrennTurnoff;
	public static Context context1;
	public static int index1;
	public static int app1;

	
    Handler handler = new Handler() {  
        public void handleMessage(Message msg) {  
            if (msg.what == 1) {  
            	Log.d("timeli", "Hander--index1---app1=="+"   "+index1+"      "+app1);
            	RemoteViews remoteViews2 = SwitchWidget.initRemoteViews(context1,
 					app1);
    			remoteViews2.setInt(iconIds[index1],
    					"setBackgroundColor",
    					Color.WHITE);
    			AppWidgetManager appWidgetManager1 = AppWidgetManager
    					.getInstance(context1);
    			appWidgetManager1.updateAppWidget(app1, remoteViews2);    				
            }  
            super.handleMessage(msg);  
        };  
    }; 
    TimerTask task = new TimerTask() {  
  	  
        @Override  
        public void run() {  
            // 需要做的事:发送消息  
            Message message = new Message();  
            message.what = 1;  
            handler.sendMessage(message);  
        }  
    };
	private SetConmmonView change;

	@Override
	public void onReceive(Context context, Intent intend) {
		// TODO Auto-generated method stub

		// 系统设置
		if (intend.getAction().equals("cn.flyaudio.shortcut.system.picture")) {
			//Flog.d("lixuansy", "intend=" + intend.getAction());
			int indexViewId = intend.getExtras().getInt(KEY_VIEW_ID, 0);
			int appWidgetId = intend.getExtras().getInt(KEY_APPWIDGET_ID, 0);
			Log.d("timeli", "context1赋值--indexViewId---appWidgetId=="+"   "+indexViewId+"      "+appWidgetId);
			changgepicture(context, indexViewId, appWidgetId,500);
			//context.sendBroadcast(new Intent().setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
			context.startActivity(new Intent(Settings.ACTION_SETTINGS)
			.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			
			
		}



		
		// 复位
		if (intend.getAction().equals("cn.flyaudio.shortcut.reset.picture")) {
			//Flog.d("lixuanrest", "intend=" + intend.getAction());
			int indexViewId = intend.getExtras().getInt(KEY_VIEW_ID, 0);
			int appWidgetId = intend.getExtras().getInt(KEY_APPWIDGET_ID, 0);
            changgepicture(context, indexViewId, appWidgetId, 400);
              
			showAlertDialog(context);
		}
		// 一键加速
		if (intend.getAction().equals("cn.flyaudio.shortcut.jiasu.picture")) {
			//Flog.d("lixuanjiasu", "intend=" + intend.getAction());
			int indexViewId = intend.getExtras().getInt(KEY_VIEW_ID, 0);
			int appWidgetId = intend.getExtras().getInt(KEY_APPWIDGET_ID, 0);
            changgepicture(context, indexViewId, appWidgetId,1000);
			RecycleMemoryButton recycleMemoryButton = new RecycleMemoryButton();
			recycleMemoryButton.toggleState(context);
		}

		// 亮度的2个广播
		if (intend.getAction().equals("cn.flyaudio.shortcut.bright.picture")) {
		//	Flog.d("lixuanbright", "intend=" + intend.getAction());
			brightness = new BrightnessButton();
			brightness.toggleState(context);
		}

		if (intend.getAction().equals("cn.flyaudio.systemui.changebrightness")) {
		//	Flog.d("lixuanbright", "intend=" + intend.getAction());
			brightness = new BrightnessButton();
			int x = brightness.getActualState(context);
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
					if (0 == x) {
						remoteViews.setImageViewResource(iconIds[index],
								AppSelectActivity.statepic_light[0]);
						//Flog.d("lixuanbright", "x====0");
						remoteViews
								.setTextViewText(
										lableIds[index],
										SkinResource.getSkinStringById(AppSelectActivity.titless_bright[0])); // 文字先暂时不写..看要怎么弄好
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

		// 关屏幕的广播
		if (intend.getAction().equals("cn.flyaudio.shortcut.screen.picture")) {
			//Flog.d("lixuanscreen", "intend=" + intend.getAction());
			int indexViewId = intend.getExtras().getInt(KEY_VIEW_ID, 0);
			int appWidgetId = intend.getExtras().getInt(KEY_APPWIDGET_ID, 0);
          changgepicture(context, indexViewId, appWidgetId,500);
			scrennTurnoff = new ScrennTurnoff();
			scrennTurnoff.toggleStateturnoff(context);
		}
		

			
	}

	private void showAlertDialog(Context context) {
		// TODO Auto-generated method stub
		Intent dialog = new Intent();
		dialog.setComponent(new ComponentName("com.android.launcher3",
				"com.android.flyaudio.powerwidget.AlertDialogActivity"));

		// Intent dialog = new Intent(context, AlertDialogActivity.class);
		//Flog.d("lixuanrest", "dialog有 =");
		dialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(dialog);
	}

	
	
	public void changgepicture(Context context,int indexViewId,int appWidgetId ,long x){
		context1=context;
		index1=indexViewId;
		app1=appWidgetId;
		if(context1!=null){
			Log.d("timeli", "context1不为空--系统颜色值=="+DrawableTools.getCurColorTheme());
			RemoteViews  remoteViews1 = SwitchWidget.initRemoteViews(context,
				appWidgetId);
		remoteViews1.setInt(iconIds[indexViewId],
				"setBackgroundColor",
				DrawableTools.getCurColorTheme());
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews1);

		Timer timer=new Timer();
		timer.schedule(task, x);
	}else {
		Log.d("timeli", "context1为空");
		}
	}
	

	
	
}
