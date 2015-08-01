package com.kewensheng.breath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kewensheng.cls.SendCodeCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;
import com.kewensheng.tool.TimeCount;

public class WangJiMiMaAty extends BaseAcitivity implements OnClickListener,IModelChangeListener{
	private BreathController mController;
	private EditText mAccountEt;
	private EditText mPwdEt;
	private EditText mPwdAgainEt;
	private EditText mCodeEt;
	private Button mSure;
	//��֤��Btn
	private Button mYanBtn;
	
	//������֤���Ժ󵹼�ʱ
	private TimeCount time;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.ModifyPassword_finish:
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {}

				break;
			case IdiyMessage.SendVCode_finish:
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					SendCodeCls cls = (SendCodeCls) msg.obj;
					if("true".equals(cls.getSuccess().toString())){
						BaseToast.toastSay(WangJiMiMaAty.this, "��֤���Ѿ����ͳɹ�,�����");
					}else{
						BaseToast.toastSay(WangJiMiMaAty.this, cls.getMessage().toString());
					}
				}

				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO �Զ����ɵķ������
		super.onCreate(arg0);
		setContentView(R.layout.aty_mimachongzhi);
		mAccountEt = (EditText) findViewById(R.id.editText1);
		mCodeEt = (EditText) findViewById(R.id.editText2);
		mPwdEt = (EditText) findViewById(R.id.editText3);
		mPwdAgainEt = (EditText) findViewById(R.id.editText4);
		mSure = (Button) findViewById(R.id.button1);
		mSure.setOnClickListener(this);
		
		mYanBtn = (Button) findViewById(R.id.btn);
		mYanBtn.setOnClickListener(this);
		//��������֤���Ժ󵹼�ʱ
		time = new TimeCount(60000, 1000, mYanBtn);
		mController = new BreathController(this);
		mController.setIModelChangeListener(this);
	}
	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();

	}

	@Override
	public void onClick(View arg0) {			
		String mobile = mAccountEt.getText().toString();
		switch (arg0.getId()) {
		//ȷ��
		case R.id.button1:
			String password = mPwdEt.getText().toString();
			String password1 = mPwdAgainEt.getText().toString();
			String vcode  = mCodeEt.getText().toString();
			mController.sendAsyncMessage(IdiyMessage.ModifyPassword, "ModifyPassword",mobile,password,password1,vcode);
			break;
		//������֤��
		case R.id.btn:
			BaseToast.toastSay(this, "�ѷ�����֤��");
			time.start();
			mController.sendAsyncMessage(IdiyMessage.SendVCode, "SendVCode",mobile);
			break;

		default:
			break;
		}
	}

}
