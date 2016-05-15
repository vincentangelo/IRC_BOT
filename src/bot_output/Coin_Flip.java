package bot_output;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import utils.Constants;

public class Coin_Flip
{
	public Coin_Flip() throws Exception
	{
		boolean heads = false;
		String headsTails = "";
		double num = Math.random();
		if (num > .5)
			heads = true;
		
		if (heads)
			headsTails = "Heads";
		else
			headsTails = "Tails";
		
		BufferedWriter writer = Constants.WRITER;
		BufferedReader reader = Constants.READER;
		
		writer.write("PRIVMSG " + Constants.CHANNEL + " :" + headsTails + "\r\n");
	}
}
