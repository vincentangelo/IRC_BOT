package bot_output;

import java.io.IOException;
import java.util.HashMap;

import utils.StaticVariables;

//Basic program to convert english phrases to morse code
public class Morse_Code
{
	private String morseCode;
	private Writer writer = new Writer();
	
	public Morse_Code()
	{
		StaticVariables.programRunning = true;
	}
	
	//Displays program instructions message to user
	public void introduce_morse()
	{
		try
		{
			writer.writeMessage("Please enter a string to be encoded ($exit to leave program):");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//Converts from english to morse
	public void encode(String englishString)
	{
		System.out.println(englishString);
		String letter;
		String stringToMorse = "";
		
		morseCode = englishString;

		morseCode = morseCode.trim();

		for (int i = 0; i < morseCode.length(); i++)
		{
			letter = morseCode.substring(i, i + 1);

			if (letter.equals(" "))
			{
				stringToMorse = stringToMorse + EnglishToMorse.get(letter);
			} else
			{
				letter = letter.toUpperCase();
				stringToMorse = stringToMorse + EnglishToMorse.get(letter) + " ";
			}
		}

		stringToMorse.trim();
		//If program is running and user isn't trying to exit
		//Send message of results
		if(englishString.equals("$exit"))
		{
			StaticVariables.programRunning = false;
		} else
			{
			try
			{	//results
				writer.writeMessage(englishString + " encoded: " + stringToMorse);
				introduce_morse();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//Hashmap stores morse code data
	private static HashMap<String, String> EnglishToMorse = new HashMap<String, String>();
    static {
        EnglishToMorse.put("A", ".-");
        EnglishToMorse.put("B", "-...");
        EnglishToMorse.put("C", "-.-.");
        EnglishToMorse.put("D", "-..");
        EnglishToMorse.put("E", ".");
        EnglishToMorse.put("F", "..-.");
        EnglishToMorse.put("G", "--.");
        EnglishToMorse.put("H","....");
        EnglishToMorse.put("I", "..");
        EnglishToMorse.put("J", ".---");
        EnglishToMorse.put("K", "-.-");
        EnglishToMorse.put("L", ".-..");
        EnglishToMorse.put("M", "--");
        EnglishToMorse.put("N", "-.");
        EnglishToMorse.put("O", "---");
        EnglishToMorse.put("P", ".--.");
        EnglishToMorse.put("Q", "--.-");
        EnglishToMorse.put("R", ".-.");
        EnglishToMorse.put("S", "...");
        EnglishToMorse.put("T", "-");
        EnglishToMorse.put("U", "..-");
        EnglishToMorse.put("V", "...-");
        EnglishToMorse.put("W", ".--");
        EnglishToMorse.put("X", "-..-");
        EnglishToMorse.put("Y", "-.--");
        EnglishToMorse.put("Z", "--..");

        EnglishToMorse.put(" ", "  ");

        EnglishToMorse.put("0", "-----");
        EnglishToMorse.put("1", ".----");
        EnglishToMorse.put("2", "..---");
        EnglishToMorse.put("3", "...--");
        EnglishToMorse.put("4", "....-");
        EnglishToMorse.put("5", ".....");
        EnglishToMorse.put("6", "-....");
        EnglishToMorse.put("7", "--...");
        EnglishToMorse.put("8", "---..");
        EnglishToMorse.put("9", "----.");
        EnglishToMorse.put(".", ".-.-");
        EnglishToMorse.put(",", "--..--");
        EnglishToMorse.put("?", "..--..");
        EnglishToMorse.put("!", "-.-.--");
        EnglishToMorse.put("SOS", "···−−−···.");
    }

}
