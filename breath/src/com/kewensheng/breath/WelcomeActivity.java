package com.kewensheng.breath;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {
	private View mRootView;
	private Timer timer;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Context ctx = WelcomeActivity.this;       
			SharedPreferences sp = ctx.getSharedPreferences("Detail", MODE_PRIVATE);
			switch (msg.what) {
			//获取SharedPreferences对象
			case 1:
//				if (sp.getBoolean("LoginState", false)) {
//					startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//					//运行完毕以后直接销毁掉 就不会返回到这个界面了
//					finish();}
//				else{
//					startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//					finish();}
//				break;
				startActivity(new Intent(WelcomeActivity.this,DengLuAty.class));
				finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg); 
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setFlags(flag, flag);
		super.onCreate(savedInstanceState);
		mRootView=LayoutInflater.from(this).inflate(R.layout.aty_welcome, null);
		setContentView(mRootView);
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Message message = new Message();      
				message.what = 1;      
				handler.sendMessage(message);    
			}
		}, 1000);
	}

}
