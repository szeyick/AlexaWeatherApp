package com.bomWeather.messageManagement.awsIoT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;

/**
 * The WeatherTopicPublisher.
 * <p>
 * This publisher is responsible for sending messages back to AWS-IoT
 * on a specified topic.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class WeatherTopicPublisher {

	/**
	 * The logger.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(NonBlockingPublishListener.class.getSimpleName());
	
	/**
	 * A reference to the AWS-IoT client.
	 */
	private final AWSIotMqttClient awsIotClient;
	
	/**
	 * The topic to publish to.
	 */
	private String publishToTopic;

	/**
	 * Constructor.
	 * @param awsIotClient - The AWS-IoT Client.
	 * @param topic - The topic to publish to.
	 */
	public WeatherTopicPublisher(AWSIotMqttClient awsIotClient, String topic) {
		this.awsIotClient = awsIotClient;
		publishToTopic = topic;
	}

	/**
	 * @param jsonPayload - The payload to send to the topic as a JSON string.
	 */
	public void publish(String jsonPayload) {
		AWSIotMessage message = new NonBlockingPublishListener(publishToTopic, AWSIotQos.QOS0, jsonPayload);
		try {
			awsIotClient.publish(message);
		} catch (AWSIotException e) {
	    	LOGGER.info(System.currentTimeMillis() + ": Publish Failed - " + jsonPayload);
		}
	}
	
	/**
	 * The NonBlockingPublishListener.
	 * <p>
	 * This class is responsible for listening to the message that has been published and 
	 * updating the status.
	 * <p>
	 * <b>Warning: <b>None.
	 * <p>
	 * @author szeyick
	 * @version 0.0.1
	 */
	private class NonBlockingPublishListener extends AWSIotMessage {

		/**
		 * The logger.
		 */
		private Logger LOGGER = LoggerFactory.getLogger(NonBlockingPublishListener.class.getSimpleName());
		
		/**
		 * Constructor.
		 * @param topic - The topic to listen to.
		 * @param qos - The QoS variable.
		 * @param payload - The message to send.
		 */
	    public NonBlockingPublishListener(String topic, AWSIotQos qos, String payload) {
	        super(topic, qos, payload);
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public void onSuccess() {
	    	LOGGER.info(System.currentTimeMillis() + ": Message Successfully Sent - " + getStringPayload());
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public void onFailure() {
	    	LOGGER.info(System.currentTimeMillis() + ": Message Failed To Publish - " + getStringPayload());
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public void onTimeout() {
	    	LOGGER.info(System.currentTimeMillis() + ": Publish Timeout - " + getStringPayload());
	    }
	}
}