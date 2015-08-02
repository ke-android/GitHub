package com.kewensheng.breath;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.MedicineCls;
import com.kewensheng.cls.MedicineDetailCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;

public class AddMedicineAty extends BaseAcitivity implements IModelChangeListener{
	//名字
	private Spinner nameSp;
	private nameSpAdp nameSpAdp;
	//剂量
	private Spinner sizeSp;
	private sizeSpAdp sizeSpAdp;

	private Spinner unitSp;
	private ArrayAdapter<String> unitAdp;

	private BreathController mController;
	private SharedPreferences sp;

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.GeDayUserMedicineList_finish://日常用药
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) msg.obj;
					if(returnedJson!=null&&returnedJson.size()!=0){
						setData(returnedJson);
					}else{
						Toast.makeText(AddMedicineAty.this, "网络错误请稍后再试", Toast.LENGTH_SHORT).show();
					}
				}

				break;
			case IdiyMessage.GeEmergencyMedicineList_finish://紧急用药
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					ArrayList<MedicineCls> returnedJson = (ArrayList<MedicineCls>) msg.obj;
					if(returnedJson!=null&&returnedJson.size()!=0){
						setData(returnedJson);
					}else{
						Toast.makeText(AddMedicineAty.this, "网络错误请稍后再试", Toast.LENGTH_SHORT).show();
					}
				}

				break;
			}
		}

		private void setData(ArrayList<MedicineCls> returnedJson) {
			nameSpAdp.setData(returnedJson);
		}
	};
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_add_medicine);
		findViewById(R.id.back).setVisibility(View.INVISIBLE);
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText("选择药物");		

		nameSp = (Spinner) findViewById(R.id.name);
		nameSpAdp = new nameSpAdp(this);
		nameSp.setAdapter(nameSpAdp);
		nameSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				MedicineCls a = nameSpAdp.getItem(arg2);
				String str = nameSpAdp.getItem(arg2).getDoseList();
				sizeSpAdp.setData(nameSpAdp.getItem(arg2).getDoseList());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO 自动生成的方法存根

			}
		});

		sizeSp = (Spinner) findViewById(R.id.size);
		sizeSpAdp = new sizeSpAdp(this);
		sizeSp.setAdapter(sizeSpAdp);

		//		String[] sizedata = {"80/4.5 μg","160/4.5 μg","50/100μg"}; 
		//		sizeAdp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sizedata);

		unitSp = (Spinner) findViewById(R.id.unit);
		String[] unitdata = {"1次","2次","3次","4次","5次","6次","7次","8次"}; 
		unitAdp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,unitdata);
		unitSp.setAdapter(unitAdp);

		mController = new BreathController(this);
		mController.setIModelChangeListener(this);
		String key = getIntent().getStringExtra("from");
		if(key!=null&&"keep".equals(key))
			mController.sendAsyncMessage(IdiyMessage.GeDayUserMedicineList, "GetDayUserMedicineList");
		if(key!=null&&"urgency".equals(key))
			mController.sendAsyncMessage(IdiyMessage.GeEmergencyMedicineList, "GetEmergencyMedicineList");
	}
	@Override
	public void next(View view) {
		super.next(view);
		MedicineCls  a = nameSpAdp.getItem(nameSp.getSelectedItemPosition());
		MedicineDetailCls b = sizeSpAdp.getItem(sizeSp.getSelectedItemPosition());
		String c = JSON.toJSONString(b);
		a.setDoseList(c);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", a);
		intent.putExtras(bundle);
		
		String key = getIntent().getStringExtra("from");
		if(key!=null&&"keep".equals(key))
		setResult(IdiyMessage.keep, intent);
		else if(key!=null&&"urgency".equals(key))
		setResult(IdiyMessage.urgency, intent);
		finish();

//		SharedPreferences sp = null;
//		sp = this.getSharedPreferences("Medicine", MODE_PRIVATE);
//		String medicine = "";
//		String key = getIntent().getStringExtra("from");
//		if(key!=null&&"keep".equals(key))
//			medicine = sp.getString("keepmedicine", "");
//		else if(key!=null&&"urgency".equals(key))
//			medicine = sp.getString("urgencymedicine", "");
//		ArrayList<MedicineCls> d;
//		if(medicine!=null&&medicine.length()>0)
//			d = (ArrayList<MedicineCls>) JSON.parseArray(medicine, MedicineCls.class);
//		else
//			d = new ArrayList<MedicineCls>();
//		d.add(a);
//		String st = JSON.toJSONString(d);
//		SharedPreferences.Editor et = sp.edit();
//		if("keep".equals(key))
//			et.putString("keepmedicine", st);
//		else if("urgency".equals(key))
//			et.putString("urgencymedicine", st);
//		et.commit();
	}
	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}
	
	@Override
	public void back(View view) {
		setResult(IdiyMessage.back);
		super.back(view);
	}
}

class nameSpAdp extends BaseAdapter{
	private Context mContext;
	private ArrayList<MedicineCls> data;
	public nameSpAdp(Context c) {
		this.mContext = c;
		data = new ArrayList<MedicineCls>();
	}
	public void setData(ArrayList<MedicineCls> returnedJson) {
		data.clear();
		data = returnedJson;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public MedicineCls getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		LayoutInflater _LayoutInflater=LayoutInflater.from(mContext); 
		convertView=_LayoutInflater.inflate(R.layout.item_tv, null); 
		if(convertView!=null) 
		{ 
			TextView tv=(TextView)convertView.findViewById(R.id.tv); 
			tv.setText(data.get(position).getMedicineName()+"("+data.get(position).getProductName()+")"); 
		} 
		return convertView;	
	}
}

class sizeSpAdp extends BaseAdapter{
	private Context mContext;
	private ArrayList<MedicineDetailCls> data;
	public sizeSpAdp(Context c) {
		this.mContext = c;
		data = new ArrayList<MedicineDetailCls>();
	}
	public void setData(String doseSpecification) {
		data.clear();
		data =(ArrayList<MedicineDetailCls>) JSON.parseArray(doseSpecification, MedicineDetailCls.class);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return data.size();
	}

	@Override
	public MedicineDetailCls getItem(int arg0) {
		// TODO 自动生成的方法存根
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		LayoutInflater _LayoutInflater=LayoutInflater.from(mContext); 
		convertView=_LayoutInflater.inflate(R.layout.item_tv, null); 
		if(convertView!=null) 
		{ 
			TextView tv=(TextView)convertView.findViewById(R.id.tv); 
			tv.setText(data.get(position).getDoseSpecification()+data.get(position).getDoseUnit()); 
		} 
		return convertView;	
	}

}