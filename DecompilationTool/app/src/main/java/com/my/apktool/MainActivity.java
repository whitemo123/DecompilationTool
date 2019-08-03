package com.my.apktool;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.*;
import android.support.design.widget.*;
import android.view.*;

public class MainActivity extends AppCompatActivity
{
	Toolbar toolbar;
	DrawerLayout drawerLayout;
	NavigationView navigationView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		init();
		
		Initialization();
		
		listener();
    }
	
	private void init()
	{
		// TODO: Implement this method
		toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		navigationView = (NavigationView) findViewById(R.id.main_navigationview);
	}
	
	private void Initialization()
	{
		// TODO: Implement this method
		setSupportActionBar(toolbar);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,
			drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
		drawerLayout.setDrawerListener(toggle);
		toggle.syncState();
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
