package com.my.apktool.Utils;
import android.annotation.*;
import android.os.*;
import java.io.*;
import java.util.*;

public class SortByFolderAndSize implements Comparator<File>
{
	boolean first;
	boolean second;

	public SortByFolderAndSize( boolean firstSequence,boolean secondSequence) 
	{
		this.first = firstSequence;
		this.second = secondSequence;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public int compare(File lhs, File rhs) 
	{
		if (first) 
		{
			if (!lhs.isFile() && rhs.isFile())
			{
				return -1;
			}
			if (lhs.isFile() && !rhs.isFile())
			{
				return 1;
			}
		} 
		else 
		{
			if (!lhs.isFile() && rhs.isFile())
			{
				return 1;
			}
			if (lhs.isFile() && !rhs.isFile()) 
			{
				return -1;
			}
		}

		if (second) 
		{
			if (lhs.isFile() && rhs.isFile()) 
			{
				return Long.compare(lhs.length(), rhs.length());
			}
		} 
		else 
		{
			if (lhs.isFile() && rhs.isFile())
			{
				return -Long.compare(lhs.length(), rhs.length());
			}
		}
		return 0;
	}
}
