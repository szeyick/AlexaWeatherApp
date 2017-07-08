package com.bomWeather.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The ConfigurationReader.
 * <p>
 * This reader class is responsible for providing the
 * offline configuration defined in config.properties.
 * <p>
 * <b>Warning: </b>None.
 * <p> 
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class ConfigurationReader extends Properties {

	/**
	 * The logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationReader.class.getName());
	
	/**
	 * The name of the property file.
	 */
	private static final String PROPERTY_FILE = "resources/config.properties";

	/**
	 * Constructor
	 */
	public ConfigurationReader() {
		loadProperties();
	}
	
	/**
	 * Load the properties into the configuration.
	 */
	private void loadProperties() {
		InputStream stream = null;
        try {
        	stream = new FileInputStream(PROPERTY_FILE);
            load(stream);
        } 
        catch (IOException exception) {
        	LOGGER.info("Unable to load file: " + PROPERTY_FILE);
        	exception.printStackTrace();
        }
        finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException exception) {
					LOGGER.info("Unable to close file: " + PROPERTY_FILE);
					exception.printStackTrace();
				}
			}
		}
	}
}
