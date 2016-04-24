package com.uws.radd.service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.uws.radd.service.DatabaseService.DatabaseBinder;

public class ConnectionService extends Service {
	//public final static int MASTER_ACTION = 0;
	public final static int DEFAULT_ACTION = -1;
	public final static int QUEUE_ACTION = 0;
	public final static int MIX_ACTION = 1;
	public final static int QUERY_ACTION = 2;
	
	public final static String PI_IP = "10.10.0.1";
	public final static int MASTER_PORT = 5000;
	public final static int MIX_PORT = 5001;
	
	public static String MIX_COMMAND = "run";
	
	private Thread masterThread;
	private Thread mixThread;
	private BufferedReader masterIn = null;
    private PrintWriter masterOut = null;
    private BufferedReader mixIn = null;
    private PrintWriter mixOut = null;
    private String name;
    private DatabaseService db;

	public ConnectionService() {
		super();
	}
	
	public void onCreate(){
		super.onCreate();
		Log.i("Service","Created");
		
		Intent intent = new Intent(this, DatabaseService.class);
		bindService(intent, databaseConnection, Context.BIND_AUTO_CREATE);
		
		name = "Computer" + (int) Math.random() * 1000;
		masterThread = new Thread(new MasterThread());
		masterThread.start();
		mixThread = new Thread(new MixThread());
		mixThread.start();
		while(masterOut==null||mixOut==null){
		}
		try{
			masterOut.println(name);
			mixOut.println(name);
		}
		catch(Exception e){
			Log.e("Service","problem printing name to master or mix");
		}
	}
	
	public void onDestroy(){
		Log.i("Service", "Destroyed");
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
		while(masterOut==null||mixOut==null){
		}
		Log.i("ConnectionService","handle Intent");
		int action = intent.getIntExtra("action", DEFAULT_ACTION);
		switch(action){
		default:
			break;
		case QUEUE_ACTION:
			String queueCommand = intent.getStringExtra("command");
			masterOut.println(queueCommand);
			break;
		case MIX_ACTION:
			String mixCommand = intent.getStringExtra("command");
			mixOut.println(mixCommand);
			break;
		case QUERY_ACTION:
			String queryCommand = intent.getStringExtra("command");
			masterOut.println(queryCommand);
			break;
		}
	}
	
    private ServiceConnection databaseConnection = new ServiceConnection(){
    	public void onServiceConnected(ComponentName className, IBinder binder){
    		DatabaseBinder b = (DatabaseBinder) binder;
    		db = b.getService();
    		
    	}
    	
    	public void onServiceDisconnected(ComponentName className){
    		db = null;
    	}
    };
	
	public class MasterThread implements Runnable {
		@Override
		public void run() {
			try {
				//Establish connection
				InetAddress serverAddress = InetAddress.getByName(PI_IP);
				Socket socket = new Socket(serverAddress, MASTER_PORT);
				//Loop forever
				while(true){
					try{
						//Initialize input and output
						masterOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
								socket.getOutputStream())), true);
						masterIn = new BufferedReader(new InputStreamReader(
								socket.getInputStream()));
						//Read input message when received
						String msg;
						while ((msg = masterIn.readLine()) != null) {
							if(msg.equalsIgnoreCase("invalid name")){
								name = "Computer" + (int) Math.random() * 1000;
								masterOut.println(name);
							}
							db.runQuery(msg);
						}
					}
					catch(Exception e){
						//Log error
						Log.e("Client", "S: error", e);
					}
				}
			}
			catch(Exception e){
				//Log error
				Log.e("Client", "C: error", e);
			}
		}
	}
	
	public class MixThread implements Runnable {
		@Override
		public void run() {
			try {
				//Establish connection
				InetAddress serverAddress = InetAddress.getByName(PI_IP);
				Socket socket = new Socket(serverAddress, MIX_PORT);
				//Loop forever
				while(true){
					try{
						//Initialize input and output
						mixOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
								socket.getOutputStream())), true);
						mixIn = new BufferedReader(new InputStreamReader(
								socket.getInputStream()));
						//Read input message when received
						String msg;
						while ((msg = mixIn.readLine()) != null) {
							db.runQuery(msg);
						}
					}
					catch(Exception e){
						//Log error
						Log.e("Client", "S: error", e);
					}
				}
			}
			catch(Exception e){
				//Log error
				Log.e("Client", "C: error", e);
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
