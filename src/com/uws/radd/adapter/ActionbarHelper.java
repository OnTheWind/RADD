package com.uws.radd.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;

import com.uws.radd.R;
import com.uws.radd.activity.MixQueue;
import com.uws.radd.service.ConnectionService;


public class ActionbarHelper {
	static public boolean click(final Context context, MenuItem item){
		switch (item.getItemId()) {
	        case R.id.action_queue:
	        	//Intent actionIntent = new Intent(context, MixQueue.class);
	        	//context.startActivity(actionIntent);
	        	new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
	            .setTitle("Cancel Drink")
	            .setMessage("Are you sure you want to cancel a drink?")
	            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                	Intent mixIntent = new Intent(context, ConnectionService.class);
	                	mixIntent.putExtra("action", ConnectionService.QUEUE_ACTION);
	            	    mixIntent.putExtra("command","cancel");
	    			    context.startService(mixIntent);
	                }
	             })
	            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) { 
	                    // do nothing
	                }
	             })
	             .show();
	        	break;
	        case R.id.action_mix:
	        	new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
	            .setTitle("Mix Drink")
	            .setMessage("Are you sure you want to mix a drink?")
	            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                	Intent mixIntent = new Intent(context, ConnectionService.class);
	                	mixIntent.putExtra("action", ConnectionService.MIX_ACTION);
	            	    mixIntent.putExtra("command",ConnectionService.MIX_COMMAND);
	    			    context.startService(mixIntent);
	                }
	             })
	            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) { 
	                    // do nothing
	                }
	             })
	             .show();
	        	break;
	        default:
	        	break;
	    }
		
		return true;
	}
}
