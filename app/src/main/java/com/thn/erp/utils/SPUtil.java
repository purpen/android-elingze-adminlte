package com.thn.erp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.thn.erp.AppApplication;


public class SPUtil {
	public static void write(String key, String value) {
		SharedPreferences sp = AppApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		sp.edit().putString(key,value).apply();
	}

	public static void write(String key, boolean value) {
		SharedPreferences sp = AppApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).apply();
	}

	public static String read(String key) {
		SharedPreferences sp = AppApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		return sp.getString(key,"");
	}

	public static boolean readBool(String key) {
		SharedPreferences sp = AppApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}

	public static void remove(String key) {
		SharedPreferences sp = AppApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		sp.edit().remove(key).apply();
	}

	public static void clear(String key) {
		SharedPreferences sp = AppApplication.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		sp.edit().clear().apply();
	}
}
