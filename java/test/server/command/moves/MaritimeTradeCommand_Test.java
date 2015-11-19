package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

public class MaritimeTradeCommand_Test 
{
	ExchangeWrapper mockExchange; 
	MaritimeTrade_Command cmdObj; 
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testGetCurrentModel() throws ServerInvalidRequestException{
		String jsonString = "{\"type\": \"maritimeTrade\",\"playerIndex\": 1,"
				+ "\"ratio\": 4, \"inputResource\": \"wood\", "
				+ "\"outputResource\": \"wheat\"}";
		JsonObject json = new JsonParser().parse(jsonString)
				.getAsJsonObject();
		mockExchange.setJson(json);
		cmdObj = new MaritimeTrade_Command(mockExchange);
		cmdObj.setPlayerID(1);
		cmdObj.setGameID(1);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
}
