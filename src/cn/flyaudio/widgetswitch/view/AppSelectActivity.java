package cn.flyaudio.widgetswitch.view;

import static cn.flyaudio.widgetswitch.alltools.Constants.*;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;




import cn.flyaudio.widgetswitch.alltools.DrawableTools;
import cn.flyaudio.widgetswitch.alltools.Flog;
import cn.flyaudio.widgetswitch.alltools.SetConmmonView;
import cn.flyaudio.widgetswitch.alltools.SkinResource;
import cn.flyaudio.widgetswitch.db.AppWidgetDao;
import cn.flyaudio.widgetswitch.state.BrightnessButton;
import cn.flyaudio.widgetswitch.state.FlyModel;
import cn.flyaudio.widgetswitch.state.MobileDataButton;
import cn.flyaudio.widgetswitch.state.WifiApButton;
import cn.flyaudio.widgetswitch.state.WifiButton;



public class AppSelectActivity extends Activity {

	private final int GRID_COMUMNS = 6;
	private final int GRID_ROWS = 3;
	private final int GRID_NUM_ITEM = (GRID_COMUMNS * GRID_ROWS);
	private int indexViewId = -1;
	private int viewId = 0;
	private int appWidgetId = 0;

	// private GridView gridView = null;
	private View view = null;
	private List<ImageView> mListImageView = null;
	private List<View> mListview = null;
	private LinearLayout mPageIndicateLayout = null;
	private int mCountPage = 0;
	private TextView app_select_textview;
	private List<Picture> pictures;
	public WifiButton wifibutton;
	public WifiApButton wifiApButton;
	public MobileDataButton mobileDataButton;
	public FlyModel flyModel;
	private BrightnessButton brightnessButton;
	public Boolean state = false;
	private SetConmmonView set;
	private View contentView = null;
	
	public static int[] imgs = new int[] { SkinResource.getSkinDrawableIdByName("pic1"),SkinResource.getSkinDrawableIdByName("pic2"),
		SkinResource.getSkinDrawableIdByName("pic3"),SkinResource.getSkinDrawableIdByName("pic4"),SkinResource.getSkinDrawableIdByName("pic5"),
	SkinResource.getSkinDrawableIdByName("pic6"),SkinResource.getSkinDrawableIdByName("pic7"),SkinResource.getSkinDrawableIdByName("pic8"),
	SkinResource.getSkinDrawableIdByName("pic9"),SkinResource.getSkinDrawableIdByName("pic10")};
	
	public static int[] statepic = new int[] {SkinResource.getSkinDrawableIdByName("waimianpic1_u"),SkinResource.getSkinDrawableIdByName("waimianpic2_u"),SkinResource.getSkinDrawableIdByName("waimianpic3_u"),
		SkinResource.getSkinDrawableIdByName("waimianpic4_u"),SkinResource.getSkinDrawableIdByName("waimianpic5_u"),SkinResource.getSkinDrawableIdByName("waimianpic6_u"),
		SkinResource.getSkinDrawableIdByName("waimianpic7_u"),SkinResource.getSkinDrawableIdByName("waimianpic8_u"),SkinResource.getSkinDrawableIdByName("waimianpic9_u"),
		SkinResource.getSkinDrawableIdByName("waimianpic10_u")};
	// 亮度有3种状态,需要3张图片,以及3种不同文字

	public static int[] statepic_light = new int[] {SkinResource.getSkinDrawableIdByName("waimianpic1_1_u"),SkinResource.getSkinDrawableIdByName("waimianpic1_2_u")};
	
	public static int[] titless2 = new int[] { SkinResource.getSkinStringIdByName("brightness"),SkinResource.getSkinStringIdByName("ap_button"),SkinResource.getSkinStringIdByName("quick_button"),
		SkinResource.getSkinStringIdByName("reset_button"),SkinResource.getSkinStringIdByName("mobiledata_button"),SkinResource.getSkinStringIdByName("wifi_button"),
		SkinResource.getSkinStringIdByName("airplane_mode_button"),SkinResource.getSkinStringIdByName("settings_button"),SkinResource.getSkinStringIdByName("carsetting_button"),
		SkinResource.getSkinStringIdByName("auto_bright_button")};
	
	public static int[] titless_bright = new int[] {SkinResource.getSkinStringIdByName("night_bright_button"),SkinResource.getSkinStringIdByName("mid_bright_button"),SkinResource.getSkinStringIdByName("bright_on_button")};
	
      public static int[]  aaaaa;
	class OnGridViewItemClickListener implements OnItemClickListener {

		private int i;
		public int icon1;

		OnGridViewItemClickListener(int page) {
		}

		@Override
		// 根据点击的位置图标,将其显示再插件上
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			Picture picture1 = pictures.get(position);
			int activityName1 = picture1.getTitlestring();
			switch(position){
			case 5:
				if (1 == wifibutton.getActualStatemy(AppSelectActivity.this)) {
					state = true;
				}
				icon1 = statepic[5];
				break;
			case 1:
				if (1 == wifibutton.getActualStatemy(AppSelectActivity.this)) {
					state = true;
				}
				icon1 = statepic[1];
				break;
			case 4:
				for (int x = 0; x < titless2.length; x++) {
					if (activityName1 == (titless2[x])) {
						i = x;
					}
				}
				icon1 = statepic[i];
				if (mobileDataButton.getActualStatemy(AppSelectActivity.this)) {
					state = true;
				}
				break;
			case 6:
				for (int x = 0; x < titless2.length; x++) {
					if (activityName1 == (titless2[x])) {
						i = x;
					}
				}
				icon1 = statepic[i];
				if (flyModel.getActualState(AppSelectActivity.this)) {
					state = true;
				}
				break;
			case 0:
				if ((1 == brightnessButton
						.getActualState(AppSelectActivity.this) || (0 == brightnessButton
						.getActualState(AppSelectActivity.this)))) {
					icon1 = statepic_light[0];
					state = true;
				} else if ((3 == brightnessButton
						.getActualState(AppSelectActivity.this))) {
					state = true;
					icon1 = statepic_light[1];

				} else {
					icon1 = statepic[0];
					state = true;
				}
				break;
			case 2:
				icon1 = statepic[2];
				break;
			case 3:
				icon1 = statepic[3];
				break;
			case 7:
				icon1 = statepic[7];
				break;
			case 8:
				icon1 = statepic[8];
				break;
			case 9:
				icon1 = statepic[9];
				break;
			default:
			break;
			
			}			

			if (viewId != 0 && appWidgetId != 0) {
				RemoteViews remoteViews = SwitchWidget.initRemoteViews(
						AppSelectActivity.this, appWidgetId);

				set.setCommonView(AppSelectActivity.this, remoteViews,
						indexViewId, icon1, activityName1, state);
				// update remoteViews
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(AppSelectActivity.this);
				appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

				// add . to distinguish FlyAuido app
				new AppWidgetDao(AppSelectActivity.this).add(appWidgetId,
						indexViewId, activityName1, icon1).close();
				// data persistence:save used sharedPreferenc
				SwitchWidget.dataRestore(AppSelectActivity.this, appWidgetId,
						remoteViews, false);
			} else {
				Flog.d("lixuan", "lililil=======");
			}
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//contentView = inflate.inflate(R.layout.activity_app_select, null);
		contentView = inflate.inflate(SkinResource.getSkinLayoutIdByName("activity_app_select"), null);
		if (contentView != null) {
			contentView = SkinResource.getSkinLayoutViewByName("activity_app_select");
		}
		this.setContentView(contentView);
	
	//	setContentView(R.layout.activity_app_select);
		// initialize data
		indexViewId = getIntent().getExtras().getInt(KEY_VIEW_ID, -1);
		if (indexViewId == -1)
			viewId = 0;
		else
			viewId = viewIds[indexViewId];
		appWidgetId = getIntent().getExtras().getInt(KEY_APPWIDGET_ID, 0);
		Flog.d(TAG, "[AppSelectActivity onCreate] appWidgetId:" + appWidgetId
				+ " viewId:" + viewId);
		RemoteViews remoteViews = SwitchWidget.initRemoteViews(
				AppSelectActivity.this, appWidgetId);

		SwitchWidget.dataRestore(AppSelectActivity.this, appWidgetId,
				remoteViews, false);
		// initialize view
		/* initView(); */
		initView();
		wifibutton = new WifiButton();
		wifiApButton = new WifiApButton();
		mobileDataButton = new MobileDataButton(AppSelectActivity.this);
		flyModel = new FlyModel();
		brightnessButton = new BrightnessButton();
		set = new SetConmmonView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		//RelativeLayout app_rl = (RelativeLayout) findViewById(R.id.app_select);
		RelativeLayout app_rl = (RelativeLayout) contentView.findViewById(SkinResource.getSkinResourceId("app_select","id"));
		if (app_rl != null) {
			Drawable drawable = null;
			try {
				// drawable = SkinUtil.getDrawableFromSkin(Launcher.skinContext,
				// "app_select_bg");
				// app_rl.setBackground(drawable);
				//app_rl.setBackgroundResource(R.drawable.app_select_bg);
				app_rl.setBackgroundResource(SkinResource.getSkinDrawableIdByName("app_select_bg"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//RelativeLayout top_rl = (RelativeLayout) findViewById(R.id.top_bar);
		RelativeLayout top_rl = (RelativeLayout) contentView.findViewById(SkinResource.getSkinResourceId("top_bar","id'"));
		if (top_rl != null) {
			Drawable drawable = null;
			try {
				// drawable = SkinUtil.getDrawableFromSkin(Launcher.skinContext,
				// "top_bar_bg");
			//	top_rl.setBackgroundResource(R.drawable.top_bar_bg);
				top_rl.setBackgroundResource(SkinResource.getSkinDrawableIdByName("top_bar_bg"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		LinearLayout ll_tv_bg = (LinearLayout) contentView.findViewById(SkinResource.getSkinResourceId("ll_tv_bg", "id"));
		if (ll_tv_bg != null) {
			ll_tv_bg.setBackgroundColor(DrawableTools.getCurColorTheme());
		}

		app_select_textview = (TextView) contentView.findViewById(SkinResource.getSkinResourceId("app_select_back_btn", "id"));

		TextView backButton = (TextView) contentView.findViewById(SkinResource.getSkinResourceId("app_select_back_btn", "id"));

		if (backButton != null) {
			backButton.setBackgroundResource(SkinResource.getSkinDrawableIdByName("box_back_button"));
			// backButton.setBackground(SkinUtil.getDrawableFromSkin(Launcher.skinContext,
			// "box_back_button"));
			backButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					AppSelectActivity.this.finish();
				}
			});

		}

		ViewPager pager = (ViewPager) contentView.findViewById(SkinResource.getSkinResourceId("viewpager", "id"));
		// calculate page number

		//LayoutInflater inflater = getLayoutInflater().from(this);                加载girdview     ------?????
		mListview = new ArrayList<View>();
		//view = inflater.inflate(R.layout.viewpager_layout, null);
		view = SkinResource.getSkinLayoutViewByName("viewpager_layout");
		// initialize grid view
		pictures = new ArrayList<Picture>();
		for (int i = 0; i < imgs.length; i++) {
			Picture picture = new Picture(titless2[i], imgs[i]);
			pictures.add(picture);
		}
		//GridView gridView = (GridView) view.findViewById(R.id.grid);
		GridView gridView = (GridView) view.findViewById(SkinResource.getSkinResourceId("grid", "id"));
		AppInfoAdapter adapter;

		adapter = new AppInfoAdapter(this, pictures, imgs);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnGridViewItemClickListener(0));
		// complete one page
		mListview.add(view);

		// initialize page indicate
		mPageIndicateLayout = (LinearLayout) contentView.findViewById(SkinResource.getSkinResourceId("page_indicate", "id"));
		mListImageView = new ArrayList<ImageView>();
		ImageView imageView = new ImageView(this);

		//imageView.setImageResource(R.drawable.page_indicator);
		imageView.setImageResource(SkinResource.getSkinDrawableIdByName("page_indicator"));

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(5, 0, 5, 0);
		lp.width = 27;
		lp.height = 8;
		mPageIndicateLayout.addView(imageView, lp);
		mListImageView.add(imageView);

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// Log.d(TAG,"[AppSelectActivity onPageSelected] position:"+position);
				ImageView imageView = mListImageView.get(position);
			    Drawable draw = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("page_indicator_focused"));
				Drawable temp = SkinResource.getSkinContext().getResources().getDrawable(SkinResource.getSkinDrawableIdByName("page_indicator_focused_bg"));				
				temp = DrawableTools.bitmapToDrawable(DrawableTools
						.colorChange(DrawableTools.drawableToBitmap(draw), temp));
				imageView.setImageDrawable(temp);
				if (position - 1 >= 0) {
					ImageView imageViewPrev = mListImageView.get(position - 1);
					imageViewPrev.setImageResource(SkinResource.getSkinDrawableIdByName("page_indicator"));
				}
				if (position + 1 < mCountPage) {
					ImageView imageViewNext = mListImageView.get(position + 1);
					imageViewNext.setImageResource(SkinResource.getSkinDrawableIdByName("page_indicator"));
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		pager.setAdapter(new PagerAppInfoAdapter(mListview));

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		RemoteViews remoteViews = SwitchWidget.initRemoteViews(
				AppSelectActivity.this, appWidgetId);
		SwitchWidget.dataRestore(AppSelectActivity.this, appWidgetId,
				remoteViews, false);
	}

	class PagerAppInfoAdapter extends PagerAdapter {
		List<View> mListView = null;

		PagerAppInfoAdapter(List<View> list) {
			mListView = list;
		}

		@Override
		public int getCount() {
			return mListView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListView.get(position));
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListView.get(position));
			return mListView.get(position);
		}
	};

}
