package com.bomWeather.messageManagement;

import com.bomWeather.dataManagement.WeatherDataStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The WeatherReportStrategy.
 * <p>
 * This strategy class is responsible for performing
 * actions related to obtaining a weather report
 * response.
 * <p>
 * <b>Warning: <b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class WeatherReportStrategy implements IMessageStrategy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createResponseMessage(Object intent, WeatherDataStore dataStore) {
		// Perform the data retrieval, put it into an object and gson it out.
		Temperature temp = new Temperature();
		temp.temperature = 25;
		temp.intent = "TemperatureIntent";
		temp.reportDescription = "Cloudy. Medium (60%) chance of showers, mainly in the east. "
				+ "Light winds becoming north to northwesterly 15 to 20 km/h in the late evening.";
		Gson gson = new GsonBuilder().create();
		return gson.toJson(temp);
	}

	/**
	 * The Temperature.
	 * <p>
	 * This class is responsible for holding the temperature value.
	 * <p>
	 * <b>Warning: </b>None.
	 * @author szeyick
	 * @version 0.0.1
	 */
	private class Temperature {
		
		/**
		 * The name of the intent.
		 */
		public String intent;
		
		/**
		 * The temperature.
		 */
		public int temperature;
		
		/**
		 * The description of the report.
		 */
		public String reportDescription;
		
	}
}
