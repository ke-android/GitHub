package com.kewensheng.controller;

import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.kewensheng.tool.LogCat;


public class UpdateController extends BaseController{
	 Context mContext;
	    
	    public UpdateController(Context context) {
	        super(context);
	        this.mContext = context;
	    }

	    @Override
	    public void handlerMessage(int action, Object... values) {
	        switch (action) {
	        case IdiyMessage.GeVersionList:
	            notifyDataChange(IdiyMessage.GeVersionList_finish, loadVersionData());
	            break;
	        }

	    }
	    
	    
	    private HashMap<String, Object> loadVersionData() {
	        PackageManager packageManager = mContext.getPackageManager();
	        // getPackageName()是你当前类的包名，0代表是获取版本信息
//	        PackageInfo packInfo;
	        String version = null;
	        try {
	            version = packageManager.getPackageInfo(mContext.getPackageName(),0).versionCode + "";
	            LogCat.say("version>>>>>>>>>>>"+version);
	        } catch (NameNotFoundException e1) {
	            e1.printStackTrace();
	        }
	        try {
	            LogCat.say("loadVersionData>>>>>>>>>>>>>"+version);
//	            return getUpData(XhEduApplication.loginUser.getUserId(), version);
//	            这里写下面那个方法
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public HashMap<String, Object> getUpData(
	            String userId ,String Version) throws Exception {
//	    	这里写获取版本信息和URL之类的方法
	    	return null;
	    }
}
