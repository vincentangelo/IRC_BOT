package bot_output;

import java.io.BufferedWriter;
import java.io.IOException;

import utils.StaticVariables;

//Writes all the output to the irc client
public class Writer
{
	BufferedWriter writer = StaticVariables.WRITER;
	
	//Writes a message to the chatroom
	public void writeMessage(String message) throws IOException
	{
		System.out.println("______Writing______");
		writer.write("PRIVMSG " + StaticVariables.CHANNEL + " :" + message + "\r\n");
		writer.flush();
	}
	
	//Sends pong to server to keep connection alive
	public void pong(String afterPing) throws IOException
	{
		writer.write("PONG " + afterPing + "\r\n");
		writer.flush();
	}
}
