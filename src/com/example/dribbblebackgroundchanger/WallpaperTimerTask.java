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


public class WallpaperTimerTask extends TimerTask {
	Context c;
	String username = "";
	String URL;
	
	public WallpaperTimerTask(Context c, String username){
		this.c = c;
		this.username = username;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(username == ""){
			URL = "http://api.dribbble.com/shots/everyone";
		}
		else{
			URL = "http://api.dribbble.com/players/" + username + "/shots";
			Log.d(URL, URL);
		}
		DownloadImageTask t = new DownloadImageTask();
		t.execute(URL);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		

		public DownloadImageTask() {

		}

		protected Bitmap doInBackground(String... urls) {
			
			//default wallpaper
			String urldisplay = "https://dribbble.com/system/users/1/screenshots/21603/shot_1274474082.png";
			Bitmap bitmap = null;
			try {
				ImageParse imgparse = new ImageParse();
				String[] listOfURLs = imgparse.parseURL(urls[0]);
				boolean foundLink = false;
				while(!foundLink) {
					//randomly selects a url image from array of url links
					Random rand = new Random();
					int i = rand.nextInt(listOfURLs.length);
					//checks that the url string is indeed a url link to an image
					if (listOfURLs[i].startsWith("http") && !listOfURLs[i].contains("gif")) {
						urldisplay = listOfURLs[i];
						Log.d("URL", urldisplay);
						foundLink = true;
					}
				}
				
				//converts image to bitmap
				InputStream in = new java.net.URL(urldisplay).openStream();
				bitmap = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap result) {
			//sets wallpaper to bitmap image
			WallpaperManager wallPaperManager = WallpaperManager.getInstance(c);
			try{
				wallPaperManager.setBitmap(result);
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
