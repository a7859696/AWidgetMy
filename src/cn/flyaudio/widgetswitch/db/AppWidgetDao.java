package cn.flyaudio.widgetswitch.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.flyaudio.widgetswitch.alltools.Constants;
import cn.flyaudio.widgetswitch.alltools.Flog;
import cn.flyaudio.widgetswitch.alltools.ListTools;
import cn.flyaudio.widgetswitch.view.Picture;

public class AppWidgetDao {

	private DBHelper helper;
	private SQLiteDatabase db;

	public AppWidgetDao(Context context) {         	//创建数据库！  ,,存储的是appWidgetId, index, pkgName
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	public AppWidgetDao add(int appWidgetId, int index, String activityName1,int icon1) {		
		db.execSQL("INSERT INTO widget VALUES(null, ?, ?, ?,?)", new Object[] { appWidgetId, index, activityName1,icon1});//数据
		return this;
	}
  
	//自己改

	public AppWidgetDao add(int appWidgetId, int index, int activityName1,int icon1) {		
		db.execSQL("INSERT INTO widget VALUES(null, ?, ?, ?,?)", new Object[] { appWidgetId, index, activityName1,icon1});//数据
		return this;
	}
	
	
	
	public AppWidgetDao update(String name,int icon){
		  ContentValues values=new ContentValues();
		  values.put("icon", icon);
		  Log.d("lixuan", "indexViewId="+name);
		  db.update(DBHelper.TABLE_NAME, values, "icon=?",new String[]{name});
		
		return this;
	}
	
	
	
	public AppWidgetDao delete(int indexViewId,int appWidgetId) {		
		db.delete(DBHelper.TABLE_NAME, "indexViewId = ? and appWidgetId = ?", new String[] { String.valueOf(indexViewId),String.valueOf(appWidgetId) });
		return this;
	}
	public AppWidgetDao delete(int appWidgetId) {		
		db.delete(DBHelper.TABLE_NAME, "appWidgetId = ?", new String[] { String.valueOf(appWidgetId) });
		return this;
	}

	public AppWidgetDao delete(String pkgName) {		
		db.delete(DBHelper.TABLE_NAME, "pkgName = ?", new String[] { pkgName });
		return this;
	}

	public String[] query(int appWidgetId) {
		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE appWidgetId = ?",
				new String[] { String.valueOf(appWidgetId) });
		String[] pkgNames = new String[Constants.viewIds.length];// { null, null, null };
		while (c.moveToNext()) {
			String pkgName = c.getString(c.getColumnIndex("pkgName"));
			int index = c.getInt(c.getColumnIndex("indexViewId"));
			pkgNames[index] = pkgName;
			if (Constants.DEBUG)
				Log.i(Constants.TAG, "[AppWidgetDao query] appWidgetId:" + appWidgetId + " indexViewId:" + index
						+ " pkgName:" + pkgName);
		}
		c.close();
		return pkgNames;
	}
	
	
//自己写，查询icon图像！！！
	public int[] query2(int appWidgetId) {
		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE appWidgetId = ?",
				new String[] { String.valueOf(appWidgetId) });
		int[] pkgNames = new int[Constants.viewIds.length];// { null, null, null };
		while (c.moveToNext()) {
			int pkgName = c.getInt(c.getColumnIndex("pkgName"));
			int index = c.getInt(c.getColumnIndex("indexViewId"));
			pkgNames[index] = pkgName;
			if (Constants.DEBUG)
				Log.i(Constants.TAG, "[AppWidgetDao query] appWidgetId:" + appWidgetId + " indexViewId:" + index
						+ " pkgName:" + pkgName);
		}
		c.close();
		return pkgNames;
	}
	
	
	
	
	
	public int[] query1(int appWidgetId) {
		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE appWidgetId = ?",
				new String[] { String.valueOf(appWidgetId) });
		int[] icon = new int[Constants.viewIds.length];// { null, null, null };
		while (c.moveToNext()) {
			int pkgName = c.getInt(c.getColumnIndex("icon"));
			int index = c.getInt(c.getColumnIndex("indexViewId"));
			icon[index] = pkgName;

			if (Constants.DEBUG)
				Log.i(Constants.TAG, "[AppWidgetDao query] appWidgetId:" + appWidgetId + " indexViewId:" + index
						+ " pkgName:" + pkgName);
		}
		c.close();
		return icon;
	}
	//自己写
	public List<Picture>queryPkgName(){
		Cursor c=db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
         List<Picture> three=new ArrayList<Picture>();
         int appWidgetId1;
         int index;
         int pkgName;
         if(c.moveToFirst()){
        	do {
            appWidgetId1 = c.getInt(c.getColumnIndex("appWidgetId"));
		    index = c.getInt(c.getColumnIndex("indexViewId"));
		    pkgName=c.getInt(c.getColumnIndex("pkgName"));
		    Log.d("lixuandata", "appWidgeId1,index,pakgName="+"   "+appWidgetId1+"   "+index+"    "+pkgName);
		    three.add(new Picture(pkgName, index, appWidgetId1));
		} while (c.moveToNext()) ;}
        		c.close();
        
	 return three;
	}
	
	
	
	public List<Integer> query(String pkgName) {
		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE pkgName = ?", new String[] { pkgName });
		ArrayList<Integer> list = new ArrayList<Integer>(3);
		while (c.moveToNext()) {
			int appWidgetId = c.getInt(c.getColumnIndex("appWidgetId"));
			int index = c.getInt(c.getColumnIndex("index"));
			list.add(appWidgetId * Constants.viewIds.length + index);
			if (Constants.DEBUG)
				Log.i(Constants.TAG, "[AppWidgetDao query] pkgName:" + pkgName + " appWidgetId:" + appWidgetId
						+ " indexViewId:" + index);
		}
		c.close();
		return list;
	}

	public List<Integer> getAppWidgetIds() {
		Cursor c = db.rawQuery("SELECT DISTINCT appWidgetId FROM " + DBHelper.TABLE_NAME, null);
		ArrayList<Integer> list = new ArrayList<Integer>(5);
		while (c.moveToNext()) {
			int appWidgetId = c.getInt(c.getColumnIndex("appWidgetId"));
			if (Constants.DEBUG)
				Log.i(Constants.TAG, "[AppWidgetDao getAppWidgetIds] appWidgetId:" + appWidgetId);
			list.add(appWidgetId);
		}
		c.close();
		return list;
	}

	public void close() {
		helper.close();
	}

	public static void saveAppWidgetId(Context context, int appWidgetId) {
		SharedPreferences preferences = context.getSharedPreferences("appWidget", 0);
		String str = preferences.getString("allAppWidgetId", null);
		List<Integer> ids = null;
		if(str == null) {
			ids = new LinkedList<Integer>();
		} else {
			ids = ListTools.praseIntegerList(str);
		}
		if (!ids.contains(appWidgetId)) {
			ids.add(appWidgetId);
			SharedPreferences.Editor editor = preferences.edit();
			String store = ListTools.listToString(ids);
			editor.putString("allAppWidgetId", store);
			editor.commit();
			Log.i(Constants.TAG, "[AppWidgetDao saveAppWidgetId] store:" + store);
		}
	}

	public static void removeAppWidgetId(Context context, int appWidgetId) {
		SharedPreferences preferences = context.getSharedPreferences("appWidget", 0);
		String str = preferences.getString("allAppWidgetId", null);
		Flog.d(Constants.TAG, "ShortcutWidget removeAppWidgetId str:" + str);
		if(str == null || "".equals(str)) return;
		List<Integer> ids = ListTools.praseIntegerList(str);
		
		if (ids.contains(appWidgetId)) {
			ids.remove((Integer)appWidgetId);			
			if(ids.size() <= 0)
			{				
				SharedPreferences.Editor editor = preferences.edit();				
				editor.putString("allAppWidgetId", null);
				editor.commit();
			}
			else {
				SharedPreferences.Editor editor = preferences.edit();
				String store = ListTools.listToString(ids);
				Flog.d(Constants.TAG, "ShortcutWidget removeAppWidgetId store:" + store);
				editor.putString("allAppWidgetId", store);
				editor.commit();
			}						
		}
	}

	public static int[] getAllAppWidgetId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("appWidget", 0);
		String str = preferences.getString("allAppWidgetId", null);
		if(str == null) return new int[0];
		String[] strIds = str.split(",");
		int[] ids = new int[strIds.length];
		if (ids != null && !"".equals(str)) {
			for (int i = 0; i < strIds.length; i++) {
				ids[i] = Integer.parseInt(strIds[i]);
			}
		}
		return ids;
	}

}
