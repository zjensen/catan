package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import server.command.ExchangeWrapper;
import server.command.moves.RollNumber_Command;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonPrimitive;

public class RollNumberCommand_Test {

	ExchangeWrapper mockExchange; 
	RollNumber_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"type\": \"rollNumber\"," +
  "\"playerIndex\": 0," +
  "\"number\": 6" +
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
		cmdObj = new RollNumber_Command(mockExchange);
		cmdObj.setPlayerID(0);
		cmdObj.setGameID(0);
		
		assert(cmdObj.execute().getClass() != JsonPrimitive.class);
	}
	
}
