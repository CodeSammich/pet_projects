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

//@SuppressWarnings("all")

public class Upgrades extends ActionBarActivity {

	static double cookieCount;
	CursorThread cursorThread;
	GrandmaThread grandmaThread;
	FarmThread farmThread;
	FactoryThread factoryThread;

	public static int savedCursors;
	public static int savedGrandmas;
	public static int savedFarms;
	public static int savedFactories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upgrades);
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		// Gets Cookies And Number of Cursors From Main (Not necessary for
		// Cursors?)
		Intent intent = getIntent();
		cookieCount = intent.getDoubleExtra("cookie_count", 0);
		editor.putInt("number_cursors", intent.getIntExtra("number_Cursors", 0));
		editor.commit();
		
		editor.putInt("number_grandmas", intent.getIntExtra("number_Grandmas", 0));
		editor.commit();
		
		editor.putInt("number_farms", intent.getIntExtra("number_Farms", 0));
		editor.commit();
		
		editor.putInt("number_factories", intent.getIntExtra("number_Factories", 0));
		editor.commit();
		
		// Save Number of Upgrades
		savedCursors = sharedPref.getInt("number_cursors", 0);
		Cursor.numberOfCursors = savedCursors;
		
		savedGrandmas = sharedPref.getInt("number_grandmas", 0);
		Grandma.numberOfGrandmas = savedGrandmas;
		
		savedFarms = sharedPref.getInt("number_farms", 0);
		Farm.numberOfFarms = savedFarms;
		
		savedFactories = sharedPref.getInt("number_factories", 0);
		Factory.numberOfFactories = savedFactories;

		// Number of Cookies in Upgrade Screen
		refreshCookies();

		// Update Number of Cursors on Screen
		TextView numOfCursorsOnScreen = (TextView) findViewById(R.id.numCursors);
		numOfCursorsOnScreen.setText("Cursors: " + Cursor.numberOfCursors);

		// Update Number of Grandmas - Screen
		TextView numOfGrandmasOnScreen = (TextView) findViewById(R.id.numGrandmas);
		numOfGrandmasOnScreen.setText("Grandmas: " + Grandma.numberOfGrandmas);

		// Update Number of Farms - Screen
		TextView numOfFarmsOnScreen = (TextView) findViewById(R.id.numFarms);
		numOfFarmsOnScreen.setText("Farms: " + Farm.numberOfFarms);

		// Update Number of Farms - Screen
		TextView numOfFactoriesOnScreen = (TextView) findViewById(R.id.numFactories);
		numOfFactoriesOnScreen.setText("Factories: "
				+ Factory.numberOfFactories);

		// Initialize All Threads and Run!
		cursorThread = new CursorThread(this);
		cursorThread.start();

		grandmaThread = new GrandmaThread(this);
		grandmaThread.start();

		farmThread = new FarmThread(this);
		farmThread.start();

		factoryThread = new FactoryThread(this);
		factoryThread.start();

		// ==================IVAN COMMENT==========================

		// We create a thread here as well as a thread in the main activity.
		// The thread will be ended in the intent passing stage.
		// In this case that happens in the toWar method.

		// ==================END IVAN COMMENT=====================

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upgrades, menu);
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

	// Back to Main Screen
	public void toWar(View view) {
		// ==================IVAN COMMENT==========================

		// In here we should end the current thread using
		// cursorThread.setRunning(false);

		// ==================END IVAN COMMENT=====================

		cursorThread.setRunning(false);
		grandmaThread.setRunning(false);
		farmThread.setRunning(false);
		factoryThread.setRunning(false);

		Intent i = new Intent(Upgrades.this, MainActivity.class);
		i.putExtra("cookie_count", cookieCount);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	// ==================IVAN COMMENT==========================

	// for refreshCookies(View v) you should remove the View. There's no point
	// in it and it only
	// makes it much harder to use other places!

	// ==================END IVAN COMMENT=====================

	// //Different Upgrades (Cursors, Grandma's, etc...)
	// Creates Cursor
	public void makeCursor(View view) {
		if (cookieCount >= Cursor.BASE_COST) {
			// Cost Cookies and Display Number to User
			Cursor.numberOfCursors++;
			cookieCount -= Cursor.BASE_COST;
			Cursor.BASE_COST += (Cursor.SCALE_COST * Cursor.BASE_COST);
			refreshCookies();

			// Update Number of Cursors on Screen
			TextView numOfCursorsOnScreen = (TextView) findViewById(R.id.numCursors);
			numOfCursorsOnScreen.setText("Cursors: " + Cursor.numberOfCursors);
			
			TextView cursorPrice = (TextView) findViewById(R.id.cursorNeeded);
			cursorPrice.setText(Cursor.BASE_COST + " Cookies");

			// Send Number of Cursors to Main (To Preserve Number of Cursors)
			Intent i = new Intent(Upgrades.this, MainActivity.class);
			i.putExtra("number_Cursors", Cursor.numberOfCursors);
		}

		// Saves Number of Cursors
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("number_Cursors", Cursor.numberOfCursors);
		editor.commit();

	}

	// Creates Grandma
	public void makeGrandma(View view) {
		if (cookieCount >= Grandma.BASE_COST) {
			Grandma.numberOfGrandmas++;
			cookieCount -= Grandma.BASE_COST;
			Grandma.BASE_COST += (Grandma.SCALE_COST * Grandma.BASE_COST);
			refreshCookies();

			// Update Number of Grandmas - Screen
			TextView numOfGrandmasOnScreen = (TextView) findViewById(R.id.numGrandmas);
			numOfGrandmasOnScreen.setText("Grandmas: "
					+ Grandma.numberOfGrandmas);
			
			TextView grandmaPrice = (TextView) findViewById(R.id.grandmaNeeded);
			grandmaPrice.setText(Grandma.BASE_COST + " Cookies");

			// Send Number of Grandmas to Main (To Preserve Number of Grandmas)
			Intent i = new Intent(Upgrades.this, MainActivity.class);
			i.putExtra("number_Grandmas", Grandma.numberOfGrandmas);
		}

		// Saves Number of Grandmas
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("number_Grandmas", Grandma.numberOfGrandmas);
		editor.commit();

	}

	public void makeFarm(View view) {
		if (cookieCount >= Farm.BASE_COST) {
			Farm.numberOfFarms++;
			cookieCount -= Farm.BASE_COST;
			Farm.BASE_COST += (Farm.SCALE_COST * Farm.BASE_COST);
			refreshCookies();

			// Update Number of Farms - Screen
			TextView numOfFarmsOnScreen = (TextView) findViewById(R.id.numFarms);
			numOfFarmsOnScreen.setText("Farms: " + Farm.numberOfFarms);
			
			TextView farmPrice = (TextView) findViewById(R.id.farmNeeded);
			farmPrice.setText(Farm.BASE_COST + " Cookies");

			// Send Number of Farms to Main (To Preserve Number of Farms)
			Intent i = new Intent(Upgrades.this, MainActivity.class);
			i.putExtra("number_Farms", Farm.numberOfFarms);
		}

		// Saves Number of Farms
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("number_Farms", Farm.numberOfFarms);
		editor.commit();

	}

	// Creates Factories
	public void makeFactory(View view) {
		if (cookieCount >= Factory.BASE_COST) {
			Factory.numberOfFactories++;
			cookieCount -= Factory.BASE_COST;
			Factory.BASE_COST += (Factory.SCALE_COST * Factory.BASE_COST);
			refreshCookies();

			// Update Number of Farms - Screen
			TextView numOfFactoriesOnScreen = (TextView) findViewById(R.id.numFactories);
			numOfFactoriesOnScreen.setText("Factories: "
					+ Factory.numberOfFactories);
			
			TextView factoryPrice = (TextView) findViewById(R.id.factoryNeeded);
			factoryPrice.setText(Factory.BASE_COST + " Cookies");

			// Send Number of Factories to Main (To Preserve Number of
			// Factories)
			Intent i = new Intent(Upgrades.this, MainActivity.class);
			i.putExtra("number_Factories", Factory.numberOfFactories);
		}

		// Saves Number of Factories
		SharedPreferences sharedPref = this
				.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("number_Factories", Factory.numberOfFactories);
		editor.commit();

	}

	// Update Number of Cookies on Army Screen
	public void refreshCookies() {
		TextView numCookies = (TextView) findViewById(R.id.cookieCount);
		numCookies.setText("Cookies: " + (int) cookieCount);
	}
}
