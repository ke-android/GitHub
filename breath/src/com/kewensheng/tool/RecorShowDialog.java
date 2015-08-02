package com.kewensheng.tool;


import com.kewensheng.breath.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class RecorShowDialog {
	private Context mContext;
	public RecorShowDialog(Context c) {
		this.mContext = c;		
	}
	//	showNormal();
	//}
	//else if(change>=0.6&&change<0.8){
	//	showWarring();
	//}else if(change<0.6){
	//	showRisk();
	//}
	public void showNormal(String name){
		final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.normal_dialog);
		TextView textView = (TextView) window.findViewById(R.id.title);
		textView.setText("°²È« "+name+"L/min");
		Button button = (Button) window.findViewById(R.id.close);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}
	public void showWarring(String name){
		final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.warn_dialog);
		TextView textView = (TextView) window.findViewById(R.id.title);
		textView.setText("×¢Òâ "+name+"L/min");
		Button button = (Button) window.findViewById(R.id.close);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
		Button button2 = (Button) window.findViewById(R.id.doctor);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}
	public void showRisk(String name){
		final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.risk_dialog);
		TextView textView = (TextView) window.findViewById(R.id.title);
		textView.setText("Î£ÏÕ "+name+"L/min");
		Button button = (Button) window.findViewById(R.id.close);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
		Button button2 = (Button) window.findViewById(R.id.doctor);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}
}
