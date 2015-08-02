package com.kewensheng.breath;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kewensheng.cls.XieYiAtyCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;

public class XieYiAty extends BaseAcitivity implements OnClickListener,IModelChangeListener{
	private TextView mTv;
	private BreathController mController;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.GetAPPAboutMe_finish:
				//                mLoadMask.stopLoad();
				if (msg.obj != null) {
					XieYiAtyCls cls = (XieYiAtyCls) msg.obj;
					mTv.setText(cls.getAboutme());
				}
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO 自动生成的方法存根
		super.onCreate(arg0);
		setContentView(R.layout.aty_xieyi);
		findViewById(R.id.save).setVisibility(View.INVISIBLE);
		TextView textView = (TextView) findViewById(R.id.title);
		textView.setText("用户协议");
		mTv = (TextView) findViewById(R.id.textView1);
		mController = new BreathController(this);
		mController.setIModelChangeListener(this);
		mController.sendAsyncMessage(IdiyMessage.GetAPPAboutMe, "GetAPPAboutMe");
	}
	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}

	@Override
	public void onClick(View arg0) {

	}

}
