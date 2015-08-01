package com.kewensheng.breath;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.MedicineCls;

public class AddClockMedicineAty extends BaseAcitivity{
	private SharedPreferences sp;
	private ListView listView;
	private AddClockMedicineAtyAdp lvAdp;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_add_clock_medicine);
		findViewById(R.id.back).setVisibility(View.GONE);
		TextView text = (TextView) findViewById(R.id.title);
		text.setText("提醒药物设置");
		listView = (ListView) findViewById(R.id.lv);
		lvAdp = new AddClockMedicineAtyAdp(this);
		listView.setAdapter(lvAdp);
		String key = getIntent().getStringExtra("from");
		if(key!=null&&"AddClockAty".equals(key)){
			sp= this.getSharedPreferences("Medicine",MODE_PRIVATE);
			String medicine = sp.getString("keepmedicine", "");
			ArrayList<MedicineCls> d = new ArrayList<MedicineCls>();
			if(medicine!=null&&medicine.length()>0)
				d = (ArrayList<MedicineCls>) JSON.parseArray(medicine, MedicineCls.class);
			lvAdp.setData(d);
		}
	}
	@Override
	protected void onResume() {
		lvAdp.notifyDataSetChanged();
		super.onResume();
	}
	@Override
	public void next(View view) {
		ArrayList<MedicineCls> b = new ArrayList<MedicineCls>();
		b=lvAdp.getCheckedMedicine();
		String string = JSON.toJSONString(b);
		setResult(1, new Intent().putExtra("data", string));
		
		finish();
		super.next(view);
	}
}
class AddClockMedicineAtyAdp extends BaseAdapter{
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<MedicineCls> data;
	private ArrayList<Boolean> isChecked;
	
	public ArrayList<MedicineCls> getCheckedMedicine(){
		ArrayList<MedicineCls> arrayList = new ArrayList<MedicineCls>();
		for (int i = 0; i < isChecked.size(); i++) {
			if(isChecked.get(i))
				arrayList.add(data.get(i));
		}
		return arrayList;
	}
	
	public AddClockMedicineAtyAdp(Context c) {
		this.mContext = c;
		inflater = LayoutInflater.from(c);
		data = new ArrayList<MedicineCls>();
		isChecked = new ArrayList<Boolean>();
	}
	
	public void setData(ArrayList<MedicineCls> d) {
		data =d;
		isChecked.clear();
		for (int i = 0; i < data.size(); i++) {
			isChecked.add(false);
		}
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return data.size();
	}

	@Override
	public MedicineCls getItem(int arg0) {
		// TODO 自动生成的方法存根
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}
	static class ViewHolder {
		CheckBox box;
		TextView text;
	}
	private void setIsChecked(int arg0, boolean arg1) {
		isChecked.set(arg0, arg1);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_clock_medicine, arg2,false);
			holder = new ViewHolder();
			holder.box = (CheckBox) convertView.findViewById(R.id.chb);
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				setIsChecked(position,arg1);
			}
		});
		holder.box.setSelected(isChecked.get(position));
		holder.text.setText(""+data.get(position).getMedicineName()+"("+data.get(position).getProductName()+")");
		return convertView;	
	}

}