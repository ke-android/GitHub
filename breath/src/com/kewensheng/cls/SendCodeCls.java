package com.kewensheng.cls;

import com.alibaba.fastjson.annotation.JSONField;

public class SendCodeCls {
	@JSONField(name = "success")
	private String success;
	@JSONField(name = "type")
	private String type;
	@JSONField(name = "message")
	private String message;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
