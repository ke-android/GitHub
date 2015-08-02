package com.kewensheng.controller;

public class IdiyMessage {
	//APP调用接口新增患者信息过来
	public static final int AddUser = 1;
	public static final int AddUser_finish = 2;
	//    紧急用药接口
	public static final int GeEmergencyMedicineList = 3;
	public static final int GeEmergencyMedicineList_finish = 4;
	//    日常用药接口
	public static final int GeDayUserMedicineList = 5;
	public static final int GeDayUserMedicineList_finish = 6;
	//    PEF吹波接口
	public static final int SendUserPef = 7;
	public static final int SendUserPef_finish = 8;
	//   ACT接口
	public static final int SendUserACT = 9;
	public static final int SendUserACT_finish = 10;
	//更新版本
	public static final int GeVersionList = 11;
	public static final int GeVersionList_finish = 12;   



	public static final int UPDATE_PROGRESS = 9;
	public static final int UPDATE_FINISH = 10;   
	public static final int UPDATE_ERROR = 11;

	//标示哪种类型的药物
	public static final int keep = 12;   
	//紧急用药
	public static final int urgency = 13;
	//返回
	public static final int back = 14;
	//闹钟
	public static final int clock = 15;

	//计时提醒更新
	public static final int SET_ALARM = 201;
	public static final int SHOW_ALARM = 202;

	public static final int SET_UPLOAD = 203;
	public static final int RUN_UPLOAD = 204;
	//用户登陆
	public static final int UserLogin = 205;
	public static final int UserLogin_finish = 206;
	//注册
	public static final int RegUser = 207;
	public static final int RegUser_finish = 208;
	//发送验证码
	public static final int SendVCode = 209;
	public static final int SendVCode_finish = 209;
	//获取关于我信息
	public static final int GetAPPAboutMe = 210;
	public static final int GetAPPAboutMe_finish = 211;
	//修改密码
	public static final int ModifyPassword = 212;
	public static final int ModifyPassword_finish = 213;

}
