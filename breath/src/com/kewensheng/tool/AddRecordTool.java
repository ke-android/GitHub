package com.kewensheng.tool;

import android.content.Context;
import android.content.SharedPreferences;

public class AddRecordTool {
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	public AddRecordTool(Context c) {
		this.mContext = c;
		mSharedPreferences = mContext.getSharedPreferences("Clock", mContext.MODE_PRIVATE);
	}
	
}
