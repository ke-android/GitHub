package com.kewensheng.cls;


import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CopyOfMedicineDetailCls implements Serializable{
//	"MedicineID": 1,
//    "MedicineName": "�����ε¸�Ī����",
//    "ProductName": "�űؿɶ���",
//    "DoseSpecification": "80/4.5,160/4.5,320/4",
//    "DoseUnit": "��g",
//    "DayDosage": "2��4 |2��4 |1��2",
//    "Status": "1",
//    "MedicineType": 1,
//    "Introduction": "�űؿɶ���",
//    "MedicineTypeName": "ά����ҩ",
//    "DoseList": [
//        {
//            "MedicineID": 1,
//            "DoseSpecification": "80/4.5",
//            "DoseUnit": "��g",
//            "DayDosage": "2��4 "
	@JSONField(name = "MedicineID")
	private String MedicineID;
	@JSONField(name = "MedicineName")
	private String MedicineName;
	@JSONField(name = "ProductName")
	private String ProductName;
	@JSONField(name = "DoseSpecification")
	private String DoseSpecification;
	@JSONField(name = "DoseUnit")
	private String DoseUnit;
	@JSONField(name = "DayDosage")
	private String DayDosage;
	@JSONField(name = "Status")
	private String Status;
	@JSONField(name = "MedicineType")
	private String MedicineType;
	@JSONField(name = "Introduction")
	private String Introduction;
	@JSONField(name = "MedicineTypeName")
	private String MedicineTypeName;
	@JSONField(name = "DoseList")
	private String DoseList;
	
}
