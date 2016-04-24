package com.uws.radd.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.uws.radd.R;
import com.uws.radd.adapter.ActionbarHelper;
import com.uws.radd.data.Drink;
import com.uws.radd.service.DatabaseService;
import com.uws.radd.service.DatabaseService.DatabaseBinder;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 *  
 */
public class TileMenu extends Activity {
    final static int contentView = R.layout.activity_tile_menu;
    
    private DatabaseService db;
	
    private Drink[] drinks;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(contentView);
		setTitle("Menu");
		
		Intent intent = new Intent(this, DatabaseService.class);
		bindService(intent, databaseConnection, Context.BIND_AUTO_CREATE);
	}
    
    private ServiceConnection databaseConnection = new ServiceConnection(){
    	public void onServiceConnected(ComponentName className, IBinder binder){
    		DatabaseBinder b = (DatabaseBinder) binder;
    		db = b.getService();
    		createGrid();
    	}
    	
    	public void onServiceDisconnected(ComponentName className){
    		db = null;
    	}
    };
    
    @Override
    protected void onDestroy(){
    	unbindService(databaseConnection);
    	super.onDestroy();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return ActionbarHelper.click(this, item);
    }
    
    public void createGrid(){
		Log.i("db drink count",db.getDrinkCount()+"");
		drinks = db.getAllDrinks();
		createButtons();
    }
    
	protected void createButtons(){
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		 LayoutInflater inflater = getLayoutInflater();
		TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);
		
		TableRow tr = new TableRow(this);
		tr.setGravity(Gravity.CENTER);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
       params.setMargins(1,1,1,1);
		
		for (int i = 0; i < db.getDrinkCount(); i++) {
			if((i%4)==0){
				tr = new TableRow(this);
				tr.setGravity(Gravity.CENTER);
				tl.addView(tr);
			}
			    Button b = (Button) inflater.inflate((R.layout.main_button),null);
			    
			    b.setId(Integer.parseInt(drinks[i].getId()));
			    b.setWidth(width/4);
			    b.setHeight(width/4);
			    final int id_ = b.getId();
			    b.setText(drinks[i].getName());
			    b.setTextSize(20);
			   
			    tr.addView(b);
			   
			    final Button b1 = ((Button) findViewById(id_));
			    b1.setOnClickListener(new View.OnClickListener() {
			        public void onClick(View view) {
			            Intent i = new Intent(TileMenu.this, MixMenu.class);
			            i.putExtra("id", b1.getId());
			            i.putExtra("name", b1.getText());
			            db.close();
			            startActivity(i);
			        }
			    });   
		}
		Button addDrink = (Button) inflater.inflate((R.layout.main_button),null);
	    
		addDrink.setId(1234);
		addDrink.setWidth(width/4);
		addDrink.setHeight(width/4);
	    final int id_ = addDrink.getId();
	    addDrink.setText("+");
	    addDrink.setTextSize(40);;
	   
	    tr.addView(addDrink);
	   
	    final Button addDrink1 = ((Button) findViewById(id_));
	    addDrink1.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            Intent i = new Intent(TileMenu.this, CreateDrink.class);
	            i.putExtra("id", addDrink1.getId());
	            i.putExtra("name", addDrink1.getText());
	            db.close();
	            startActivity(i);
	        }
	    });
		
	}
}
