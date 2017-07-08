package com.bomWeather.dataManagement;

/**
 * The IDataStoreModel.
 * <p>
 * This interface is responsible for providing common methods for
 * data models to be created.
 * <p>
 * <b>Warning: </b>None.
 * <p>
 * @author szeyick
 * @version 0.0.1
 *
 */
public interface IDataStoreModel {

	/**
	 * @return the type.
	 */
	public String getType();
	
	/**
	 * @return the unit.
	 */
	public String getUnit();
	
	/**
	 * @return the value.
	 */
	public float getValue();
	
}
