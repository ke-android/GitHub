package com.kewensheng.cls;

import com.alibaba.fastjson.annotation.JSONField;

public class ReturnedCls {
//	{"success":"true","type":"1","errmsg":"���ݴ洢�ɹ�"} true��ʾ�ɹ�
	@JSONField(name= "success")
	private String success;
	@JSONField(name= "type")
	private String type;
	@JSONField(name= "errmsg")
	private String errmsg;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
}
