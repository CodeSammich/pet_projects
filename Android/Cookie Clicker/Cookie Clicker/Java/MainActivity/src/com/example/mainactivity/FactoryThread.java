package com.example.mainactivity;

import android.app.Activity;

public class FactoryThread extends Thread {
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
	// when we create a thread in a class we use "new FactoryThread(this);"
	// instead.

	// ==================END IVAN COMMENT=====================

	public FactoryThread(Activity activity) {
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
					Thread.sleep(1000);//(long)Factory.cps);

					MainActivity.count += Factory.cps * Factory.numberOfFactories;
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
					Thread.sleep(1000);//(long)Factory.cps);

					Upgrades.cookieCount += Factory.cps * Factory.numberOfFactories;
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
		}
	}
}