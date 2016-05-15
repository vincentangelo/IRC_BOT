package server_connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import bot_output.Command_Center;
import utils.Constants;

public class InitBot
{
	// The server to connect to
	private String server = "irc.freenode.net";
	private String nick, login, channel;

	// Users message - uses string split
	private String[] usersRawMessage = null;
	private String userMessage = "", botsResponse;

	public InitBot() throws Exception
	{
		login = "SaVi_Bot";
		nick = "SaVi_Bot";
		channel = Constants.CHANNEL;

		// Connect the bot to the irc client
		connect_Bot();
	}
	
	public void connect_Bot() throws Exception
	{
		// Connect directly to the IRC server.
		Socket socket = Constants.SOCKET;
		BufferedWriter writer = Constants.WRITER;
		BufferedReader reader = Constants.READER;

		//Socket socket = new Socket(server, 6667);
		//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		//BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Log on to the server.
		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + login + " 8 * : Testing Bot Connection\r\n");
		writer.flush();

		// Read lines from the server until it tells us we have connected.
		String line = null;
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

		// Join the channel.
		writer.write("JOIN " + channel + "\r\n");
		writer.flush();

		// Keep reading lines from the server.
		while ((line = reader.readLine()) != null)
		{
			// Allows irc server to know that the bot is still active
			// Keeps connection alive
			if (line.toLowerCase().startsWith("ping "))
			{
				System.out.println("Ping");
				// Respond to ping messages with 'PONG' and
				// the line that the 'PING' query sent out
				writer.write("PONG " + line.substring(5) + "\r\n");
				writer.flush();
			}
			// If a user has sent a message to the chat
			else if (line.toLowerCase().contains("privmsg") && line.toLowerCase().contains(channel))
			{
				usersRawMessage = line.split(":");
				userMessage = usersRawMessage[2].toString();

				//Handle the users commands
				if (userMessage.toLowerCase().startsWith("$"))
				{
					userMessage = userMessage.substring(1);
					Command_Center commands = new Command_Center(userMessage);
					botsResponse = commands.distribute_commands();
					System.out.println("Writing ___________");
					writer.write("PRIVMSG " + channel + " :" + botsResponse + "\r\n");
					writer.flush();
				} else
				{
					writer.write("PRIVMSG " + channel + " :To view a list of the bots commands"
							+ " please type '$help'\r\n");
					writer.flush();
				}
			} else
			{
				// Print the raw line received by the bot.
				System.out.println(line);
			}
		}
	}
}
