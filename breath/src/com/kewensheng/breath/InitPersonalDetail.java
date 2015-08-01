package com.kewensheng.breath;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.kewensheng.cls.PersonalDetaileCls;
import com.kewensheng.cls.ReturnedCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;
import com.kewensheng.tool.DateTimePickDialogUtil;
import com.kewensheng.tool.LogCat;
import com.mrwujay.cascade.activity.CityMainActivity;

public class InitPersonalDetail extends BaseAcitivity implements OnClickListener,IModelChangeListener{
	private BreathController mController;
	private TextView title;
	private EditText nameEt;
	private EditText birthdayEt;//弹出年月日选择器
	private EditText heightEt;
	private EditText weightEt;
	//联系人
	private EditText contactEt;
	private String contactName;
	private String contactNumber;
	private int age;
	private float pef;
	//地址
	private EditText addressEt;
	//数据源
	private PersonalDetaileCls personalDetaileCls;
	//性别
	private RadioGroup rdoGroupSex;
	private RadioButton rdoBtnMan;
	private RadioButton rdoBtnWoman;
	private String sexFlag= "M";
	//是否过敏
	private RadioGroup rdoGroupAllergy;
	private RadioButton rdoBtnAllergyYes;
	private RadioButton rdoBtnAllergyNo;
	private int allergyFlag=0;
	//是否吸烟
	private RadioGroup rdoGroupSmoke;
	private RadioButton rdoBtnSmokeYes;
	private RadioButton rdoBtnSmokeNo;
	private int smokeFlag=0;
	//显示吸烟输入框
	private LinearLayout smokeYearLinearLayout;
	private EditText somkeYearEt;
	//医保类型
	private Spinner hisSp;
	private ArrayAdapter<String> hisAdp;

	private EditText fev1Et;

	private SharedPreferences sp;
	private BreathController mConroller;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.AddUser_finish:
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					ReturnedCls returnedJson = (ReturnedCls) msg.obj;
					if("true".equals(returnedJson.getSuccess())){
						BaseToast.toastSay(InitPersonalDetail.this, "上传服务器成功!");
						String str = JSONObject.toJSONString(personalDetaileCls);
						SharedPreferences.Editor editor = sp.edit(); 
						editor.putString("detail", str);
						editor.putBoolean("LoginState", true);
						editor.commit();
						if(getIntent().getStringExtra("from")!=null&&"MainActivity".equalsIgnoreCase(getIntent().getStringExtra("from")))
							finish();
						else
							startActivity(new Intent(InitPersonalDetail.this,MedicineAty.class).putExtra("from", "InitPersonalDetail"));
					}else{
						Toast.makeText(InitPersonalDetail.this, ""+returnedJson.getErrmsg(), Toast.LENGTH_SHORT).show();
					}
				}

				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_init_personal_detail);
		mConroller = new BreathController(this);
		mConroller.setIModelChangeListener(this);

		birthdayEt = (EditText) findViewById(R.id.birthday);
		nameEt = (EditText) findViewById(R.id.name);
		//女
		rdoGroupSex = (RadioGroup) findViewById(R.id.rdoGroupSex);
		rdoGroupSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if(arg1 == rdoBtnMan.getId())
					sexFlag = "M";
				else if(arg1 == rdoBtnWoman.getId())
					sexFlag = "W";
			}
		});
		rdoBtnMan = (RadioButton) findViewById(R.id.rdoBtnMan);
		rdoBtnWoman = (RadioButton) findViewById(R.id.rdoBtnWoman);
		rdoBtnMan.setChecked(true);
		//过敏 否
		rdoGroupAllergy = (RadioGroup) findViewById(R.id.rdoGroupAllergy);
		rdoBtnAllergyYes = (RadioButton) findViewById(R.id.rdoBtnAllergyYes);
		rdoBtnAllergyNo = (RadioButton) findViewById(R.id.rdoBtnAllergyNo);
		rdoGroupAllergy.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if(arg1 == rdoBtnAllergyYes.getId())
					allergyFlag = 1;
				else if(arg1 == rdoBtnAllergyNo.getId())
					allergyFlag = 0;
			}
		});
		rdoBtnAllergyNo.setChecked(true);
		//吸烟 否
		rdoGroupSmoke = (RadioGroup) findViewById(R.id.rdoGroupSmoke);
		rdoBtnSmokeYes = (RadioButton) findViewById(R.id.rdoBtnSmokeYes);
		rdoBtnSmokeNo = (RadioButton) findViewById(R.id.rdoBtnSmokeNo);
		rdoBtnSmokeNo.setChecked(true);

		smokeYearLinearLayout = (LinearLayout) findViewById(R.id.smokeYear);

		somkeYearEt = (EditText) findViewById(R.id.somkeYearEt);
		rdoGroupSmoke.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if(arg1 == rdoBtnSmokeYes.getId()){
					smokeFlag = 1;
					smokeYearLinearLayout.setVisibility(View.VISIBLE);
				}
				else if(arg1 == rdoBtnSmokeNo.getId()){
					smokeFlag = 0;
					smokeYearLinearLayout.setVisibility(View.GONE);
				}

			}
		});
		//		rdoGroupSex = (RadioGroup) findViewById(R.id.rdoGroupSex);
		//		rdoGroupAllergy = (RadioGroup) findViewById(R.id.rdoGroupAllergy);
		//		rdoGroupSmoke = (RadioGroup) findViewById(R.id.rdoGroupSmoke);

		heightEt = (EditText) findViewById(R.id.height);
		weightEt = (EditText) findViewById(R.id.weight);

		contactEt = (EditText) findViewById(R.id.contact);
		contactEt.setFocusable(false);
		contactEt.setOnClickListener(this);

		addressEt = (EditText) findViewById(R.id.address);
		addressEt.setFocusable(false);
		addressEt.setOnClickListener(this);

		hisSp = (Spinner) findViewById(R.id.his);
		String[] hisdata = {"职工医保","居民医保","新型农村合作医疗","无"}; 
		hisAdp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hisdata);
		hisSp.setAdapter(hisAdp);

		fev1Et = (EditText) findViewById(R.id.fev1);

		birthdayEt.setFocusable(false);
		birthdayEt.setOnClickListener(this);
		title = (TextView)findViewById(R.id.title);
		title.setText("设置个人资料");



		Context ctx = InitPersonalDetail.this;       
		sp = ctx.getSharedPreferences("Detail", MODE_PRIVATE);
		if(getIntent().getStringExtra("from")!=null&&"MainActivity".equalsIgnoreCase(getIntent().getStringExtra("from")))
			initData(sp);
		mController = new BreathController(this);
		personalDetaileCls = new PersonalDetaileCls();
	}

	private void initData(SharedPreferences sp) {
		String a = sp.getString("detail", null);
		PersonalDetaileCls cls = JSONObject.parseObject(a, PersonalDetaileCls.class);
		nameEt.setText(cls.getContactusername()+"");
		birthdayEt.setText(cls.getBirthday()+"");
		heightEt.setText(cls.getHeight()+"");
		weightEt.setText(cls.getWeight()+"");
		contactName = cls.getContactusername();
		contactNumber = cls.getContactmobile();
		contactEt.setText(cls.getContactmobile()+"("+cls.getContactusername()+")");
		addressEt.setText(cls.getArea()+"");
		somkeYearEt.setText(cls.getSmokingyear()+"");
		//			//女
		//					private RadioButton rdoBtnMan;
		//			//过敏 否
		//			private RadioButton rdoBtnAllergyYes;
		//			//吸烟 否
		//			private RadioButton rdoBtnSmokeYes;
		//		rdoBtnMan.setSelected(intToBoolean(cls.getSex()));
		//		rdoBtnAllergyYes.setChecked(intToBoolean(cls.getIsallergy()));
		//		rdoBtnSmokeYes.setChecked(intToBoolean(cls.getIssmokinghistory()));

		sexFlag = cls.getSex();
		allergyFlag = cls.getIsallergy();
		smokeFlag = cls.getIssmokinghistory();
		if("W".equals(sexFlag))
			rdoBtnWoman.setChecked(true);
		else if("M".equals(sexFlag))
			rdoBtnMan.setChecked(true);
		if(allergyFlag ==0)
			rdoBtnAllergyNo.setChecked(true);
		else if(allergyFlag ==1)
			rdoBtnAllergyYes.setChecked(true);
		if(smokeFlag ==0)
			rdoBtnSmokeNo.setChecked(true);
		else if(smokeFlag ==1)
			rdoBtnSmokeYes.setChecked(true);
		fev1Et.setText(cls.getFev1()+"");
	}

	private boolean intToBoolean(int i) {
		switch (i) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.birthday:
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(  
					InitPersonalDetail.this, "1980年1月1日");  
			dateTimePicKDialog.dateTimePicKDialog(birthdayEt);  
			break;
		case R.id.contact:
			AlertDialog.Builder builder01 = new AlertDialog.Builder(this);  
			builder01.setIcon(R.drawable.ic_launcher);  
			builder01.setTitle("设置紧急联系人");  
			builder01.setMessage("紧急联系人,用于紧急情况向联系人发送资讯?可能会涉及到读取联系人权限,如果需要请点击确定,否则点击取消.");  
			builder01.setPositiveButton("确定",  
					new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton) {  
					startActivityForResult(new Intent(
							Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI), 0);
					dialog.dismiss();
				}  
			});  
			builder01.setNegativeButton("取消",  
					new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton) {  
					dialog.dismiss();
				}  
			});  
			builder01.show();  
			break;

		case R.id.address:
			startActivityForResult(new Intent(this,CityMainActivity.class), 1);
		default: 
			break;
		}
	}
	@Override
	public void next(View view) {
		super.next(view);
		//		if(contentCheck(nameEt)&&contentCheck(birthdayEt)&&contentCheck(heightEt)
		//				&&contentCheck(weightEt)&&contentCheck(contactEt)
		//				&&contentCheck(addressEt));
		if(contentCheck(nameEt))
			if(contentCheck(birthdayEt))
				if(contentCheck(heightEt))
					if(contentCheck(weightEt))
						if(contentCheck(addressEt))
							if(contentCheck(fev1Et))
							{
								computePefBySex(computeAge());
								saveData();
							}
							else
								BaseToast.toastSay(this, "fev1不能为空");
						else
							BaseToast.toastSay(this, "地址不能为空");
					else
						BaseToast.toastSay(this, "体重不能为空");
				else
					BaseToast.toastSay(this, "身高不能为空");
			else
				BaseToast.toastSay(this, "生日不能为空");
		else
			BaseToast.toastSay(this, "姓名不能为空");
	}

	private int computeAge(){
		Calendar calendar = Calendar.getInstance();
		int now = calendar.get(Calendar.YEAR);
		String year = birthdayEt.getText()+"";
		LogCat.say("InitPersonalDetail","now:"+now+"age:"+age);
		year = year.substring(0, 4);
		int year1 = Integer.parseInt(year);
		age = now -year1;
		LogCat.say("InitPersonalDetail","year:"+year1+"now:"+now+"age:"+age);
		return age;
	}

	private void computePefBySex(int num) {
		float a = num;
		String x = heightEt.getText()+"";
		int y = Integer.parseInt(x);
		float w = y;
		if(num>=15){
			if(rdoBtnMan.isChecked()){
				pef = (float) (75.6+20.4*a-0.41*a*a+0.002*a*a*a+1.19*w);
			}else{
				pef = (float) (282+1.79*1-0.046*a*a+0.68*w);
			}
		}else if(num>=5&&num<15){
			if(rdoBtnMan.isChecked()){
				pef = (float) (5.20*w-427.1);
			}else{
				pef = (float) (4.97*w-399.8);
			}
		}
		//		if("男".equals(sexAdp.getItem(rdoGroupSex.getSelectedItemPosition()))){
		//			if(num>14)
		//				pef = (float) (75.6+20.4*a-0.41*a*a+0.002*a*a*a+1.19*w);
		//			else if(num>=5&&num<=14)
		//				pef = (float) (5.20*w-427.1);
		//			else if(num>0&&num<=4)
		//				pef = 0;
		//		}else{
		//			if(num>14)
		//				pef = (float) (282+1.79*1-0.046*a*a+0.68*w);
		//			else if(num>=5&&num<=14)
		//				pef = (float) (4.97*w-399.8);
		//			else if(num>0&&num<=4)
		//				pef = 0;
		//		}
		//		14岁以上采用成人算法
		//		男性成人=75.6+20.4A-0.41A^2+0.002A^3+1.19X
		//		女性成人=282+1.79A-0.046A^2+0.68X
		//		5-14岁儿童的PEF算法
		//		男童=5.20X-427.1
		//		女童=4.94X-399.8
	}

	private int computeYear(String a){
		//		String date = DateTimePickDialogUtil.spliteString(a, "日", "index", "front"); // 日期
		//		String yearStr = DateTimePickDialogUtil.spliteString(date, "年", "index", "front");
		String  yearStr = a.substring(0,4);
		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		return currentYear;
	}

	private void saveData() {
		String userid = getIntent().getStringExtra("userid");
		int id = Integer.parseInt(userid);
		String patientType = getIntent().getStringExtra("Patienttype");
		personalDetaileCls.setPatienttype(patientType);

		personalDetaileCls.setUserid(id);
		personalDetaileCls.setUsername(nameEt.getText()+"");//姓名
		personalDetaileCls.setSex(sexFlag);//男性 女性

		String birthdayStr = birthdayEt.getText()+"";
		//		birthdayStr = birthdayStr.replace("年", "-");
		//		birthdayStr = birthdayStr.replace("月", "-");
		//		birthdayStr = birthdayStr.replace("日", "");
		personalDetaileCls.setBirthday(birthdayStr);//出生日期

		personalDetaileCls.setHeight(heightEt.getText()+"");//身高
		personalDetaileCls.setWeight(weightEt.getText()+"");//体重
		personalDetaileCls.setIsallergy(allergyFlag);//是否过敏
		//因为参数列表项那里面要求的是 1234 不是0123 所以这里把数值加1
		personalDetaileCls.setMedicalinsurance(hisSp.getSelectedItemPosition()+1);//医保类型
		personalDetaileCls.setContactusername(contactName);//联系人姓名
		personalDetaileCls.setContactmobile(contactNumber);//联系人电话
		personalDetaileCls.setArea(addressEt.getText()+"");//地址
		personalDetaileCls.setIssmokinghistory(smokeFlag);//吸烟
		personalDetaileCls.setSmokingyear(somkeYearEt.getText()+"");//吸烟
		personalDetaileCls.setFev1(fev1Et.getText()+"");//fev1值
		personalDetaileCls.setPef(pef);
		//		String a = sp.getString("Patienttype", "0");
		//		personalDetaileCls.setPatienttype(a);
		//		String str = JSONObject.toJSONString(personalDetaileCls);
		//		editor.putString("detail", str);
		//		editor.putBoolean("LoginState", true);
		//		editor.commit();
		mConroller.sendAsyncMessage(IdiyMessage.AddUser, "ModifyUserInfo",personalDetaileCls);
		//		患者手机号码：mobile
		//		姓名：username 
		//		出生日期： birthday
		//		身高： height
		//		体重： weight
		//		是否过敏： isallergy
		//		联系人姓名： contactusername
		//		联系人手机： contactmobile
		//		详细地址： area
		//		医保类型： medicalinsurance
		//		是否有吸烟史： issmokinghistory
		//		FEV1值： fev1
		//		患者类型： patienttype 暂定：1 哮喘，2 慢阻肺，3 肺癌，4 其它
		//		http://breathe.gmcc.net/api/Service.ashx?cmd=NJServerHandler.AddUser&username=%E5%91%A8%E6%96%87%E7%87%95&birthday=1949-07-18&isallergy=1
		//		http://210.21.34.4:9100/api/APPService.ashx?cmd=NJServerHandler.AddUser&username=%E5%91%A8%E6%96%87%E7%87%95&birthday=1949-07-18&isallergy=1&height=156&weight=56&contactusername=%E6%9E%97%E6%96%87%E4%BC%9F&contactmobile=13480249657&area=%E4%B8%89%E5%85%83%E9%87%8C%E5%8C%97%E7%AB%99&medicalinsurance=1&issmokinghistory=1&fev1=23&patienttype=1
		//			返回JSON串
		//			{"success":"true","type":"1","errmsg":""} true表示成功
		//			失败的话会返回形如：
		//			{"success":"false","type":"0","errmsg":"Unknown method"}


		//		

	}

	//根据RadioButton是否被选中 判断是男是女 是或者否
	private int strToInt(RadioButton rdoBtnMan2) {
		if(rdoBtnMan2.isSelected())
			return 1;
		else 
			return 0;
	}


	private String ad( String  a){
		return "&"+a+"=";
	}

	private boolean contentCheck(EditText a){
		if("".equals(a.getText()+"")){
			return false;
		}
		else{
			return true;
		}
	}

	//返回联系人数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			ContentResolver reContentResolverol = getContentResolver();
			Uri contactData = data.getData();
			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(contactData, null, null, null, null);
			cursor.moveToFirst();
			contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
					null, 
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, 
					null, 
					null);
			while (phone.moveToNext()) {
				contactNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactEt.setText(contactNumber+" ("+contactName+")");
			}

		}
		//返回地点和时间
		if(resultCode == 0){
			if(data!=null)
				//			if(!"".equals(data.getExtras().getString("address"))&&data.getExtras().getString("address")!=null)
				addressEt.setText(""+data.getExtras().getString("address"));
		}
	}

	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}

}
