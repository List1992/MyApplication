package com.example.administrator.myapplication.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.administrator.myapplication.App;

public class UIUtils {
	/**
	 * 获取屏幕宽度
	 * 
	 * @Description:
	 * @param activity
	 * @return
	 *
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @Description:
	 * @param activity
	 * @return
	 *
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @Description:
	 * @param activity
	 * @return
	 *
	 */
	public static int getStatusBarHeight(Activity activity) {
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 *
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = App.getContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 *
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(float dipValue) {
		final float scale = App.getContext().getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 *
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 *
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static View inflate(int layoutId) {
		return View.inflate(App.getContext(), layoutId, null);
	}

	public static <T extends Object> T checkNull(String msg, T t) {
		if (t == null) {
			throw new NullPointerException(msg + " can not be null!");
		}
		return t;
	}

	public static <T extends Object> T checkNull(T t) {
		return checkNull("params", t);
	}
}
