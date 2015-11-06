package server.command.games;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.GamesFacade;

public class List_Command implements ICommand {

	/**
	 * Command object for listing all games
	 */
	public List_Command()
	{
		
	}
	
	@Override
	public JsonElement execute()
	{
		return GamesFacade.list();
	}

}
