package com.bomWeather.messageManagement;

/**
 * The IMessageFactory.
 * <p>
 * This interface provides public accessible methods
 * to the message factory.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
public interface IMessageFactory {

	/**
	 * @param message - The recieved message payload.
	 */
	public String createResponseMessage(String message);
	
}
