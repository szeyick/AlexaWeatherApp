package com.bomWeather.messageManagement.awsIoT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.bomWeather.messageManagement.MessageEventPublisher;

/**
 * The WeatherTopicListener.
 * <p>
 * This class is responsible for receiving messages from AWS-IoT from the
 * specified topic. Messages sent to the topic that this listener is registered to
 * will be processed by this listener.
 * <p>
 * It extends {@link AWSIoTTopic} for the purpose of being able to process the
 * inbound payload.
 * <p>
 * <b>Waring: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public class WeatherTopicListener extends AWSIotTopic {

	/**
	 * The logger to log messages generated by this class.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(WeatherTopicListener.class.getSimpleName());
	
	/**
	 * The message publisher.
	 */
	private MessageEventPublisher publisher;
	
	/**
	 * Constructor.
	 * @param topic - The topic to subscribe to.
	 * @param qos - The QoS type.
	 * @param messageConfiguration - The configuration.
	 */
    public WeatherTopicListener(String topic, AWSIotQos qos, MessageEventPublisher publisher) {
        super(topic, qos);
        this.publisher = publisher;
    }

    /**
     * Method that is called when a message is posted to the subscribed topic. 
     * @param message - the message to be processed.
     */
    @Override
    public void onMessage(AWSIotMessage message) {
    	LOGGER.info(System.currentTimeMillis() + ": Message Received - " + message.getStringPayload());
        publisher.publish(message.getStringPayload());
    }
}
