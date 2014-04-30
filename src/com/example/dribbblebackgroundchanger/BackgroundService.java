package com.example.dribbblebackgroundchanger;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service{
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
	 *instantiate timer 
	 */
	@Override
	public void onCreate(){
		super.onCreate();
		timer = new Timer();
		Log.d("Service create", "oncreate");
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		timer.cancel();
	}
	
	/**
	 *schedules a task for timer with proper delays 
	 */
	@Override 
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("Service start", "onstart");
		TIME_INTERVAL = Integer.parseInt((intent.getStringExtra("frequency").trim()));
		username = intent.getStringExtra("username");
		c = getApplicationContext();
		changeWallpaper = new WallpaperTimerTask(c, username );
		timer.scheduleAtFixedRate(changeWallpaper, TIME_INTERVAL, TIME_INTERVAL);
		return super.onStartCommand(intent, flags, startId);
	}
}
