package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonPrimitive;

public class AcceptTradeCommand_Test {

	ExchangeWrapper mockExchange; 
	AcceptTrade_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"type\": \"acceptTrade\"," +
  "\"playerIndex\": \"0\"," +
  "\"willAccept\": true" +
  "}";
	
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testRollNumber() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonInput);
		cmdObj = new AcceptTrade_Command(mockExchange);
		cmdObj.setPlayerID(0);
		cmdObj.setGameID(0);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
	
}
