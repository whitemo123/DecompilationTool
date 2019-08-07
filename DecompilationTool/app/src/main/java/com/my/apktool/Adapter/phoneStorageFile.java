package com.my.apktool.Adapter;

public class phoneStorageFile
{
	private int fileImage;
	private String fileSize,fileTime,fileName,filePath,lastPath;
	
	public phoneStorageFile(String fileName,int fileImage,String fileTime,String fileSize,String filePath,String lastPath)
	{
		this.fileImage = fileImage;
		this.fileSize = fileSize;
		this.fileTime = fileTime;
		this.fileName = fileName;
		this.filePath = filePath;
		this.lastPath = lastPath;
	}
	
	public int getFileImage()
	{
		return fileImage;
	}
	
	public String getFileSize()
	{
		return fileSize;
	}
	
	public String getFileTime()
	{
		return fileTime;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public String getLastPath()
	{
		return lastPath;
	}
	
	public void setFileImage(int fileImage)
	{
		this.fileImage = fileImage;
	}
	
	public void setFileTime(String fileTime)
	{
		this.fileTime = fileTime;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public void setFileSize(String fileSize)
	{
		this.fileSize = fileSize;
	}
	
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
	
	public void setLastPath(String lastPath)
	{
		this.lastPath = lastPath;
	}
}
