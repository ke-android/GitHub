package com.kewensheng.controller;

/**
 *		To notify view that model is change 
 * 
 * @author linwb
 */
public interface IModelChangeListener {
	
	/**
	 * 	call it when model has changed;
	 * 
	 */
	public void onModelChanged(int action,Object... value);
	
}
