package com.bomWeather.dataManagement;

/**
 * The AirTemperature.
 * <p>
 * This class represents the data model for representing Air Temperature.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class AirTemperature implements IDataStoreModel {

	/**
	 * The air temperature type.
	 */
	private String type;
	
	/**
	 * The air temperature unit.
	 */
	private String unit;
	
	/**
	 * The air temperature value.
	 */
	private float value;
	
	/**
	 * Constructor.
	 * @param type - The type.
	 * @param unit - The unit.
	 * @param value - The value.
	 */
	public AirTemperature(String type, String unit, float value) {
		this.type = type;
		this.unit = unit;
		this.value = value;
	}
	
	/**
	 * @return the type.
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * @return the unit.
	 */
	@Override
	public String getUnit() {
		return unit;
	}

	/**
	 * @return the value.
	 */
	@Override
	public float getValue() {
		return value;
	}
}
