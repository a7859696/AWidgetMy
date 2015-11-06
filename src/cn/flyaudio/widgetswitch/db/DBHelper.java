package cn.flyaudio.widgetswitch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	/*final static String CREATE_TABLE_SQL = 
			"CREATE TABLE IF NOT EXISTS widget(_id INTEGER PRIMARY KEY AUTOINCREMENT, appWidgetId INTEGER, indexViewId INTEGER, pkgName VARCHAR, icon INTEGER)";*/
	final static String CREATE_TABLE_SQL = 
			"CREATE TABLE IF NOT EXISTS widget(_id INTEGER PRIMARY KEY AUTOINCREMENT, appWidgetId INTEGER, indexViewId INTEGER, pkgName INTEGER, icon INTEGER)";
	final static String DATABASE_NAME = "switchwidget.db";
	final static String TABLE_NAME = "widget";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
