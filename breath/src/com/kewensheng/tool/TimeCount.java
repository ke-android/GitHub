package com.kewensheng.tool;

import android.os.CountDownTimer;
import android.widget.Button;
/**倒计时使用**/

public class TimeCount extends CountDownTimer {
	private Button checking;
	public TimeCount(long millisInFuture, long countDownInterval,Button btn) {
		super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
		this.checking = btn;
	}
	
	@Override
	public void onFinish() {//计时完毕时触发
		checking.setText("重新验证");
		checking.setClickable(true);
	}
	
	@Override
	public void onTick(long millisUntilFinished){//计时过程显示
		checking.setClickable(false);
		checking.setText(millisUntilFinished /1000+"秒");
	}
}
