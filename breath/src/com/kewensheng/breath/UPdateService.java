package com.kewensheng.breath;

import com.kewensheng.controller.IModelChangeListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;

public class UPdateService extends Service implements IModelChangeListener{
	private Handler mHandler = new Handler(){
		
	};
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Context ctx = getApplicationContext();    
		SharedPreferences sp = ctx.getSharedPreferences("ActDetail", MODE_PRIVATE);
		String actData = sp.getString("actid", "");
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void onModelChanged(int action, Object... value) {
		// TODO 自动生成的方法存根
		
	}


}
