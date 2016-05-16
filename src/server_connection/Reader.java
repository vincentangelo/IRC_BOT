package server_connection;

import java.io.BufferedReader;
import java.io.IOException;

import bot_output.Command_Center;
import bot_output.Writer;
import utils.StaticVariables;

public class Reader
{
	// Intitialize reader and writer
	private BufferedReader reader = StaticVariables.READER;
	private Writer writer = new Writer();
	
	// Users raw message - uses string split
	private String[] usersRawMessage;
	private String line = null, userMessage; 

	
	// Allows bot to connect to the server
	public void connect() throws IOException
	{
		// Read lines from the server until it tells us we have connected.
		while ((line = reader.readLine()) != null)
		{
			if (line.indexOf("004") >= 0)
			{
				System.out.println("We are now logged in.");
				break;
			} else if (line.indexOf("433") >= 0)
			{
				System.out.println("Nickname is already in use.");
				return;
			}
		}
	}
	
	//Reads the servers output
	public void readServerOutput() throws IOException
	{
		// Keep reading lines from the server.
		while ((line = reader.readLine()) != null)
		{
			System.out.println(line);
			// Allows irc server to know that the bot is still active
			// Keeps connection alive
			if (line.toLowerCase().startsWith("ping "))
			{
				System.out.println("Ping");
				// Respond to ping messages with 'PONG' and
				// the line that the 'PING' query sent out
				writer.pong(line.substring(5));
			}
			// If a user has sent a message to the chat
			else if (line.toLowerCase().contains("privmsg") && line.toLowerCase().contains(StaticVariables.CHANNEL))
			{
				usersRawMessage = line.split(":");
				userMessage = usersRawMessage[2].toString();

				// Handle the users commands
				if (userMessage.toLowerCase().startsWith("$") && StaticVariables.programRunning == false)
				{
					userMessage = userMessage.substring(1);
					Command_Center commands = new Command_Center(userMessage);
					commands.distributeCommands();
				} 
				else if(StaticVariables.programRunning)
				{
					//if program is running, send the program specific userMessage
					Command_Center commands = new Command_Center(userMessage);
					commands.distributeCommands();
				}
				//kills the bot
				else if(userMessage.equals("go kill yourself") && StaticVariables.programRunning == false)
				{
					StaticVariables.SOCKET.close();
				}
				else
				{
					writer.writeMessage("To view a list of the bots commands please type '$help'");
				}
			} 
			else //if line isn't a ping or a sent message
			{
				// Print the raw line received by the bot.
				System.out.println(line);
			}
		}
	}
}
