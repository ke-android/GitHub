package com.kewensheng.cls;


import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ClockCls implements Serializable{
	@JSONField(name = "name")
	private int name;
	@JSONField(name = "hour")
	private int hour;
	@JSONField(name = "minute")
	private int minute;
	@JSONField(name = "medicine")
	private String medicine;
	@JSONField(name = "state")
	private Boolean state;
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	
}
