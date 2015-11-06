package server.command.game;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.GameFacade;
import shared.communication.game.GameModel_Input;

public class Model_Command implements ICommand {

	private GameModel_Input params = null;
	
	/**
	 * Command object for getting the client model
	 * @param version
	 */
	public Model_Command(int version)
	{
		//here we will deserialize the JSON into a GameModel_Input object
	}

	@Override
	public JsonElement execute()
	{
		return GameFacade.model(params);
	}

}
