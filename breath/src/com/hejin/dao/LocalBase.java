package com.hejin.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalBase extends SQLiteOpenHelper{

	public LocalBase(Context context){
		super(context, "iBreath.db", null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//初次建立的时候建立SQL数据库表格
		db.execSQL("CREATE TABLE IF NOT EXISTS PAITENT_DAILY(ID integer primary key autoincrement,DATE text,MOBILE text"
						+ ",WAKEUP integer default 0"
						+ ",LIMITEDACT integer default 0"
						+ ",BREATHING integer default 0"
						+ ",COUGH integer default 0"
						+ ",DYSPNEA integer default 0"
						+ ",TOTALSCORE int default 0)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
