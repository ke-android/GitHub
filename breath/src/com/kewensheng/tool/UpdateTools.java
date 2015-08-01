package com.kewensheng.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kewensheng.breath.R;
import com.kewensheng.cls.VerSionCodeCls;
import com.kewensheng.controller.IdiyMessage;


public class UpdateTools {
	//
	private Context mContext;

	//    //��ȡ���İ汾��Ϣ
	//    private HashMap<String, Object> mUpdataList;

	private VerSionCodeCls mVerSionCode;

	//dialog
	private Dialog mDownloadDialog;

	//����url
	private String   mUrl;

	//����·��
	private String mSavePath;

	//����
	private int progress;

	//������
	private ProgressBar mProgress;

	//������ֵ
	private  TextView mTvUpdate;

	//handler������ʾ����ʱ��ʵʱ���
	private Handler mUpdateHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// ��������
			case IdiyMessage.UPDATE_PROGRESS:
				// ���ý�����λ��
				mProgress.setProgress(progress);
				mTvUpdate.setText(progress+"%");
				break;
			case IdiyMessage.UPDATE_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			case IdiyMessage.UPDATE_ERROR:
				//                NoticeUtil.warnTipStr(mContext, "�������");
				BaseToast.toastSay(mContext, "�������");
			default:
				break;
			}
		};
	};

	public UpdateTools(Context context,VerSionCodeCls cls) {
		this.mContext = context;
		this.mVerSionCode = cls;
	}

	/**
	 * ��ʾ���ص���
	 * @param mUpdataList
	 */
	public void shwoDialog() {
		//        final HashMap<String, Object> mData =mUpdataList;
		final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.pop_update);
		TextView textView = (TextView) window.findViewById(R.id.title);
		textView.setText(""+mVerSionCode.getPackageName());
		TextView textView2 = (TextView) window.findViewById(R.id.content);
		//����ո�����url��ID
		textView2.setText(mVerSionCode.getVersionContent()+"");
		Button button = (Button) window.findViewById(R.id.later);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
		Button button2 = (Button) window.findViewById(R.id.now);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				alertDialog.dismiss();
				//����ո�����url��ID
				mUrl = mVerSionCode.getArchiveFilePath();
				LogCat.say("���µ�url"+mUrl);
				if("".equals(mUrl)||mUrl == null){
					//                    NoticeUtil.warnTipStr(mContext, "��������");
					BaseToast.toastSay(mContext, "��������");
				}else{
					showDownloadDialog();                    
				}
			}
		});
	}



	public void showDownloadDialog() {

		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(mContext);
		//        builder.setTitle("��������");
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_soft_progressbar, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mTvUpdate = (TextView) v.findViewById(R.id.number_progress);
		builder.setView(v);
		builder.setCancelable(false);
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// �����ļ�
		downloadApk();
	}
	protected void downloadApk() {
		new downloadApkThread().start();
	}

	/**
	 * �����ļ�
	 * @author k
	 *
	 */
	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// ��ô洢����·��
					mSavePath = Environment.getExternalStorageDirectory() + "/iBreath/download";
					//���������ַ�
					URL url = new URL(mVerSionCode.getArchiveFilePath());

					// ��������
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();

					// ��ȡ�ļ���С
					int length = conn.getContentLength();

					//�ж��Ƿ����ļ� 
					if(0 == length){
						mDownloadDialog.dismiss();
						mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_ERROR);
					}
					// ����������
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// �ж��ļ�Ŀ¼�Ƿ���� �����ھʹ���
					if (!file.exists())
					{
						file.mkdirs();
					}

					File apkFile = new File(mSavePath, "iBreath");
					FileOutputStream fos = new FileOutputStream(apkFile);

					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do{
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / length) * 100);

						// ���½���
						mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_PROGRESS);
						if (numread <= 0)
						{
							// �������
							mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_FINISH);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					}while(true);

					fos.close();
					is.close();
				}else{
					//���������ַ�
					mUrl = mVerSionCode.getArchiveFilePath();
					URL url = new URL(mUrl);

					// ��������
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();

					// ��ȡ�ļ���С
					int length = conn.getContentLength();

					//�ж��Ƿ����ļ� 
					if(0 == length){
						mDownloadDialog.dismiss();
						mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_ERROR);
					}
					// ����������
					InputStream is = conn.getInputStream();

					File file = mContext.getDir("iBreath", mContext.MODE_PRIVATE |
							mContext.MODE_WORLD_READABLE | mContext.MODE_WORLD_WRITEABLE);;
							mSavePath = file.getPath();
							// �ж��ļ�Ŀ¼�Ƿ���� �����ھʹ���
							if (!file.exists())
							{
								file.mkdirs();
							}

							File apkFile = new File(mSavePath, "iBreath");

							FileOutputStream fos = new FileOutputStream(apkFile);

							int count = 0;
							// ����
							byte buf[] = new byte[1024];
							// д�뵽�ļ���
							do{
								int numread = is.read(buf);
								count += numread;
								// ���������λ��
								progress = (int) (((float) count / length) * 100);

								// ���½���
								mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_PROGRESS);
								if (numread <= 0)
								{
									// �������
									mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_FINISH);
									break;
								}
								// д���ļ�
								fos.write(buf, 0, numread);
							}while(true);

							fos.close();
							is.close();

							//����apkȨ��Ϊ�ɶ��Ϳ�д
							String[] command = {"chmod","777",file.getPath() + "/iBreath"};
							ProcessBuilder builder = new ProcessBuilder(command);
							builder.start();
				}

			} catch (MalformedURLException e)
			{
				e.printStackTrace();
				mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_ERROR);
			} catch (IOException e)
			{
				e.printStackTrace();
				mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_ERROR);
			}
			// ȡ�����ضԻ�����ʾ
			mDownloadDialog.dismiss();
		}
	}; 

	/**
	 * ��װapk
	 */
	protected void installApk() {

		File apkfile = new File(mSavePath, "iBreath");

		LogCat.say("���մ洢λ��"+apkfile.getPath());
		if (!apkfile.exists())
		{
			return;
		}

		// ͨ��Intent��װAPK�ļ�
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}
}
