package client.Facade;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import client.facade.ClientFacade;
import client.interpreter.Interpreter;
import shared.communication.moves.*;
import shared.definitions.ResourceType;
import shared.locations.*;
import shared.models.ClientModel;

public class CanDoMasterTest 
{
	private ClientModel clientModel;
	private ClientFacade cf;
	
	@Before
	public void setUp() throws Exception
	{
		Interpreter interpreter = new Interpreter();
				
		StringBuilder result = new StringBuilder("");
	    File file = new File("CanDoTestJSON.txt");
		
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String jsonString = result.toString();
				
		JsonElement jsonToParse = new JsonParser().parse(jsonString).getAsJsonObject();

		clientModel = interpreter.deserialize(jsonToParse);
		
		cf = new ClientFacade(clientModel);
				
	}

	@Test
	public void canSendChat_Test()
	{
		SendChat_Input params1 = new SendChat_Input(1, "");
		SendChat_Input params2 = new SendChat_Input(1, null);
		
		SendChat_Input params3 = new SendChat_Input(1, "This better work");
		
		
		assertFalse(cf.canSendChat(params1));
		assertFalse(cf.canSendChat(params2));
		
		assertTrue(cf.canSendChat(params3));
	}
	
	
	@Test
	public void canRollNumber_Test()
	{
		RollNumber_Input params1 = new RollNumber_Input(0, 1);
		RollNumber_Input params2 = new RollNumber_Input(0, 13);
		RollNumber_Input params3 = new RollNumber_Input(2, 7);
		RollNumber_Input params4 = new RollNumber_Input(-4, 9);
		
		RollNumber_Input params5 = new RollNumber_Input(0, 12);
		RollNumber_Input params6 = new RollNumber_Input(0, 2);
		
		
		assertFalse(cf.canRollNumber(params1));
		assertFalse(cf.canRollNumber(params2));
		assertFalse(cf.canRollNumber(params3));
		assertFalse(cf.canRollNumber(params4));
		
		assertTrue(cf.canRollNumber(params5));
		assertTrue(cf.canRollNumber(params6));
	}
	
	
	@Test
	public void canRobPlayer_Test()
	{
		RobPlayer_Input params1 = new RobPlayer_Input(0, 0, new HexLocation(0,1)); //same person
		RobPlayer_Input params2 = new RobPlayer_Input(0, 4, new HexLocation(0,1)); //non existent person
		RobPlayer_Input params3 = new RobPlayer_Input(1, 0, new HexLocation(0,1)); //not player's turn
		RobPlayer_Input params4 = new RobPlayer_Input(0, 3, new HexLocation(2,-2)); //robber is there
		RobPlayer_Input params5 = new RobPlayer_Input(0, 2, new HexLocation(-4,7)); //invalid hex
		
		RobPlayer_Input params6 = new RobPlayer_Input(0, 3, new HexLocation(-1,1)); //g2g
		
		
		assertFalse(cf.canRobPlayer(params1));
		assertFalse(cf.canRobPlayer(params2));
		assertFalse(cf.canRobPlayer(params3));
		assertFalse(cf.canRobPlayer(params4));
		assertFalse(cf.canRobPlayer(params5));
		
		assertTrue(cf.canRobPlayer(params6));
	}
	
	@Test
	public void canFinishTurn_Test()
	{
		FinishTurn_Input params1 = new FinishTurn_Input(1); //not their turn
		
		FinishTurn_Input params2 = new FinishTurn_Input(0); //g2g
		
		
		assertFalse(cf.canFinishTurn(params1));
		
		assertTrue(cf.canFinishTurn(params2));
	}
	
	@Test
	public void canBuyDevCard_Test()
	{
		BuyDevCard_Input params1 = new BuyDevCard_Input(1); //not their turn
		assertFalse(cf.canBuyDevCard(params1));
		
		BuyDevCard_Input params2 = new BuyDevCard_Input(0); //nice!
		assertTrue(cf.canBuyDevCard(params2));
		
		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turns
		
		BuyDevCard_Input params3 = new BuyDevCard_Input(1);  //not enough cards
		assertFalse(cf.canBuyDevCard(params3));
		
		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
	}
	
	@Test
	public void canYearOfPlenty_Test()
	{
		YearOfPlenty_Input params1 = new YearOfPlenty_Input(0, ResourceType.BRICK, ResourceType.SHEEP); //good
		assertTrue(cf.canYearOfPlenty(params1));
		
		YearOfPlenty_Input params2 = new YearOfPlenty_Input(1, ResourceType.BRICK, ResourceType.SHEEP); //wrong turn
		assertFalse(cf.canYearOfPlenty(params2));
		
		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turns
		
		YearOfPlenty_Input params3 = new YearOfPlenty_Input(1, ResourceType.BRICK, ResourceType.SHEEP); //no old cards to play
		assertFalse(cf.canYearOfPlenty(params3));
		
		cf.getClientModel().getTurnTracker().setCurrentTurn(2); //switch turns
		
		YearOfPlenty_Input params4 = new YearOfPlenty_Input(2, ResourceType.BRICK, ResourceType.SHEEP); //already played this turn
		assertFalse(cf.canYearOfPlenty(params4));
		
		cf.getClientModel().getTurnTracker().setCurrentTurn(3); //switch turns
		
		YearOfPlenty_Input params5 = new YearOfPlenty_Input(3, ResourceType.BRICK, ResourceType.SHEEP); //doesnt have card
		assertFalse(cf.canYearOfPlenty(params5));
		
		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
	}
	
	@Test
	public void canRoadBuilding_Test()
	{
		
	}
	
	@Test
	public void canSoldier_Test()
	{
		
	}
	
	@Test
	public void canMonument_Test()
	{
		
	}
	
	@Test
	public void canBuildRoad_Test()
	{
		
	}
	
	@Test
	public void canBuildSettlement_Test()
	{
		
	}
	
	@Test
	public void canBuildCity_Test()
	{
		
	}
	
	@Test
	public void canOfferTrade_Test()
	{
		
	}
	
	@Test
	public void canAccepTrade_Test()
	{
		
	}
	
	@Test
	public void canMaritimeTrade_Test()
	{
		
	}
	
	@Test
	public void canDiscardCards_Test()
	{
		
	}

}
