package bot_output;

import java.io.IOException;

import utils.StaticVariables;

public class Weather 
{
	private Writer writer = new Writer();
	private String zipCode;
	public Weather()
	{
		//Tell the command center this program is running
		StaticVariables.programRunning = true;
	}
	
	public void initWeather()
	{
		try
		{
			writer.writeMessage("Enter zip code ('$exit' to leave program): ");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void getWeather(String zip)
	{
		zipCode = zip;
		
		if(zipCode.equals("$exit"))
		{
			StaticVariables.programRunning = false;
		}
		else
		{
			try
			{
				//placeholder text
				writer.writeMessage("Zipcode is: " + zipCode);
				initWeather();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
