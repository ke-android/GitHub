package com.kewensheng.breath;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.hejin.widget.ConfirmDialog;
import com.kewensheng.cls.ClockCls;
import com.kewensheng.cls.MedicineCls;

public class AlarmReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.i("hj", "alarm broadcast receive");
		//		Intent i = new Intent();
		//		i.setClass(arg0, MainActivity.class);
		//		arg0.startActivity(i);
		//		String name = arg1.getStringExtra("clockname");
		//		Context ctx = arg0;       
		//		SharedPreferences sp = ctx.getSharedPreferences("Detail", arg0.MODE_PRIVATE);
		//		String clockcontent = sp.getString("clock", "");
		//		if(!clockcontent.equals("")){
		//			ArrayList<ClockCls> a = (ArrayList<ClockCls>) JSONObject.parseArray(clockcontent, ClockCls.class);
		//			for (int i = 0; i < a.size(); i++) {
		//				if(name.equals(a.get(i).getName())){
//		String name = arg1.getStringExtra("clockname");
		int name = arg1.getExtras().getInt("clockname");
//		String msg = "你设置的闹钟时间到了,本次需服用测试用药";
		String msg = "";
		SharedPreferences sp = arg0.getSharedPreferences("Clock", Context.MODE_PRIVATE);
		String clockstr = sp.getString("clock", "");
		ArrayList<ClockCls> clockCls = new ArrayList<ClockCls>();
		Log.i("hj", "ArrayList<ClockCls> clockCls = new ArrayList<ClockCls>();");
		if(clockstr!=null&&!clockstr.equals(""))
			clockCls= (ArrayList<ClockCls>) JSON.parseArray(clockstr, ClockCls.class);
		for (int i = 0; i < clockCls.size(); i++) {
//			if(name.equals(clockCls.get(i).getName()+"")){
			if(name==clockCls.get(i).getName()){
				String str = clockCls.get(i).getMedicine();
				ArrayList<MedicineCls> list = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
				msg = "您设置的闹钟时间到了,本次需要服用";
				for (int j = 0; j < list.size(); j++) {
					msg = msg+list.get(j).getMedicineName()+"/";
				}
			}
		}
		msg = msg.substring(0, msg.length()-1);
		Log.i("hj", msg);
		showDialog(arg0, msg);
	}

	private void showDialog(Context arg0,String msg) {
		// TODO Auto-generated method stub
		final ConfirmDialog dlg = new ConfirmDialog(arg0, msg);
		dlg.show();
		dlg.setButton("确定", "取消");
		dlg.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		}, new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});
	}
}
