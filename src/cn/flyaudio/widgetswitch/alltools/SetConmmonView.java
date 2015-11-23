package cn.flyaudio.widgetswitch.alltools;

import static cn.flyaudio.widgetswitch.alltools.Constants.iconIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.lableIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.shortcutIds;
import static cn.flyaudio.widgetswitch.alltools.Constants.viewIds;

import cn.flyaudio.widgetswitch.state.BrightnessButton;
import cn.flyaudio.widgetswitch.state.FlyModel;
import cn.flyaudio.widgetswitch.state.MobileDataButton;
import cn.flyaudio.widgetswitch.state.WifiApButton;
import cn.flyaudio.widgetswitch.state.WifiButton;
import cn.flyaudio.widgetswitch.view.AppSelectActivity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class SetConmmonView {

	private WifiApButton wifiApButton;
	private String name;
	private MobileDataButton mobileDataButton;
	private WifiButton wifibutton;
	private FlyModel flyModel;

	//AppActivity 调用该setCommonView,
	public void setCommonView(Context context, RemoteViews remoteViews,
			int index, int icon1, int lable, Boolean state) {
		
		if (lable == AppSelectActivity.titless2[0]) {
			remoteViews.setViewVisibility(shortcutIds[index], View.VISIBLE);
			remoteViews.setViewVisibility(viewIds[index], View.GONE);
			remoteViews.setImageViewResource(iconIds[index], icon1);
			if (state) {
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						DrawableTools.getCurColorTheme());
			} else
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						Color.WHITE);

			if (icon1 == AppSelectActivity.statepic_light[0]) {
				remoteViews.setTextViewText(lableIds[index],
						SkinResource.getSkinStringById(AppSelectActivity.titless_bright[0]));
			} else if (icon1 == AppSelectActivity.statepic_light[1]) {
				remoteViews.setTextViewText(lableIds[index],
						SkinResource.getSkinStringById(AppSelectActivity.titless_bright[2]));
			} else
				remoteViews.setTextViewText(lableIds[index],
						SkinResource.getSkinStringById(AppSelectActivity.titless_bright[1]));
		} 
		
		else {
			remoteViews.setViewVisibility(shortcutIds[index], View.VISIBLE);
			remoteViews.setViewVisibility(viewIds[index], View.GONE);
			remoteViews.setImageViewResource(iconIds[index], icon1);
			remoteViews.setTextViewText(lableIds[index],
					SkinResource.getSkinStringById(lable));       //getString() -------   大问题!
			
			if (state) {
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						DrawableTools.getCurColorTheme());
			} else
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						Color.WHITE);
		}
	}

	//widget调用该setCommonView,
	public void setCommonView(Context context, RemoteViews remoteViews,
			int index, int icon1, int lable) {
		
		name = SkinResource.getSkinStringById(lable);	
		remoteViews.setViewVisibility(shortcutIds[index], View.VISIBLE);
		remoteViews.setViewVisibility(viewIds[index], View.GONE);
		remoteViews.setImageViewResource(iconIds[index], icon1);
		Log.d("lixuanbright", " 进入   setCommonView=  =  -------setImageViewResource:"+icon1+"         "+name);
		remoteViews.setTextViewText(lableIds[index], name);

		if (lable == AppSelectActivity.titless2[1]) {
			wifiApButton = new WifiApButton();
			if (1 == wifiApButton.getActualStatemy(context)) {
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						DrawableTools.getCurColorTheme());
				Log.d("lixuanupdata", "wifiAP=====true");
			} else
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						Color.WHITE);		
		} 
		else if (lable == AppSelectActivity.titless2[4]) {
			mobileDataButton = new MobileDataButton(context);
			if (mobileDataButton.getActualStatemy(context)) {
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						DrawableTools.getCurColorTheme());
			} else
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						Color.WHITE);
		} 
		else if (lable == AppSelectActivity.titless2[5]) {
			wifibutton = new WifiButton();
			if (1 == wifibutton.getActualStatemy(context)) {
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						DrawableTools.getCurColorTheme());
			} else
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						Color.WHITE);
		} 
		else if (lable == AppSelectActivity.titless2[6]) {
			flyModel = new FlyModel();
			if (flyModel.getActualState(context)) {
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						DrawableTools.getCurColorTheme());
			} else
				remoteViews.setInt(iconIds[index], "setBackgroundColor",
						Color.WHITE);
		}
		else {
			remoteViews.setInt(iconIds[index], "setBackgroundColor",
					Color.WHITE);
		}

	}

	public void setCommonView2(Context context, RemoteViews remoteViews,
			int index, int icon1, int lable) {
		remoteViews.setViewVisibility(shortcutIds[index], View.VISIBLE);
		remoteViews.setViewVisibility(viewIds[index], View.GONE);
		// remoteViews.setImageViewResource(iconIds[index], icon1);
		// Log.d("lixuanupdate", "getString(lable)==" +
		// context.getString(lable));

		remoteViews.setInt(iconIds[index], "setBackgroundColor",
				DrawableTools.getCurColorTheme());
		BrightnessButton brightness = new BrightnessButton();
		int x = brightness.getActualState(context);
		Log.d("lixuanbright", " setCommonView2 =  = = =   ===:" +x);
		if (0 == x||0==1) {
			remoteViews.setImageViewResource(iconIds[index],
					AppSelectActivity.statepic_light[0]);
			remoteViews.setTextViewText(lableIds[index],
					SkinResource.getSkinStringByName("night_bright_button"));

		} else if (2 == x) {
			remoteViews.setImageViewResource(iconIds[index],
					AppSelectActivity.statepic[0]);
		
			remoteViews.setTextViewText(lableIds[index],
					SkinResource.getSkinStringByName("mid_bright_button"));
			
		} else if (3 == x) {
			remoteViews.setImageViewResource(iconIds[index],
					AppSelectActivity.statepic_light[1]);
		
			remoteViews.setTextViewText(lableIds[index],
					SkinResource.getSkinStringByName("bright_on_button"));
		}
	}

}
