package com.bomWeather.messageManagement;

import com.bomWeather.dataManagement.IDataStoreModel;
import com.bomWeather.dataManagement.Station;
import com.bomWeather.dataManagement.WeatherDataStore;
import com.bomWeather.messageManagement.messages.WeatherIntent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The TemperatureStrategy.
 * <p>
 * This strategy class is responsible for responding to and processing temperature
 * requests.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class TemperatureStrategy implements IMessageStrategy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createResponseMessage(Object intent, WeatherDataStore dataStore) {
		// Perform the data retrieval, put it into an object and gson it out.
		WeatherIntent weatherIntent = (WeatherIntent) intent;
		String city = weatherIntent.getCity();
		
		Station station = dataStore.getWeatherStation(city);
		IDataStoreModel model = station.getData("air_temperature");
		
		Temperature temp = new Temperature();
		temp.temperature = model.getValue();
		temp.intent = "TemperatureIntent";
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
		public float temperature;
		
	}

}
