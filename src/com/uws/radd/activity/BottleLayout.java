package com.uws.radd.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.uws.radd.R;
import com.uws.radd.adapter.ActionbarHelper;
import com.uws.radd.data.QueuedDrink;
import com.uws.radd.service.ConnectionService;
import com.uws.radd.service.DatabaseService;
import com.uws.radd.service.DatabaseService.DatabaseBinder;

public class BottleLayout extends Activity {
	final static int contentView = R.layout.activity_bottle_layout;
	final private int numBottles = 9;
	
	private DatabaseService db;
	private Button[] bottleButtons;
	private int currentBottle;
	final Spinner ingredientSpinner = new Spinner(this);
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		setTitle("Bottle Layout");
		
		Intent intent = new Intent(this, DatabaseService.class);
		bindService(intent, databaseConnection, Context.BIND_AUTO_CREATE);
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
	
    private ServiceConnection databaseConnection = new ServiceConnection(){
    	public void onServiceConnected(ComponentName className, IBinder binder){
    		DatabaseBinder b = (DatabaseBinder) binder;
    		db = b.getService();
    		initialize();
    	}
    	
    	public void onServiceDisconnected(ComponentName className){
    		db = null;
    	}
    };
    
    public void initialize(){
    	buildSpinner();
    	
		String[] bottles = db.getCurrentBottles();    	
    	
		bottleButtons = new Button[numBottles];
		bottleButtons[0]=(Button)findViewById(R.id.bottle1);
		bottleButtons[1]=(Button)findViewById(R.id.bottle2);
		bottleButtons[2]=(Button)findViewById(R.id.bottle3);
		bottleButtons[3]=(Button)findViewById(R.id.bottle4);
		bottleButtons[4]=(Button)findViewById(R.id.bottle5);
		bottleButtons[5]=(Button)findViewById(R.id.bottle6);
		bottleButtons[6]=(Button)findViewById(R.id.bottle7);
		bottleButtons[7]=(Button)findViewById(R.id.bottle8);
		bottleButtons[8]=(Button)findViewById(R.id.bottle9);
		
    	for(int i=0; i<numBottles; i++){
    		if(bottles[i]!=null)
    			bottleButtons[i].setText(bottles[i]);
    		
    		bottleButtons[i].setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View view) {
    				currentBottle=view.getId();
    				new AlertDialog.Builder(BottleLayout.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
    	            .setTitle("Change Ingredient")
    	            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
    	                public void onClick(DialogInterface dialog, int which) {
    	                	Intent mixIntent = new Intent(BottleLayout.this, ConnectionService.class);
    	                	mixIntent.putExtra("action", ConnectionService.QUEUE_ACTION);
    	            	    mixIntent.putExtra("command","location|"+currentBottle+"|"+ingredientSpinner.getSelectedItem().toString().substring(0, 1));
    	            	    startService(mixIntent);
    	                }
    	             })
    	            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
    	                public void onClick(DialogInterface dialog, int which) {
    	                	
    	                }
    	             })
    	             .setView(ingredientSpinner)
    	             .show();
    			}
    		});
    	}
    }

	private void buildSpinner() {
		String[] ingredients=db.getAllSubstances();
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dark_spinner, ingredients);
    	ingredientSpinner.setBackgroundColor(0x444444);//Gray
    	ingredientSpinner.setAdapter(adapter);
	}

}
