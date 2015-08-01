package com.kewensheng.cls;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class PersonalDetaileCls implements Serializable{
	//	姓名：username 
	//	出生日期： birthday
	//	身高： height
	//	体重： weight
	//	是否过敏： isallergy
	//	联系人姓名： contactusername
	//	联系人手机： contactmobile
	//	详细地址： area
	//	医保类型： medicalinsurance
	//	是否有吸烟史： issmokinghistory
	//	FEV1值： fev1
	//	患者类型： patienttype 暂定：1 哮喘2 慢阻肺，3 肺癌，4 其它;
	@JSONField(name = "userid")
	private int userid;
	@JSONField(name = "username")
	private String username;
	@JSONField(name = "sex")
	private String sex;
	@JSONField(name = "birthday")
	private String birthday;
	@JSONField(name = "height")
	private String height;
	@JSONField(name = "weight")
	private String weight;
	@JSONField(name = "isallergy")
	private int isallergy;
	@JSONField(name = "contactusername")
	private String contactusername;
	@JSONField(name = "contactmobile")
	private String contactmobile;
	@JSONField(name = "area")
	private String area;
	@JSONField(name = "medicalinsurance")
	private int medicalinsurance;

	@JSONField(name = "issmokinghistory")
	private int issmokinghistory;
	@JSONField(name = "smokingyear")
	private String smokingyear;
	@JSONField(name = "fev1")
	private String fev1;
	@JSONField(name = "pef")
	private float pef;
	@JSONField(name = "patienttype")
	private String patienttype;
	

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSmokingyear() {
		return smokingyear;
	}
	public void setSmokingyear(String smokingyear) {
		this.smokingyear = smokingyear;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public int getIsallergy() {
		return isallergy;
	}
	public void setIsallergy(int isallergy) {
		this.isallergy = isallergy;
	}
	public String getContactusername() {
		return contactusername;
	}
	public void setContactusername(String contactusername) {
		this.contactusername = contactusername;
	}
	public String getContactmobile() {
		return contactmobile;
	}
	public void setContactmobile(String contactmobile) {
		this.contactmobile = contactmobile;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getMedicalinsurance() {
		return medicalinsurance;
	}
	public void setMedicalinsurance(int medicalinsurance) {
		this.medicalinsurance = medicalinsurance;
	}
	public int getIssmokinghistory() {
		return issmokinghistory;
	}
	public void setIssmokinghistory(int issmokinghistory) {
		this.issmokinghistory = issmokinghistory;
	}
	public String getFev1() {
		return fev1;
	}
	public void setFev1(String string) {
		this.fev1 = string;
	}
	public float getPef() {
		return pef;
	}
	public void setPef(float pef) {
		this.pef = pef;
	}
	public String getPatienttype() {
		return patienttype;
	}
	public void setPatienttype(String patienttype) {
		this.patienttype = patienttype;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
