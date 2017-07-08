package com.bomWeather.messageManagement;

import org.springframework.context.ApplicationEvent;

/**
 * The MessageEvent.
 * <p>
 * This class is responsible for holding the message received
 * from AWS-IoT.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class MessageEvent extends ApplicationEvent {

	/**
	 * The received message.
	 */
	private String requestMessage;
	
	/**
	 * Constructor.
	 * @param source - The source of the message event.
	 * @param message - The incoming JSON request.
	 */
	public MessageEvent(Object source, String requestMessage) {
		super(source);
		this.requestMessage = requestMessage;
	}

	/**
	 * @return - The received message.
	 */
	public String getRequestMessage() {
		return requestMessage;
	}
}
