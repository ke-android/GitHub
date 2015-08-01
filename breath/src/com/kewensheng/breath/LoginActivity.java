package com.kewensheng.breath;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kewensheng.controller.IModelChangeListener;

public class LoginActivity extends Activity implements android.view.View.OnClickListener,IModelChangeListener{
//	private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);
//		sp = this.getSharedPreferences("Detail", MODE_PRIVATE);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
//			SharedPreferences.Editor editor = sp.edit(); 
//			editor.putString("Patienttype", "1");
//			editor.commit();
			//把患者类型一并传输过去 
			String userid = getIntent().getStringExtra("userid");
			startActivity(new Intent(this,InitPersonalDetail.class).putExtra("Patienttype", "1").putExtra("userid", userid));
			break;
		case R.id.button2:
			Toast.makeText(this, "此功能暂未开放,请静候更新", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button3:
			Toast.makeText(this, "此功能暂未开放,请静候更新", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}


	@Override
	public void onModelChanged(int action, Object... value) {
		// TODO Auto-generated method stub
		
	}
    
}
