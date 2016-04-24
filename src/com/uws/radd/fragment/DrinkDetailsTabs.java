package com.uws.radd.fragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.uws.radd.R;
import com.uws.radd.adapter.TabsPagerAdapter;
public class DrinkDetailsTabs extends FragmentActivity implements ActionBar.TabListener {
	final static int contentView = R.layout.drink_details_tabs;
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	private String[] tabs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		setTitle("Drink Details");
		
		Bundle bundle = getIntent().getExtras();
		if(bundle.getCharSequenceArray("drinks") != null){
			tabs = (String[]) bundle.getCharSequenceArray("drinks");
		}
		
		viewPager = (ViewPager) findViewById(R.id.pager);
	//	actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(mAdapter);
		//actionBar.setHomeButtonEnabled(false);
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String tab_name: tabs){
			//actionBar.addTab(actionBar.newTab().setTabListener(this));
		}
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override 
			public void onPageSelected(int position){
				actionBar.setSelectedNavigationItem(position);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2){
				
			}
			
			public void onPageScrollStateChanged(int arg0){
				
			}
		});
	}
	
	@Override
	public void onTabReselected(Tab tb, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
