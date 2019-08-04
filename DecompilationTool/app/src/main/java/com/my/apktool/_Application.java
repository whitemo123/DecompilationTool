package com.my.apktool;
import android.app.*;
import android.content.*;

public class _Application extends Application
{
	public static Context mContext;

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		
		mContext = getApplicationContext();
		
		
	}
	
	
	
}
