package com.uws.radd.activity;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
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
import com.uws.radd.data.Drink;
import com.uws.radd.service.ConnectionService;
import com.uws.radd.service.DatabaseService;
import com.uws.radd.service.DatabaseService.DatabaseBinder;


public class MixMenu extends Activity {
    final static int contentView = R.layout.activity_mix_menu;
    
	Bundle bundle;
	Drink[] drinks;
	private int id;
	private String name;
	private DatabaseService db;
	Spinner ounces;
	private Button submit;
	private String submitDrink;
	private String drinkId;
	private String cupSize;
	private  Button[] button;
	private boolean sending = false;
	//Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		setTitle("Mix Drink");
		
		bundle = getIntent().getExtras();
		id = (Integer) bundle.getInt("id");
		name = bundle.getString("name");
        drinkId = Integer.toString(id);

        Intent intent = new Intent(this, DatabaseService.class);
		bindService(intent, databaseConnection, Context.BIND_AUTO_CREATE);
	}
	
    private ServiceConnection databaseConnection = new ServiceConnection(){
    	public void onServiceConnected(ComponentName className, IBinder binder){
    		DatabaseBinder b = (DatabaseBinder) binder;
    		db = b.getService();
    		loadPage();
    	}
    	
    	public void onServiceDisconnected(ComponentName className){
    		db = null;
    	}
    };
    
    public void loadPage(){
		drinks = db.getAllDrinks();
		ounces = (Spinner) findViewById(R.id.spinner1);
		populateOunces();
		button();
		submit = (Button) findViewById(R.id.cancel);
		submitOnClickListener();
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

	public void submitOnClickListener(){
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cupSize = ounces.getSelectedItem().toString().split(" ")[0];
				submitDrink = "order|1234|" + drinkId + "|" +cupSize;
				
				//If a message is not being sent
				if(!sending){
					sending=true;
					Intent intent = new Intent(MixMenu.this, ConnectionService.class);
					intent.putExtra("action", ConnectionService.QUEUE_ACTION);
				    intent.putExtra("command",submitDrink);
				    startService(intent);
					
					//Wait 1 second before re-enabling send button
					Timer buttonTimer = new Timer();
					buttonTimer.schedule(new TimerTask(){
						@Override
						public void run(){
							sending=false;
						}
					}, 1000);
				}
			}
		});
	}
	
	public void button(){
		//Creating buttons for drinks listed in database
		LinearLayout linearLayout =  (LinearLayout)findViewById(R.id.mainButtonVertical);
		button = new Button[db.getDrinkCount()];
		for (int i=0 ; i<db.getDrinkCount(); i++)
		{
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(LinearLayout.HORIZONTAL);

			TextView textview = new TextView(this);
			//textview.setText("Text view" + i);
			textview.setId(i);

			l.addView(textview);
			button[i] = new Button(this);

			button[i].setId(Integer.parseInt(drinks[i].getId()));
			button[i].setText(drinks[i].getName());
			button[i].setTextSize(12);
			button[i].setWidth(150);
			button[i].setHeight(60);
			
			l.addView(button[i]);
			linearLayout.addView(l);   
			final Button b1 = ((Button) button[i]);
			button[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {

					TextView text = (TextView)findViewById(R.id.editText1);
					text.setTextColor(Color.WHITE);
					text.setWidth(300);
					text.setHeight(200);
					text.setText(db.selectDescriptionById(b1.getId()));
					text.setTextColor(Color.WHITE);
					drinkId = Integer.toString(b1.getId());
					TextView header = (TextView)findViewById(R.id.textView1);
					header.setText(b1.getText());    	     
				}
			});
		}
		TextView text = (TextView)findViewById(R.id.editText1);
		text.setTextColor(Color.WHITE);
		text.setWidth(300);
		text.setHeight(200);
		text.setText(db.selectDescriptionById(id));

		TextView header = (TextView)findViewById(R.id.textView1);
		header.setText(name);
	}

	

	public void setRecipeId(int i){
		drinkId = Integer.toString(i);
	}

	public void populateOunces(){
		
		ArrayList<String> ouncesList = new ArrayList<String>();
		ouncesList.add("1 Ounce");
		ouncesList.add("1.5 Ounces");
		for (int i = 2; i <= 24; i++)
		{
			if (i%2 == 0){
				ouncesList.add(Integer.toString(i) + " Ounces");
			}

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ouncesList);
		ounces.setBackgroundColor(Color.GRAY);
		ounces.setAdapter(adapter);

	}
}