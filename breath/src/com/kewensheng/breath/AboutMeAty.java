package com.kewensheng.breath;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;

public class AboutMeAty extends BaseAcitivity implements OnClickListener, IModelChangeListener{
	private BreathController mController;

	private Handler mHandler=new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case IdiyMessage.GeVersionList_finish:
                break;
            default:
                break;
            }
        }


    };
	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}

	@Override
	public void onClick(View arg0) {
		
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mController = new BreathController(this);
        mController.setIModelChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.GeVersionList, "ServerHandler. GetAPPAboutMe");
	}
}
