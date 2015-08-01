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

	//    //获取到的版本信息
	//    private HashMap<String, Object> mUpdataList;

	private VerSionCodeCls mVerSionCode;

	//dialog
	private Dialog mDownloadDialog;

	//下载url
	private String   mUrl;

	//保存路径
	private String mSavePath;

	//进度
	private int progress;

	//进度条
	private ProgressBar mProgress;

	//进度数值
	private  TextView mTvUpdate;

	//handler用来显示下载时的实时情况
	private Handler mUpdateHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// 正在下载
			case IdiyMessage.UPDATE_PROGRESS:
				// 设置进度条位置
				mProgress.setProgress(progress);
				mTvUpdate.setText(progress+"%");
				break;
			case IdiyMessage.UPDATE_FINISH:
				// 安装文件
				installApk();
				break;
			case IdiyMessage.UPDATE_ERROR:
				//                NoticeUtil.warnTipStr(mContext, "网络错误");
				BaseToast.toastSay(mContext, "网络错误");
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
	 * 显示下载弹窗
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
		//下面空格输入url的ID
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
				//下面空格输入url的ID
				mUrl = mVerSionCode.getArchiveFilePath();
				LogCat.say("更新的url"+mUrl);
				if("".equals(mUrl)||mUrl == null){
					//                    NoticeUtil.warnTipStr(mContext, "不能下载");
					BaseToast.toastSay(mContext, "不能下载");
				}else{
					showDownloadDialog();                    
				}
			}
		});
	}



	public void showDownloadDialog() {

		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(mContext);
		//        builder.setTitle("正在下载");
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_soft_progressbar, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mTvUpdate = (TextView) v.findViewById(R.id.number_progress);
		builder.setView(v);
		builder.setCancelable(false);
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 下载文件
		downloadApk();
	}
	protected void downloadApk() {
		new downloadApkThread().start();
	}

	/**
	 * 下载文件
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
				// 判断SD卡是否存在，并且是否具有读写权限
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// 获得存储卡的路径
					mSavePath = Environment.getExternalStorageDirectory() + "/iBreath/download";
					//下面输入字符
					URL url = new URL(mVerSionCode.getArchiveFilePath());

					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();

					// 获取文件大小
					int length = conn.getContentLength();

					//判断是否有文件 
					if(0 == length){
						mDownloadDialog.dismiss();
						mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_ERROR);
					}
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在 不存在就创建
					if (!file.exists())
					{
						file.mkdirs();
					}

					File apkFile = new File(mSavePath, "iBreath");
					FileOutputStream fos = new FileOutputStream(apkFile);

					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do{
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);

						// 更新进度
						mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_PROGRESS);
						if (numread <= 0)
						{
							// 下载完成
							mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					}while(true);

					fos.close();
					is.close();
				}else{
					//下面输入字符
					mUrl = mVerSionCode.getArchiveFilePath();
					URL url = new URL(mUrl);

					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();

					// 获取文件大小
					int length = conn.getContentLength();

					//判断是否有文件 
					if(0 == length){
						mDownloadDialog.dismiss();
						mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_ERROR);
					}
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = mContext.getDir("iBreath", mContext.MODE_PRIVATE |
							mContext.MODE_WORLD_READABLE | mContext.MODE_WORLD_WRITEABLE);;
							mSavePath = file.getPath();
							// 判断文件目录是否存在 不存在就创建
							if (!file.exists())
							{
								file.mkdirs();
							}

							File apkFile = new File(mSavePath, "iBreath");

							FileOutputStream fos = new FileOutputStream(apkFile);

							int count = 0;
							// 缓存
							byte buf[] = new byte[1024];
							// 写入到文件中
							do{
								int numread = is.read(buf);
								count += numread;
								// 计算进度条位置
								progress = (int) (((float) count / length) * 100);

								// 更新进度
								mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_PROGRESS);
								if (numread <= 0)
								{
									// 下载完成
									mUpdateHandler.sendEmptyMessage(IdiyMessage.UPDATE_FINISH);
									break;
								}
								// 写入文件
								fos.write(buf, 0, numread);
							}while(true);

							fos.close();
							is.close();

							//设置apk权限为可读和可写
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
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	}; 

	/**
	 * 安装apk
	 */
	protected void installApk() {

		File apkfile = new File(mSavePath, "iBreath");

		LogCat.say("最终存储位置"+apkfile.getPath());
		if (!apkfile.exists())
		{
			return;
		}

		// 通过Intent安装APK文件
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}
}
