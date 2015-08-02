package com.kewensheng.breath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.kewensheng.cls.SendCodeCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;

public class DengLuAty extends BaseAcitivity implements OnClickListener,IModelChangeListener{
	private BreathController mController;
	private EditText mAccountEt;
	private EditText mPwdEt;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.UserLogin_finish:
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					SendCodeCls cls = (SendCodeCls) msg.obj;
					if("true".equals(cls.getSuccess().toString())){
						startActivity(new Intent(DengLuAty.this,MainActivity.class));
					}else{
						BaseToast.toastSay(DengLuAty.this, cls.getMessage().toString());
					}
				}

				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_denglu);
		mController = new BreathController(this);
		mController.setIModelChangeListener(this);
		mAccountEt = (EditText) findViewById(R.id.editText1);
		mPwdEt = (EditText) findViewById(R.id.editText2);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.textView2).setOnClickListener(this);
		findViewById(R.id.textView1).setOnClickListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button1:
//			if(!"".equals(mAccountEt.getText().toString())||!"".equals(mPwdEt.getText().toString()))
//			startActivity(new Intent(this,LoginActivity.class));
//			else  
//				BaseToast.toastSay(this, "ÕËºÅºÍÃÜÂë²»ÄÜÎª¿Õ");
			login();
			break;
		//×¢²á
		case R.id.textView2:
			startActivity(new Intent(this,ZhuCheAty.class));
			break;
			//Íü¼ÇÃÜÂë
		case R.id.textView1:
			startActivity(new Intent(this,WangJiMiMaAty.class));
			break;
		default:
			break;
		}
		//		findViewById(R.id.class)
	}

	private void login() {
		String account;
		String password;
		account = mAccountEt.getText().toString();
		password = mPwdEt.getText().toString();
		mController.sendAsyncMessage(IdiyMessage.UserLogin,"UserLogin",account,password);
	}

}
