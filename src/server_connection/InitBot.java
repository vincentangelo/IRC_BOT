package server_connection;

import java.io.BufferedWriter;

import utils.StaticVariables;

public class InitBot
{
	// The server to connect to
	private String nick, login, channel;

	public InitBot() throws Exception
	{
		login = "SaVi_Bot";
		nick = "SaVi_Bot";
		channel = StaticVariables.CHANNEL;

		// Connect the bot to the irc client
		connect_Bot();
	}

	public void connect_Bot() throws Exception
	{
		// Use default buffered writer
		BufferedWriter writer = StaticVariables.WRITER;
		// Use buffered reader from class reader
		Reader reader = new Reader();

		// Log on to the server.
		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + login + " 8 * : Testing Bot Connection\r\n");
		writer.flush();

		// Make sure the bot is connected
		reader.connect();

		// Join the channel.
		writer.write("JOIN " + channel + "\r\n");
		writer.flush();

		// Loop through server output
		reader.readServerOutput();
	}
}
