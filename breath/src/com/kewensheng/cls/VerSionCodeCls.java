package com.kewensheng.cls;

import com.alibaba.fastjson.annotation.JSONField;

public class VerSionCodeCls {
	@JSONField(name = "ID")
	private int id;
	@JSONField(name = "VersionName")
	private String versionName;
	@JSONField(name = "VersionCode")
	private String versionCode;
	@JSONField(name = "PackageName")
	private String packageName;
	@JSONField(name = "IconPath")
	private String iconPath;
	@JSONField(name = "ArchiveFilePath")
	private String archiveFilePath;
	@JSONField(name = "CreateDatetime")
	private String createDatetime;
	@JSONField(name = "VersionContent")
	private String versionContent;
	@JSONField(name = "DeviceType")
	private String deviceType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getArchiveFilePath() {
		return archiveFilePath;
	}
	public void setArchiveFilePath(String archiveFilePath) {
		this.archiveFilePath = archiveFilePath;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getVersionContent() {
		return versionContent;
	}
	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
}
