package com.bomWeather.messageManagement;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * The MessageEventPublisher.
 * <p>
 * This class is responsible for publishing events recieved from AWS-IoT to be
 * notified internally in the application.
 * <p>
 * <b>Warning; </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class MessageEventPublisher implements ApplicationEventPublisherAware {

	/**
	 * The event publisher.
	 */
	private ApplicationEventPublisher publisher;

	/**
	 * The publisher.
	 */
	public void setApplicationEventPublisher (ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * Publish the event.
	 * @param jsonMessage - The JSON message as a string.
	 */
	public void publish(String jsonMessage) {
		MessageEvent event = new MessageEvent(this, jsonMessage);
		publisher.publishEvent(event);
	}
}
