package com.iti.ioiobot;

import android.os.Handler;

public class BotHandler extends Handler{
	private static Handler mHandler;
	public static Handler getBotHandler()
	{
		return mHandler;
	}
	
	public BotHandler (Handler h) 
	{
		mHandler = h;
	}
}