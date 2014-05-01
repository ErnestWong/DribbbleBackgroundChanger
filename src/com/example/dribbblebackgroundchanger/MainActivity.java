package com.example.dribbblebackgroundchanger;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context c;
	private int frequency;
	private String username;
	public static boolean validUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize fields
		c = getApplicationContext();
		final Intent intent = new Intent(this, BackgroundService.class);
		final TextView statmsg = (TextView) findViewById(R.id.msgstats);
		final TextView warn = (TextView) findViewById(R.id.warning);
		final TextView offline = (TextView) findViewById(R.id.offline);
		final EditText editUsername = (EditText) findViewById(R.id.edituser);
		final EditText editFrequency = (EditText) findViewById(R.id.editfreq);
		final Button frequencyButton = (Button) findViewById(R.id.freqbutton);
		final Button usernameButton = (Button) findViewById(R.id.userbutton);
		
		// initial values for frequency and username
		String initialFreq = 10000 + "";
		String initialUsername = "simplebits";

		// store frequency and username values in intent to pass to service
		intent.putExtra("username", initialUsername);
		intent.putExtra("frequency", initialFreq);
		c.startService(intent);
		
		//set initial visibility of offline warning message and stat msg
		warn.setVisibility(View.GONE);
		offline.setVisibility(View.GONE);
		statmsg.setText("Frequency: 10 seconds. \nFollowing: simplebits");
		
		
		/**
		 * listener for frequency send button; frequency value is retrieved from
		 * the editText and sent to service through intent. Service is
		 * temporarily stopped and restarted
		 */
		frequencyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				frequency = Integer.parseInt(editFrequency.getText().toString());
				
				// ensures input is within 5 to 100 seconds
				if (frequency < 5 || frequency > 100) {
					String outOfRange = "Please enter a number between 5 and 100";
					Toast.makeText(getApplicationContext(), outOfRange,
							Toast.LENGTH_LONG).show();
					return;
				}

				//set text to display overview
				statmsg.setText("Frequency: " + frequency
						+ " seconds. \nFollowing: " + username);
				String stringFreq = frequency + "";
				Log.d("FREQUENCY", frequency + "");
				
				// sends string to service
				c.stopService(intent);
				intent.putExtra("frequency", stringFreq);
				c.startService(intent);
				
				//set up edittext for next time
				editFrequency.setText("");
				hideSoftKeyboard();
			}
		});

		/**
		 * listener for username send button; username is retrieved from the
		 * editText and sent to service through intent. Service is temporarily
		 * stopped and restarted
		 */
		usernameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Log.d("button", "button");
				username = editUsername.getText().toString();
				// ensures username is not empty
				if (username == null) {
					String nonValidInput = "Please enter a string";
					Toast.makeText(getApplicationContext(), nonValidInput,
							Toast.LENGTH_LONG).show();
					return;
				}
				
				//set text to display overview
				statmsg.setText("Frequency: " + frequency
						+ "seconds. \nFollowing: " + username);
				
				//sends data to service
				c.stopService(intent);
				intent.putExtra("username", username);
				c.startService(intent);

				//set up for next time
				hideSoftKeyboard();
				Log.d(username, username);
				editUsername.setText("");

			}
		});

		/**
		 * This method listens for network change; if connection is lost,
		 * offline mode is entered. Regular layout is resumed once connection is
		 * found
		 */
		BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (!isNetworkAvailable()) {
					// displays a warning
					String warning = "Please check your network connection";
					Toast.makeText(getApplicationContext(), warning,
							Toast.LENGTH_LONG).show();

					// sets layout screen to offline mode
					warn.setVisibility(View.VISIBLE);
					offline.setVisibility(View.VISIBLE);

				} else {
					// resume regular layout when connection is restored
					// setContentView(R.layout.activity_main);
					warn.setVisibility(View.GONE);
					offline.setVisibility(View.GONE);
				}
			}

			/**
			 * checks if network is available; returns false if not available
			 * 
			 * @return
			 */
			private boolean isNetworkAvailable() {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = connectivityManager
						.getActiveNetworkInfo();
				return activeNetworkInfo != null;
			}
		};

		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(networkStateReceiver, filter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	/**
	 * hides the keyboard from user interface screen
	 */
	public void hideSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) this
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
