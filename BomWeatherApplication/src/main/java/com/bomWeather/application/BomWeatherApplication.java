package com.bomWeather.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The BomWeatherApplication.
 * <p>
 * This class is responsible for functioning as the entry point into
 * the application.
 * <p>
 * <b>Warning: </b>None.
 * <p>
 * @author szeyick
 * @version 0.0.1
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.bomWeather")
public class BomWeatherApplication {

	/**
	 * The application entry point.
	 * @param args - Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BomWeatherApplication.class);
	}
}
