package com.example.dribbblebackgroundchanger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.NumberPicker;

/**
 * This class describes a numberPicker widget that allows user to select time
 * interval. The range in seconds is specified to be 5 to 100 seconds
 * 
 * @author E Wong
 * 
 */
public class FrequencyNumberPicker extends NumberPicker {

	/**
	 * constructor for MainActivity
	 * @param context
	 */
	public FrequencyNumberPicker(Context context) {
		super(context);
		setMaxValue(100);
		setMinValue(5);
	}

	/**
	 * constructors for XML custom textFields
	 * @param context
	 * @param attrs
	 */
	public FrequencyNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		processAttributeSet(attrs);
	}

	public FrequencyNumberPicker(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		processAttributeSet(attrs);
	}

	/**
	 * converts value from seconds to milliseconds
	 * @return
	 */
	public int getNumber() {
	
		Log.d("MAX", getMaxValue() + "");
		Log.d("CURRENT", getValue() + "");
		return (getValue() * 1000);
	}

	private void processAttributeSet(AttributeSet attrs) {
		// This method reads the parameters given in the xml file and sets the
		// properties according to it
		this.setMinValue(5);
		this.setMaxValue(100);
	}
}
