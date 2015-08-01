package com.kewensheng.tool;

import android.app.Application;

	public class MyApp extends Application{

		private static MyApp instance;
		
		
		public static MyApp getContext(){
			return instance;
		}


		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			instance=this;
		}
	}
