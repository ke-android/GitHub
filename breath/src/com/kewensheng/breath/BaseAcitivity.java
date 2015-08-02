package com.kewensheng.breath;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;


public class BaseAcitivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
	}

	public void back(View view) {
		finish();
	}
	
	public void next(View view) {
	}
}
