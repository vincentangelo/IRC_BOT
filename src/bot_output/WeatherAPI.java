package bot_output;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class WeatherAPI 
{
	private String zipcode, currentWeather;
	private String results = "";
	private double rawWeather = 0;

	public WeatherAPI(String zip)
	{
		zipcode = zip;
	}
	
	public String connect()
	{
		try {
			URI uri = new URI("http://api.openweathermap.org/data/2.5/weather?zip=" + zipcode + 
					",us&units=imperial&appid=f892d9fe15f914a9983b52c193220df1");
			JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
			JSONObject root = new JSONObject(tokener);
			JSONObject main = root.getJSONObject("main");
			Object temp = main.get("temp");
			
			currentWeather = temp.toString();
			//Get the value of the string
			rawWeather = Double.valueOf(currentWeather);
			
			NumberFormat formatter = new DecimalFormat("#0");   
			//format double to round up
			currentWeather = formatter.format(rawWeather);
			
			results = currentWeather;
			
			
		} catch (Exception e) {
			results = "Error connecting to API (make sure zipcode is valid)";
			e.printStackTrace();
		}
		
		return results;
	}
}
