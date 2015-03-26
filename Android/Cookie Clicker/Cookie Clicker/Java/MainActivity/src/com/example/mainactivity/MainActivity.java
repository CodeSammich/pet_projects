package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	public static double count;
	
	//Declare All Threads
	CursorThread cursorThread;
	GrandmaThread grandmaThread;
	FarmThread farmThread;
	FactoryThread factoryThread;

	//==================IVAN COMMENT==========================
	
	//Declare a cursor Thread here that will be initialized inside the onCreate method.
	
	//count should also be changed to a double to give us a better representation of cookies increased.
	
	//==================END IVAN COMMENT=====================
	
	
//	double cps = ((Cursor.numberOfCursors * Cursor.cps) 
//			+ (Grandma.numberOfGrandmas * Grandma.cps) + 
//			(Farm.numberOfFarms * Farm.cps) +
//			(Factory.numberOfFactories * Factory.cps));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Score Save
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		double savedCount = sharedPref.getInt("Saved Count", 0);
		count = savedCount;

		// Restart Button
		TextView restart = (TextView) findViewById(R.id.restart);
		restart.setText("Invite Locusts");

		//Sync Score between Main and Upgrades Screen
		Intent intent = getIntent();
		count = intent.getDoubleExtra("cookie_count", count);

		//Sync Number of Upgrades
		Intent cursors = getIntent();
		Intent grandmas = getIntent();
		Intent farms = getIntent();
		Intent factories = getIntent();
		
		///////THREADS!!
		//==================IVAN COMMENT==========================
		
		//This thread needs to be initialized using new CursorThread(this);
		
		//==================END IVAN COMMENT=====================
		
		//Initialize All Threads and Run!
		cursorThread = new CursorThread(this); 
		cursorThread.start();
		
		grandmaThread = new GrandmaThread(this); 
		grandmaThread.start();
		
		farmThread = new FarmThread(this); 
		farmThread.start();
		
		factoryThread = new FactoryThread(this); 
		factoryThread.start();
		//Prevent Negatives
		if(count <0) {
			count = 0;
		}
		
		
		//Get Saved Upgrades Numbers
//		Cursor.numberOfCursors = sharedPref.getInt("number_cursors", 0);
//		Grandma.numberOfGrandmas = sharedPref.getInt("number_grandmas", 0);
//		Farm.numberOfFarms = sharedPref.getInt("number_farms", 0);
//		Factory.numberOfFactories = sharedPref.getInt("number_factories", 0);
		
		//Display Number of Cookiess
		refreshCookies();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Cookie Button
	public void buttonClicked(View view) {
		// If Cookie is clicked
		count += 1;
		
		TextView textView = (TextView) findViewById(R.id.textView);
		textView.setText("Cookies: " + (int)count);

		//Saves Number of Cookies
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("Saved Count", (int)count);
		editor.commit();
	}

	// Restart Button
	public void restartButton(View v) {
		// Restart Button Clicked
		count = 0;

		// Saves Number of Cookies
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("Saved Count", (int)count);
		editor.commit();

		//Prints Number of Cookies
		TextView textView = (TextView) findViewById(R.id.textView);
		textView.setText("Cookies: " + (int)count);

		// Reset Number of Cursors/Cost
		Cursor.numberOfCursors = 0;
		Cursor.BASE_COST = 15;
		Grandma.numberOfGrandmas = 0;
		Grandma.BASE_COST = 100;
		Farm.numberOfFarms = 0;
		Farm.BASE_COST = 500;
		Factory.numberOfFactories = 0;
		Factory.BASE_COST = 3000;
		
		//Delete Previous Saved Upgrades and Replace with 0
		SharedPreferences sharedPrefs = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editors = sharedPrefs.edit();
		editors.putInt("Saved Cursors", 0);
		editors.commit();
		
		editors.putInt("Saved Grandmas", 0);
		editors.commit();
		
		editors.putInt("Saved Farms", 0);
		editors.commit();
		
		editors.putInt("Saved Factories", 0);
		editors.commit();
	}

	// Army Screen (Upgrades)
	public void armyScreen(View v) {
		// Sends Number of Cookies to Army Screen
		
		//==================IVAN COMMENT==========================
		
		//The cursorThread here should be ended here by using cursorThread.setRUnning(false);
		// **Reminder: you need to create that method in CursorThread!**
		
		//==================END IVAN COMMENT=====================
		cursorThread.setRunning(false);
		grandmaThread.setRunning(false);
		farmThread.setRunning(false);
		factoryThread.setRunning(false);
		Intent i = new Intent(MainActivity.this, Upgrades.class);

		//Sends Number of Upgrades/Cookies
		i.putExtra("cookie_count", count);
		i.putExtra("number_Cursors", Cursor.numberOfCursors);
		i.putExtra("number_Grandmas", Grandma.numberOfGrandmas);
		i.putExtra("number_Farms", Farm.numberOfFarms);
		i.putExtra("number_Factories", Factory.numberOfFactories);
		
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		//==================IVAN COMMENT==========================
		
		//Please for the love of all that is holy create a refreshCookies() method inside this class
		// that is almost identical to the one in the Upgrades class!
		
		//==================END IVAN COMMENT=====================
	}
	
	public void refreshCookies() {
		TextView numCookies = (TextView) findViewById(R.id.textView);
		numCookies.setText("Cookies: " + (int)count);
	}
}
