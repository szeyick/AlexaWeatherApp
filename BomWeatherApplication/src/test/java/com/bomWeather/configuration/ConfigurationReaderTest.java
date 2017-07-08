package com.bomWeather.configuration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The ConfigurationReaderTest.
 * <p>
 * This test class is responsible for testing the {@link ConfigurationReader}.
 * <p>
 * <b>Warning: </b>None.
 * <p>
 * @author szeyick
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationReader.class)
public class ConfigurationReaderTest {

	/**
	 * Test that the configuration file loads correctly.
	 */
	@Test
	public void testLoadConfiguration() {
		ConfigurationReader reader = new ConfigurationReader();
		assertNotNull("Expected property to be valid", reader.getProperty(ConfigurationConstants.CERT_FILE_NAME));
		assertNotNull("Expected property to be valid", reader.getProperty(ConfigurationConstants.CLIENT_ENDPOINT));
		assertNotNull("Expected property to be valid", reader.getProperty(ConfigurationConstants.CLIENT_ID));
		assertNotNull("Expected property to be valid", reader.getProperty(ConfigurationConstants.PRIVATE_KEY_FILE_NAME));
		assertNotNull("Expected property to be valid", reader.getProperty(ConfigurationConstants.TOPIC_LISTENER_NAME));
		assertNotNull("Expected property to be valid", reader.getProperty(ConfigurationConstants.TOPIC_PUBLISHER));
		
		assertNull("Expected property to not exist", reader.getProperty("NO_PROPERTY"));
	}
}
