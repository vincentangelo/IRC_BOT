package bot_output;

import java.io.IOException;

import utils.StaticVariables;

public class Command_Center
{
	//A program = Requires multi-line interaction with bot
	private String usersCommand;
	private Morse_Code morse;
	Writer writer = new Writer();
	
	//Distributes the users commands
	public Command_Center(String usersCommand)
	{
		this.usersCommand = usersCommand;
	}
	public void distributeCommands() throws IOException
	{

		if(StaticVariables.programRunning == false)
		{
			StaticVariables.currentProgram = "";
			//Direct bot to the right method based on users commands
			switch(usersCommand)
			{
			//Not a program (just a method)
			case "help": 
				 help();
				break;
			//Not a program (just a method)
			case "flipcoin":
				try
				{
					new Coin_Flip();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			//Considered a program
			case "morsecode":
				StaticVariables.currentProgram = "morsecode";
				morse = new Morse_Code();
				morse.introduce_morse();
				break;
			default: 
				writer.writeMessage("Unknown Command. Type '$help' for assistance");
				break;	
			}
		//A program is running, finds out which one
		} else if (StaticVariables.programRunning)
		{		
			//if the program that is running is morse code:
			if(StaticVariables.currentProgram.equals("morsecode"))
			{
				morse = new Morse_Code();
				morse.encode(usersCommand);
			}
		}
	}
	
	//Provides help to direct user
	private void help() throws IOException
	{
		//Provides a list of commands available to the user
		String help = "List of commands include: ['$help'] [$flipcoin] ['$morsecode']";
		
		writer.writeMessage(help);
	}
}
