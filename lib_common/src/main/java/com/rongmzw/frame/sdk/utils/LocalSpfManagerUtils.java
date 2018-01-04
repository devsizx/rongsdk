package com.rongmzw.frame.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalSpfManagerUtils {

	public static void putStringShared(Context context, String shareName, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getStringShared(Context context, String shareName, String key, String defValue) {
		SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		return preferences.getString(key, defValue);
	}

	public static void putIntShared(Context context, String shareName, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getIntShared(Context context, String shareName, String key, int defValue) {
		SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		return preferences.getInt(key, defValue);
	}

	public static void putBooleanShared(Context context, String shareName, String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBooleanShared(Context context, String shareName, String key, boolean defValue) {
		SharedPreferences preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		return preferences.getBoolean(key, defValue);
	}
	public static void clearDatas(Context context, String shareName, String key){
		SharedPreferences preferences=context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
		preferences.edit().clear().commit();
	}
}
