package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.facade.MovesFacade;
import shared.communication.moves.BuyDevCard_Input;

public class BuyDevCard_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private BuyDevCard_Input params = null;
	
	/**
	 * Command object for buying a dev card
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public BuyDevCard_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a BuyDevCard_Input object
	}

	@Override
	public JsonElement execute()
	{
		return MovesFacade.buyDevCard(params, playerID, gameID);
	}

}
