package com.my.apktool;
import android.support.v7.app.*;
import android.os.*;
import android.support.v7.widget.Toolbar;
import java.util.*;
import java.io.*;
import com.my.apktool.Adapter.*;
import android.widget.*;

public class phoneStorageActivity extends AppCompatActivity
{
	Toolbar toolbar;
	ListView recyclerview;
	List<File> data = new ArrayList<File>();
	String rootpath;
	File[] files;
	Stack<String> nowPathStack;
	phoneStorageFileAdapter fileAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonestorage);
		
		init();
		
		Initialization();
	}

	private void init()
	{
		// TODO: Implement this method
		toolbar = (Toolbar) findViewById(R.id.phonestorage_toolbar);
		recyclerview = (ListView) findViewById(R.id.phonestorage_listview);
	}
	
	private void Initialization()
	{
		setSupportActionBar(toolbar);
		rootpath = Environment.getExternalStorageDirectory().toString();
        nowPathStack = new Stack<>();
		files = Environment.getExternalStorageDirectory()
			.listFiles();
		nowPathStack.push(rootpath);
        for (File f : files)
		{
			data.add(f);
		}
		
		fileAdapter = new phoneStorageFileAdapter(phoneStorageActivity.this,data);
		recyclerview.setAdapter(fileAdapter);
	}
}
