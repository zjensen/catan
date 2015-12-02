package server.command.games;

import server.command.ExchangeWrapper;
import server.command.ServerCommand;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;
import server.persistence.provider.IProvider;
import shared.communication.games.CreateGame_Input;
import shared.models.Game;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class Create_Command extends ServerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4835850925190495482L;
	private CreateGame_Input params = null;
	
	/**
	 * Command object for creating a game
	 * @param json
	 */
	public Create_Command(ExchangeWrapper exchange)
	{
		super(exchange);
		//here we will deserialize the JSON into a CreateGame_Input object
	}

	@Override
	public JsonElement execute() throws ServerInvalidRequestException
	{
		if (httpObj.getExchange() == null)
		{
			params = gson.fromJson(json, CreateGame_Input.class);
			return ServerManager.instance().getGamesFacade().create(params);
		}
		if(!hasUserCookie)
		{
			return new JsonPrimitive("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
		try
		{
			params = gson.fromJson(json, CreateGame_Input.class);
			JsonElement result = ServerManager.instance().getGamesFacade().create(params);
			addGame();
			return result;
		} 
		catch(Exception e)
		{
			throw new ServerInvalidRequestException("Malformed JSON");
		}
	}

	@Override
	public JsonElement execute(String json) throws ServerInvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addGame()
	{
		if(ServerManager.instance().getProvider()==null)
			return;
		IProvider p = ServerManager.instance().getProvider();
		p.startTransaction();
		try
		{
			Game game = ServerManager.instance().getGamesManager().getMostRecent();
			p.addGame(game);
			p.endTransaction(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			p.endTransaction(false);
		}
	}

}
