package com.my.apktool;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.my.apktool.Adapter.*;
import com.my.apktool.Utils.*;
import java.io.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import android.widget.AdapterView.*;
import android.view.*;

public class phoneStorageActivity extends AppCompatActivity
{
	Toolbar toolbar;
	ListView recyclerview;
	List<File> data = new ArrayList<File>();
	String rootpath;
	File[] files;
	Stack<String> nowPathStack;
	phoneStorageFileAdapter fileAdapter;
	int sortWay = FileSortFactory.SORT_BY_FOLDER_AND_NAME;
	
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
		Collections.sort(this.data, FileSortFactory.getWebFileQueryMethod(sortWay));
		
		fileAdapter = new phoneStorageFileAdapter(phoneStorageActivity.this,data,onClickListener);
		recyclerview.setAdapter(fileAdapter);
		
		recyclerview.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					// TODO: Implement this method
					
					File file = files[p3];
					if(file.isDirectory())
					{
						nowPathStack.push("/" + file.getName());
						showChanges(getPathString());
					}
				}
				});
	}
	
	private void showChanges(String path)
	{
		files = new File(path).listFiles();
        data.clear();
        for (File f : files)
		{
            data.add(f);
        }
		Collections.sort(this.data, FileSortFactory.getWebFileQueryMethod(sortWay));
        files = fileAdapter.setfiledata(data);
	}
	
	private String getPathString() 
	{
        Stack<String> temp = new Stack<>();
        temp.addAll(nowPathStack);
        String result = "";
        while (temp.size() != 0) 
		{
            result = temp.pop() + result;
        }
        return result;
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if(getPathString().equals(rootpath))
			{
				phoneStorageActivity.this.finish();
			}
			else
			{
				nowPathStack.pop();
				showChanges(getPathString());
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View p1)
		{
			// TODO: Implement this method
			ImageView iv2 = (ImageView) p1;
			int pos = (Integer) iv2.getTag();
			Toast.makeText(phoneStorageActivity.this,""+data.get(pos).getName(),3000).show();
		}
	};
}
