package bot_output;

public class Coin_Flip
{
	public Coin_Flip() throws Exception
	{
		//Declare variables
		boolean heads = false;
		String headsTails = "";
		double num = Math.random();
		Writer writer = new Writer();
		
		if (num > .5)
			heads = true;
		
		if (heads)
			headsTails = "Heads";
		else
			headsTails = "Tails";
		
		writer.writeMessage(headsTails);
	}
}
