package com.kewensheng.controller;


import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.MedicineCls;
import com.kewensheng.cls.PersonalDetaileCls;
import com.kewensheng.cls.ReturnedCls;
import com.kewensheng.cls.SendCodeCls;
import com.kewensheng.cls.XieYiAtyCls;
import com.kewensheng.cls.ZhuCheAtyCls;
import com.kewensheng.tool.GetVersionCode;
import com.kewensheng.tool.LogCat;
import com.kewensheng.tool.MyApp;
import com.kewensheng.util.NetWorkConst;
import com.kewensheng.util.NetWorkRequest;

public class BreathController extends BaseController{

	public BreathController(Context context) {
		super(context);
	}

	@Override
	public void handlerMessage(int action, Object... values) {
		switch (action) {
		//APP���ýӿ�����������Ϣ����
		case IdiyMessage.AddUser:
			notifyDataChange(IdiyMessage.AddUser_finish, postAddUser((String)values[0], (PersonalDetaileCls)values[1]));
			break;

			//    �ճ���ҩ�ӿ�
		case IdiyMessage.GeDayUserMedicineList:
			notifyDataChange(IdiyMessage.GeDayUserMedicineList_finish, postGeDayUserMedicineList((String)values[0]));
			break;

			//    ������ҩ�ӿ�
		case IdiyMessage.GeEmergencyMedicineList:
			notifyDataChange(IdiyMessage.GeEmergencyMedicineList_finish, postGeEmergencyMedicineList((String)values[0]));
			break;

			//    PEF�����ӿ�
		case IdiyMessage.SendUserPef:
			notifyDataChange(IdiyMessage.SendUserPef_finish, postSendUserPef((String)values[0]));
			break;

			//   ACT�ӿ�
		case IdiyMessage.SendUserACT:
			notifyDataChange(IdiyMessage.SendUserACT_finish, postSendUserACT((String)values[0]));
			break;

			//���°汾
		case IdiyMessage.GeVersionList:
			notifyDataChange(IdiyMessage.GeVersionList_finish, postGeVersionList((String)values[0]));
			break;
			
			//��½
		case IdiyMessage.UserLogin:
			notifyDataChange(IdiyMessage.UserLogin_finish, postServerHandler_UserLogin((String)values[0],(String)values[1],(String)values[2]));
			break;
			//������֤��
		case IdiyMessage.SendVCode:
			notifyDataChange(IdiyMessage.SendVCode_finish, postSendVCode((String)values[0],(String)values[1]));
			break;
			//ע���û�
		case IdiyMessage.RegUser:
			notifyDataChange(IdiyMessage.RegUser_finish, postRegUser((String)values[0],(String)values[1],(String)values[2],(String)values[3],(String)values[4],(String)values[5]));
			break;
			//��ȡ��������Ϣ
		case IdiyMessage.GetAPPAboutMe:
			notifyDataChange(IdiyMessage.GetAPPAboutMe_finish, postGetAPPAboutMe((String)values[0]));
			break;
			//��ȡ��������Ϣ
//			mController.sendAsyncMessage(IdiyMessage.ModifyPassword, "ModifyPassword",mobile,password,password1,vcode);
		case IdiyMessage.ModifyPassword:
			notifyDataChange(IdiyMessage.ModifyPassword_finish, postModifyPassword((String)values[0],(String)values[1],(String)values[2],(String)values[3],(String)values[4]));
			break;

			
		default:
			break;
		}
	}
//	mController.sendAsyncMessage(IdiyMessage.ModifyPassword, "ModifyPassword",mobile,password,password1,vcode);

	private Object postModifyPassword(String string, String mobile,
			String password, String password1, String vcode) {		
		String jsonString = NetWorkConst.HEAD+string;
		jsonString = jsonString + ad("mobile")+ mobile+ad("password")+ password+ad("Password1")+ password1+ad("vcode")+vcode;
		String str = NetWorkRequest.doPost("", jsonString);
//		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
		SendCodeCls returnedJson = JSON.parseObject(str, SendCodeCls.class);
		return returnedJson;
	}

	private Object postGetAPPAboutMe(String string) {		
		String jsonString = NetWorkConst.HEAD+string;
		String str = NetWorkRequest.doPost("", jsonString);
		XieYiAtyCls returnedJson = JSON.parseObject(str, XieYiAtyCls.class);
		return returnedJson;
	}

	private Object postRegUser(String string, String mobile, String password,
			String password1, String vcode, String patienttype) {		
		String jsonString = NetWorkConst.HEAD+string;
		jsonString = jsonString + ad("mobile")+ mobile+ad("password")+ password+ad("password1")+ password1+ad("vcode")+ vcode+ad("patienttype")+ patienttype;
		String str = NetWorkRequest.doPost("", jsonString);
//		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
		LogCat.say(""+str);
		ZhuCheAtyCls returnedJson = JSON.parseObject(str, ZhuCheAtyCls.class);
		return returnedJson;
	}

	private Object postSendVCode(String string, String phone) {		
		String jsonString = NetWorkConst.HEAD+string;
		jsonString = jsonString + ad("mobile")+ phone;
		String str = NetWorkRequest.doPost("", jsonString);
		LogCat.say(""+str);
//		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
		SendCodeCls returnedJson = JSON.parseObject(str, SendCodeCls.class);
		return returnedJson;
	}

	//��½
	private Object postServerHandler_UserLogin(String string,String account,String psd) {		
		String jsonString = NetWorkConst.HEAD+string;
		jsonString = jsonString + ad("mobile")+ account+ad("password")+psd;
		String str = NetWorkRequest.doPost("", jsonString);
//		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
		LogCat.say(str);
		SendCodeCls returnedJson = JSON.parseObject(str, SendCodeCls.class);
		return returnedJson;
	}

	//    �ճ���ҩ�ӿ�
	private ArrayList<MedicineCls> postGeDayUserMedicineList(String string) {		
		String jsonString = NetWorkConst.HEAD+string;
		String str = NetWorkRequest.doPost("", jsonString);
		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
		return returnedJson;
	}

	//���°汾
	private String postGeVersionList(String string) {		
		String jsonString = NetWorkConst.HEAD+string;
		String versionCode = GetVersionCode.getVersionCode(mContext);
		jsonString = jsonString + ad("DeviceType")+ "android"+ad("VersionCode")+versionCode;
		String str = NetWorkRequest.doPost("", jsonString);
//		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
//		return returnedJson;
		return str;
	}

	//   ACT�ӿ�
	private String postSendUserACT(String string) {
		// TODO �Զ����ɵķ������
		return null;
	}

	//    PEF�����ӿ�
	private String postSendUserPef(String string) {
		// TODO �Զ����ɵķ������
		return null;
	}

	//    ������ҩ�ӿ�
	private ArrayList<MedicineCls> postGeEmergencyMedicineList(String string) {
		String jsonString = NetWorkConst.HEAD+string;
		String str = NetWorkRequest.doPost("", jsonString);
		ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
		return returnedJson;
	}
	//	");//����
	//	personalDetaileCls.setSex(strToInt(rdoBtnMan));//���� Ů��
	//	personalDetaileCls.setBirthday(birthdayEt.getText()+"");//��������
	//	personalDetaileCls.setHeight(heightEt.getText()+"");//���
	//	personalDetaileCls.setWeight(weightEt.getText()+"");//����
	//	personalDetaileCls.setIsallergy(strToInt(rdoBtnAllergyYes));//�Ƿ����
	//	personalDetaileCls.setMedicalinsurance(hisSp.getSelectedItemPosition());//ҽ������
	//	personalDetaileCls.setContactusername(contactName);//��ϵ������
	//	personalDetaileCls.setContactmobile(contactNumber);//��ϵ�˵绰
	//	personalDetaileCls.setArea(addressEt.getText()+"");//��ַ
	//	personalDetaileCls.setIssmokinghistory(strToInt(rdoBtnSmokeYes));//����
	//	personalDetaileCls.setSmokingyear(somkeYearEt.getText()+"");//����
	//	personalDetaileCls.setFev1(fev1Et.getText()+"");/
	//APP���ýӿ�����������Ϣ����
	private Object postAddUser(String method,PersonalDetaileCls personalDetaileCls) {
		String jsonString = NetWorkConst.HEAD+method;
//		SharedPreferences sp = MyApp.getContext().getContext().getSharedPreferences("Detail", MyApp.getContext().getContext().MODE_PRIVATE);
//		String cls = sp.getString("detail", null);
//		PersonalDetaileCls personalDetaileCls = JSON.parseObject(cls, PersonalDetaileCls.class);
		jsonString = jsonString+ad("username")+personalDetaileCls.getUsername()+
				ad("userid")+personalDetaileCls.getUserid()+
				ad("sex")+personalDetaileCls.getSex()+
				ad("birthday")+personalDetaileCls.getBirthday()+
				ad("height")+personalDetaileCls.getHeight()+
				ad("weight")+personalDetaileCls.getWeight()+
				ad("isallergy")+personalDetaileCls.getIsallergy()+
				ad("medicalinsurance")+personalDetaileCls.getMedicalinsurance()+
				ad("contactusername")+personalDetaileCls.getContactusername()+
				ad("contactmobile")+personalDetaileCls.getContactmobile()+
				ad("area")+personalDetaileCls.getArea()+
				ad("issmokinghistory")+personalDetaileCls.getIssmokinghistory()+
				ad("smokingyear")+personalDetaileCls.getSmokingyear()+
				ad("fev1")+personalDetaileCls.getFev1()+
				ad("patienttype")+personalDetaileCls.getPatienttype();
		String str = NetWorkRequest.doPost("", jsonString);
		//		if(str!=null&&str.length()!=0)
		LogCat.say(str);
		ReturnedCls returnedCls = JSON.parseObject(str, ReturnedCls.class);
		return returnedCls;
	}
	private String ad( String  a){
		return "&"+a+"=";
	}

}
