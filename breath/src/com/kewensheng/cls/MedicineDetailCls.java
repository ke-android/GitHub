package com.kewensheng.cls;


import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MedicineDetailCls implements Serializable{
//	"MedicineID": 1,"MedicineID": 1,
//  "DoseSpecification": "80/4.5",
//  "DoseUnit": "¦Ìg",
//  "DayDosage": "2¡¢4 "
	@JSONField(name = "MedicineID")
	private String medicineID;
	@JSONField(name = "DoseSpecification")
	private String doseSpecification;
	@JSONField(name = "DoseUnit")
	private String doseUnit;
	@JSONField(name = "DayDosage")
	private String dayDosage;
	public String getMedicineID() {
		return medicineID;
	}
	public void setMedicineID(String a) {
		medicineID = a;
	}
	public String getDoseSpecification() {
		return doseSpecification;
	}
	public void setDoseSpecification(String a) {
		doseSpecification = a;
	}
	public String getDoseUnit() {
		return doseUnit;
	}
	public void setDoseUnit(String a) {
		doseUnit = a;
	}
	public String getDayDosage() {
		return dayDosage;
	}
	public void setDayDosage(String a) {
		dayDosage = a;
	}
}
