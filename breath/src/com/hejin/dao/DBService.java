package com.hejin.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kewensheng.cls.DayRecordActCls;
import com.kewensheng.cls.DayRecordCls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBService {
	
	private static String tableName="PAITENT_DAILY";
	private LocalBase localbase;
	
	public DBService(Context context){
		this.localbase = new LocalBase(context);
	}
	
	//д���¼
	public boolean putPaitentData(DayRecordCls data){
		SQLiteDatabase db = null;
		Cursor c = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sf.format(new Date());
			db = localbase.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("WAKEUP", data.getNightremind());//ҹ�����
			values.put("LIMITEDACT", data.getActivityhinder());//�����
			values.put("BREATHING", data.getBreathhold());//��������
			values.put("DYSPNEA", data.getGasp());//��Ϣ
			values.put("COUGH", data.getCough());//����
			c = db.query(tableName, null, null, null, null, null, "id");
			if(c.moveToFirst()){
				if(c.getString(c.getColumnIndex("DATE")).equals(today)){
					//�Ѵ��ڵ�������
					int id = c.getInt(c.getColumnIndex("ID"));
//					db.update("PAITENT_DAILY", values, "id = "+id, )
					db.update(tableName, values, "ID =?", new String[] {id+""});
					return true;
				}
			}else{
				//�����ڵ�������
				//���뵱�������
				values.put("DATE", today);
				db.insert(tableName, null, values);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			c.close();
			db.close();
		}
		
		return false;
	}
	
	//��ȡ��¼
	public List<DayRecordCls> loadPaitentData(){
		SQLiteDatabase db = null;
		Cursor c = null;
//		DayRecordCls result = new DayRecordCls();
		List<DayRecordCls> result = new ArrayList<DayRecordCls>();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sf.format(new Date());
			db = localbase.getReadableDatabase();
			c = db.query(tableName, null, null, null, null, null, "ID");
			while(c.moveToNext()){
				if(!c.getString(c.getColumnIndex("DATE")).equals(today)){
					//���ڷ��ϣ�
					DayRecordCls temp = new DayRecordCls();
					temp.setNightremind(c.getInt(c.getColumnIndex("WAKEUP")));
					temp.setActivityhinder(c.getInt(c.getColumnIndex("LIMITEDACT")));
					temp.setBreathhold(c.getInt(c.getColumnIndex("BREATHING")));
					temp.setCough(c.getInt(c.getColumnIndex("COUGH")));
					temp.setGasp(c.getInt(c.getColumnIndex("DYSPNEA")));
					result.add(temp);
					
				}
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			c.close();
			db.close();
		}
		return null;
	}
	
	public boolean deletePaitentData(String date){
		SQLiteDatabase db =null;
		try {
			db = localbase.getWritableDatabase();
			db.delete(tableName, "DATE =?", new String[]{date});
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			db.close();
		}
		return false;
		
	}

}
