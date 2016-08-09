package com.example.administrator.myapplication.utils;

import android.util.Log;

public class LogUtil {
	private static String TAG = "wczhgl";
	private static final int level = 6;

	private static int ERROR_LEVEL = 5;
	private static int WARN_LEVEL = 4;
	private static int DEBUG_LEVEL = 3;
	private static int INFO_LEVEL = 2;
	private static int VERBOSE_LEVEL = 1;

	public static void e(String msg) {
		if (ERROR_LEVEL <= level) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String tag,String msg) {
		if (ERROR_LEVEL <= level) {
			Log.e(tag, msg);
		}
	}

	public static void e(Exception e) {
		if (ERROR_LEVEL <= level) {
			e.printStackTrace();
		}
	}

	public static void w(String msg) {
		if (WARN_LEVEL <= level) {
			Log.w(TAG, msg);
		}
	}
	public static void w(String tag,String msg) {
		if (WARN_LEVEL <= level) {
			Log.w(tag, msg);
		}
	}

	public static void i(String msg) {
		if (INFO_LEVEL <= level) {
			Log.i(TAG, msg);
		}
	}
	public static void i(String tag,String msg) {
		if (WARN_LEVEL <= level) {
			Log.i(tag, msg);
		}
	}
	public static void d(String msg) {
		if (DEBUG_LEVEL <= level) {
			Log.d(TAG, msg);
		}
	}
	public static void d(String tag,String msg) {
		if (DEBUG_LEVEL <= level) {
			Log.d(tag, msg);
		}
	}

	public static void v(String msg) {
		if (VERBOSE_LEVEL <= level) {
			Log.v(TAG, msg);
		}
	}

	public static void v(String tag,String msg) {
		if (VERBOSE_LEVEL <= level) {
			Log.v(tag, msg);
		}
	}
}