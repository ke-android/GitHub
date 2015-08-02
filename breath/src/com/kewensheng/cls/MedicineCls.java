package com.kewensheng.cls;


import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MedicineCls implements Serializable{
//	"MedicineID": 1,
//    "MedicineName": "布地奈德福莫特罗",
//    "ProductName": "信必可都保",
//    "DoseSpecification": "80/4.5,160/4.5,320/4",
//    "DoseUnit": "μg",
//    "DayDosage": "2、4 |2、4 |1、2",
//    "Status": "1",
//    "MedicineType": 1,
//    "Introduction": "信必可都保",
//    "MedicineTypeName": "维持用药",
//    "DoseList": [
//        {
//            "MedicineID": 1,
//            "DoseSpecification": "80/4.5",
//            "DoseUnit": "μg",
//            "DayDosage": "2、4 "
	@JSONField(name = "MedicineID")
	private String medicineID;
	@JSONField(name = "MedicineName")
	private String medicineName;
	@JSONField(name = "ProductName")
	private String productName;
	@JSONField(name = "DoseSpecification")
	private String doseSpecification;
	@JSONField(name = "DoseUnit")
	private String doseUnit;
	@JSONField(name = "DayDosage")
	private String dayDosage;
	@JSONField(name = "Status")
	private String status;
	@JSONField(name = "MedicineType")
	private String medicineType;
	@JSONField(name = "Introduction")
	private String introduction;
	@JSONField(name = "MedicineTypeName")
	private String medicineTypeName;
	@JSONField(name = "DoseList")
	private String doseList;
	
	public String getMedicineID() {
		return medicineID;
	}
	public void setMedicineID(String a) {
		medicineID = a;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String a) {
		medicineName = a;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String a) {
		productName = a;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String a) {
		status = a;
	}
	public String getMedicineType() {
		return medicineType;
	}
	public void setMedicineType(String a) {
		medicineType = a;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String a) {
		introduction = a;
	}
	public String getMedicineTypeName() {
		return medicineTypeName;
	}
	public void setMedicineTypeName(String a) {
		medicineTypeName = a;
	}
	public String getDoseList() {
		return doseList;
	}
	public void setDoseList(String a) {
		doseList = a;
	}
	
}
