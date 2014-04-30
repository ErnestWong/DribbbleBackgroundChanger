package com.example.dribbblebackgroundchanger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {

	WallpaperManager wallpaper;
	private Context c;
	ImageView imageView;
	private String frequency;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		c = getApplicationContext();
		frequency = "5000";
		username = "simplebits";
		Intent intent = new Intent(this, BackgroundService.class);
		intent.putExtra("frequency", frequency);
		intent.putExtra("username", username);
		c.startService(intent);
		
		final EditText editFrequency = (EditText) findViewById(R.id.edit_frequency);
		final EditText editUsername = (EditText) findViewById(R.id.username_edit);
		
		final Button frequencyButton = (Button) findViewById(R.id.send_frequency);
		final Button usernameButton = (Button) findViewById(R.id.send_username);
		
		usernameButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				username = editUsername.getText().toString();
				//frequency = Integer.parseInt(intermediate);
				//Log.d("FREQUENCY", frequency + "");
				hideSoftKeyboard();
			}
		});
		
		usernameButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				username = editFrequency.getText().toString();
				
				Log.d("USERNAME", username);
				hideSoftKeyboard();
			}
		});
	}

	
	public void hideSoftKeyboard() {
	    InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
