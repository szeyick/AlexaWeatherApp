package com.bomWeather.messageManagement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bomWeather.dataManagement.WeatherDataStore;
import com.bomWeather.messageManagement.messages.Intent;
import com.bomWeather.messageManagement.messages.RangeReportIntent;
import com.bomWeather.messageManagement.messages.WeatherIntent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The MessageFactory.
 * <p>
 * The factory is responsible for creating response messages to requests
 * read in from the AWS-Iot Topic.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class MessageFactory implements IMessageFactory {

	/**
	 * A collection of all the strategies that can process
	 * intent messages.
	 */
	private Map<String, IMessageStrategy> strategies;
	
	/**
	 * A reference to the data store.
	 */
	@Autowired
	private WeatherDataStore dataStore;
	
	/**
	 * Constructor.
	 */
	public MessageFactory(WeatherDataStore dataStore) {
		strategies = new HashMap<>();
		strategies.put("TemperatureIntent", new TemperatureStrategy());
		strategies.put("WeatherReportIntent", new WeatherReportStrategy());
		this.dataStore = dataStore;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createResponseMessage(String message) {
		Gson gson = new GsonBuilder().create();
		Intent intent = gson.fromJson(message, Intent.class);
		String response = null;
		if ("WeatherReportIntent".equals(intent.getIntentName())) {
			WeatherIntent weatherIntent = gson.fromJson(message, WeatherIntent.class);
			response = strategies.get("WeatherReportIntent").createResponseMessage(weatherIntent, dataStore);
		}
		else if ("TemperatureIntent".equals(intent.getIntentName())) {
			WeatherIntent weatherIntent = gson.fromJson(message, WeatherIntent.class);
			response = strategies.get("TemperatureIntent").createResponseMessage(weatherIntent, dataStore);
		}
		else if ("RangeReportShortIntent".equals(intent.getIntentName())) {
			RangeReportIntent weatherIntent = gson.fromJson(message, RangeReportIntent.class);
			weatherIntent.getCity(); 
		}
		return response;
	}
}
