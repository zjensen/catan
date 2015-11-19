package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class MonopolyCommand_Test 
{
	ExchangeWrapper mockExchange; 
	Monopoly_Command cmdObj; 
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testMonopoly() throws ServerInvalidRequestException{
		String jsonString = "{\"type\": \"Monopoly\",\"resource\": \"WOOD\"," 
				+ "\"playerIndex\": 1}";
		JsonObject json = new JsonParser().parse(jsonString)
				.getAsJsonObject();
		mockExchange.setJson(json);
		cmdObj = new Monopoly_Command(mockExchange);
		cmdObj.setPlayerID(1);
		cmdObj.setGameID(1);
		
		assert(cmdObj.execute().getClass() == JsonPrimitive.class);
	}
}
