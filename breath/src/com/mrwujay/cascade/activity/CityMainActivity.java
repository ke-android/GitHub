package com.mrwujay.cascade.activity;


import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kewensheng.breath.R;

public class CityMainActivity extends CityBaseActivity implements OnClickListener, OnWheelChangedListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private EditText addressEt;
	private Button mBtnConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_city);
		setUpViews();
		setUpListener();
		setUpData();
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		addressEt = (EditText) findViewById(R.id.addressEt);
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	// 添加onclick事件
    	mBtnConfirm.setOnClickListener(this);
    }
	
	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(CityMainActivity.this, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
//			showSelectedResult();
			//返回详细地点
			String str = mCurrentProviceName+"/"+mCurrentCityName+"/"
					+mCurrentDistrictName+"/"+addressEt.getText();
			setResult(0, new Intent().putExtra("address", str));
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
//		setResult(0, new Intent().putExtra("adress", ""));
		super.onBackPressed();
	}
	
	private void showSelectedResult() {
		Toast.makeText(CityMainActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}
}
