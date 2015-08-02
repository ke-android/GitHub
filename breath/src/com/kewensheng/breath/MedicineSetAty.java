package com.kewensheng.breath;
//package com.kewensheng.breath;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.zip.Inflater;
//
//import android.app.AlarmManager;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSONObject;
//import com.kewensheng.cls.ClockCls;
//import com.kewensheng.cls.MedicineCls;
//import com.kewensheng.controller.BreathController;
//import com.kewensheng.controller.IdiyMessage;
//import com.kewensheng.tool.BaseToast;
//import com.kewensheng.tool.LogCat;
//import com.kewensheng.tool.Mark;
//
//public class MedicineSetAty extends BaseAcitivity implements OnClickListener{
//	private BreathController mController;
//	private TextView title;
//	private ListView listView;
//	private MedicineSetAtyAdp adapter;
//
//	private TextView remitTv;
//	private TextView urgencyTv;
//
//	private ClockCls clockCls;
//	//private ArrayList<ClockCls> listViewData;
//	private MedicineCls remitCls;
//	private MedicineCls urgencyCls;
//	@Override
//	protected void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.aty_medicine_setting);
//		title = (TextView)findViewById(R.id.title);
//		title.setText("常用药设置");
//		remitTv = (TextView) findViewById(R.id.remitcls);
//		urgencyTv = (TextView) findViewById(R.id.urgencycls);
//		//缓解
//		findViewById(R.id.remit).setOnClickListener(this);
//		//紧急
//		findViewById(R.id.urgency).setOnClickListener(this);
//		findViewById(R.id.addremind).setOnClickListener(this);
//		listView = (ListView) findViewById(R.id.remindtime);
//		adapter = new MedicineSetAtyAdp(this);
//		listView.setAdapter(adapter);
//		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				AlertDialog.Builder builder = new Builder(MedicineSetAty.this);
//				builder.setMessage("确定删除？");
//
//				builder.setTitle("提示");
//				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//						arg0.dismiss();
//					}
//
//				});
//				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//						adapter.delete(listView.getSelectedItemPosition());
//						arg0.dismiss();
//					}
//				});
//				builder.create().show();
//				return false;
//			}
//		});
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				startActivity(new Intent(MedicineSetAty.this,ClockDetailAty.class).putExtra("data", adapter.getItemId(listView.getSelectedItemPosition())));
//			}
//		});
//		mController = new BreathController(this);
//		mController.sendAsyncMessage(IdiyMessage.GeEmergencyMedicineList);
//	}
//	@Override
//	public void onClick(View arg0) {
//		//		Intent intent = new Intent(AddRemindAty.class,AddMedicineAty.class);
//		switch (arg0.getId()) {
//		//缓解
//		case R.id.remit:
//			startActivityForResult(new Intent(MedicineSetAty.this,AddMedicineAty.class).putExtra("from", Mark.remit), Mark.remit);
//			break;
//			//紧急用药
//		case R.id.urgency:
//			startActivityForResult(new Intent(MedicineSetAty.this,AddMedicineAty.class).putExtra("from", Mark.urgency), Mark.urgency);
//			break;
//			//常用药
//		case R.id.addremind:
//			startActivityForResult(new Intent(MedicineSetAty.this,AddColckAty.class).putExtra("from", Mark.addremind), Mark.addremind);
//			break;
//
//		default:
//			break;
//		}
//	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
//		super.onActivityResult(requestCode, resultCode, arg2);
//		switch (requestCode) {
//		case Mark.remit:
//			String remitname = arg2.getStringExtra("name");
//			String remitsize = arg2.getStringExtra("size");
//			String remitunit = arg2.getStringExtra("unit");
//			remitCls = new MedicineCls();
////			remitCls.setName(remitname);
////			remitCls.setSize(remitsize);
////			remitCls.setUnit(remitunit);
//			remitTv.setText("缓解用药:"+remitname+"/规格:("+remitsize+")/剂量:("+remitunit+")");
//			break;
//		case Mark.urgency:
//			String urgencyname = arg2.getStringExtra("name");
//			String urgencysize = arg2.getStringExtra("size");
//			String urgencyunit = arg2.getStringExtra("unit");
//			urgencyCls = new MedicineCls();
////			urgencyCls.setName(urgencyname);
////			urgencyCls.setSize(urgencysize);
////			urgencyCls.setUnit(urgencyunit);
//			urgencyTv.setText("紧急用药:"+urgencyname+"/规格:("+urgencysize+")/剂量:("+urgencyunit);
//			break;
//		case Mark.addremind:
//			LogCat.say(getIntent().getStringExtra("")+"");
//			clockCls = new ClockCls();
//			clockCls.setName(arg2.getExtras().getInt("hour")+"_"+arg2.getExtras().getInt("minute"));
//			clockCls.setHour(arg2.getExtras().getInt("hour")+"");
//			clockCls.setMinute(arg2.getExtras().getInt("minute")+"");
////			clockCls.setMedicine((ArrayList<MedicineCls> )arg2.getSerializableExtra("medicine"));
//			adapter.setData(clockCls);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void next(View view) {
//		super.next(view);
//		//		Context ctx = MedicineSetAty.this;       
//		//		SharedPreferences sp = ctx.getSharedPreferences("Detail", MODE_PRIVATE);
//		//		SharedPreferences.Editor editor = sp.edit(); 
//		//		editor.putBoolean("LoginState", true);
//		//		editor.putString("Clock", adapter.getData().toString());
//		//		editor.putString("remitMedicine", remitCls.toString());
//		//		editor.putString("urgencyMedicine", urgencyCls.toString());
//		//		editor.commit();
//		ArrayList<ClockCls> a = new ArrayList<ClockCls>();
//		a = adapter.getData();
//		String str = JSONObject.toJSONString(a);
//		String remitstr = JSONObject.toJSONString(remitCls);
//		String urgencystr = JSONObject.toJSONString(urgencyCls);
//
//		Context ctx = MedicineSetAty.this;       
//		SharedPreferences sp = ctx.getSharedPreferences("Detail", MODE_PRIVATE);
//		SharedPreferences.Editor editor = sp.edit(); 
//		editor.putString("clock", str);
//		editor.putString("remitmedicine", remitstr);
//		editor.putString("urgencymedicine", urgencystr);
//		editor.commit();
//		for (int i = 0; i < a.size(); i++) {
//			setClock(a.get(i).getName(),a.get(i).getHour()+"",a.get(i).getMinute()+"");
//		}
//		BaseToast.toastSay(this, "闹钟设置成功");
//		startActivity(new Intent(MedicineSetAty.this,MainActivity.class));
//	}
//	private void setClock(String name, String hour, int minute) {
//		Calendar calendar;
//		calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.set(Calendar.HOUR_OF_DAY, hour);
//		calendar.set(Calendar.MINUTE, minute);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		Intent intent = new Intent(this,AlarmReceiver.class).putExtra("clockname", name);
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent, 0);
//		AlarmManager am;
//		/* 获取闹钟管理的实例 */
//		am = (AlarmManager) getSystemService(ALARM_SERVICE);
//		/* 设置闹钟 */
//		long i = calendar.getTimeInMillis();
//		//		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//		/* 设置周期闹 */
////		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000,pendingIntent);
//		
//		//发送设置闹钟的广播
//		Intent alarmSetIntent = new Intent();
//		alarmSetIntent.setAction(IdiyMessage.SET_ALARM+"");
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("alarm_setting", calendar);
//		bundle.putString("alarm_name", name);
//		sendBroadcast(alarmSetIntent);
//	}
//}
//class MedicineSetAtyAdp extends BaseAdapter{
//	LayoutInflater inflater;
//	public void delete(int i){
//		mStr.remove(i+1);
//		notifyDataSetChanged();
//	}
//	private Context mContext;
//	private ArrayList<ClockCls> mStr;
//
//	public ArrayList<ClockCls> getData(){
//		return mStr;
//	}
//	public void setData(ClockCls a){
//		if(mStr==null)
//			mStr = new ArrayList<ClockCls>();
//		mStr.add(a);
//		notifyDataSetChanged();
//	}
//
//	public MedicineSetAtyAdp(Context c) {
//		this.mContext = c;
//		inflater = LayoutInflater.from(mContext);
//		mStr = new ArrayList<ClockCls>();
//	}
//	@Override
//	public int getCount() {
//		return mStr.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		return mStr.get(arg0);
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		return arg0;
//	}
//	static class ViewHolder {
//		TextView text;
//	}
//	@Override
//	public View getView(int position, View convertView, ViewGroup arg2) {
//		ViewHolder holder;
//		if(convertView == null){
//			convertView = inflater.inflate(R.layout.item_tv, arg2,false);
//			holder = new ViewHolder();
//			holder.text = (TextView) convertView.findViewById(R.id.tv);
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.text.setText(""+mStr.get(position).getHour()+"点"+mStr.get(position).getMinute()+"分");
//		return convertView;	
//	}
//
//}