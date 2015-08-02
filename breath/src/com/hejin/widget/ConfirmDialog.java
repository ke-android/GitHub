package com.hejin.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.kewensheng.breath.R;
import com.kewensheng.tool.Notice;

/**
 * @author: Calvinyang
 * @Description: TODO
 * @date: Jan 11, 2014 7:25:59 PM
 */
public class ConfirmDialog extends Dialog {
	private Context context;
	private String hint;

	/**
	 * @param context
	 */
	public ConfirmDialog(Context context) {
		super(context, R.style.dialog_style);
		this.context = context;
	}
	
	

	/**
	 * @param context
	 * @param hint
	 */
	public ConfirmDialog(Context context, String hint) {
		this(context);
		this.hint = hint;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_confirm);
		Window window = getWindow();
		LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		params.width = (int) (CommonUtils.getWidth(context) - 100 * CommonUtils.getDensity(context));
		params.height = (int) (CommonUtils.getDensity(context) * (hint.length() / 12 * 30 + 120));
		window.setAttributes(params);
		TextView tv = (TextView) findViewById(R.id.confirm_content);
		tv.setText(hint);
		setCancelable(false);
		findViewById(R.id.confirm_cancel).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View paramView) {
						dismiss();
					}
				});
		findViewById(R.id.confirm_ok).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View paramView) {
						dismiss();
					}
				});
	}

	/**
	 * 
	 * @Title: setListener
	 * @Description: 设置按钮事件
	 * @param yesListener
	 * @param noListener
	 */
	public void setListener(android.view.View.OnClickListener yesListener,
			android.view.View.OnClickListener noListener) {
		if (yesListener != null) {
			findViewById(R.id.confirm_ok).setOnClickListener(yesListener);
		}
		if (noListener != null) {
			findViewById(R.id.confirm_cancel).setOnClickListener(noListener);
		}
	}

	/**
	 * 
	 * @Title: setButton
	 * @Description: 设置按钮提示
	 * @param yes
	 * @param no
	 */
	public void setButton(String yes, String no) {
		if (yes != null) {
			((TextView) findViewById(R.id.confirm_ok)).setText(yes);
		}
		if (no != null) {
			((TextView) findViewById(R.id.confirm_cancel)).setText(no);
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		Notice.playBeepSoundAndVibrate(getContext(), R.raw.notice, 0);
		super.show();
	}
}
