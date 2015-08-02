package com.kewensheng.tool;

import com.alibaba.fastjson.JSONObject;
import com.kewensheng.cls.PersonalDetaileCls;

import android.content.Context;
import android.content.SharedPreferences;

public class PersonalTool {
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private PersonalDetaileCls personalDetaileCls;

	public PersonalTool(Context c) {
		this.mContext = c;
		mSharedPreferences = mContext.getSharedPreferences("Detail", mContext.MODE_PRIVATE);
		personalDetaileCls = new PersonalDetaileCls();
		String a = mSharedPreferences.getString("detail", null);
		personalDetaileCls = JSONObject.parseObject(a, PersonalDetaileCls.class);
	}
	
	public PersonalDetaileCls getPersonalCls(){
		return personalDetaileCls;
	}
	
	public float getPef() {
		return personalDetaileCls.getPef();
	}
}
