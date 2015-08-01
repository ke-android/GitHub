package com.kewensheng.tool;

import android.os.CountDownTimer;
import android.widget.Button;
/**����ʱʹ��**/

public class TimeCount extends CountDownTimer {
	private Button checking;
	public TimeCount(long millisInFuture, long countDownInterval,Button btn) {
		super(millisInFuture, countDownInterval);//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		this.checking = btn;
	}
	
	@Override
	public void onFinish() {//��ʱ���ʱ����
		checking.setText("������֤");
		checking.setClickable(true);
	}
	
	@Override
	public void onTick(long millisUntilFinished){//��ʱ������ʾ
		checking.setClickable(false);
		checking.setText(millisUntilFinished /1000+"��");
	}
}
