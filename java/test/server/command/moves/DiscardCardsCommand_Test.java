package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class DiscardCardsCommand_Test 
{
	ExchangeWrapper mockExchange; 
	DiscardCards_Command cmdObj; 
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testDiscardCards() throws ServerInvalidRequestException{
		String jsonString = "{\"type\": \"discardCards\",\"playerIndex\": 1,"
				+ "\"discardedCards\":{\"brick\": 1, \"ore\": 1, \"sheep\": 1, \"wheat\": 3, \"wood\": 1}}";
		JsonObject json = new JsonParser().parse(jsonString)
				.getAsJsonObject();
		mockExchange.setJson(json);
		cmdObj = new DiscardCards_Command(mockExchange);
		cmdObj.setPlayerID(1);
		cmdObj.setGameID(1);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
}
