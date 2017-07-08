package com.bomWeather.dataManagement;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The FtpClientThread.
 * <p>
 * This thread class is responsible for connecting to the BOM FTP Site
 * and updating all the application data.
 * <p>
 * <b>Warning: </b>None.
 * @author szeyick
 * @version 0.0.1
 */
@Component
public class FtpClientThread {

	/**
	 * A reference to the data store.
	 */
	@Autowired
	private WeatherDataStore dataStore;
	
	/**
	 * A reference to the factory.
	 */
	@Autowired
	private DataStoreFactory factory;
	
	/**
	 * Constructor.
	 * @param dataStore - A reference to the data store.
	 */
	public FtpClientThread(WeatherDataStore dataStore, DataStoreFactory factory) {
		this.dataStore = dataStore;
		this.factory = factory;
	}
	
	/**
	 * Connect to BOM FTP Client and update.
	 * Method is scheduled to execute every 5000 milliseconds.
	 */
	@Scheduled(fixedRate = 300000)
	public void update() {
		FTPClient client = new FTPClient();
		FileOutputStream fos = null;

		try {
			client.connect("ftp2.bom.gov.au");
			client.login("anonymous", "guest");

			String filename = "IDV60920.xml";
			fos = new FileOutputStream(filename);
			client.retrieveFile("anon/gen/fwo/" + filename, fos);
			InputStream stream = client.retrieveFileStream("anon/gen/fwo/" + filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
			String line;
			StringBuilder sb = new StringBuilder();
			while((line = bufferedReader.readLine()) != null )
			{ 
				sb.append(line);
				sb.append("\n");
			} 
			JSONObject myJson;
			
			myJson = XML.toJSONObject(sb.toString());
			JSONArray jsonArray = myJson.getJSONObject("product").getJSONObject("observations").getJSONArray("station"); 
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject stationElement = jsonArray.getJSONObject(i);
				JSONObject periodElement = stationElement.getJSONObject("period");
				
				int bomID = stationElement.getInt("bom-id");
				String stationName = stationElement.getString("description");
				if ("Melbourne (Olympic Park)".equals(stationName)) {
					stationName = "Melbourne";
				}
				String description = stationElement.getString("description");
				
				Station station = new Station(bomID, stationName, description);
				
				JSONObject level = periodElement.getJSONObject("level");
				JSONArray elementsArray = level.getJSONArray("element");
				for (int j = 0; j < elementsArray.length(); j++) {
					JSONObject element = elementsArray.getJSONObject(j);
					IDataStoreModel model = factory.createDataStoreModel(element);
					if (model != null) {
						station.addData(model);
					}
				}
				dataStore.addWeatherStationData(station);
			}
			fos.close();
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}