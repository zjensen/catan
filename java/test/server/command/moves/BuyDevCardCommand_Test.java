package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonPrimitive;

public class BuyDevCardCommand_Test {

	ExchangeWrapper mockExchange; 
	BuyDevCard_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"type\": \"rollNumber\"," +
  "\"playerIndex\": 0" +
  "}";
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testBuyDevCard() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonInput);
		cmdObj = new BuyDevCard_Command(mockExchange);
		cmdObj.setPlayerID(0);
		cmdObj.setGameID(0);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
	
}
