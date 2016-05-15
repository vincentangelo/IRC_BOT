package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Constants
{
	public static final String CHANNEL = "#savibottest";
	public static final String SERVER = "irc.freenode.net";
	
	//Add error trapping for socket
	public static final Socket SOCKET;
	public static final BufferedWriter WRITER;
	public static final BufferedReader READER;
	static{
		try{
	        SOCKET = new Socket(SERVER, 6667);
	        WRITER = new BufferedWriter(new OutputStreamWriter(SOCKET.getOutputStream()));
	        READER = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));
	    }catch(final IOException e){
	        throw new RuntimeException("Failed to create SOCKET instance in static block.",e);
	    }  
	}
}
