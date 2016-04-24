package com.uws.radd.activity;

import java.util.ArrayList;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uws.radd.R;
import com.uws.radd.adapter.ActionbarHelper;
import com.uws.radd.service.ConnectionService;
import com.uws.radd.service.DatabaseService;
import com.uws.radd.service.DatabaseService.DatabaseBinder;

public class CreateDrink extends Activity{
	final static int contentView = R.layout.activity_create_drink;
	
	private String[] substances;
	private DatabaseService db;
	ArrayList<String> list=new ArrayList<String>();
	ArrayList<Spinner> parts=new ArrayList<Spinner>();
	private Button cancel;
	private Button save;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		setTitle("Create Drink");
		
		Intent intent = new Intent(this, DatabaseService.class);
		bindService(intent, databaseConnection, Context.BIND_AUTO_CREATE);
		
		cancel = (Button) findViewById(R.id.Cancel);
		save = (Button) findViewById(R.id.Save);
		
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CreateDrink.this, TileMenu.class));				
			}
		});
		
		//add to database and to main page
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText drinkName = (EditText) findViewById(R.id.editText2);
				String drink = drinkName.getText().toString();
				String description = " ";
				
				String drinkQuery="new"+drink;
				
				for(int i=0; i<list.size(); i++){
					String substanceName = list.get(i);
					String part = parts.get(i).getSelectedItem().toString().substring(0, 1);
					drinkQuery+="|"+substanceName+":"+part;
				}
				
				Toast.makeText(getApplicationContext(), "Added "+drinkQuery, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(CreateDrink.this, ConnectionService.class);
				intent.putExtra("action", ConnectionService.QUERY_ACTION);
			    intent.putExtra("command",drinkQuery);
			    //startService(intent);
			    
				startActivity(new Intent(CreateDrink.this, TileMenu.class));
			}
		});
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
    	substances = new String[db.getSubstanceCount()];
		substances = db.getAllSubstances();
		LoadRecipeList();
    }
    
    public void populateParts(Spinner spinner){
    	ArrayList<String> parts = new ArrayList<String>();
    	for(int i=1; i<10; i++){
    		parts.add(Integer.toString(i)+" Parts");
    	}
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dark_spinner, parts);

    	spinner.setBackgroundColor(0x444444);//Gray
    	spinner.setAdapter(adapter);
    }
	
	public void LoadRecipeList(){
		//Creating buttons for drinks listed in database
        LinearLayout linearLayout =  (LinearLayout)findViewById(R.id.recipe_list);
        Button[] substanceButtons = new Button[substances.length];
        
        for (int i=0 ; i<substances.length; i++)
        {
        	LinearLayout l = new LinearLayout(this);
        	l.setOrientation(LinearLayout.HORIZONTAL);

        	TextView textview = new TextView(this);
        	textview.setId(i);

        	l.addView(textview);
        	substanceButtons[i] = new Button(this);
        	
        	substanceButtons[i].setText(substances[i]);  
        	substanceButtons[i].setTextColor(Color.WHITE);
        	substanceButtons[i].setTextSize(12);
        	substanceButtons[i].setWidth(300);
        	substanceButtons[i].setHeight(60);
        	
        	l.addView(substanceButtons[i]);
        	linearLayout.addView(l);
        	      	  	
        	        	
        	substanceButtons[i].setOnClickListener(new View.OnClickListener() {
        		//Onclick adds to ingrident list
    	        public void onClick(View view) {
    	            //Context context = getApplicationContext();
    	        	
    	        	Button substanceButton = (Button)view;
    	        	String substanceName = substanceButton.getText().toString();
    	        	
    	        	Boolean exist=false;
    	        	//Check if ingredient already exists
    	        	for(int i=0; i<list.size(); i++){
    	        		if(list.get(i).equals(substanceName)){
    	        			exist=true;
    	        			break;
    	        		}
    	        	}
    	        	
    	        	if(exist){    	        		
        	        	Toast.makeText(CreateDrink.this,"Ingredient already exists!!",Toast.LENGTH_SHORT).show();
    	        	}
    	        	else{
    	        		LinearLayout ingredientLayout =  (LinearLayout)findViewById(R.id.ingredient_list);
    	        		final LinearLayout substance = new LinearLayout(CreateDrink.this);
    	        		final Button ingredientButton = new Button(CreateDrink.this);
    	        		ingredientButton.setText(substanceName);
    	        		ingredientButton.setTextSize(12);
    	        		ingredientButton.setId(substances.length);
    	        		ingredientButton.setWidth(300);
    	        		ingredientButton.setHeight(60);
    	        		ingredientButton.setTextColor(Color.WHITE);
        	        	
        	        	final Spinner partSpinner = new Spinner(CreateDrink.this);
        	        	populateParts(partSpinner);
        	        	substance.addView(partSpinner);
        	        	parts.add(partSpinner);
        	        	substance.addView(ingredientButton);
        	        	ingredientLayout.addView(substance);
        	        	list.add(substanceName);        	        	
    	        	   	        	
	    	        	//Remove ingredient when clicked 
        	        	
        	        	ingredientButton.setOnClickListener(new View.OnClickListener(){
	    	        		public void onClick(View view) {
	    	        			for(int i=0; i<list.size(); i++){
	    	        				if(list.get(i).equals(ingredientButton.getText().toString())){
	    	        					list.remove(i);
	    	        					parts.remove(i);
	    	        				}
	    	        			}
	    	        			substance.removeAllViews();
	    	        		}
	    	        	});
    	        	}
    	        }
    	        
        	});
        	list.clear();
        }
	}
	//Save
	//Order
	//Cancel
	
	
}
