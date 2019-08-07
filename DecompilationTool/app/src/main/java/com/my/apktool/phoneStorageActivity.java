package com.my.apktool;
import android.support.v7.app.*;
import android.os.*;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;

public class phoneStorageActivity extends AppCompatActivity
{
	Toolbar toolbar;
	RecyclerView recyclerview;
	
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
		recyclerview = (RecyclerView) findViewById(R.id.phonestorage_recyclerview);
	}
	
	private void Initialization()
	{
		setSupportActionBar(toolbar);
	}
}
