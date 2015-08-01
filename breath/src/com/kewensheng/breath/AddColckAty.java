package com.kewensheng.breath;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.ClockCls;
import com.kewensheng.cls.MedicineCls;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;

public class AddColckAty extends BaseAcitivity implements OnClickListener{
	private TimePicker timepicker;
	private LinearLayout layout;
	private ListView listView;
	private AddColckAtyAdp adapter;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_add_warning);	
		findViewById(R.id.back).setVisibility(View.INVISIBLE);
		TextView tv = (TextView) findViewById(R.id.title);
		tv.setText("设定提醒时间");	
		timepicker = (TimePicker) findViewById(R.id.timepicker);
		layout  = (LinearLayout) findViewById(R.id.addmedicine);
		layout.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.medicine);
		adapter = new AddColckAtyAdp(this);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				AlertDialog.Builder builder = new Builder(AddColckAty.this);
				builder.setMessage("确定删除？");

				builder.setTitle("提示");
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}

				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						adapter.delete(listView.getSelectedItemPosition());
						arg0.dismiss();
					}
				});
				builder.create().show();
				return false;
			}
		});
		sp = this.getSharedPreferences("Clock", MODE_PRIVATE);
	}

	private ArrayList<ClockCls> getData(){
		String clockstr = sp.getString("clock", "");
		ArrayList<ClockCls> clockCls = new ArrayList<ClockCls>();
		if(clockstr!=null&&!clockstr.equals(""))
			clockCls= (ArrayList<ClockCls>) JSON.parseArray(clockstr, ClockCls.class);
		return clockCls;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.addmedicine:
			startActivityForResult(new Intent(AddColckAty.this,AddClockMedicineAty.class).putExtra("from", "AddClockAty"),1);
			break;

		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO 自动生成的方法存根
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==1){
			String str = arg2.getStringExtra("data");
			ArrayList<MedicineCls> b  = (ArrayList<MedicineCls>) JSON.parseArray(str, MedicineCls.class);
			adapter.setData(b);
		}
	}

	@Override
	protected void onResume() {
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void back(View view) {
		setResult(IdiyMessage.back);
		super.back(view);
	}

	@Override
	public void next(View view) {
		super.next(view);
		//		ArrayList<ClockCls> data = getData();
		ClockCls b = new ClockCls();
		b.setState(true);
		b.setHour(timepicker.getCurrentHour());
		b.setMinute(timepicker.getCurrentMinute());
		ArrayList<MedicineCls> cls = adapter.getData();
		if(cls.size()==0){
			BaseToast.toastSay(this,"请至少选择一种药物");
		}
		else{
			String str = JSON.toJSONString(adapter.getData());
			b.setMedicine(str);
			b.setState(true);

			sp = this.getSharedPreferences("Clock", MODE_PRIVATE);
			String clockstr = sp.getString("clock", "");
			ArrayList<ClockCls> clockCls = new ArrayList<ClockCls>();
			if(clockstr!=null&&!clockstr.equals(""))
				clockCls= (ArrayList<ClockCls>) JSON.parseArray(clockstr, ClockCls.class);
			b.setName(clockCls.size());
			clockCls.add(b);
			String str1 = JSON.toJSONString(clockCls);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("clock", str1);
			editor.commit();
			//			Intent intent = new Intent();
			//			Bundle bundle = new Bundle();
			//			bundle.putSerializable("data", b);
			//			intent.putExtras(bundle);
			//			setResult(IdiyMessage.clock,intent);
			//		data.add(b);
			//		SharedPreferences.Editor et = sp.edit();
			//		String c = JSON.toJSONString(data);
			//		et.putString("clock", c);
			//		et.commit();


			Log.i("hj", "next()");
			Intent i = new Intent();
			i.setAction(IdiyMessage.SET_ALARM+"");
			Bundle sendBundler = new Bundle();
			sendBundler.putSerializable("data", b);
			//			int hour = timepicker.getCurrentHour();
			//			int minute = timepicker.getCurrentMinute();
			//			Calendar calendar = Calendar.getInstance();
			//			calendar.set(Calendar.HOUR_OF_DAY, hour);
			//			calendar.set(Calendar.MINUTE, minute);
			//			calendar.set(Calendar.SECOND, 0);
			//			calendar.set(Calendar.MILLISECOND, 0);
			//			bundle.putSerializable("alarm_setting",calendar);
			//			bundle.putInt("hour", hour);
			//			bundle.putInt("minute", minute);
			//			bundle.putString("alarm_name", "");
			i.putExtras(sendBundler);
			sendBroadcast(i);
			finish();
		}
	}

	//	@Override
	//	protected void onActivityResult(int request, int arg1, Intent arg2) {
	//		super.onActivityResult(request, arg1, arg2);
	//		switch (request) {
	//		case Mark.clock:
	//			String name = arg2.getStringExtra("name");
	//			String size = arg2.getStringExtra("size");
	//			String unit = arg2.getStringExtra("unit");
	//			MedicineCls a = new MedicineCls();
	////			a.setName(name);
	////			a.setSize(size);
	////			a.setUnit(unit);
	////			ArrayList<MedicineCls> arrayList = new ArrayList<MedicineCls>();
	////			arrayList.add(a);
	//			adapter.setData(a);
	//			break;
	//
	//		default:
	//			break;
	//		}
	//	}

}
class AddColckAtyAdp extends BaseAdapter{
	LayoutInflater inflater;
	private Context mContext;
	private ArrayList<MedicineCls> mStr;
	public void delete(int i){
		mStr.remove(i+1);
		notifyDataSetChanged();
	}

	public ArrayList<MedicineCls> getData(){
		return mStr;
	}

	public void setData(ArrayList<MedicineCls> b){
		mStr = b;
		notifyDataSetChanged();
	}

	public AddColckAtyAdp(Context c) {
		this.mContext = c;
		mStr = new ArrayList<MedicineCls>();
		inflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return mStr.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mStr.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	static class ViewHolder {
		TextView text;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_tv, arg2,false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//		holder.text.setText("   "+mStr.get(position).getName()+"/"+mStr.get(position).getSize()+"/"+mStr.get(position).getUnit());
		holder.text.setText(mStr.get(position).getMedicineName()+"("+mStr.get(position).getProductName()+")");
		return convertView;	
	}

}