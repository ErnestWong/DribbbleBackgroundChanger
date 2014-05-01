package com.example.dribbblebackgroundchanger;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * This class describes a service that runs in the background; the service
 * changes the home screen wallpaper periodically depending on time interval
 * specified by user
 * 
 * @author E Wong
 * 
 */
public class BackgroundService extends Service {
	Timer timer;
	TimerTask changeWallpaper;
	Activity activity;
	Context c;
	private int TIME_INTERVAL;
	private String username;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * instantiate timer
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		timer = new Timer();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	/**
	 * schedules a task for timer with proper delays
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Service start", "onstart");
		c = getApplicationContext();

		// retrieves data from MainActivity intent containing the user inputs of
		// frequency and username. Convert the units to ms
		TIME_INTERVAL = (Integer.parseInt((intent.getStringExtra("frequency")
				.trim()))) * 1000;
		username = intent.getStringExtra("username");
		Log.d(username, username);

		// purge all previous tasks on timer then set new timer task with user
		// input specifying time intervals
		timer.purge();
		changeWallpaper = new WallpaperTimerTask(c, username);
		timer.scheduleAtFixedRate(changeWallpaper, TIME_INTERVAL, TIME_INTERVAL);
		return super.onStartCommand(intent, flags, startId);
	}

}
