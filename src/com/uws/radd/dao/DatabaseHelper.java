package com.uws.radd.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.uws.radd.service.DatabaseService;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static SQLiteDatabase db;
	
	Context helperContext;
	
	public DatabaseHelper(Context context){		
		super(context, DatabaseService.DATABASE_NAME, null, 33);
		helperContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  onCreate(db);
	}
	
	public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
	
	private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = helperContext.getAssets().open(DatabaseService.DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DatabaseService.DB_PATH + DatabaseService.DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
	
	private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DatabaseService.DB_PATH + DatabaseService.DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
	
	@Override
    public synchronized void close() {
        if (db != null)
        	db.close();

        super.close();
    }
}
