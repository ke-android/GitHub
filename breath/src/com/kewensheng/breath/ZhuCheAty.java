package com.kewensheng.breath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kewensheng.cls.SendCodeCls;
import com.kewensheng.cls.ZhuCheAtyCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;
import com.kewensheng.tool.TimeCount;

public class ZhuCheAty extends BaseAcitivity implements OnClickListener,IModelChangeListener{
	private BreathController mController;
	private EditText mAccountEt;
	private EditText mPwdEt;
	private EditText mPwdAgainEt;
	private EditText mCodeEt;
	//发送验证码
	private Button mYanBtn;
	private CheckBox mBox;
	
	//发送验证码以后倒计时
	private TimeCount time;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.SendVCode_finish:
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					SendCodeCls cls = (SendCodeCls) msg.obj;
					if("true".equals(cls.getSuccess().toString())){
						BaseToast.toastSay(ZhuCheAty.this, "验证码已经发送成功,请查收");
					}else{
						BaseToast.toastSay(ZhuCheAty.this, cls.getMessage().toString());
					}
				}

				break;
			case IdiyMessage.RegUser_finish:
				if(msg.obj != null){
					ZhuCheAtyCls cls = (ZhuCheAtyCls) msg.obj;
					if("true".equals(cls.getSuccess().toString())){
						BaseToast.toastSay(ZhuCheAty.this, cls.getMessage().toString());
						startActivity(new Intent(ZhuCheAty.this,LoginActivity.class).putExtra("userid", cls.getUserid()));
						finish();
					}else{
						BaseToast.toastSay(ZhuCheAty.this, cls.getMessage().toString());
					}
				}
			}
		}
	};
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_zhuche);
		mController = new BreathController(this);
		mController.setIModelChangeListener(this);
		mAccountEt = (EditText) findViewById(R.id.editText1);
		mPwdEt = (EditText) findViewById(R.id.editText3);
		mPwdAgainEt = (EditText) findViewById(R.id.editText4);
		mCodeEt = (EditText) findViewById(R.id.editText2);
		mBox = (CheckBox) findViewById(R.id.chb);
		mYanBtn = (Button) findViewById(R.id.btn);
		//发送完验证码以后倒计时
		time = new TimeCount(60000, 1000, mYanBtn);
		mYanBtn.setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.agree).setOnClickListener(this);
	}
	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			//发送验证码
		case R.id.btn:
//			BaseToast.toastSay(this, "已发送验证码");
			time.start();
			String phone = mAccountEt.getText().toString();
			mController.sendAsyncMessage(IdiyMessage.SendVCode, "SendVCode",phone);
			break;
			//用户协议
		case R.id.agree:
			startActivity(new Intent(this,XieYiAty.class));
			break;
			//注册
		case R.id.button1:
			if("".equals(mAccountEt.getText().toString())){
				BaseToast.toastSay(this, "账号不能为空");
			}
			else if("".equals(mPwdEt.getText().toString())){
				BaseToast.toastSay(this, "验证码不能为空");
			}
			else if("".equals(mPwdEt.getText().toString())){
				BaseToast.toastSay(this, "密码不能为空");
			}
			else if("".equals(mPwdAgainEt.getText().toString())){
				BaseToast.toastSay(this, "再次输入密码不能为空");
			}else if(!mBox.isChecked()){
				BaseToast.toastSay(this, "请先勾选用户协议");
			}

			else {
				String mobile = mAccountEt.getText().toString();
				String password = mPwdEt.getText().toString();
				String Password1 = mPwdAgainEt.getText().toString();
				String vcode = mCodeEt.getText().toString();
				String patienttype = "";
				//				String mobile, String password,
				//				String Password1, String vcode, String patienttype
				mController.sendAsyncMessage(IdiyMessage.RegUser,
						"RegUser",mobile,password,Password1,vcode,patienttype);
			}
			break;
		default:
			break;
		}

	}
	//	private boolean checkCode() {
	//		if(mPwdEt.getText().toString().equals(mPwdAgainEt.getText().toString())){
	//			return true;
	//		}else{
	//			return false;
	//		}
	//	}

}
