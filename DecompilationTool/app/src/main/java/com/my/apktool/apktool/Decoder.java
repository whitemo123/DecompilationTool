package com.my.apktool.apktool;
import android.content.*;
import brut.androlib.*;
import java.io.*;

public class Decoder extends Apktool
{

	
	private String apkFile;
	private String outFile;

	public Decoder(Context mContext,String apkFile, String outFile)
	{
		super(mContext);
		this.apkFile = apkFile;
		this.outFile = outFile;
	}
	
	@Override
	public String title()
	{
		// TODO: Implement this method
		return "反编译";
	}

	@Override
	public String getInFile()
	{
		// TODO: Implement this method
		return apkFile;
	}

	@Override
	public String getOutFile()
	{
		// TODO: Implement this method
		return outFile;
	}

	@Override
	public void run()
	{
		super.run();
		
		ui(0);
		
		ApkDecoder decode = new ApkDecoder(this);
		decode.setFrameworkDir("/sdcard/.aide/");
		decode.setApkFile(new File(apkFile));
		decode.setOutDir(new File(outFile));
		try
		{
			decode.decode();
		}
		catch (AndrolibException e)
		{
			e.printStackTrace();
		}

		ui(1);
		
	}
	
	
	
}
