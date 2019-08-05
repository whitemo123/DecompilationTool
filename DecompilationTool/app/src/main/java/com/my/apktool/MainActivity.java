package com.my.apktool;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.*;
import android.support.design.widget.*;
import android.view.*;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import java.util.*;
import com.my.apktool.Adapter.*;

public class MainActivity extends AppCompatActivity
{
	Toolbar toolbar;
	DrawerLayout drawerLayout;
	NavigationView navigationView;
	RecyclerView recyclerView;
	GridLayoutManager gridLayoutManager;
	List<fileData> data = new ArrayList<fileData>();
	fileAdapter mfileAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		init();
		
		dealData();//加载历史项目
		
		Initialization();
		
		listener();
    }

	private void dealData()
	{
		// TODO: Implement this method
		for(int i=0;i<5;i++)
		{
			fileData mobai = new fileData();
			mobai.setApkIcon(R.drawable.android);
			mobai.setFileName("文件"+i);
			mobai.setFileTime(201983);
			mobai.setFileSize(111111);
			data.add(mobai);
		}
	}
	
	private void init()
	{
		// TODO: Implement this method
		toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		navigationView = (NavigationView) findViewById(R.id.main_navigationview);
		recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
	}
	
	private void Initialization()
	{
		// TODO: Implement this method
		setSupportActionBar(toolbar);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,
			drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
		drawerLayout.setDrawerListener(toggle);
		toggle.syncState();
		
		gridLayoutManager = new GridLayoutManager(MainActivity.this,1,GridLayoutManager.VERTICAL,false);
		recyclerView.setLayoutManager(gridLayoutManager);
		
		mfileAdapter = new fileAdapter(MainActivity.this,data);
		recyclerView.setAdapter(mfileAdapter);
	}
	
	private void listener()
	{
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

				@Override
				public boolean onNavigationItemSelected(MenuItem p1)
				{
					// TODO: Implement this method
					return false;
				}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.menu_item,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		
		return true;
	}
}
