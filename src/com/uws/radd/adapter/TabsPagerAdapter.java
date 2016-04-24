package com.uws.radd.adapter;

import com.uws.radd.fragment.DrinkFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Fragment getItem(int position) {
        return DrinkFragment.newInstance(Integer.toString(position));
    }
	

	public int getCount(){
		return 10;
	}
	
	public CharSequence getPageTitle(int position){
		return "Page" + position;
	}
}
