package cn.flyaudio.widgetswitch.view;

import android.content.Intent;

public class Picture {

	private String title;
	private int imageId;
	private Intent intent;
	private int pkgNames;
	private int indexViewId;
	private int appWidgetId;
	private int titlestring;

	public Picture() {
		super();
	}

	public Picture(int pkgNames, int indexViewId, int appWidgetId) {
		super();
		this.pkgNames = pkgNames;
		this.indexViewId = indexViewId;
		this.appWidgetId = appWidgetId;
	}

	/*
	 * public Picture(String title, int imageId) { super(); this.title = title;
	 * this.imageId = imageId; }
	 */
	public Picture(int titlestring, int imageId) {
		super();
		this.titlestring = titlestring;
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public int getPkgNames() {
		return pkgNames;
	}

	public void setPkgNames(int pkgNames) {
		this.pkgNames = pkgNames;
	}

	public int getIndexViewId() {
		return indexViewId;
	}

	public void setIndexViewId(int indexViewId) {
		this.indexViewId = indexViewId;
	}

	public int getAppWidgetId() {
		return appWidgetId;
	}

	public void setAppWidgetId(int appWidgetId) {
		this.appWidgetId = appWidgetId;
	}

	public int getTitlestring() {
		return titlestring;
	}

	public void setTitlestring(int titlestring) {
		this.titlestring = titlestring;
	}
}
