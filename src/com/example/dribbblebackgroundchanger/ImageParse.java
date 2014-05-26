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

	private final String TAG_SHOTS = "shots";
	private final String TAG_IMAGE_URL = "image_url";
	private final String TAG_IMAGE_WIDTH = "width";
	private final String TAG_IMAGE_HEIGHT = "height";
	
	private final backupURL = "http://api.dribbble.com/shots/everyone";

	public ImageParse(Context context) {
		c = context;

	}

	public String connectHTTP(String url) {
		try {
			// create URl object and establish connection to internet
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// open buffer reader to prepare to read the page
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String inputLine;
			String result = "";

			// reads the page and stores text into a string
			while ((inputLine = reader.readLine()) != null) {
				result = result + inputLine;
			}
			reader.close();

			// returns the string which is in JSON format
			return result;

		} catch (MalformedURLException e) {
			Log.d(TAG_HTTP_ERROR, e + "");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG_HTTP_ERROR, e + "");
			e.printStackTrace();
		} catch (Exception e) {
			Log.d(TAG_HTTP_ERROR, e + "");
			e.printStackTrace();
		}
		// returns null if any error occurs
		return null;
	}

	private List<String> getURLFromJson(String content) throws Exception{
		
		if(jsonString == null){ //throw exception
			
		} 
		else {	
			//new changes (add JSON), need to import
			JSONObject jsonObj = new JSONObject(content);
			JSONArray jsonArray = new JSONArray(TAG_SHOTS);

			List<String> urlLinks = new List<String>();
			
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject tmp = jsonArray.getJSONObject(i);
				String imgURL = tmp.getString(TAG_IMAGE_URL);
				if(!imgURL.contains("gif")){
					urlLinks.add(imgURL);
				}
			}
			
			if(urlLinks.isEmpty()){
				String newJsonStr = connectHTTP(backupURL);
				urlLinks = getURLFromJson(newJsonStr);
			}
		}
		
		return urlLinks;
	}


	
	}

}
