package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class YearOfPlentyCommand_Test 
{
	ExchangeWrapper mockExchange; 
	YearOfPlenty_Command cmdObj; 
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testYOP() throws ServerInvalidRequestException{
		String jsonString = "{\"type\": \"Year_of_Plenty\",\"playerIndex\": 1,"
				+ "\"resource1\":\"Wheat\", \"resource2\":\"Wheat\"}";
		JsonObject json = new JsonParser().parse(jsonString)
				.getAsJsonObject();
		mockExchange.setJson(json);
		cmdObj = new YearOfPlenty_Command(mockExchange);
		cmdObj.setPlayerID(1);
		cmdObj.setGameID(1);
		
		assert(cmdObj.execute().getClass() == JsonPrimitive.class);
	}
}
