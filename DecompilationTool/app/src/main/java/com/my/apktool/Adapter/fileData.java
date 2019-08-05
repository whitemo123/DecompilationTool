package com.my.apktool.Adapter;

public class fileData
{
	/*
	apkIcon 软件图标
	fileName 文件名字
	fileTime 最后一次修改时间
	fileSize 文件大小
	*/
	private int apkIcon;
	private String fileName;
	private long fileTime,fileSize;
	
	public int getApkIcon()
	{
		return apkIcon;
	}
	
	public void setApkIcon(int apkIcon)
	{
		this.apkIcon = apkIcon;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public long getFileTime()
	{
		return fileTime;
	}
	
	public void setFileTime(long fileTime)
	{
		this.fileTime = fileTime;
	}
	
	public long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
	}
}
