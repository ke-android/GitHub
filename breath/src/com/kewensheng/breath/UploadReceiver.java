package com.kewensheng.breath;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpConnection;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

import com.hejin.dao.DBService;
import com.kewensheng.cls.DayRecordCls;
import com.kewensheng.tool.LogCat;
import com.kewensheng.util.NetWorkConst;
import com.kewensheng.util.NetWorkRequest;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

public class UploadReceiver extends BroadcastReceiver{
	//上传访问的地址
//	public static String base = "http://210.21.34.4:9100/api/APPService.ashx?cmd=ServerHandler.SendUserACT";
	public static String HOST = "http://210.21.34.4:9100/api/appService.ashx";
	public static String MODULE = "cmd=NJServerHandler.SendUserACT";
	private String tel="";
	private Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//收到广播启动上传（访问URL）
		Log.i("hj", "get start upload broadcast");
		try {
			mContext = context;
//			tel= getPhoneNumber(context);
			tel="13480249527";
			Log.i("hj", "tel:"+tel);
//			DBService db = new DBService(context);
//			List<DayRecordCls> uploadData = db.loadPaitentData();
			List<DayRecordCls> uploadData = new ArrayList<DayRecordCls>();
			DayRecordCls test = new DayRecordCls();
			test.setGasp(1);
			test.setActivityhinder(1);
			test.setBreathhold(1);
//			test.setControlmedicine(1);
			test.setCough(0);
			test.setNightremind(4);
			uploadData.add(test);
//			if(uploadData.size()!=0){
				runupload(uploadData);
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private void runupload(List<DayRecordCls> uploadData) {
		Log.i("hj", "try upload");
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < uploadData.size(); i++) {
				startUpload(uploadData.get(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**启动上传*/
	private void startUpload(DayRecordCls dayRecordCls) {
		// TODO Auto-generated method stub
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
		String date =sf.format(new Date());
//		try {
//
//			date = URLEncoder.encode(date, "UTF-8");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
        try {
//        	final String url = HOST+"?"+MODULE+"&mobile="+tel+dayRecordCls.toUploadString()+"&senddatetime="+date;

        	final String url ="http://210.21.34.4:9100/api/Service.ashx?cmd=NJServerHandler.GeEmergencyMedicineList";
        	Log.i("hj", "uploadUrl:"+url);
        	Thread thread =new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//访问地址
		        	String result = NetWorkRequest.doPost(null, url);
		        	Log.i("hj", "res:"+result);
				}
			});
        	
        	thread.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	private String getPhoneNumber(Context context){   
	    TelephonyManager mTelephonyMgr;   
	    mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
	    return mTelephonyMgr.getLine1Number();   
	}   
}
