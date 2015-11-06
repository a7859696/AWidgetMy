package cn.flyaudio.widgetswitch.alltools;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;

public class ListTools {

	public static <E extends Object> String listToString(List<E> list) {
		if(list == null) return null;
		StringBuilder builder = new StringBuilder();      //StringBuilder,append()    增加字符串
		for (int i = 0; i < list.size() ; i++) {
			builder.append(list.get(i).toString());
			builder.append(",");
		}
		builder.deleteCharAt(builder.length()-1);       //指定位置删除字符
		if(Constants.DEBUG) Log.d(Constants.TAG, "[ListTools listToString] str: "+builder.toString());
		return builder.toString();
	}
	
	public static List<Integer> praseIntegerList(String str) {
		if(str == null) return null;
		List<Integer> list = new LinkedList<Integer>();
		String[] array = str.split(",");
		for(String s : array) {
			try{
				list.add(Integer.parseInt(s));                     
			}catch (NumberFormatException e) {
				e.printStackTrace();
				list = null;
				break;
			}
		}
		if(Constants.DEBUG) 
			if(list != null)
				Log.d(Constants.TAG, "[ListTools praseIntegerList] list: "+list.toString());
		return list;
	}
}
