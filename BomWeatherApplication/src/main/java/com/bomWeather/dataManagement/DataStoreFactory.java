package com.bomWeather.dataManagement;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * The DataStoreFactory.
 * <p>
 * This factory class functions as a singleton to create a data model
 * given an inbound JSON request object.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class DataStoreFactory {

	/**
	 * @param element - The inbound JSON object.
	 * @return a model containing the request data.
	 */
	public IDataStoreModel createDataStoreModel(JSONObject element) {
		IDataStoreModel model = null;
		try {
			String type = element.getString("type");
			if ("air_temperature".equals(type)) {
				String unit = element.getString("units");
				double value = element.getDouble("content");
				model = new AirTemperature(type, unit, (float) value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return model;
	}
}
