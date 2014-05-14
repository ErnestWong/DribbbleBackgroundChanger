package com.example.dribbblebackgroundchanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * This class sends http requests and parses the contents of the string. The
 * resulting img links are stored in an array in this class which is accessible
 * to all
 * 
 * @author E Wong
 * 
 */
public class ImageParse {
	//initialize fields
	private Context c;
	private List<String> parsedContent;
	private URL url;
	private HttpURLConnection connection;
	
	public ImageParse(Context context) {
		c = context;

	}

	/**
	 * sends http request
	 * @param link
	 * @return
	 */
	public InputStream sendhttpRequest(String link) {
		try {
			url = new URL(link);
			connection = (HttpURLConnection) url.openConnection();
			Log.d("sucess", link);
			return connection.getInputStream();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	/**
	 * returns URL of img links
	 * 
	 * @param link
	 * @return
	 */
	public List<String> getURLs() {
		if (parsedContent == null) {
			String TAG = "ERROR NULL STRING";
			Log.d(TAG, "getURLs");
		}
		return parsedContent;
	}

	/**
	 * reads content of http link and stores img links in parsedContent string
	 * array
	 * 
	 * @param in
	 */
	public void parseURL(String urlLink) {
		BufferedReader reader = null;
		try {
			InputStream stream = sendhttpRequest(urlLink);
			//if stream is null, then replace invalid url with default url
			if (stream == null) {
				stream = sendhttpRequest("http://api.dribbble.com/shots/everyone");
				String warningTAG = "User doesn't exist, please try again";
				Log.d(warningTAG, urlLink);
				
			}
			
			// set content string to the contents in http site
			reader = new BufferedReader(new InputStreamReader(stream));
			String content = "";
			while ((content = reader.readLine()) != null) {
				if (content != null) {
					// split the string to identify image link urls
					parsedContent = content.split("image_url\":\"");
					for (int i = 0; i < parsedContent.length; i++) {

						// parse string to remove extra data at the end of url
						// link
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
			
			//new changes (add JSON), need to import
			JSONObject jsonObj = new JSONObject(content);
			JSONArray jsonArray = new JSONArray("shots");
			
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject tmp = jsonArray.get(i);
				
				String imgURL = tmp.getString("image_url");
				if(!imgURL.contains("gif")) parsedContent.add(imgURL);
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
