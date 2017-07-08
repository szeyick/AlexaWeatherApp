package com.bomWeather.dataManagement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * The WeatherDataStore.
 * <p>
 * This data store is responsible for storing the data for
 * a given weather station.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class WeatherDataStore {

	/**
	 * A collection of weather station data.
	 */
	private Map<String, Station> stationMap;
	
	/**
	 * Constructor.
	 */
	public WeatherDataStore() {
		stationMap = new HashMap<>();
	}
	
	/**
	 * @return - the weather station data.
	 */
	public Station getWeatherStation(String stationName) {
		return stationMap.get(stationName);
	}
	
	public void addWeatherStationData(Station station) {
		stationMap.put(station.getStationName(), station);
	}
}
