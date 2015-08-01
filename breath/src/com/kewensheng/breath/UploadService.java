package com.kewensheng.breath;


import java.util.Calendar;

import com.kewensheng.breath.AlarmService.alarmSetReceiver;
import com.kewensheng.cls.DayRecordCls;
import com.kewensheng.controller.IdiyMessage;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class UploadService extends Service{

	private boolean isRunning=false;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("hj", "upload service create");
		registerReceiver();
		super.onCreate();
	}


	


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		Log.i("hj", "restartService");
		stop();
		super.onDestroy();
	}
	
	


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("hj", "upload service start");
		//每次start时自动重新设置闹钟任务
//		test();
//		setUpAlarms();
		checkRunning();
		
		sendTestBroadcast();
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void sendTestBroadcast(){
		Intent i3 = new Intent();
		i3.setAction(IdiyMessage.SET_UPLOAD+"");
		Bundle bundle = new Bundle();
		DayRecordCls test = new DayRecordCls();
		test.setGasp(1);
		test.setActivityhinder(1);
		test.setBreathhold(1);
//		test.setControlmedicine(1);
		test.setCough(1);
		test.setNightremind(1);
		bundle.putSerializable("dayRecord", test);
		i3.putExtras(bundle);
		sendBroadcast(i3);
	}
	
	@SuppressWarnings("deprecation")
	private void checkRunning() {
		// TODO Auto-generated method stub
		if (!isRunning) {
		      isRunning=true;
		      //重启动服务
		      Intent i = new Intent();
		      i.setClass(this, UploadService.class);
		      startService(i);
		    }
		
	}
	
	private void stop() {
		// TODO Auto-generated method stub
		if (isRunning) {
//		      Log.w(getClass().getName(), "Got to stop()!");
		      isRunning=false;
			  Intent intent =new Intent();
			  intent.setClass(this, UploadService.class);
			  startService(intent);
		 }
	}


	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	/**广播接收器，用于接收设置/更新计时提醒设置*/ 
	public class uploadTaskReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(IdiyMessage.SET_UPLOAD+"")){
				Log.i("hj", "get set broadcast");
				Bundle bundle = intent.getExtras();
				if(bundle!=null){
					DayRecordCls data =(DayRecordCls) bundle.getSerializable("dayRecord");
					Log.i("hj", data.toString());
					setUploadTask(data);
				}
			}
		}
	}
	
	//设置上传任务
	public void setUploadTask(DayRecordCls data) {
		// TODO Auto-generated method stub
		try {
			PendingIntent uploadSlender = null;
			Intent startintent = new Intent();
			startintent.setClass(getApplication(), UploadReceiver.class);
			startintent.setAction(IdiyMessage.RUN_UPLOAD+"");
			AlarmManager am = (AlarmManager) getApplication().getSystemService(Activity.ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR, 12);
			calendar.set(Calendar.MINUTE, 12);
			calendar.set(Calendar.SECOND, 0);
			Log.i("hj", "set up task");
			getApplicationContext().sendBroadcast(startintent);
//			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000,uploadSlender);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	private void registerReceiver() {
		// TODO Auto-generated method stub
		IntentFilter dynamic_filter = new IntentFilter();
		dynamic_filter.addAction(IdiyMessage.SET_UPLOAD+"");	//添加动态广播的Action
		registerReceiver(new uploadTaskReceiver(), dynamic_filter);
		
	}
	
	


}
