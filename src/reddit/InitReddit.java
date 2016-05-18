package reddit;

import java.io.IOException;

import bot_output.Writer;
import utils.StaticVariables;

//initialize Reddit program
public class InitReddit 
{
	private Writer writer = new Writer();
	private String writerQuestion;
	public InitReddit()
	{
		//let command center know its running
		StaticVariables.programRunning = true;
	}
	
	public void introduceReddit()
	{
		try
		{
			writer.writeMessage("Ask the bot any question you would like:");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void answerQuestion(String question)
	{
		writerQuestion = question;
		
		//Temporary response, displays back question
		if(writerQuestion.equals("$exit"))
		{
			StaticVariables.programRunning = false;
		} else
			{
			try
			{	//results
				writer.writeMessage(writerQuestion);
				introduceReddit();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
