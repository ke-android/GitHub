package com.kewensheng.receiver;


import com.kewensheng.breath.AlarmService;
import com.kewensheng.breath.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
/**开机自启动SERVICE*/
public class BootCompleteReceiver extends BroadcastReceiver{
	private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO 自动生成的方法存根
		//通过intent 启动SERVICE
		Toast.makeText(arg0, "recive", 10).show();
		try {
//			if(arg1.getAction().equals(ACTION_BOOT)){
				Intent intent =new Intent(arg0,AlarmService.class);
				arg0.startService(intent);
//				Intent i = new Intent();
//				i.setClass(arg0, MainActivity.class);
//				arg0.startActivity(i);
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
