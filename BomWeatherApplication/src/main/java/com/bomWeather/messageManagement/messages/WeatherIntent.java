package com.bomWeather.messageManagement.messages;

/**
 * The WeatherIntent.
 * <p>
 * This class is responsible for holding the message data for
 * a weather report request. It is used by GSon to convert the
 * inbound JSON message into a Java object.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class WeatherIntent {

	/**
	 * The name of the intent.
	 */
	private String intent;
	
	/**
	 * The city to retrieve the request for.
	 */
	private String city;
	
	/**
	 * @return - The city for the report.
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @return the intent this request is from.
	 */
	public String getIntent() {
		return intent;
	}
}
