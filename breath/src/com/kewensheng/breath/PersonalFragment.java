package com.kewensheng.breath;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.kewensheng.cls.VerSionCodeCls;
import com.kewensheng.controller.BreathController;
import com.kewensheng.controller.IModelChangeListener;
import com.kewensheng.controller.IdiyMessage;
import com.kewensheng.tool.BaseToast;
import com.kewensheng.tool.ToRound;
import com.kewensheng.tool.UpdateTools;

public class PersonalFragment extends Fragment implements OnClickListener, IModelChangeListener{
	private BreathController mController;
	private ImageView imageView1;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case IdiyMessage.GeVersionList_finish:
                if (msg.obj != null) {
                	String str = (String) msg.obj;
                	VerSionCodeCls cls = JSON.parseObject(str, VerSionCodeCls.class);
                    if (str != null && str.length() != 0){
                    	String netVersionStr = "0";
//                        String netVersionStr = mUpdataList.get("update_type").toString();
                        UpdateTools updateTools = new UpdateTools(getActivity(),cls);
                        if("0".equals(netVersionStr)){
                        	BaseToast.toastSay(getActivity(), "�Ѿ������°汾!");
                        }
                        if("1".equals(netVersionStr)){
                            updateTools.shwoDialog();
                        }else if("2".equals(netVersionStr)){
                             String mUrl = cls.getArchiveFilePath();
                            if("".equals(mUrl)||mUrl == null){
//                                NoticeUtil.warnTipStr(getActivity(), "��������");
                                BaseToast.toastSay(getActivity(), "��������");
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
        };
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_personal, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageView1 = (ImageView) getView().findViewById(R.id.image1);
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.doctor);
		Bitmap output=ToRound.toRoundCorner(bitmap, 150.0f);
		imageView1.setImageBitmap(output);
		getView().findViewById(R.id.detail).setOnClickListener(this);
		getView().findViewById(R.id.changemedicine).setOnClickListener(this);
		getView().findViewById(R.id.medicineremind).setOnClickListener(this);
		getView().findViewById(R.id.mydoctor).setOnClickListener(this);
		getView().findViewById(R.id.messagecenter).setOnClickListener(this);
		getView().findViewById(R.id.changepassword).setOnClickListener(this);
		getView().findViewById(R.id.update).setOnClickListener(this);
		getView().findViewById(R.id.about).setOnClickListener(this);
		mController = new BreathController(getActivity());
		mController.setIModelChangeListener(this);
	}

	public void sentIntent(Context c, Class<?> cls){
		startActivity(new Intent(c,cls));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//����޸ĸ�����Ϣ
		case R.id.detail:
			startActivity(new Intent(getActivity(),InitPersonalDetail.class).putExtra("from", "MainActivity"));
			break;
			//�޸�ҩ��
		case R.id.changemedicine:
			startActivity(new Intent(getActivity(),MedicineAty.class).putExtra("from", "MainActivity"));
			break;
			//�޸�����
		case R.id.medicineremind:
			startActivity(new Intent(getActivity(),ClockAty.class).putExtra("from", "MainActivity"));
			break;
			//�ҵ�ҽ��
		case R.id.mydoctor:
			BaseToast.toastSay(getActivity(), "��δ����");
			break;
			//��Ϣϵͳ
		case R.id.messagecenter:
			BaseToast.toastSay(getActivity(), "��δ����");
			break;
			//�޸�����
		case R.id.changepassword:
			BaseToast.toastSay(getActivity(), "��δ����");
			break;
			//������
		case R.id.update:
//			BaseToast.toastSay(getActivity(), "��δ����");
	        mController.sendAsyncMessage(IdiyMessage.GeVersionList, "CheckVersion");
			break;
			//����
		case R.id.about:
			startActivity(new Intent(getActivity(),AboutMeAty.class));
//			BaseToast.toastSay(getActivity(), "��δ����");
			break;

		default:
			break;
		}
	}
	@Override
	public void onModelChanged(int action, Object... value) {
		mHandler.obtainMessage(action,value[0]).sendToTarget();
	}
}
