package com.bomWeather.messageManagement;

import com.bomWeather.dataManagement.WeatherDataStore;

/**
 * The IMessageStrategy.
 * <p>
 * This interface is responsible for providing a common means to refer
 * to difference message processors that can be used to construct a response
 * message.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public interface IMessageStrategy {

	/**
	 * @param intent - The intent.
	 * @param dataStore - The data to process.
	 * @return the response message as a string.
	 */
	public String createResponseMessage(Object intent, WeatherDataStore dataStore);
	
}
