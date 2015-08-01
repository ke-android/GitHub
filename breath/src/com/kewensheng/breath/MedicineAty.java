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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.MedicineCls;
import com.kewensheng.controller.IdiyMessage;

public class MedicineAty extends BaseAcitivity{
	private ExpandableListView expandableListView;
	private MedicineAtyAdp medicineAtyAdp;
	private SharedPreferences sp;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		MedicineCls a;
		switch (resultCode) {
		case IdiyMessage.keep:
			a = (MedicineCls) intent.getSerializableExtra("data");
			medicineAtyAdp.addKeepMedicine(a);
			break;
		case IdiyMessage.urgency:
			a = (MedicineCls) intent.getSerializableExtra("data");
			medicineAtyAdp.addUrgencyMedicine(a);
			break;
		case IdiyMessage.back:
			break;
		default:
			break;
		}
	}
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_medicine);
		expandableListView = (ExpandableListView) findViewById(R.id.list);
		TextView text = (TextView) findViewById(R.id.title);
		text.setText("药物设置");
		findViewById(R.id.back).setVisibility(View.GONE);
		medicineAtyAdp =new MedicineAtyAdp(this,this);
		expandableListView.setAdapter(medicineAtyAdp);
		//		startActivityForResult(intent, requestCode);
		//		MedicineAty.this.startActivityForResult(intent, requestCode)
		//		expandableListView.setOnLongClickListener(new OnLongClickListener() {
		//			
		//			@Override
		//			public boolean onLongClick(View v) {
		//				int groupId = (Integer) v.getTag(R.id.a);
		//				int childId = (Integer) v.getTag(R.id.about);
		//				if(childId == -1){
		//					if(groupId == 0)
		//						//选择维持用药
		//						startActivity(new Intent(MedicineAty.this,AddMedicineAty.class).putExtra("from", "keep"));
		//					else if(groupId == 1)
		//						//选择紧急用药
		//						startActivity(new Intent(MedicineAty.this,AddMedicineAty.class).putExtra("from", "urgency"));
		//				}
		//				return false;
		//			}
		//		});
		sp = this.getSharedPreferences("Medicine", MODE_PRIVATE);
		getData();
	}

	@Override
	public void next(View view) {
		super.next(view);
		final String key = getIntent().getStringExtra("from");
		new AlertDialog.Builder(this).
		setTitle("保存").
		setMessage("是否保存您所做的操作").
		setPositiveButton("保存", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				SharedPreferences.Editor et = sp.edit();
				String keepStr = JSON.toJSONString(medicineAtyAdp.getKeepMedicine());
				String urgencyStr = JSON.toJSONString(medicineAtyAdp.getUrgencyMedicine());
				et.putString("keepmedicine", keepStr);
				et.putString("urgencymedicine", urgencyStr);
				et.commit();
				if(key!=null&&"InitPersonalDetail".equals(key))
					startActivity(new Intent(MedicineAty.this,ClockAty.class).putExtra("from", "MedicineAty"));
				else
					finish();
			}
		}).
		setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(key!=null&&"InitPersonalDetail".equals(key))
					startActivity(new Intent(MedicineAty.this,ClockAty.class).putExtra("from", "MedicineAty"));
				else
					finish();				
			}
		}).show();

	}

	@Override
	protected void onResume() {
//		getData();
		super.onResume();
	}
	
	private void getData(){
		String keepstr = sp.getString("keepmedicine", "");
		String urgencystr = sp.getString("urgencymedicine", "");
		ArrayList<MedicineCls> keepCls = new ArrayList<MedicineCls>();
		ArrayList<MedicineCls> urgencyCls = new ArrayList<MedicineCls>();
		if(keepstr!=null&&!keepstr.equals(""))
			keepCls= (ArrayList<MedicineCls>) JSON.parseArray(keepstr, MedicineCls.class);
		if(urgencystr!=null&&!urgencystr.equals(""))
			urgencyCls = (ArrayList<MedicineCls>) JSON.parseArray(urgencystr, MedicineCls.class);
		medicineAtyAdp.setData(keepCls,urgencyCls);
	}


}
class MedicineAtyAdp extends BaseExpandableListAdapter{
	private MedicineAty mActivity;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private Context mContext;
	private LayoutInflater inflater;
	private String[] generalsTypes = new String[] { "日常用药", "紧急用药"};
	private ArrayList<MedicineCls> keepMedicine;
	private ArrayList<MedicineCls> urgencyMedicine;
	private ArrayList<ArrayList<MedicineCls>> list;
	//用户各种药物数据

	public MedicineAtyAdp(Context c,MedicineAty a) {
		this.mActivity = a;
		this.mContext = c;
		inflater = LayoutInflater.from(mContext);
		list = new ArrayList<ArrayList<MedicineCls>>();
		keepMedicine = new ArrayList<MedicineCls>();
		urgencyMedicine = new ArrayList<MedicineCls>();
		sp = mContext.getSharedPreferences("Medicine", mContext.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setData(ArrayList<MedicineCls> a,ArrayList<MedicineCls> b){
		keepMedicine = a;
		urgencyMedicine = b;
		notifyDataSetChanged();
	}

	public ArrayList<MedicineCls> getKeepMedicine(){
		return keepMedicine;
	}
	public void addKeepMedicine(MedicineCls a){
		keepMedicine.add(a);
		notifyDataSetChanged();
	}
	public ArrayList<MedicineCls> getUrgencyMedicine(){
		return urgencyMedicine;
	}
	public void addUrgencyMedicine(MedicineCls a){
		urgencyMedicine.add(a);
		notifyDataSetChanged();
	}
	private ArrayList<MedicineCls> switchGroup(int i) {
		switch (i) {
		case 0:
			return keepMedicine;
		case 1:
			return urgencyMedicine;

		default:
			return null;
		}

	}

	private void refresh() {
		String keepstr = sp.getString("keepmedicine", "");
		String urgencystr = sp.getString("urgencymedicine", "");
		ArrayList<MedicineCls> keepCls = new ArrayList<MedicineCls>();
		ArrayList<MedicineCls> urgencyCls = new ArrayList<MedicineCls>();
		if(keepstr!=null&&!keepstr.equals(""))
			keepCls= (ArrayList<MedicineCls>) JSON.parseArray(keepstr, MedicineCls.class);
		if(urgencystr!=null&&!urgencystr.equals(""))
			urgencyCls = (ArrayList<MedicineCls>) JSON.parseArray(urgencystr, MedicineCls.class);
		setData(keepCls,urgencyCls);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return switchGroup(arg0).get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(final int groupId, final int childId, boolean arg2, View convertView,
			ViewGroup groupView) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_tv, groupView,false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(switchGroup(groupId).get(childId).getMedicineName()+"("+switchGroup(groupId).get(childId).getProductName()+")");
		convertView.setTag(R.id.a,groupId);
		convertView.setTag(R.id.about,childId);
		holder.text.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				final AlertDialog alertDialog;
				alertDialog = new AlertDialog.Builder(mContext).
						setTitle("删除药物").
						setMessage("确定删除"+switchGroup(groupId).get(childId).getMedicineName()+"吗?").
						setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								//						ArrayList<MedicineCls> a = switchGroup(groupId);
								//						a.remove(childId);
								//						String st = JSON.toJSONString(a);
								//						if(groupId==0)
								//							editor.putString("keepMedicine", st);
								//						if(groupId==1)
								//							editor.putString("urgencyMedicine",st);
								//						editor.commit();
								//						refresh();
								if(groupId == 0)
									keepMedicine.remove(childId);
								if(groupId == 1)
									urgencyMedicine.remove(childId);
								notifyDataSetChanged();
							}


						}).
						setNegativeButton("取消", null).show();

				return false;
			}
		});
		return convertView;}

	@Override
	public int getChildrenCount(int arg0) {
		return switchGroup(arg0).size();
	}

	@Override
	public Object getGroup(int arg0) {
		return switchGroup(arg0);
	}

	@Override
	public int getGroupCount() {
		return generalsTypes.length;
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(final int position, boolean arg1, View convertView, ViewGroup parentGroup) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_group_tv, parentGroup,false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			holder.image = (LinearLayout) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(""+generalsTypes[position]);
		//因为ExpandListView 没有提供长按选项 所以使用这个来判断长按点击的是父控件 还是子控件
		convertView.setTag(R.id.a, position);
		convertView.setTag(R.id.about,-1);
		holder.image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (position) {
				case 0:
					//					mContext.startActivity(new Intent(mContext,AddMedicineAty.class).putExtra("from", "keep"));
					mActivity.startActivityForResult(new Intent(mContext,AddMedicineAty.class).putExtra("from", "keep"), 1);
					break;
				case 1:
					//					mContext.startActivity(new Intent(mContext,AddMedicineAty.class).putExtra("from", "urgency"));
					mActivity.startActivityForResult(new Intent(mContext,AddMedicineAty.class).putExtra("from", "urgency"), 1);
					break;
				default:
					break;
				}
			}
		});
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}
	static class ViewHolder {
		TextView text;
		LinearLayout image;
	}
}