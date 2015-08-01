package com.kewensheng.tool;

import android.util.Log;

public class LogCat {
	
	public static void say(String message){
	    Log.v("KeWen","Call  >> "+message);
	}
	public static void say(String tag,String message){
        Log.v(tag,"Call  >> "+message);
    }
	
}
