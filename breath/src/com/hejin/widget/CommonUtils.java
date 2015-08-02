/**
 * @Title: CommonUtils.java
 * @Description: TODO
 * @author: Calvinyang
 * @date: Nov 29, 2013 11:55:09 AM
 * Copyright: Copyright (c) 2013
 * @version: 1.0
 */
package com.hejin.widget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

/**
 * @author: Calvinyang
 * @Description: TODO
 * @date: Nov 29, 2013 11:55:09 AM
 */
public class CommonUtils {
	private static DisplayMetrics dm;

	/**
	 * �?测网络是否可�?
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isAvailable();
	}

	/**
	 * 
	 * @Title: getWidth
	 * @Description: 获取窗口宽度
	 * @param context
	 * @return
	 */
	public static int getWidth(Context context) {
		if (dm == null) {
			dm = context.getResources().getDisplayMetrics();
		}
		return dm.widthPixels;
	}

	/**
	 * 
	 * @Title: getDensity
	 * @Description: 获取屏幕密度
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context) {
		if (dm == null) {
			dm = context.getResources().getDisplayMetrics();
		}
		return dm.density;
	}

	/**
	 * 
	 * @Title: getHeight
	 * @Description: 获取窗口宽度
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		if (dm == null) {
			dm = context.getResources().getDisplayMetrics();
		}
		return dm.heightPixels;
	}

}
