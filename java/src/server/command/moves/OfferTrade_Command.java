package server.command.moves;

import com.google.gson.JsonElement;

import server.command.ICommand;
import server.manager.ServerManager;
import shared.communication.moves.OfferTrade_Input;

public class OfferTrade_Command implements ICommand {

	private int playerID = -1;
	private int gameID = -1;
	private OfferTrade_Input params = null;
	
	/**
	 * Command object for offering a trade
	 * @param json
	 * @param playerID
	 * @param gameID
	 */
	public OfferTrade_Command(String json, int playerID, int gameID)
	{
		this.playerID = playerID;
		this.gameID = playerID;
		//here we will deserialize the JSON into a OfferTrade_Input object
	}

	@Override
	public JsonElement execute()
	{
		return ServerManager.instance().getMovesFacade().offerTrade(params, playerID, gameID);
	}

}
