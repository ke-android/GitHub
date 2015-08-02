package com.kewensheng.controller;


import android.content.Context;

/**
 * 	A interface to get the data,such as netwrok_data,sqlite_data;
 * 
 * @author linwb
 */
public abstract class BaseController {
	
	private IModelChangeListener mChangeListener;
	protected Context mContext;
	
	public BaseController(Context context) {
		mContext = context;
	}
	
	public IModelChangeListener getmChangeListener() {
		return mChangeListener;
	}

	public void setIModelChangeListener(IModelChangeListener mChangeListener) {
		this.mChangeListener = mChangeListener;
	}

	/**
	 * 	sendSyncMessage pass view to controller;
	 */
	public void  sendMessage(final int action,final Object... values){
			handlerMessage(action, values);
	}
	
	/**
	 * 	sendAsyncMessage pass view to controller;
	 */
	public void sendAsyncMessage(final int action,final Object... values){
		class GetDataRunnable implements Runnable{
			@Override
			public  void run() {
				handlerMessage(action, values);
			}
		}
		ThreadUtil.getInstance().getThreadPool().submit(new GetDataRunnable());
	};
	
	/**
	 * 	Handler Message when Controller get A Sync or Async action;
	 * 
	 */
	public abstract void handlerMessage(int action,Object... values);
	
	/**
	 * 	Called it when your Model has dealed ,then the class that implement 
	 * 			IModelChangeListener will call method onModelChanged;
	 */
	public void notifyDataChange(int action,Object... values){
		if (mChangeListener!=null) {
			mChangeListener.onModelChanged(action, values);
		}
	};
	
}
