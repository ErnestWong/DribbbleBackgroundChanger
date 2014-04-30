package com.example.dribbblebackgroundchanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class ImageParse {

	String[] parsedContent;
	String link;

	public ImageParse() {

	}
	
	/**
	 * returns URL of img links
	 * @param link
	 * @return
	 */
	public String[] parseURL(String link) {
		try {
			// parse url and open connection to access http
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			readStream(con.getInputStream());
			
			// debugging purposes
			String TAG = "parse";
			for (int i = 0; i < parsedContent.length; i++) {
				Log.d(TAG, parsedContent[i]);
			}
			return parsedContent;

		} catch (Exception e) {
			String TAG = "ERROR";
			Log.d(TAG, e + "");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * reads content of http link and stores img links in parsedContent string
	 * array
	 * 
	 * @param in
	 */
	private void readStream(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String content = "";
			// set content string to the contents in http site
			while ((content = reader.readLine()) != null) {

				if (content != null) {
					// split the string to identify image link urls
					parsedContent = content.split("image_url\":\"");
					for (int i = 0; i < parsedContent.length; i++) {

						// parse string to remove extra data at the end of url link
						// application only handles .png and .jpq extensions
						parsedContent[i] = parsedContent[i].split(".png")[0]
								+ ".png";

						if (parsedContent[i].contains("{")) {
							parsedContent[i] = parsedContent[i].split(".jpg")[0]
									+ ".jpg";
						}

					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
