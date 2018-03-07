package parsing;

import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * Parse the weather data returned from the URL openweathermap.org.
 * 
 * Download javax.json-ri-x.0 at
 * https://java.net/projects/jsonp/downloads/directory/ri
 * 
 * Unzip and add to library.
 * 
 */
public class MainOpenWeatherMap {
	
	//API Key: 2fd3c759f8aa48fa57b20e5b3e516d53
	//https://openweathermap.org/appid
	//http://openweathermap.org/current
	private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?zip=60661&units=metric&APPID=2fd3c759f8aa48fa57b20e5b3e516d53";

	/**
	 * Call the URL and read the JSON.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String readJsonWeather() throws Exception {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(WEATHER_URL);
		Scanner in = new Scanner(url.openStream());
		while (in.hasNext()) {
			sb.append(in.next());
		}
		in.close();
		return sb.toString();
	}
	
	/**
	 * Convert Unix seconds to a Java date.
	 * 
	 * @param unixSeconds
	 * @return
	 */
	public static String convertUnixUtcTime(long unixSeconds){
		Date date = new Date(unixSeconds * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	/**
	 * Parse the Json and print the info we're interested in.
	 * 
	 * @param json
	 */
	public static Weather parseJson(String json) {
		JsonParser parser = Json.createParser(new StringReader(json));
		Event event = null;
		Weather weather = new Weather();

		while (parser.hasNext()) {
			event = parser.next();
			if (event == Event.KEY_NAME && parser.getString().equalsIgnoreCase("country")) {
				event = parser.next();
				weather.setCountry(parser.getString());
			} else if (event == Event.KEY_NAME && parser.getString().equalsIgnoreCase("sunrise")) {
				event = parser.next();
				weather.setSunrise(convertUnixUtcTime(parser.getLong()));
			} else if (event == Event.KEY_NAME && parser.getString().equalsIgnoreCase("sunset")) {
				event = parser.next();
				weather.setSunset(convertUnixUtcTime(parser.getLong()));
			} else if (event == Event.KEY_NAME && parser.getString().equalsIgnoreCase("temp")) {
				event = parser.next();
				weather.setTemperature(parser.getString() + "c");
			} else if (event == Event.KEY_NAME && parser.getString().equalsIgnoreCase("name")) {
				event = parser.next();
				weather.setCity(parser.getString()); 
			}
		}
		
		return weather;
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String json = readJsonWeather();
		Weather weather = parseJson(json);
		System.out.println(weather);
	}

}
