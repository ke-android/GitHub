package com.kewensheng.cls;

import com.alibaba.fastjson.annotation.JSONField;

public class DayRecord {
	@JSONField(name = "dayRecordCls")
	private DayRecordCls dayRecordCls;
	@JSONField(name = "recordActCls")
	private DayRecordActCls recordActCls;

}
