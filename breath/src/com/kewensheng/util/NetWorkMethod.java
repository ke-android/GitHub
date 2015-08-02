package com.kewensheng.util;

public class NetWorkMethod {
	public static String uploadDetail(){
//		private SharedPreferences sp;
		//	 sp = ctx.getSharedPreferences("Detail", MODE_PRIVATE);
		//	 String str = sp.getString("detail", null);
		//	 PersonalDetaileCls personalDetaileCls = JSON.parseObject(str, PersonalDetaileCls.class);
		//	String baseurl = "http://210.21.34.4:9100/api/APPService.ashx?cmd=NJServerHandler.AddUser";
		//	String sendurl = baseurl+ad("username")+personalDetaileCls.getUsername()+
		//					 ad("birthday")+	personalDetaileCls.getBirthday()+
		//					 ad("height") + personalDetaileCls.getHeight()+
		//					 ad("weight") + personalDetaileCls.getWeight()+
		//					 ad("isallergy") + personalDetaileCls.getIsallergy()+
		//					 ad("contactusername") + personalDetaileCls.getContactusername()+
		//					 ad("contactmobile") + personalDetaileCls.getContactmobile()+
		//					 ad("area") + personalDetaileCls.getArea()+
		//					 ad("medicalinsurance") + personalDetaileCls.getMedicalinsurance()+
		//					 ad("issmokinghistory") + personalDetaileCls.getIssmokinghistory()+
		//					 ad("fev1") + personalDetaileCls.getFev1()+
		//					 ad("patienttype") + personalDetaileCls.getPatienttype();
		return null;
	}
	public static String uploadPef(){
//		command方法：cmd=NJServerHandler.SendUserPef 这个参数是固定的
//				患者手机号码：mobile
//				吹波日期：senddatetime
//				PEF数据浮点值： pefvalue
		
		return null;
	}
	public static String getMedicine(){
		String url = "http://210.21.34.4:9100/api/APPService.ashx?cmd=NJServerHandler.GeMedicineList";
		return null;
	}
	private String ad( String  a){
		return "&"+a+"=";
	}
}
