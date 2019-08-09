package com.my.apktool.apktool;
import java.io.*;
import android.content.*;
import brut.androlib.*;
import brut.androlib.res.*;

public class InstallFramework extends Apktool
{

	@Override
	public String getInFile()
	{
		// TODO: Implement this method
		return frameworkFile;
	}

	@Override
	public String getOutFile()
	{
		// TODO: Implement this method
		return frameworkFile;
	}

	
	private String frameworkFile;
	private String frameworkDir;

	public InstallFramework(Context mContext,String frameworkFile, String frameworkDir)
	{
		super(mContext);
		this.frameworkFile = frameworkFile;
		this.frameworkDir = frameworkDir;
	}
	
	
	@Override
	public void run()
	{
		
		//显示提示框
		
		
		
		ui(0);
		
		ApkOptions opt = ApkOptions.INSTANCE;
		opt.frameworkFolderLocation = this.frameworkDir;
		AndrolibResources res = new AndrolibResources(this);
		res.apkOptions = opt;
		
		try
		{
			res.installFramework(new File(this.frameworkFile));
		}
		catch (AndrolibException e)
		{
			e.printStackTrace();
		}

		ui(1);
		
		//关闭提示框
		
	}

	public String title()
	{
		return "安装框架";
	}
	
	
}

