package com.kewensheng.tool;

import android.content.Context;
import android.widget.Toast;

public class BaseToast {
	public static void toastSay(Context c,String text){
		Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
	}
}
