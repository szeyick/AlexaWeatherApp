package com.bomWeather.messageManagement.messages;

/**
 * The RangeReportIntent.
 * <p>
 * This class is responsible for holding the message data for
 * a range report request. It is used by GSon to convert the
 * inbound JSON message into a Java object.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class RangeReportIntent extends WeatherIntent {

	/**
	 * The day to retrieve the report for.
	 */
	private String day;
	
	/**
	 * @return - The day for this report.
	 */
	public String getDay() {
		return day;
	}
}
