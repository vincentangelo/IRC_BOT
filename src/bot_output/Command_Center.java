package bot_output;

public class Command_Center
{
	private String usersCommand;
	
	//Distributes the users commands
	public Command_Center(String usersCommand)
	{
		this.usersCommand = usersCommand;
	}
	public String distribute_commands()
	{
		String botsResponse;

		//Direct bot to right method based on users commands
		switch(usersCommand)
		{
		case "help": 
			botsResponse = help();
			break;
		case "flipcoin":
			try
			{
				new Coin_Flip();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
		default: 
			botsResponse = "Unknown Command. Type '$help' for assistance";
			break;
			
		}
		
		return botsResponse;
	}
	
	private String help()
	{
		//Provides a list of commands available to the user
		String help = "List of commands include: ['$help'] [$flipcoin]";
		return help;
	}
}
