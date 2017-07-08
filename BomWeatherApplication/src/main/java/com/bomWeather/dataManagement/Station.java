package com.bomWeather.dataManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * The Station.
 * <p>
 * This class represents the weather station model.
 * <p>
 * <b>Warning: </b>None.
 * <p>
 * @author szeyick
 * @version 0.0.1
 */
public class Station {

	/**
	 * The identifier for the weather station.
	 */
	private int bomID;
	
	/**
	 * The name of the weather station.
	 */
	private String stationName;
	
	/**
	 * A description of the weather station.
	 */
	private String description;
	
	/**
	 * The data stored for the weather station.
	 */
	private Map<String, IDataStoreModel> data;
	
	/**
	 * Constructor.
	 * @param bomID - The identifier for the weather station.
	 * @param stationName - The station name.
	 * @param description - The description of the station.
	 */
	public Station(int bomID, String stationName, String description) {
		this.bomID = bomID;
		this.stationName = stationName;
		this.description = description;
		data = new HashMap<>();
	}
	
	/**
	 * @return the ID.
	 */
	public int getBomID() {
		return bomID;
	}
	
	/**
	 * @return the station name.
	 */
	public String getStationName() {
		return stationName;
	}
	
	/**
	 * @return the station description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param model - The data model to add to the station.
	 */
	public void addData(IDataStoreModel model) {
		data.put(model.getType(), model);
	}
	
	/**
	 * @param type - The data type to retrieve.
	 * @return the stored data at the weather station.
	 */
	public IDataStoreModel getData(String type) {
		return data.get(type);
	}
}
