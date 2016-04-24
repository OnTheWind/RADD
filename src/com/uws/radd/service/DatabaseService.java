package com.uws.radd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.uws.radd.dao.DatabaseHelper;
import com.uws.radd.data.Drink;
import com.uws.radd.data.QueuedDrink;

public class DatabaseService extends Service{
	public final static String DATABASE_NAME = "Radd_Database";
	public final static String DB_PATH = Environment.getExternalStorageDirectory()+"/radd/";//"/data/data/com.example.radd/databases/";
	
	private final IBinder databaseBinder = new DatabaseBinder();
	private DatabaseHelper dbHelper;
	protected SQLiteDatabase db;
	
	private final static String DRINK_TABLE = "drinkTable";
	private final static String RECIPE_TABLE = "recipeTable";
	private final static String SUBSTANCE_TABLE = "substanceTable";
	private final static String LOCATION_TABLE = "locationTable";
	private final static String QUEUE_TABLE = "queueTable";
	
	public void onCreate(){
		super.onCreate();
		Log.i("Service","Created");
		open();
	}
	
	public void onDestroy(){
		Log.i("Service", "Destroyed");
		db.close();
		super.onDestroy();
	}
	
	@Override
	public void onStart(Intent intent, int startId){
		handleIntent(intent);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		handleIntent(intent);
		return START_STICKY;
	}
	
	protected void handleIntent(Intent intent) {
		Log.i("service","intent recieved");

	}

	@Override
	public IBinder onBind(Intent intent) {
		return databaseBinder;
	}
	
	public class DatabaseBinder extends Binder {
		public DatabaseService getService() {
			return DatabaseService.this;
		}
	}
	
	public void open() throws SQLException {
		dbHelper = new DatabaseHelper(this);
        try {
        	dbHelper.createDataBase();
        } catch (IOException ioe) {
        	Toast.makeText(this, "Unable to create database. Exception: "+ioe, Toast.LENGTH_LONG).show();
            throw new Error("Unable to create database");
        }

        try {
        	String myPath = DB_PATH + DATABASE_NAME;
        	db = this.openOrCreateDatabase(myPath, 0, null);
        } catch (SQLException sqle) {
        	Toast.makeText(this, "Unable to create database. Exception: "+sqle, Toast.LENGTH_LONG).show();
            throw sqle;
        }
    }
	
	public void close() {
        dbHelper.close();
    }
	
	public void runQuery(String query){
		try{
			db.execSQL(query);
		}catch(Exception e){
			Toast.makeText(this, "Unable to Update Database. Exception: "+e, Toast.LENGTH_LONG).show();
		}
	}
	
	public Integer getDrinkCount(){
		Cursor countCursor;
		String count = "";
		try{
			Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

			if (c.moveToFirst()) {
			    while ( !c.isAfterLast() ) {
			        Log.i("db","Table Name=> "+c.getString(0));
			        c.moveToNext();
			    }
			}
			
			countCursor = db.rawQuery("SELECT COUNT(*) FROM "+DRINK_TABLE, new String [] {});
			countCursor.moveToFirst();
			count = countCursor.getString(0);
		}catch(Exception e){
			Log.e("db","exception in count drinkTable");
		}
		return Integer.parseInt(count);
	}
	
	public Integer getSubstanceCount(){
		Cursor countCursor;
		String count = "";
		try{
			countCursor = db.rawQuery("SELECT COUNT(*) FROM "+SUBSTANCE_TABLE, new String [] {});
			countCursor.moveToFirst();
			count = countCursor.getString(0);
		}catch(Exception e){
			Toast.makeText(this, "Error when getting count. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return Integer.parseInt(count);
	}
		
	public String selectDescriptionById( Integer id ){
		Cursor descriptionCursor;
		String recipeDescription = "";
		try{
			descriptionCursor = db.query(DRINK_TABLE, new String[] {"description"}, "drinkId" + "=?", new String[]{id.toString()}, null, null, null);
			descriptionCursor.moveToFirst();
			recipeDescription = descriptionCursor.getString(0);
		}catch(Exception e){
			Toast.makeText(this, "Error when searching for description. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return recipeDescription;
	}
	
	public String selectDrinkNameById( Integer id ){
		Cursor descriptionCursor;
		String drinkName = "";
		try{
			descriptionCursor = db.query(DRINK_TABLE, new String[] {"drinkName"}, "drinkId" + "=?", new String[]{id.toString()}, null, null, null);
			descriptionCursor.moveToFirst();
			drinkName = descriptionCursor.getString(0);
		}catch(Exception e){
			Toast.makeText(this, "Error when searching for drink name. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return drinkName;
	}
	
	public Drink[] getAllDrinks(){
		Cursor infoCursor;
		ArrayList<Drink> drinkNames = new ArrayList<Drink>();
		try{
			infoCursor = db.query(DRINK_TABLE, new String[] {"drinkId","drinkName"},  null, null, null, null, null);
			if (infoCursor != null)
				infoCursor.moveToFirst();
			for(int i=0;i<infoCursor.getCount();i++){
				drinkNames.add(new Drink(infoCursor.getString(0),infoCursor.getString(1)));
				infoCursor.moveToNext();
			}
		}catch(Exception e){
			Toast.makeText(this, "Error when searching all recipes. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return drinkNames.toArray(new Drink[drinkNames.size()]);
	}
	
	public String[] getAllSubstances(){
		Cursor infoCursor;
		ArrayList<String> substanceNames = new ArrayList<String>();
		try{
			infoCursor = db.query(SUBSTANCE_TABLE, new String[] {"subName"},  "subName IS NOT NULL", null, null, null, null);
			if (infoCursor != null)
				infoCursor.moveToFirst();
			for(int i=0;i<infoCursor.getCount();i++){
				substanceNames.add(infoCursor.getString(0));
				infoCursor.moveToNext();
			}
		}catch(Exception e){
			Toast.makeText(this, "Error when searching all recipes. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return (String[]) substanceNames.toArray(new String[substanceNames.size()]);
	}
	
	public String[] getCurrentBottles(){
		Cursor locationCursor;
		ArrayList<String> currentBottles = new ArrayList<String>();
		try{
			locationCursor = db.query(LOCATION_TABLE, new String[] {"subName"}, null, null, null, null, null);
			if(locationCursor != null)
				locationCursor.moveToFirst();
			for(int i=0;i<locationCursor.getCount();i++){
				currentBottles.add(locationCursor.getString(0));
				locationCursor.moveToNext();
			}
		}catch(Exception e){
			Toast.makeText(this, "Error when searching for current bottles. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return (String[]) currentBottles.toArray(new String[currentBottles.size()]);
	}
	
	public QueuedDrink[] getQueuedDrinks(){
		Cursor locationCursor;
		ArrayList<QueuedDrink> queuedDrinks = new ArrayList<QueuedDrink>();
		try{
			locationCursor = db.query(QUEUE_TABLE, new String[] {"queueId","userId","drinkId","cupeSize","status"}, "status=0", null, null, null, null);
			if(locationCursor != null)
				locationCursor.moveToFirst();
			for(int i=0; i<locationCursor.getCount();i++){
				int queueId = Integer.parseInt(locationCursor.getString(0));
				int userId = Integer.parseInt(locationCursor.getString(1));
				int drinkId = Integer.parseInt(locationCursor.getString(2));
				String drinkName = selectDrinkNameById(drinkId);
				String cupSize = locationCursor.getString(3);
				int status = Integer.parseInt(locationCursor.getString(4));
				queuedDrinks.add(new QueuedDrink(queueId, userId, drinkName, cupSize, status));
			}
		}catch(Exception e){
			Toast.makeText(this, "Error when searching for queued drinks. Exception: "+e, Toast.LENGTH_LONG).show();
		}
		return queuedDrinks.toArray(new QueuedDrink[queuedDrinks.size()]);
	}
}
