package com.example.dribbblebackgroundchanger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.TimerTask;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class describes a timer task that changes the homescreen wallpaper given
 * a specified time interval
 * 
 * @author E Wong
 * 
 */
public class WallpaperTimerTask extends TimerTask {
	private Context c;
	private String username;
	private String URL;

	/**
	 * username is sent through constructor as well as application context
	 * @param c
	 * @param username
	 */
	public WallpaperTimerTask(Context c, String username) {
		this.c = c;
		this.username = username;
	}

	/**
	 * URL is set to whatever username the user types in (invalid usernames are
	 * handled in the ImageParse class). Async task is executed
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL = "http://api.dribbble.com/players/" + username + "/shots";
		Log.d("URL", URL);
		DownloadImageTask t = new DownloadImageTask();
		t.execute(URL);
		
	}

	
	/**
	 * inner class extending AsyncTask to perform wallpaper change without
	 * disturbing main thread
	 * 
	 * @author E Wong
	 * 
	 */
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		/**
		 * performs the http request to retrieve img links; selects one img link
		 * and converts it to bitmap
		 */
		protected Bitmap doInBackground(String... urls) {

			// default wallpaper
			String urldisplay = "https://dribbble.com/system/users/1/screenshots/21603/shot_1274474082.png";
			Bitmap bitmap = null;
			try {
				ImageParse imgparse = new ImageParse(c);
				imgparse.parseURL(urls[0]);
				String[] listOfURLs = imgparse.getURLs();
				if (listOfURLs == null) {
					Log.d("ARRAYNULL", "Null");

				}
				boolean foundLink = false;
				while (!foundLink) {
					// randomly selects a url image from array of url links
					Random rand = new Random();
					int i = rand.nextInt(listOfURLs.length);
					// checks that the url string is indeed a url link to an
					// image
					if (listOfURLs[i].startsWith("http")
							&& !listOfURLs[i].contains("gif")) {
						urldisplay = listOfURLs[i];
						Log.d("URL", urldisplay);
						foundLink = true;
					}
				}

				// converts image to bitmap
				InputStream in = new java.net.URL(urldisplay).openStream();
				bitmap = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return bitmap;
		}

		/**
		 * uses bitmap retrieved in the previous method and sets wallpaper to
		 * that bitmap image
		 */
		protected void onPostExecute(Bitmap result) {
			// sets wallpaper to bitmap image
			WallpaperManager wallPaperManager = WallpaperManager.getInstance(c);
			try {
				if(result == null) {
					Log.d("BITMAP EROR", "NULL");
					return;
				}
				wallPaperManager.setBitmap(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
