package com.my.apktool.apktool;
import android.content.*;
import java.io.*;
import java.util.logging.*;
import android.app.*;
import android.view.*;
import com.my.apktool.R;
import android.widget.*;
import android.view.View.*;

public abstract class Apktool extends Thread implements brut.util.Logger
{

	private ApktoolTips tips;
	
	public void ui(final int i)
	{
		((Activity)mContext).runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					switch(i)
					{
						case 0:
							on();
							break;
						case 1:
							off();
							break;
					}
				}
			});
	}
	
	private void on()
	{
		
		tips = new ApktoolTips(mContext,ApktoolTips.DECODER);
		tips.setInFile(getInFile());
		tips.setOutFile(getOutFile());
		tips.show();
		
	}
	private void off()
	{
		tips.postive();
	}
	
	
	
	public Apktool(Context mContext)
	{
		this.mContext = mContext;
	}

	public abstract String title();
	
	@Override
	public void error(int text, Object[] args)
	{
		// TODO: Implement this method
	}

	@Override
	public void log(Level wARNING, String format, Throwable ex)
	{
		
		
	}

	
	@Override
	public void fine(int id, Object[] args)
	{
		// TODO: Implement this method
	}

	@Override
	public void warning(int id, Object[] args)
	{
		// TODO: Implement this method
	}

	@Override
	public void info(int id, Object[] args)
	{
		
		add((String)args[0]);
	
		//setMessage((String)args[0]);
	}
	
	public void add(final String msg)
	{
		((Activity)mContext).runOnUiThread(new Runnable(){

				@Override
				public void run()
				{

					
					tips.add(msg);

				}
			});
	}

	public abstract String getInFile();
	public abstract String getOutFile();
	
	private Context mContext;
	
	
	
	
	
		
	
	
}
