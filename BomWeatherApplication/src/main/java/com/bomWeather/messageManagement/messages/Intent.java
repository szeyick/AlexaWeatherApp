package com.bomWeather.messageManagement.messages;

/**
 * The Intent.
 * <p>
 * This class is responsible for converting the initial JSON
 * message into a Java object. In the initial request, we are only
 * concerned with the intent name.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 *
 */
public class Intent {

	/**
	 * The name of the intent.
	 */
	private String intent;
	
	/**
	 * @return the name of the intent.
	 */
	public String getIntentName() {
		return intent;
	}
	
}
