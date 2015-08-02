package com.kewensheng.cls;

import com.alibaba.fastjson.annotation.JSONField;

public class XieYiAtyCls {
@JSONField(name = "aboutme")
private String aboutme;

public String getAboutme() {
	return aboutme;
}

public void setAboutme(String aboutme) {
	this.aboutme = aboutme;
}

}
