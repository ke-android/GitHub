package com.kewensheng.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class GetVersionCode {
	public static String getVersionCode(Context c){
		try {
			PackageManager packageManager = c.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(c.getPackageName(), 0);
			String versionCode = packageInfo.versionCode+"";
			return versionCode;
		} catch (NameNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	public static String getVersion(Context c){
		try {
			PackageManager packageManager = c.getPackageManager();
			PackageInfo info = packageManager.getPackageInfo(c.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
}
