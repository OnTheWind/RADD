package com.uws.radd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.uws.radd.R;
import com.uws.radd.adapter.ActionbarHelper;

public class Settings extends Activity{
	final static int contentView = R.layout.activity_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		
		Button back = (Button) findViewById(R.id.back);
		//back.setOnClickListener(new OnClickListener(){
		//	public void OnClick(View v){
		//		Intent previous = new Intent(v.getContext(), MainMenue.class);
		//		startActivityForResult(previous,0);
		//	}
		//});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return ActionbarHelper.click(this, item);
    }
}
