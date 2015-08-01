package com.kewensheng.breath;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
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

import com.alibaba.fastjson.JSONObject;
import com.kewensheng.cls.ClockCls;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.LogCat;

public class AlarmService extends Service{
	
	private boolean isRunning=false;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("hj", "service create");
		
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
		Log.i("hj", "alarm service start");
		//每次start时自动重新设置闹钟任务
//		test();
		try {
			setUpAlarms();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		registerReceiver();
		checkRunning();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@SuppressWarnings("deprecation")
	private void checkRunning() {
		
		if (!isRunning) {
		      Log.w(getClass().getName(), "Got to play()!");
		      isRunning=true;

		      Notification note=new Notification(R.drawable.ic_launcher,
		                                          "正在运行",
		                                          System.currentTimeMillis());
		      Intent i=new Intent(this, AlarmService.class);
		    
		      i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
		                 Intent.FLAG_ACTIVITY_SINGLE_TOP);
		    
		      PendingIntent pi=PendingIntent.getActivity(this, 0,
		                                                  i, 0);
		      String appname = getResources().getString(R.string.app_name);
		      note.setLatestEventInfo(this, appname,
		                              "正在运行",
		                              pi);
		      note.flags|=Notification.FLAG_NO_CLEAR;
		      startForeground(1337, note);
		    }
		
	}
	
	private void stop() {
		// TODO Auto-generated method stub
		if (isRunning) {
//		      Log.w(getClass().getName(), "Got to stop()!");
			  Log.i("hj", "app stop");
		      isRunning=false;
		      stopForeground(true);
			  Intent intent =new Intent();
			  intent.setClass(this, AlarmService.class);
			  startService(intent);
		 }
	}


	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	/**广播接收器，用于接收设置/更新计时提醒设置*/ 
	public class alarmSetReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("hj", "alarm set receive");
			if(intent.getAction().equals(IdiyMessage.SET_ALARM+"")){
				Bundle bundle = intent.getExtras();
				if(bundle!=null){
//					Calendar calendar = (Calendar) bundle.getSerializable("alarm_setting");
					Calendar calendar = Calendar.getInstance();
//					String name = bundle.getString("alarm_name");
					ClockCls data = (ClockCls) bundle.getSerializable("data");
//					int hour= bundle.getInt("hour");
//					int minute = bundle.getInt("minute");
					int name = data.getName();
					LogCat.say("hi", "alarmSetReceiver"+name);
					calendar.set(Calendar.HOUR_OF_DAY, data.getHour());
					calendar.set(Calendar.MINUTE, data.getMinute());
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					LogCat.say("hi","闹钟时间"+calendar.getTimeInMillis()+"");
					LogCat.say("hi", "传递时间"+System.currentTimeMillis()+"");
					Log.i("hj", "alarm set receive5");
					setUpAlarms(calendar,name);
				}
			}
		}
	}
	

	/**设置定时任务*/
	private void setUpAlarms() {
		// TODO Auto-generated method stub
//		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		//获取SharedPreferences中保存的计时数据
		SharedPreferences sp = this.getSharedPreferences("Detail", this.MODE_PRIVATE);
		String clockcontent = sp.getString("clock", "");
		if(!clockcontent.equals("")){
			ArrayList<ClockCls> a = (ArrayList<ClockCls>) JSONObject.parseArray(clockcontent, ClockCls.class);
			for (int i = 0; i < a.size(); i++) {
//				if(name.equals(a.get(i).getName()))
//					Toast.makeText(this, "你设置的闹钟时间到了,本次需服用"+JSONObject.toJSONString(a.get(i).getMedicine()), Toast.LENGTH_LONG).show();
				int hour = a.get(i).getHour();
				int minute = a.get(i).getMinute();
				int name = a.get(i).getName();
				LogCat.say("hi", name+"");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Log.i("hj", calendar.getTime()+"");
				setUpAlarms(calendar, name);
			}
		}
		
	}
	
	/**设置指定时间和名称的计时任务*/
	public void setUpAlarms(Calendar calendar, int name) {
		Intent intent = new Intent(this,AlarmReceiver.class).putExtra("clockname", name);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, name,intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//		long i = calendar.getTimeInMillis();
		LogCat.say("hi", calendar.getTimeInMillis()+"闹钟具体时间");
		am.setRepeating(AlarmManager.RTC_WAKEUP, 
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
//		am.setRepeating(AlarmManager.RTC_WAKEUP, 
//				calendar.getTimeInMillis(), 5*1000,pendingIntent);
		Log.i("hj", "alarm seted");
		
	}
	
//	private void test() {
//		Log.i("hj", "runtest2");
//		// TODO Auto-generated method stub
//		Calendar calendar = Calendar.getInstance();
//		
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.set(Calendar.SECOND, calendar.SECOND+5);
//		String testname = "测试用药";
////		setUpAlarms(calendar, testname);
//	}
	
	private void registerReceiver() {
		// TODO Auto-generated method stub
		IntentFilter dynamic_filter = new IntentFilter();
		dynamic_filter.addAction(IdiyMessage.SET_ALARM+"");	//添加动态广播的Action
		registerReceiver(new alarmSetReceiver(), dynamic_filter);
		
	}
	

}
