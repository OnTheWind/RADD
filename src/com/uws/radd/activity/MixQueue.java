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

public class MixQueue extends Activity {
	final static int contentView = R.layout.activity_mix_queue;
	
	private DatabaseService db;

	ArrayList<Spinner> queuedDrinks=new ArrayList<Spinner>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		setTitle("Mix Queue");
		
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
    	
    	QueuedDrink[] queuedDrinks = db.getQueuedDrinks();
    	LinearLayout linearLayout =  (LinearLayout)findViewById(R.id.QueueList);
    	for(QueuedDrink queuedDrink : queuedDrinks){
    		LinearLayout l = new LinearLayout(this);
        	l.setOrientation(LinearLayout.HORIZONTAL);
        	
        	TextView userId = new TextView(this);
        	userId.setText(queuedDrink.getUserId()+"");
        	userId.setTextColor(Color.WHITE);
        	
        	TextView drinkName = new TextView(this);
        	drinkName.setText(queuedDrink.getDrinkName()+"");
        	drinkName.setTextColor(Color.WHITE);
        	
        	TextView cupSize = new TextView(this);
        	cupSize.setText(queuedDrink.getCupSize()+"");
        	cupSize.setTextColor(Color.WHITE);
        	
        	TextView status = new TextView(this);
        	status.setText(queuedDrink.getStatus()+"");
        	status.setTextColor(Color.WHITE);
        	
        	Button removeOrder = new Button(this);
        	removeOrder.setText("Remove");
        	removeOrder.setId(queuedDrink.getQueueId());
        	
        	removeOrder.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View view) {
    				Intent removeIntent = new Intent(MixQueue.this, ConnectionService.class);
    				removeIntent.putExtra("action", ConnectionService.QUEUE_ACTION);
    				removeIntent.putExtra("command", "cancel|"+view.getId());
    				startActivity(removeIntent);
    			}
    		});
        	
        	Button runOrder = new Button(this);
        	runOrder.setText("Run");
        	runOrder.setId(queuedDrink.getQueueId());
        	
        	runOrder.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View view) {
    				Intent removeIntent = new Intent(MixQueue.this, ConnectionService.class);
    				removeIntent.putExtra("action", ConnectionService.MIX_ACTION);
    				removeIntent.putExtra("command", "run|"+view.getId());
    				startActivity(removeIntent);
    			}
    		});
        	
        	l.addView(userId);
        	l.addView(drinkName);
        	l.addView(cupSize);
        	l.addView(status);
        	l.addView(runOrder);
        	l.addView(removeOrder);
        	
        	linearLayout.addView(l);
    	}
    }

}
