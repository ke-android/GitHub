package com.kewensheng.breath;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.VerSionCodeCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;
import com.kewensheng.tool.UpdateTools;

public class MainActivity extends BaseAcitivity implements IModelChangeListener{
    private RadioButton[] mTabs;
    private int index;
    // ��ǰfragment��index
    private int currentTabIndex;
	private HomeFragment homeFragment;
	private RecordFragment recordFragment;
	private PersonalFragment personalFragment;
	private Fragment[] fragments;
	
	private BreathController mController;
	private Handler mHandler=new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case IdiyMessage.GeVersionList_finish:
                if (msg.obj != null) {
                	String str = (String) msg.obj;
                	VerSionCodeCls cls = JSON.parseObject(str, VerSionCodeCls.class);
                    if (str != null && str.length() != 0){
                    	String netVersionStr = "0";
//                        String netVersionStr = mUpdataList.get("update_type").toString();
                        UpdateTools updateTools = new UpdateTools(MainActivity.this,cls);
                        if("0".equals("netVersionStr")){
                        	BaseToast.toastSay(MainActivity.this, "�Ѿ������°汾!");
                        }
                        if("1".equals(netVersionStr)){
                            updateTools.shwoDialog();
                        }else if("2".equals(netVersionStr)){
                             String mUrl = cls.getArchiveFilePath();
                            if("".equals(mUrl)||mUrl == null){
//                                NoticeUtil.warnTipStr(MainActivity.this, "��������");
                                BaseToast.toastSay(MainActivity.this, "��������");
                            }else{
                                updateTools.showDownloadDialog();                    
                            }
                        }
                    }
                }
                break;
            default:
                break;
            }
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        homeFragment = new HomeFragment();
        recordFragment = new RecordFragment();
        personalFragment = new PersonalFragment();
        fragments = new Fragment[]{homeFragment,recordFragment,personalFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
        mTabs = new RadioButton[3];
        mTabs[0] = (RadioButton) findViewById(R.id.home);
        mTabs[1] = (RadioButton) findViewById(R.id.record);
        mTabs[2] = (RadioButton) findViewById(R.id.personal);
        // �ѵ�һ��tab��Ϊѡ��״̬
        mTabs[0].setChecked(true);
        mController = new BreathController(this);
        mController.setIModelChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.GeVersionList, "CheckVersion");
        startAlarmService();
    }


    //���Է���
	private void startAlarmService() {
		// TODO Auto-generated method stub
		Log.i("hj", "runtest");
		try {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AlarmService.class);
			startService(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}


	/**
     * button����¼�
     * 
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
        case R.id.home:
            index = 0;
            break;
        case R.id.record:
            index = 1;
            break;
        case R.id.personal:
            index = 2;
            break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // �ѵ�ǰtab��Ϊѡ��״̬
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }


	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}
}
