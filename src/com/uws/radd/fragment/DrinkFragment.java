package com.uws.radd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uws.radd.R;
 
public class DrinkFragment extends Fragment {
	final static int contentView = R.layout.drink_fragment;
	
    int fragVal;
 
    public static DrinkFragment newInstance(String title) {
        DrinkFragment df = new DrinkFragment();
        Bundle b = new Bundle();
        b.putString("title", title);
        df.setArguments(b);
        return df;
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View layoutView = inflater.inflate(contentView, container,
                false);
       
        return layoutView;
    }
}