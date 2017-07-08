package com.bomWeather.messageManagement.awsIoT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.bomWeather.configuration.ConfigurationConstants;
import com.bomWeather.configuration.ConfigurationReader;
import com.bomWeather.messageManagement.MessageEventPublisher;
import com.bomWeather.messageManagement.awsIoT.KeyStoreGenerator.KeyStorePasswordPair;

/**
 * The AwsIotConnectionClient.
 * <p>
 * This client class is responsible for connecting to AWS IoT and
 * subscribing to a topic to receive incoming events.
 * <p>
 * <b>Warning: </b>None.
 * <p>
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class AwsIotConnectionClient {

	/**
	 * The logger to record AWS IoT Connection client. 
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(AwsIotConnectionClient.class.getName());
	
	/**
	 * The resources directory.
	 */
	private static String RESOURCES_DIR = "resources";
	
	/**
	 * The client.
	 */
	private static AWSIotMqttClient awsIotClient;
	
	/**
	 * The properties reader.
	 */
	@Autowired
	private ConfigurationReader reader;
	
	/**
	 * The listener responsible for receiving messages from AWS IoT
	 */
	private AWSIotTopic listener;
	
	/**
	 * The publisher.
	 */
	private WeatherTopicPublisher publisher;
	
	/**
	 * Constructor.
	 * @param reader - The instance of the property reader.
	 */
	public AwsIotConnectionClient(ConfigurationReader reader, MessageEventPublisher messageEventPublisher) {
		this.reader = reader;
		initialise();
		connect(messageEventPublisher);
	}
	
	/**
	 * Connect to AWS IoT.
	 */
	private void connect(MessageEventPublisher publisher) {
		try {
			awsIotClient.connect();
			listener = new WeatherTopicListener(this.reader.getProperty(ConfigurationConstants.TOPIC_LISTENER_NAME), AWSIotQos.QOS0, publisher);
			awsIotClient.subscribe(listener, true);
		} catch (AWSIotException ex) {
			LOGGER.info("Unable to connect to endpoint - " + this.reader.getProperty(ConfigurationConstants.CLIENT_ENDPOINT));
			ex.printStackTrace();
		}
	}
	
	/**
	 * Initialise the client.
	 */
	private void initialise() {
		String clientEndpoint = reader.getProperty(ConfigurationConstants.CLIENT_ENDPOINT);
        String clientId = reader.getProperty(ConfigurationConstants.CLIENT_ID);

        String certificateFile = RESOURCES_DIR + "/" + reader.getProperty(ConfigurationConstants.CERT_FILE_NAME);
        String privateKeyFile = RESOURCES_DIR + "/" + reader.getProperty(ConfigurationConstants.PRIVATE_KEY_FILE_NAME);
	
        if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
            String algorithm = "RSA";
            KeyStorePasswordPair pair = KeyStoreGenerator.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
            String topicPublisher = this.reader.getProperty(ConfigurationConstants.TOPIC_PUBLISHER);
            publisher = new WeatherTopicPublisher(awsIotClient, topicPublisher);
        }
        
        if (awsIotClient == null) {
        	String errorMessage = "Failed to construct client due to missing certificate or credentials.";
        	LOGGER.info(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
	}
	
	/**
	 * @return the listener to the topic.
	 */
	public AWSIotTopic getListener() {
		return listener;
	}
	
	/**
	 * @return - The publisher.
	 */
	public WeatherTopicPublisher getPublisher() {
		return publisher;
	}
}
