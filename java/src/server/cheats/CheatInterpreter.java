package server.cheats;

import shared.models.ClientModel;
import shared.models.Player;

public class CheatInterpreter 
{	
	//--------------------------------------------------------------------------------------------------
	//Singleton Setup
	
	private static CheatInterpreter _instance;

	public static CheatInterpreter instance() {
		if (_instance == null) 
			_instance = new CheatInterpreter();
		return _instance;
	}
		
	//--------------------------------------------------------------------------------------------------
	//Cheat Interpreting
	
	public void interpret(String cheat, Player player, ClientModel model)
	{
		Cheat cheatObj = null;
		String[] cheatArray = cheat.split(" ");
		switch (cheatArray[0].toLowerCase())
		{
			case "!give":
				cheatObj = new GiveCheat(cheatArray, player, model);
				break;
			case "!roll":
				cheatObj = new RollCheat(Integer.parseInt(cheatArray[1]), player, model);
				break;
		}
		try
		{
			cheatObj.execute();
		}
		catch(Exception e)
		{
			System.out.println("Invalid Cheat");
		}
	}
}
