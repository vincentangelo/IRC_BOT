package server_connection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connect_Bot
{
	public Connect_Bot() throws Exception 
	{
		// The server to connect to and our details.
        String server = "irc.freenode.net";
        String nick = "bot_beuser";
        String login = "bot_beuser";
        
        //Users message - uses string split
        String[] response = null;

        // The channel which the bot will join.
        String channel = "#bottest";
        
        // Connect directly to the IRC server.
        Socket socket = new Socket(server, 6667);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
        
        // Log on to the server.
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + login + " 8 * : Testing Bot Connection\r\n");
        writer.flush( );
        
        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = reader.readLine( )) != null) {
            if (line.indexOf("004") >= 0) {
                System.out.println("We are now logged in.");
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }
        
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        writer.flush( );
        
        // Keep reading lines from the server.
        while ((line = reader.readLine( )) != null) {
            if (line.toLowerCase( ).startsWith("Ping ")) {
            	System.out.println("Ping");
                // We must respond to PINGs to avoid being disconnected.
                writer.write("PONG " + line.substring(5) + "\r\n");
                writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush( );
            }
            else if(line.toLowerCase().contains("privmsg") && line.toLowerCase().contains(channel))
            {
            	response = line.split(":"); 
            	System.out.println("Writing ___________");
            	writer.write("PRIVMSG "  + channel + " :" + response[2] + "\r\n");
            	writer.flush();
            }
            else {
                // Print the raw line received by the bot.
                System.out.println(line);
            }
        }
    }
}
