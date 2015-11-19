package server.command.moves;

import org.junit.Before;
import org.junit.Test;

import server.command.ExchangeWrapper;
import server.main.ServerInvalidRequestException;
import server.manager.ServerManager;

import com.google.gson.JsonPrimitive;

public class RoadBuildingCommand_Test {

	ExchangeWrapper mockExchange; 
	RoadBuilding_Command cmdObj; 
	
	String jsonInput = "{ "+
  "\"type\": \"Road_Building\"," +
  "\"playerIndex\": \"0\"," +
  "\"spot1\": {\"x\":1,\"y\":1,\"direction\": \"N\"}," +
  "\"spot2\": {\"x\":0,\"y\":0,\"direction\": \"N\"}" +
  "}";
	
	
	@Before
	public void init(){
		mockExchange = new ExchangeWrapper(null);
		ServerManager.instance().reset();
		ServerManager.instance().setFakeFacades();
	}
	
	@Test
	public void testRoadBuilding() throws ServerInvalidRequestException{
		mockExchange.setJsonString(jsonInput);
		cmdObj = new RoadBuilding_Command(mockExchange);
		cmdObj.setPlayerID(0);
		cmdObj.setGameID(0);
		
		assert(cmdObj.execute().getClass() == JsonPrimitive.class);
	}
	
}
