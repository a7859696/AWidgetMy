package cn.flyaudio.widgetswitch.alltools;

import java.io.InputStream;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 用于加载包括其他apk中的资源（raw，layout，drawable，values/strings,colors）
 * <p>
 * 加载其他APK的资源，如果找不到就加载本地资源
 */
public class SkinResource {

	public static final String TAG = "SkinResource";
	private static Context mSkinContext = null;
	private static Context mLocalContext = null;

	public static Context getSkinContext() {
		return mSkinContext;
	}

	public static void initSkinResource(Context context, String pkgName) {
		try {
			mLocalContext = context;
			mSkinContext = context.createPackageContext(pkgName, Context.CONTEXT_IGNORE_SECURITY);
			Log.d("pictures", "The skin resource packageName:" + pkgName);
		} catch (NameNotFoundException e) {
			Log.e("pictures", "Can't find the skin resource packageName:" + pkgName);
			e.printStackTrace();
			mSkinContext = context;
		}
	}

	private static int getIdentifier(String name, String type, Context context) {
		try {	
		Log.d("pictures1", "从id222进来==或者333进来"+context.getResources().getIdentifier(name, type, context.getPackageName()));
			return context.getResources().getIdentifier(name, type, context.getPackageName());
		} catch (Exception e) {
			String packageName = null;
			if(context!=null)
				 packageName = context.getPackageName();
			final String str = "getIdentifier() Fail!! name:" + name + " type:" + type + " packageName:"
					+ packageName;
			Log.e(TAG, str);
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 皮肤包找不到，找本地
	 * @param name
	 * @param type
	 * @return
	 */
	private static int getIdentifier(String name,String type) {
		Log.d("pictures1", "id222==");
		int id = getIdentifier(name, type, mSkinContext);
		Log.d("pictures1", "id为:                     "+id);
		if (id == 0 && mSkinContext != mLocalContext) {
			Log.d("pictures1", "进入  ----id333333==");
			id = getIdentifier(name, type, mLocalContext);
			Log.d("pictures1", "id333333为:                     "+id);
		}
		return id;
	}

	public static int getSkinDrawableIdByName(String name) {
		Log.d("pictures", "进入getSkinDrawableIdByName~~");
		return getIdentifier(name, "drawable");
	}
	
	public static int getSkinLayoutIdByName(String name) {
		return getIdentifier(name, "layout");
	}
	
	public static int getSkinResourceId(String name, String type) {
		Log.d("pictures", "进入getSkinResourceId------"+getIdentifier(name, type));
		return getIdentifier(name, type);
	}
	
	public static InputStream getSkinRawInputStream(String name) {
		Context context = mSkinContext;
		int rawId = getIdentifier(name, "raw", context);
		if (rawId == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			rawId = getIdentifier(name, "raw", context);
		}
		if (rawId == 0)
			return null;
		return context.getResources().openRawResource(rawId);
	}

	public static View getSkinLayoutViewByName(String name) {
		Context context = mSkinContext;
		int id = getIdentifier(name, "layout", context);
		if (id == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			id = getIdentifier(name, "layout", context);
		}
		if(id == 0)
			return null;
		LayoutInflater mInflater = LayoutInflater.from(context);
		return mInflater.inflate(id, null);
	}

	public static String getSkinStringByName(String name) {
		Context context = mSkinContext;
		int id = getIdentifier(name, "string", context);
		if (id == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			id = getIdentifier(name, "string", context);
		}
		Log.d("pictures1", "getSkinStringIdByName----String------id="+id);
		if (id == 0) {
			return null;
		}
		return context.getResources().getString(id);
	}
	//自己写的两个函数
	public static String getSkinStringById(int id) {
		Context context = mSkinContext;
		Log.d("pictures2", "getSkinStringById----String------id="+id);
		String  string=context.getResources().getString(id);
		Log.d("pictures2", "getSkinStringById----String------String="+string);
		if (string == null && mSkinContext != mLocalContext) {
			context = mLocalContext;
			string = context.getString(id);
			Log.d("pictures2", "getSkinStringById2----String2-----String2="+string);
		}
		if (id == 0) {
			return null;
		}
		return context.getResources().getString(id);
	}
	
	public static int getSkinStringIdByName(String name) {
		Log.d("pictures1", "getSkinStringIdByName----------进来: name="+name);

//		int shuzi= getIdentifier(name, "string");
//     //   Log.d("pictures", "进入getSkinStringIDByName-------"+getIdentifier(name, "string",context)+"    "+id);
//        Log.d("pictures1", "id----------"+shuzi);
//		return shuzi;
		Context context = mSkinContext;
		int id = getIdentifier(name, "string", context);
		if (id == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			id = getIdentifier(name, "string", context);
			Log.d("pictures1", "id------111----"+id);
		}
		Log.d("pictures1", "id----222------"+id);
		if (id == 0) {
			return id;
		}
	    return id;
	}

	
	public static Drawable getSkinDrawableByName(String name) {
		Context context = mSkinContext;
		int id = getIdentifier(name, "drawable", context);
		if (id == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			id = getIdentifier(name, "drawable", context);
		}
		if (id == 0) {
			return null;
		}
		return context.getResources().getDrawable(id);

	}

	public static ColorStateList getSkinColorStateList(String name) {
		Context context = mSkinContext;
		int id = getIdentifier(name, "drawable", context);
		if (id == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			id = getIdentifier(name, "drawable", context);
		}
		if (id == 0) {
			return null;
		}
		return context.getResources().getColorStateList(id);
	}

	/**
	 * 注意顺序问题
	 * @param names
	 * @return
	 */
	public static int[] getSkinStyleableIdByName(String[] names) {
		int[] result = new int[names.length];
		for (int i = 0; i < names.length; i++) {
			result[i] = getIdentifier(names[i], "attr");
		}
		return result;
	}
	
	private static class Result{
		private final int id;
		private final Context context;
		public Result(int id,Context context){
			this.id = id;
			this.context = context;
		}
		public int getId() {
			return id;
		}
		public Context getContext() {
			return context;
		}
	}
	
	private static Result getIdentifierAndContext(String name,String type) {
		Context context = mSkinContext;
		int id = getIdentifier(name, type, context);
		//皮肤包没找到，找本地
		if (id == 0 && mSkinContext != mLocalContext) {
			context = mLocalContext;
			id = getIdentifier(name, type, context);
		}
		//返回结果中包含id和context
		return new Result(id,context);
	}

}
