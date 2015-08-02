package com.kewensheng.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 	This class is to controll thread
 * 
 * @author linwb
 */
public class ThreadUtil {
	
	private static ThreadUtil mInstance;
	private ExecutorService pool;
	
	private ThreadUtil(){
		pool = Executors.newCachedThreadPool();
	}
	
	public static ThreadUtil getInstance(){
		if (mInstance==null) {
			mInstance=new ThreadUtil();
		}
		return mInstance;
	}
	
	public ExecutorService getThreadPool(){
		return pool;
	}
	
	
	
}
