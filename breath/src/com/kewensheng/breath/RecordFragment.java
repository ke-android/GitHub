package com.kewensheng.breath;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kewensheng.cls.ClockCls;
import com.kewensheng.cls.MedicineCls;
import com.kewensheng.cls.PersonalDetaileCls;
import com.kewensheng.tool.LogCat;
import com.kewensheng.tool.PersonalTool;
import com.kewensheng.tool.RecorShowDialog;

public class RecordFragment extends Fragment implements OnClickListener,OnCheckedChangeListener{
	private RecorShowDialog mRecorShowDialog;
	private Context context;
	private ListView listView;
	private RecordFragmentAdp adp;
	private CheckBox nightremindBtn;
	private CheckBox activityhinderBtn;
	private CheckBox coughBtn;
	private CheckBox gaspBtn;
	private CheckBox breathholdBtn;
	private Button controlmedicineBtn;
	private ImageView urgencymedicineBtn;
	private Button pefBtn;
	private Button xiugaiBtn;
	private TextView tv;

	//pef记录界面
	private RelativeLayout mNormalLayout;
	private RelativeLayout mClickLayout;
	private float pef;
	private PersonalTool mTool;
	private PersonalDetaileCls cls;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_record, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();

		nightremindBtn = (CheckBox) getView().findViewById(R.id.nightremind);
		nightremindBtn.setOnClickListener(this);

		//活动受限
		activityhinderBtn = (CheckBox) getView().findViewById(R.id.activityhinder);
		activityhinderBtn.setOnCheckedChangeListener(this);
		//		activityhinderBtn.setOnClickListener(this);

		//咳嗽
		coughBtn = (CheckBox) getView().findViewById(R.id.cough);
		coughBtn.setOnCheckedChangeListener(this);
		//		coughBtn.setOnClickListener(this);

		//喘息
		gaspBtn = (CheckBox) getView().findViewById(R.id.gasp);
		gaspBtn.setOnCheckedChangeListener(this);
		//		gaspBtn.setOnClickListener(this);

		//呼吸困难
		breathholdBtn = (CheckBox) getView().findViewById(R.id.breathhold);
		breathholdBtn.setOnCheckedChangeListener(this);
		//		breathholdBtn.setOnClickListener(this);

		//		controlmedicineBtn = (Button) getView().findViewById(R.id.controlmedicine);
		//		controlmedicineBtn.setVisibility(View.GONE);
		//		controlmedicineBtn.setOnClickListener(this);

		urgencymedicineBtn = (ImageView) getView().findViewById(R.id.urgencymedicine);
		urgencymedicineBtn.setOnClickListener(this);

		//pef界面
		pefBtn = (Button) getView().findViewById(R.id.pef);
		pefBtn.setOnClickListener(this);
		xiugaiBtn = (Button) getView().findViewById(R.id.xiugaipef);
		xiugaiBtn.setOnClickListener(this);
//		private RelativeLayout mNormalLayout;
//		private RelativeLayout mClickLayout;
		tv  = (TextView) getView().findViewById(R.id.textView1);
		//点击以后显示不同的界面
		mNormalLayout = (RelativeLayout) getView().findViewById(R.id.normal);
		mClickLayout = (RelativeLayout) getView().findViewById(R.id.click);

		listView = (ListView) getView().findViewById(R.id.medicineremind);
		adp = new RecordFragmentAdp(getActivity());
		listView.setAdapter(adp);
		mTool = new PersonalTool(getActivity());
		pef = mTool.getPef();
		mRecorShowDialog = new RecorShowDialog(getActivity());
		refreshData();
	}
	@Override
	public void onResume() {
		super.onResume();
		refreshData();
	}
	private void refreshData(){
		Context ctx = getActivity();       
		SharedPreferences sp = ctx.getSharedPreferences("Clock", getActivity().MODE_PRIVATE);
		String clockcontent = sp.getString("clock", "");
		if(clockcontent!=null&&!clockcontent.equals("")){
			ArrayList<ClockCls> a = (ArrayList<ClockCls>) JSONObject.parseArray(clockcontent, ClockCls.class);
			adp.refreshData(a);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nightremind:
			LayoutInflater inflater = LayoutInflater.from(context);  
			final View textEntryView = inflater.inflate(  
					R.layout.dialoglayoutspinner, null);  
			final Spinner spinner=(Spinner)textEntryView.findViewById(R.id.spinner);  
			String[] data = {"0次","1次","2次","3次","4次","5次","更多"};
			final ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,data);  
			spinner.setAdapter(adapter);
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);  
			builder.setCancelable(false);  
			builder.setIcon(R.drawable.ic_launcher);  
			builder.setTitle("Title");  
			builder.setView(textEntryView);  
			builder.setPositiveButton("确认",  
					new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton) {  
					saveInt("nightremind",spinner.getSelectedItemPosition());
					if(spinner.getSelectedItemPosition()==0)
						nightremindBtn.setChecked(false);
					//					nightremindBtn.setText("夜间提醒"+"("+adapter.getItem(spinner.getSelectedItemPosition())+")");
					else{
						nightremindBtn.setText(adapter.getItem(spinner.getSelectedItemPosition())+"");
					}
					dialog.dismiss();
				}


			});  
			builder.setNegativeButton("取消",  
					new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton) {  
					dialog.dismiss();
				}  
			});  
			builder.show();  
			break;
			//		case R.id.activityhinder:
			//			AlertDialog.Builder builder01 = new AlertDialog.Builder(context);  
			//			builder01.setIcon(R.drawable.ic_launcher);  
			//			builder01.setTitle("Title");  
			//			builder01.setMessage("今日有无活动受限?");  
			//			builder01.setPositiveButton("有",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("activityhinder",true);
			//					activityhinderBtn.setText("活动受限"+"(有)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder01.setNegativeButton("无",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("activityhinder",false);
			//					activityhinderBtn.setText("活动受限"+"(否)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder01.show();  
			//			break;
			//		case R.id.cough:
			//			AlertDialog.Builder builder11 = new AlertDialog.Builder(context);  
			//			builder11.setIcon(R.drawable.ic_launcher);  
			//			builder11.setTitle("Title");  
			//			builder11.setMessage("今日有无咳嗽?");  
			//			builder11.setPositiveButton("有",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("cough",true);
			//					coughBtn.setText("咳嗽"+"(有)");
			//
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder11.setNegativeButton("无",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("cough",false);
			//					coughBtn.setText("咳嗽"+"(否)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder11.show(); 
			//			break;
			//		case R.id.gasp:
			//			AlertDialog.Builder builder111 = new AlertDialog.Builder(context);  
			//			builder111.setIcon(R.drawable.ic_launcher);  
			//			builder111.setTitle("Title");  
			//			builder111.setMessage("今日有无喘息?");  
			//			builder111.setPositiveButton("有",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("gasp",true);
			//					gaspBtn.setText("喘息"+"(有)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder111.setNegativeButton("无",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("gasp",false);
			//					gaspBtn.setText("喘息"+"(否)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder111.show(); 
			//			break;
			//		case R.id.breathhold:
			//			AlertDialog.Builder builder1111 = new AlertDialog.Builder(context);  
			//			builder1111.setIcon(R.drawable.ic_launcher);  
			//			builder1111.setTitle("Title");  
			//			builder1111.setMessage("今日有无憋气?");  
			//			builder1111.setPositiveButton("有",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("breathhold",true);
			//					breathholdBtn.setText("憋气"+"(有)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder1111.setNegativeButton("无",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveBoolean("breathhold",false);
			//					breathholdBtn.setText("憋气"+"(否)");
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder1111.show(); 
			//			break;
			//		case R.id.controlmedicine:
			//			LayoutInflater inflater1 = LayoutInflater.from(context);
			//	        final View editView1 = inflater1.inflate(  
			//	                R.layout.dialoglayoutedittext, null);  
			//	        final EditText edtInput=(EditText)editView1.findViewById(R.id.edtInput);  
			////			String[] data = {"0次","1次","2次","3次","4次","5次","更多"};
			////	        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,data);  
			////	        spinner.setAdapter(adapter);
			//	        final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);  
			//	        builder1.setCancelable(false);  
			//	        builder1.setIcon(R.drawable.ic_launcher);  
			//	        builder1.setTitle("请输入您今日使用控制药物的次数");  
			//	        builder1.setView(editView1);  
			//	        builder1.setPositiveButton("确认",  
			//	                new DialogInterface.OnClickListener() {  
			//	                    public void onClick(DialogInterface dialog, int whichButton) {  
			//	                    	saveString("controlmedicine", edtInput.getText()+"");
			//	                    	dialog.dismiss();
			//	                    }
			//
			//						 
			//	                });  
			//	        builder1.setNegativeButton("取消",  
			//	                new DialogInterface.OnClickListener() {  
			//	                    public void onClick(DialogInterface dialog, int whichButton) {  
			//	                    	dialog.dismiss();
			//	                    }  
			//	                });  
			//	        builder1.show();  
			//			break;
		case R.id.urgencymedicine:
			//			LayoutInflater inflater2 = LayoutInflater.from(context);
			//			final View editView2 = inflater2.inflate(  
			//					R.layout.dialog_edittext, null);  
			//			final EditText edtInput2=(EditText)editView2.findViewById(R.id.edtInput);  
			//			//			String[] data = {"0次","1次","2次","3次","4次","5次","更多"};
			//			//	        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,data);  
			//			//	        spinner.setAdapter(adapter);
			//			final AlertDialog.Builder builder2 = new AlertDialog.Builder(context);  
			//			builder2.setCancelable(false);  
			//			builder2.setIcon(R.drawable.ic_launcher);  
			//			builder2.setTitle("请输入您今日使用紧急药物的次数");  
			//			builder2.setView(editView2);  
			//			builder2.setPositiveButton("确认",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					saveString("urgencymedicine", edtInput2.getText()+"");
			//					dialog.dismiss();
			//				}
			//
			//
			//			});  
			//			builder2.setNegativeButton("取消",  
			//					new DialogInterface.OnClickListener() {  
			//				public void onClick(DialogInterface dialog, int whichButton) {  
			//					dialog.dismiss();
			//				}  
			//			});  
			//			builder2.show();  
			//			break;

			final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.dialog_edittext);
			TextView textView = (TextView) window.findViewById(R.id.title);
			textView.setText("请输入您今日使用紧急药物的次数");
			final EditText editText = (EditText) window.findViewById(R.id.edit);
			Button button = (Button) window.findViewById(R.id.close);
			button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					alertDialog.dismiss();
				}
			});
			Button button2 = (Button) window.findViewById(R.id.doctor);
			button2.setText("确认");
			button2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					saveString("urgencymedicine", editText.getText()+"");
					alertDialog.dismiss();
				}
			});
			break;
		case R.id.pef:
//			LayoutInflater inflater3 = LayoutInflater.from(context);
//			final View editView3 = inflater3.inflate(  
//					R.layout.dialoglayoutedittext, null);  
//			final EditText edtInput3=(EditText)editView3.findViewById(R.id.edtInput);  
//			final AlertDialog.Builder builder3 = new AlertDialog.Builder(context);  
//			builder3.setCancelable(false);  
//			builder3.setIcon(R.drawable.ic_launcher);  
//			builder3.setTitle("请输入此次pef值");  
//			builder3.setView(editView3);  
//			builder3.setPositiveButton("确认",  
//					new DialogInterface.OnClickListener() {  
//				public void onClick(DialogInterface dialog, int whichButton) {  
//					saveString("pef", edtInput3.getText()+"");
//					tv.setText("您今日的pef值为"+edtInput3.getText());
//					dialog.dismiss();
//					remind(edtInput3.getText()+"");
//				}
//			});  
//			builder3.setNegativeButton("取消",  
//					new DialogInterface.OnClickListener() {  
//				public void onClick(DialogInterface dialog, int whichButton) {  
//					dialog.dismiss();
//				}  
//			});  
//			builder3.show();  
//			break;


			final AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity()).create();
			alertDialog2.show();
			Window window2 = alertDialog2.getWindow();
			window2.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			window2.setContentView(R.layout.dialog_edittext);
			TextView textView2 = (TextView) window2.findViewById(R.id.title);
			textView2.setText("请输入此次pef值");
			final EditText editText2 = (EditText) window2.findViewById(R.id.edit);
			Button button4 = (Button) window2.findViewById(R.id.close);
			button4.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					alertDialog2.dismiss();
				}
			});
			Button button3 = (Button) window2.findViewById(R.id.doctor);
			button3.setText("确认");
			button3.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					saveString("pef", editText2.getText()+"");
					tv.setText(editText2.getText()+"L/min");
					mNormalLayout.setVisibility(View.GONE);
					mClickLayout.setVisibility(View.VISIBLE);
					alertDialog2.dismiss();
					remind(editText2.getText()+"");
				}
			});
			break;
		case R.id.xiugaipef:
			final AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity()).create();
			alertDialog3.show();
			Window window3 = alertDialog3.getWindow();
			window3.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			window3.setContentView(R.layout.dialog_edittext);
			TextView textView3  = (TextView) window3.findViewById(R.id.title);
			textView3.setText("修改pef值");
			final EditText editText3 = (EditText) window3.findViewById(R.id.edit);
			Button button5 = (Button) window3.findViewById(R.id.close);
			button5.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					alertDialog3.dismiss();
				}
			});
			Button button6 = (Button) window3.findViewById(R.id.doctor);
			button6.setText("确认");
			button6.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					alertDialog3.dismiss();
				}
			});
			break;
			
		default:
			break;
		}
	}

	private void remind(String string) {
		float newPef = Float.parseFloat(string);
		float change = 0;
		change = newPef/pef;
		LogCat.say("RecordFragment", "new"+string+">>>>now"+change+"pef"+pef);
		if(change>0.8){
			mRecorShowDialog.showNormal(string);
		}
		else if(change>=0.6&&change<0.8){
			mRecorShowDialog.showWarring(string);
		}else if(change<0.6){
			mRecorShowDialog.showRisk(string);
		}
	}
	private void saveString(String key,String content) {
		SharedPreferences sp = context.getSharedPreferences("Detail", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit(); 
		editor.putString(key, content);
		editor.commit();
	} 
	private void saveInt(String key,int content) {
		SharedPreferences sp = context.getSharedPreferences("Detail", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit(); 
		editor.putInt(key, content);
		editor.commit();
	} 
	private void saveBoolean(String key,Boolean content) {
		SharedPreferences sp = context.getSharedPreferences("Detail", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit(); 
		editor.putBoolean(key, content);
		editor.commit();
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.nightremind:
			saveBoolean("nightremind",arg1);
			break;
		case R.id.activityhinder:
			saveBoolean("activityhinder",arg1);
			break;
		case R.id.cough:
			saveBoolean("cough",arg1);
			break;
		case R.id.gasp:
			saveBoolean("gasp",arg1);
			break;

		default:
			break;
		}
	} 
}
class RecordFragmentAdp extends BaseAdapter{
	private Context mContext;
	private ArrayList<ClockCls> mStr;
	private LayoutInflater inflater;
	public RecordFragmentAdp(Context c) {
		// TODO 自动生成的构造函数存根
		this.mContext = c;
		mStr = new ArrayList<ClockCls>();
		inflater = LayoutInflater.from(mContext);
	}
	public void refreshData(ArrayList<ClockCls> a) {
		mStr.clear();
		mStr = a;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mStr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return mStr.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}
	static class ViewHolder{
		TextView text;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.fragment_recore_item_tv, arg2,false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//		holder.text.setText(""+mStr.get(position).getMedicine().get(position).getName()+
		//				"/(规格:"+mStr.get(position).getMedicine().get(position).getSize()+")"+
		//				"/(剂量:"+mStr.get(position).getMedicine().get(position).getUnit()+")");
		String str = mStr.get(position).getMedicine();
				if("".equals(str))
		holder.text.setText(""+mStr.get(position).getHour()+"点"+mStr.get(position).getMinute()+"分");
				else{
					MedicineCls cls = JSON.parseObject(mStr.get(position).getMedicine(), MedicineCls.class);
					holder.text.setText(""+mStr.get(position).getHour()+"点"+mStr.get(position).getMinute()+"分"+cls.getMedicineName());
				}
		return convertView;	
	}

}