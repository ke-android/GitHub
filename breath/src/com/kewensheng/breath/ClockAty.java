package com.kewensheng.breath;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kewensheng.cls.ClockCls;
import com.kewensheng.cls.MedicineCls;
import com.kewensheng.cls.MedicineDetailCls;
import com.kewensheng.controller.IdiyMessage;


public class ClockAty extends BaseAcitivity implements View.OnClickListener{
	private ListView lv;
	private ClockAtyAdp mAdp;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_clock);
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText("闹钟");				
		findViewById(R.id.back).setVisibility(View.GONE);
		lv = (ListView) findViewById(R.id.exList);
		mAdp = new ClockAtyAdp(this);
		lv.setAdapter(mAdp);
		findViewById(R.id.image).setOnClickListener(this);
		getData();
	}
	
	@Override
	protected void onResume() {
		getData();
		super.onResume();
	}
	
	private void getData(){
		sp = this.getSharedPreferences("Clock", MODE_PRIVATE);
		String clockstr = sp.getString("clock", "");
		ArrayList<ClockCls> clockCls = new ArrayList<ClockCls>();
		if(clockstr!=null&&!clockstr.equals(""))
			clockCls= (ArrayList<ClockCls>) JSON.parseArray(clockstr, ClockCls.class);
		mAdp.setData(clockCls);
	}
	@Override
	public void next(View view) {
		super.next(view);
		final String key = getIntent().getStringExtra("from");
		new AlertDialog.Builder(this).setTitle("保存")
		.setMessage("保存所做修改?")
		.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				SharedPreferences.Editor editor = sp.edit();
				ArrayList<ClockCls> a = mAdp.getData();
				String c = JSON.toJSONString(a);
				editor.putString("clock", c);
				editor.commit();
				if(key!=null&&"MainActivity".equals(key))
					finish();
				else if(key!=null &&"MedicineAty".equals(key))
					startActivity(new Intent(ClockAty.this,MainActivity.class));
			}
		}).setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(key!=null&&"MainActivity".equals(key))
					finish();
				else if(key!=null &&"MedicineAty".equals(key))
					startActivity(new Intent(ClockAty.this,MainActivity.class));				
			}
		}).show();
		
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.image:
//			startActivityForResult(new Intent(this,AddColckAty.class).putExtra("from", "ClockAty"), IdiyMessage.clock);
			startActivity(new Intent(this,AddColckAty.class).putExtra("from", "ClockAty"));
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (resultCode) {
		case IdiyMessage.clock:
			ClockCls cls = (ClockCls) intent.getSerializableExtra("data");
//			ArrayList<ClockCls> data= mAdp.getData();
//			data.add(cls);
			mAdp.addData(cls);
//			mAdp.setData(data);
			break;
		case IdiyMessage.back:
			break;
		default:
			break;
		}
	}
}

class ClockAtyAdp extends BaseAdapter{
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<ClockCls> mData;
	public ArrayList<ClockCls> getData(){
		return mData;
	}
	public ClockAtyAdp(Context c) {
		this.mContext = c;
		inflater = LayoutInflater.from(c);
		mData = new ArrayList<ClockCls>();
	}
	public void setData(ArrayList<ClockCls> clockCls) {
		mData = clockCls;
		notifyDataSetChanged();
	}
	
	public void addData(ClockCls cls){
		mData.add(cls);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ClockCls getItem(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	static class ViewHolder {
		LinearLayout layout;
		TextView text;
		TextView medicine;
		CheckBox box;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parentGroup) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_clock, parentGroup,false);
			holder = new ViewHolder();
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			holder.medicine = (TextView) convertView.findViewById(R.id.medicine);
			holder.box = (CheckBox) convertView.findViewById(R.id.notify);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(mData.get(position).getHour()+"点"+mData.get(position).getMinute()+"分");
		String str = mData.get(position).getMedicine();
		ArrayList<MedicineCls> a = (ArrayList<MedicineCls>) JSON.parseArray(str,MedicineCls.class);
		MedicineDetailCls cls = JSONObject.parseObject(a.get(0).getDoseList(), MedicineDetailCls.class);
		holder.medicine.setText(a.get(0).getMedicineName()+"/"+cls.getDoseSpecification()+"/"+cls.getDoseUnit());
		holder.layout.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				new AlertDialog.Builder(mContext).setTitle("删除闹钟")
				.setMessage("确定删除此闹钟?")
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						mData.remove(position);
						notifyDataSetChanged();
					}
				})
				.setNegativeButton("取消", null).show();
				return false;
			}
		});
		if(holder.box.isChecked())
			mData.get(position).setState(true);
		else if(!holder.box.isChecked())
			mData.get(position).setState(false);
			
		return convertView;
	}}