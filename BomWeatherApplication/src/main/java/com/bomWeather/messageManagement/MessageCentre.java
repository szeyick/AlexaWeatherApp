package com.bomWeather.messageManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bomWeather.messageManagement.awsIoT.AwsIotConnectionClient;
import com.bomWeather.messageManagement.awsIoT.WeatherTopicPublisher;

/**
 * The MessageCentre.
 * <p>
 * The message centre is responsible for the lifecycle of a message that
 * has been recieved by the application. From the time when the message is
 * received, the message centre will manage it to ensure that the correct
 * response is built and sent back to AWS-IoT.
 * <p> 
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class MessageCentre implements ApplicationListener<MessageEvent> {

	/**
	 * The logger.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(MessageCentre.class.getSimpleName());
	
	/**
	 * The message factory to construct a response.
	 */
	@Autowired
	private MessageFactory factory;
	
	/**
	 * The publisher to send outbound events.
	 */
	private WeatherTopicPublisher publisher;
	
	/**
	 * Constructor.
	 * @param factory - The message factory.
	 */
	public MessageCentre(MessageFactory factory, AwsIotConnectionClient client) {
		this.factory = factory;
		publisher = client.getPublisher();
	}
	
	/**
	 * The message event.
	 */
	public void onApplicationEvent(MessageEvent event) {
		LOGGER.info("Message Received: " + event.getRequestMessage());
		String response = factory.createResponseMessage(event.getRequestMessage());
		sendResponse(response);
	}
	
	/**
	 * @param response - The response to send.
	 */
	private void sendResponse(String response) {
		publisher.publish(response);
	}
	
	/**
	 * @param publisher - The publisher to send responses back to AWS-IoT.
	 */
	public void setPublisher(WeatherTopicPublisher publisher) {
		this.publisher = publisher;
	}
}