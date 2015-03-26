package com.example.mainactivity;

import android.app.Activity;

public class CursorThread extends Thread {
	private boolean running = false;
	// Upgrades forRefreshUpgrades = new Upgrades();
	// MainActivity forRefreshMain = new MainActivity();
	double mainOrUpgrades;

	private Activity activity;

	// ==================IVAN COMMENT==========================

	// Create a private Activity here so that we can do a bunch of things
	// described below with it.
	// This is going to be initialized in the constructor. Of course that means
	// that the constructor
	// takes an Activity as a parameter instead of none. This means that from
	// now on
	// when we create a thread in a class we use "new CursorThread(this);"
	// instead.

	// ==================END IVAN COMMENT=====================

	public CursorThread(Activity activity) {
		// super();
		this.activity = activity;
		running = true;
	}

	public void setRunning(boolean chunLi) {
		running = chunLi;
	}

	public int testActivity() {
		try {
			activity = (MainActivity) activity;
			return 0;
		} catch (ClassCastException e) {
			return 1;
		}
	}

	@Override
	public void run() {
		//To Decide Which Cookie Count to Add to (They Sync when press Armory/To War Buttons)
		if (testActivity() == 0) { //If Main Screen
			while(running) {
				try{
					//Add a cookie according to CPS, so it will add one every second/cps
					Thread.sleep(10000);//(long)Cursor.cps);

					MainActivity.count += Cursor.cps * Cursor.numberOfCursors;
					//forRefresh.refreshCookies(v);
				} catch (InterruptedException e) {
					//Do Nothing
				}

				//Update the Cookie Count, Call Text on another thread by Runnable
				activity.runOnUiThread(new Runnable() {
					 @Override
					 public void run() {
					 ((MainActivity)activity).refreshCookies();
					}});
		}}
		else if (testActivity() == 1) {
			while(running) {
				try{
					//Add a cookie according to CPS, so it will add one every second/cps
					Thread.sleep(1000);//(long)Cursor.cps);

					Upgrades.cookieCount += Cursor.cps * Cursor.numberOfCursors;
					//forRefresh.refreshCookies(v);
				} catch (InterruptedException e) {
					//Do Nothing
				}

				//Update the Cookie Count, Call Text on another thread by Runnable
				activity.runOnUiThread(new Runnable() {
					 @Override
					 public void run() {
					 ((Upgrades)activity).refreshCookies();
					}});
		}
		//==================IVAN COMMENT=========================
		
		//Change true to running!  We made it for a reason!
		
		// Also, make a BIG if else statement that checks which activity the thread is running in using 
		// the testActivity(activity) method described below.
		
		// If it's in the MainActivity then we can upgrade the cookie count in the main activity.
		// If it's in the upgrades class then we can upgrade the one in the upgrade activity.
		
		// This is done so that we can create a new thread in each activity and it'll all be good!
		
		//==================END IVAN COMMENT=====================
			//==================IVAN COMMENT==========================
			
			//This is where you will add the ability to update the text in the TextViews.
			//make sure that you are updating the appropriate text depending on which activity loop you're in!

			//This is used because we need to specify that the thread that is updating is the thread containing
			// the textview - also known as the UI thread.
			
			//
			//==================END IVAN COMMENT=====================
			
		}
	}
	// ==================IVAN COMMENT=========================

	// Create the methods:
	// 1. public void setRunning(boolean b) so that it sets running to b;
	// 2. public int testActivity(activity) so that the following happens:
	// a. it takes in the activity passed in the above constructor and tests
	// whether it's a MainActivity
	// or an Upgrades Activity.

	// ==================END IVAN COMMENT=====================
}