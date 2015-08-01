package com.kewensheng.cls;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class DayRecordCls implements Serializable{
	@JSONField(name = "nightremind")
	private int nightremind;
	@JSONField(name = "activityhinder")
	private int activityhinder;
	@JSONField(name = "cough")
	private int cough;
	@JSONField(name = "gasp")
	private int gasp;
	@JSONField(name = "breathhold")
	private int breathhold;
	@JSONField(name = "controlmedicine")
	private int controlmedicine;
	@JSONField(name = "urgencymedicine")
	private int urgencymedicine;
	public int getNightremind() {
		return nightremind;
	}
	public void setNightremind(int nightremind) {
		this.nightremind = nightremind;
	}
	public int getActivityhinder() {
		return activityhinder;
	}
	public void setActivityhinder(int activityhinder) {
		this.activityhinder = activityhinder;
	}
	public int getCough() {
		return cough;
	}
	public void setCough(int cough) {
		this.cough = cough;
	}
	public int getGasp() {
		return gasp;
	}
	public void setGasp(int gasp) {
		this.gasp = gasp;
	}
	public int getBreathhold() {
		return breathhold;
	}
	public void setBreathhold(int breathhold) {
		this.breathhold = breathhold;
	}
	public int getControlmedicine() {
		return controlmedicine;
	}
	public void setControlmedicine(int controlmedicine) {
		this.controlmedicine = controlmedicine;
	}
	public int getUrgencymedicine() {
		return urgencymedicine;
	}
	public void setUrgencymedicine(int urgencymedicine) {
		this.urgencymedicine = urgencymedicine;
	}

}
