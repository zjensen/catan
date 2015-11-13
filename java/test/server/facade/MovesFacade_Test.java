package server.facade;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import server.manager.ServerManager;
import shared.utils.Interpreter;
import shared.communication.moves.*;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.models.ClientModel;
import shared.models.DevCards;
import shared.models.TurnTracker;

public class MovesFacade_Test 
{
	private Interpreter interpreter = new Interpreter();
	private IMovesFacade mf;
	private int gameID;
	ClientModel ogModel;
	
	@Before
	public void setUp() throws Exception
	{
		StringBuilder result = new StringBuilder("");
	    File file = new File("MovesFacadeTestJSON.txt");
		
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

		ogModel = interpreter.deserialize(jsonToParse);
		
		ServerManager.instance().setFacades();
		
		ServerManager.instance().reset();
		
		gameID = ServerManager.instance().getGamesManager().addNewGameGetID(ogModel, "TITLE");
		mf = ServerManager.instance().getMovesFacade();			
	}

	@Test
	public void sendChat_Test()
	{
		SendChat_Input params1 = new SendChat_Input(1, ""); //empty string will fail
		SendChat_Input params2 = new SendChat_Input(1, null); //player not in game
		SendChat_Input params3 = new SendChat_Input(-1, ""); //incorrect playerID
		
		SendChat_Input params4 = new SendChat_Input(1, "Let's mess up the gameID");
		SendChat_Input params5 = new SendChat_Input(1, "Let's mess up the userID");
		
		SendChat_Input params6 = new SendChat_Input(1, "This better work");
		
		
		assertNull(mf.sendChat(params1,1,gameID));
		assertNull(mf.sendChat(params2,1,gameID));
		assertNull(mf.sendChat(params3,1,gameID));
		
		assertNull(mf.sendChat(params4,1,-4206669));
		assertNull(mf.sendChat(params5,-4206669,gameID));
		
		ClientModel newModel = interpreter.deserialize(mf.sendChat(params6,1,gameID)); //send the chat
		assertTrue(newModel.getChat().getLines().length == 1); //message was added
		assertTrue(newModel.getChat().getLines()[0].getMessage().equals("This better work")); //message was correct
		assertTrue(newModel.getChat().getLines()[0].getSource().equals("Brooke")); //message source was correct
	}
	
	@Test
	public void rollNumber_Test1() //test errors
	{
		RollNumber_Input params1 = new RollNumber_Input(1, 1); //wrong turn
		RollNumber_Input params2 = new RollNumber_Input(0, 13); //wrong number
		RollNumber_Input params3 = new RollNumber_Input(-4, 9); //player index out of bounds
		RollNumber_Input params4 = new RollNumber_Input(0, 12);
		RollNumber_Input params5 = new RollNumber_Input(0, 2);
		
		assertNull(mf.rollNumber(params1,1,gameID));
		assertNull(mf.rollNumber(params2,1,gameID));
		assertNull(mf.rollNumber(params3,1,gameID));
		assertNull(mf.rollNumber(params4,1,-4206669)); //wrong gameID
		assertNull(mf.rollNumber(params5,-4206669,gameID)); //wrong playerID
	}
	
	@Test
	public void rollNumber_Test2() //roll a seven, players need to discard
	{
		RollNumber_Input params1 = new RollNumber_Input(0, 7);
		
		ClientModel newModel = interpreter.deserialize(mf.rollNumber(params1,1,gameID)); //roll baby roll
		
		assertTrue(newModel.getTurnTracker().getStatus().equals("discarding")); //some players need to discard
	}
	
	@Test
	public void rollNumber_Test3() //roll a seven, no one needs to discard
	{
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().setOre(0); //make all players have < 7 resources
		
		RollNumber_Input params1 = new RollNumber_Input(0, 7);
		
		ClientModel newModel = interpreter.deserialize(mf.rollNumber(params1,1,gameID)); //roll baby roll
		
		assertTrue(newModel.getTurnTracker().getStatus().equals("robbing")); //no players need to discard, skip to robbing
	}
	
	@Test
	public void rollNumber_Test4() //check handing out resources
	{
		RollNumber_Input params1 = new RollNumber_Input(0, 6);
		
		//resources before roll
		int player0Wheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getWheat();
		int player1Wood = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(1).getResources().getWood();
		int player3Wood = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getWood();
		int bankWood = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWood();
		int bankWheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		
		
		ClientModel newModel = interpreter.deserialize(mf.rollNumber(params1,1,gameID)); //roll baby roll
		
		//resources after roll
		int player0Wheat2 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getWheat();
		int player1Wood2 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(1).getResources().getWood();
		int player3Wood2 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getWood();
		int bankWood2 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWood();
		int bankWheat2 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		
		assertTrue(player0Wheat2 == player0Wheat+1); //player gained wheat
		assertTrue(bankWheat2 == bankWheat-1); //bank gave up wheat
		
		assertTrue(player1Wood2 == player1Wood+1); //player gained wood
		assertTrue(player3Wood2 == player3Wood+1); //player gained wood
		assertTrue(bankWood2 == bankWood-2); //bank gave up 2 wood
		
		assertTrue(newModel.getTurnTracker().getStatus().equals("playing")); //no players need to discard, skip to robbing
	}
	
	@Test
	public void robPlayer_Test1() //check for failures
	{
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("robbing"); //set to robbing
		
		RobPlayer_Input params1 = new RobPlayer_Input(0, 0, new HexLocation(0,1)); //same person
		RobPlayer_Input params3 = new RobPlayer_Input(1, 0, new HexLocation(0,1)); //not player's turn
		RobPlayer_Input params4 = new RobPlayer_Input(0, 3, new HexLocation(2,-2)); //robber is there
		RobPlayer_Input params5 = new RobPlayer_Input(0, 2, new HexLocation(-4,7)); //invalid hex
		
		assertNull(mf.robPlayer(params1,1,gameID));
		assertNull(mf.robPlayer(params3,1,gameID));
		assertNull(mf.robPlayer(params4,1,gameID));
		assertNull(mf.robPlayer(params5,1,gameID));
	}
	
	@Test
	public void robPlayer_Test2() //rob someone
	{
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("robbing"); //set to robbing
		
		RobPlayer_Input params = new RobPlayer_Input(0, 3, new HexLocation(-1,1)); //g2g
		//victim here has wood, sheep, ore
		
		//resources before rob
		int playerWood = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getWood();
		int playerSheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getSheep();
		int playerOre = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getOre();
		int victimWood = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getWood();
		int victimSheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getSheep();
		int victimOre = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getOre();
		
		ClientModel newModel = interpreter.deserialize(mf.robPlayer(params,1,gameID)); //rob rob rob
		
		//resources after rob
		int playerWood2 = newModel.getPlayerByIndex(0).getResources().getWood();
		int playerSheep2 = newModel.getPlayerByIndex(0).getResources().getSheep();
		int playerOre2 = newModel.getPlayerByIndex(0).getResources().getOre();
		int victimWood2 = newModel.getPlayerByIndex(3).getResources().getWood();
		int victimSheep2 = newModel.getPlayerByIndex(3).getResources().getSheep();
		int victimOre2 = newModel.getPlayerByIndex(3).getResources().getOre();
		
		//make sure resources were swapped correctly
		if((victimWood2 == victimWood-1))
		{
			assertTrue(playerWood2 == playerWood+1);
			
			assertTrue(playerSheep2 == playerSheep);
			assertTrue(playerOre2 == playerOre);
		}
		else if((victimSheep2 == victimSheep-1))
		{
			assertTrue(playerSheep2 == playerSheep+1);
			
			assertTrue(playerWood2 == playerWood);
			assertTrue(playerOre2 == playerOre);
		}
		else if((victimOre2 == victimOre-1))
		{
			assertTrue(playerOre2 == playerOre+1);
			
			assertTrue(playerSheep2 == playerSheep);
			assertTrue(playerWood2 == playerWood);
		}
		else
		{
			assertTrue(false); //fail
		}
		
		assertTrue(newModel.getTurnTracker().getStatus().equals("playing")); //status correctly changes
		assertTrue(newModel.getMap().getRobber().equals(params.getLocation())); //robber moved
	}
	
	@Test
	public void robPlayer_Test3() //rob no one
	{
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("robbing"); //set to robbing
		
		RobPlayer_Input params = new RobPlayer_Input(0, -1, new HexLocation(0,-2)); //g2g

		ClientModel newModel = interpreter.deserialize(mf.robPlayer(params,1,gameID)); //rob rob rob
		
		assertTrue(newModel.getTurnTracker().getStatus().equals("playing")); //status correctly changes
		assertTrue(newModel.getMap().getRobber().equals(params.getLocation())); //robber moved
	}
	
	@Test
	public void canFinishTurn_Test() // Changes turn 
	{
		FinishTurn_Input params1 = new FinishTurn_Input(1); //not their turn
		FinishTurn_Input params2 = new FinishTurn_Input(0); //good 2 go
	
		assertNull(mf.finishTurn(params1, 1, gameID)); // not this player's turn
		assertNull(mf.finishTurn(params2, 1, gameID)); // not correct part of turn
		
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("playing"); //set to playing

		ClientModel newModel = interpreter.deserialize(mf.finishTurn(params2,1,gameID));
		
		assertEquals(newModel.getTurnTracker().getCurrentTurn(), 1);
		assertTrue(newModel.getTurnTracker().getStatus().equals("rolling")); //status correctly changes
		assertNull(mf.finishTurn(params2,1,gameID));
	}
	
	@Test
	public void canFinishTurn_Test2() // Going from player index 3 to 0 test
	{
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("playing"); //set to playing
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setCurrentTurn(3);

		FinishTurn_Input params2 = new FinishTurn_Input(3); //good 2 go

		ClientModel newModel = interpreter.deserialize(mf.finishTurn(params2,1,gameID));
		
		assertEquals(newModel.getTurnTracker().getCurrentTurn(), 0);
		assertTrue(newModel.getTurnTracker().getStatus().equals("rolling")); //status correctly changes
		assertNull(mf.finishTurn(params2,1,gameID));
	}
	
	@Test
	public void canBuyDevCard_Test()
	{
		BuyDevCard_Input params1 = new BuyDevCard_Input(0);
		
		assertNull(mf.buyDevCard(params1,1,gameID)); // not their turn

		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("playing");

		//resources before buying dev card
		int player1Wheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getWheat();
		int player1Sheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getSheep();
		int player1Ore   = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getOre();
		int bankWheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		int bankSheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getSheep();
		int bankOre   = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getOre();
		
		ClientModel newModel = interpreter.deserialize(mf.buyDevCard(params1,1,gameID));
		
		//resources after buying dev card
		int afterPlayer1Wheat = newModel.getPlayerByIndex(0).getResources().getWheat();
		int afterPlayer1Sheep = newModel.getPlayerByIndex(0).getResources().getSheep();
		int afterPlayer1Ore   = newModel.getPlayerByIndex(0).getResources().getOre();
		int afterBankWheat = newModel.getBank().getWheat();
		int afterBankSheep = newModel.getBank().getSheep();
		int afterBankOre   = newModel.getBank().getOre();
		
		// Check resources of player and bank
		assertTrue(player1Wheat == afterPlayer1Wheat+1);
		assertTrue(player1Sheep == afterPlayer1Sheep+1);
		assertTrue(player1Ore   == afterPlayer1Ore+1);
		
		assertTrue(bankWheat == afterBankWheat-1);
		assertTrue(bankSheep == afterBankSheep-1);
		assertTrue(bankOre   == afterBankOre-1);
	}

	@Test
	public void canYearOfPlenty_Test() // Successfully plays a year of plenty card
	{
		YearOfPlenty_Input params1 = new YearOfPlenty_Input(0, ResourceType.WHEAT, ResourceType.SHEEP);

		assertNull(mf.yearOfPlenty(params1,1,gameID)); // not their turn
		
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("playing");
		
		//resources before playing year of plenty card
		int player1Wheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getWheat();
		int player1Sheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getSheep();
		int bankWheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		int bankSheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getSheep();
	
		ClientModel newModel = interpreter.deserialize(mf.yearOfPlenty(params1,1,gameID));
		
		//resources after playing year of plenty card
		int afterPlayer1Wheat = newModel.getPlayerByIndex(0).getResources().getWheat();
		int afterPlayer1Sheep = newModel.getPlayerByIndex(0).getResources().getSheep();
		int afterBankWheat = newModel.getBank().getWheat();
		int afterBankSheep = newModel.getBank().getSheep();
		
		// Check resources of player and bank
		assertTrue(player1Wheat == afterPlayer1Wheat-1);
		assertTrue(player1Sheep == afterPlayer1Sheep-1);
		assertTrue(bankWheat == afterBankWheat+1);
		assertTrue(bankSheep == afterBankSheep+1);
	}	
	
	@Test
	public void canYearOfPlenty_Test2() // Try to play a year of plenty card that the player doesn't have
	{	
		YearOfPlenty_Input params2 = new YearOfPlenty_Input(3, ResourceType.WHEAT, ResourceType.SHEEP);
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("playing");
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setCurrentTurn(3);
		
		//resources before trying to use year of plenty card
		int player4Wheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getWheat();
		int player4Sheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getSheep();
		int bankWheat4 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		int bankSheep4 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getSheep();
		int player4YearOfPlentyCards = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getTotalYearOfPlentyCards();
		
		assertNull(mf.yearOfPlenty(params2,1,gameID));
		
		//resources before after trying to use year of plenty card
		int afterPlayer4Wheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getWheat();
		int afterPlayer4Sheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getResources().getSheep();
		int afterBankWheat4 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		int afterBankSheep4 = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getSheep();
		int afterPlayer4YearOfPlentyCards = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(3).getTotalYearOfPlentyCards();
		
		assertTrue(player4Wheat == afterPlayer4Wheat);
		assertTrue(player4Sheep == afterPlayer4Sheep);
		assertTrue(bankWheat4 == afterBankWheat4);
		assertTrue(bankSheep4 == afterBankSheep4);
		assertTrue(player4YearOfPlentyCards == afterPlayer4YearOfPlentyCards);
	}	
		
	@Test
	public void canYearOfPlenty_Test3() // Plays a card. Then tries to play another one
	{
		YearOfPlenty_Input params1 = new YearOfPlenty_Input(0, ResourceType.WHEAT, ResourceType.SHEEP);
		
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getTurnTracker().setStatus("playing");
		
		//resources before playing year of plenty card
		int player1Wheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getWheat();
		int player1Sheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getResources().getSheep();
		int bankWheat = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getWheat();
		int bankSheep = ServerManager.instance().getGamesManager().getClientModelById(gameID).getBank().getSheep();
		int player1YearOfPlentyCards = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getTotalYearOfPlentyCards();
		
		ClientModel newModel = interpreter.deserialize(mf.yearOfPlenty(params1,1,gameID));
		
		//resources after playing year of plenty card
		int afterPlayer1Wheat = newModel.getPlayerByIndex(0).getResources().getWheat();
		int afterPlayer1Sheep = newModel.getPlayerByIndex(0).getResources().getSheep();
		int afterBankWheat = newModel.getBank().getWheat();
		int afterBankSheep = newModel.getBank().getSheep();
		int afterPlayer1YearOfPlentyCards = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getTotalYearOfPlentyCards();
		
		// Check resources of player and bank
		assertTrue(player1Wheat == afterPlayer1Wheat-1);
		assertTrue(player1Sheep == afterPlayer1Sheep-1);
		assertTrue(bankWheat == afterBankWheat+1);
		assertTrue(bankSheep == afterBankSheep+1);
		assertTrue(player1YearOfPlentyCards == afterPlayer1YearOfPlentyCards+1);
		
		// Give more year of plenty cards to player
		DevCards cards = new DevCards(0,0,0,2,0);
		ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).setOldDevCards(cards);

		// Grab new value of year of plenty cards
		afterPlayer1YearOfPlentyCards = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getTotalYearOfPlentyCards();
		
		assertNull(mf.yearOfPlenty(params1,1,gameID));

		//resources after trying to play 2nd year of plenty card
		int twoAfterPlayer1Wheat = newModel.getPlayerByIndex(0).getResources().getWheat();
		int twoAfterPlayer1Sheep = newModel.getPlayerByIndex(0).getResources().getSheep();
		int twoAfterBankWheat = newModel.getBank().getWheat();
		int twoAfterBankSheep = newModel.getBank().getSheep();
		int twoAfterPlayer1YearOfPlentyCards = ServerManager.instance().getGamesManager().getClientModelById(gameID).getPlayerByIndex(0).getTotalYearOfPlentyCards();
				
		// Check resources of player and bank after trying to play 2nd year of plenty card
		assertTrue(afterPlayer1Wheat == twoAfterPlayer1Wheat);
		assertTrue(afterPlayer1Sheep == twoAfterPlayer1Sheep);
		assertTrue(afterBankWheat == twoAfterBankWheat);
		assertTrue(afterBankSheep == twoAfterBankSheep);
		assertTrue(afterPlayer1YearOfPlentyCards == twoAfterPlayer1YearOfPlentyCards);
	}	

	
//	@Test
//	public void canRoadBuilding_Test()
//	{
//		Player p = clientModel.getPlayerByIndex(0);
//		EdgeLocation e1 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North); //free
//		EdgeLocation e2 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest); //free
//		EdgeLocation e3 = new EdgeLocation(new HexLocation(0,2), EdgeDirection.North); //taken
//		EdgeLocation e4 = new EdgeLocation(new HexLocation(0,3), EdgeDirection.NorthEast); //off map
//		
//		p.setRoads(1);
//		
//		RoadBuilding_Input params2 = new RoadBuilding_Input(0, e1, null); //no
//		RoadBuilding_Input params3 = new RoadBuilding_Input(0, e1, e2); //no
//		RoadBuilding_Input params4 = new RoadBuilding_Input(0, e3, null); //no
//		RoadBuilding_Input params5 = new RoadBuilding_Input(0, null, e3); //no
//		assertFalse(mf.canRoadBuilding(params2));
//		assertFalse(mf.canRoadBuilding(params3));
//		assertFalse(mf.canRoadBuilding(params4));
//		assertFalse(mf.canRoadBuilding(params5));
//		
//		p.setRoads(13);
//		
//		RoadBuilding_Input params6 = new RoadBuilding_Input(0, e1, e2); //yes
//		RoadBuilding_Input params7 = new RoadBuilding_Input(2, e1, e2); //false
//		RoadBuilding_Input params8 = new RoadBuilding_Input(0, e1, e4); //false
//		assertTrue(mf.canRoadBuilding(params6));
//		assertFalse(mf.canRoadBuilding(params7));
//		assertFalse(mf.canRoadBuilding(params8));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn
//		
//		RoadBuilding_Input params9 = new RoadBuilding_Input(1, e1, e2); //no
//		assertFalse(mf.canRoadBuilding(params9));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn bacc
//	}
//	
//	@Test
//	public void canSoldier_Test()
//	{
//		Soldier_Input params1 = new Soldier_Input(0, 3, new HexLocation(2, -2)); //robber already here
//		Soldier_Input params2 = new Soldier_Input(0, 2, new HexLocation(1, -1)); //good
//		Soldier_Input params3 = new Soldier_Input(0, 0, new HexLocation(1, -1)); //cant rob self
//		Soldier_Input params4 = new Soldier_Input(0, 3, new HexLocation(0, 0)); //not at location
//		Soldier_Input params5 = new Soldier_Input(0, 3, new HexLocation(-3, 1)); //not a land location
//		
//		assertFalse(mf.canSoldier(params1));
//		assertTrue(mf.canSoldier(params2));
//		assertFalse(mf.canSoldier(params3));
//		assertFalse(mf.canSoldier(params4));
//		assertFalse(mf.canSoldier(params5));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn
//		
//		Soldier_Input params6 = new Soldier_Input(1, 2, new HexLocation(1, -1)); //no dev card
//		assertFalse(mf.canSoldier(params6));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canMonument_Test()
//	{
//		Monument_Input params1 = new Monument_Input(0); //bad
//		assertFalse(mf.canMonument(params1));
//		
//		Monument_Input params2 = new Monument_Input(1); //wrong turn
//		assertFalse(mf.canMonument(params2));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turns
//		
//		Monument_Input params3 = new Monument_Input(1); //monument is a new card
//		assertFalse(mf.canMonument(params3)); //failing
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(2); //switch turns
//		
//		Monument_Input params4 = new Monument_Input(2); //already played this turn
//		assertFalse(mf.canMonument(params4));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(3); //switch turns
//		
//		Monument_Input params5 = new Monument_Input(3); //doesnt have card
//		assertFalse(mf.canMonument(params5));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canBuildRoad_Test()
//	{
//		BuildRoad_Input params1 = new BuildRoad_Input(0, new EdgeLocation(new HexLocation(2,1), EdgeDirection.North), false); //good
//		BuildRoad_Input params2 = new BuildRoad_Input(0, new EdgeLocation(new HexLocation(2,1), EdgeDirection.NorthEast), false); //not connected
//		BuildRoad_Input params3 = new BuildRoad_Input(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast), false); //location already taken
//		assertTrue(mf.canBuildRoad(params1));
//		assertFalse(mf.canBuildRoad(params2));
//		assertFalse(mf.canBuildRoad(params3));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn
//		
//		BuildRoad_Input params4 = new BuildRoad_Input(1, new EdgeLocation(new HexLocation(2,1), EdgeDirection.North), true); //good
//		BuildRoad_Input params5 = new BuildRoad_Input(1, new EdgeLocation(new HexLocation(-3,2), EdgeDirection.North), false); //not on land
//		BuildRoad_Input params6 = new BuildRoad_Input(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast), true); //location already taken
//		assertTrue(mf.canBuildRoad(params4));
//		assertFalse(mf.canBuildRoad(params5));
//		assertFalse(mf.canBuildRoad(params6));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canBuildSettlement_Test()
//	{
//		Player p = clientModel.getPlayerByIndex(0);
//		Player p3 = clientModel.getPlayerByIndex(3);
//		clientModel.getMap().getRoads().put(new EdgeLocation(new HexLocation(2,0),EdgeDirection.NorthWest), p);
//		clientModel.getMap().getRoads().put(new EdgeLocation(new HexLocation(1,1),EdgeDirection.North), p);
//		clientModel.getMap().getRoads().put(new EdgeLocation(new HexLocation(-1,1),EdgeDirection.NorthWest), p3);
//		
//		
//		BuildSettlement_Input params1 = new BuildSettlement_Input(0, new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest),false); //true
//		BuildSettlement_Input params2 = new BuildSettlement_Input(0, new VertexLocation(new HexLocation(1,1), VertexDirection.NorthWest),false); //too close
//		BuildSettlement_Input params3 = new BuildSettlement_Input(0, new VertexLocation(new HexLocation(-1,0), VertexDirection.NorthEast),false); //nooo
//		BuildSettlement_Input params4 = new BuildSettlement_Input(0, new VertexLocation(new HexLocation(2,1), VertexDirection.NorthWest),false); //something already there
//		BuildSettlement_Input params5 = new BuildSettlement_Input(1, new VertexLocation(new HexLocation(2,1), VertexDirection.NorthWest),false); //wrong turn
//		assertTrue(mf.canBuildSettlement(params1));
//		assertFalse(mf.canBuildSettlement(params2));
//		assertFalse(mf.canBuildSettlement(params3));
//		assertFalse(mf.canBuildSettlement(params4));
//		assertFalse(mf.canBuildSettlement(params5));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(3); //switch turn back
//		
//		BuildSettlement_Input params6 = new BuildSettlement_Input(3, new VertexLocation(new HexLocation(-1,1), VertexDirection.NorthWest),false); //no supplies
//		assertFalse(mf.canBuildSettlement(params6));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//		
//		clientModel.getMap().getRoads().remove(new EdgeLocation(new HexLocation(2,0),EdgeDirection.NorthWest));
//		clientModel.getMap().getRoads().remove(new EdgeLocation(new HexLocation(1,1),EdgeDirection.North));
//		clientModel.getMap().getRoads().remove(new EdgeLocation(new HexLocation(-1,1),EdgeDirection.NorthWest));
//	}
//	
//	@Test
//	public void canBuildCity_Test()
//	{
//		BuildCity_Input params1 = new BuildCity_Input(0, new VertexLocation(new HexLocation(2,1), VertexDirection.NorthWest)); //yee
//		BuildCity_Input params2 = new BuildCity_Input(0, new VertexLocation(new HexLocation(0,1), VertexDirection.NorthWest)); //no settlement here
//		assertTrue(mf.canBuildCity(params1));
//		assertFalse(mf.canBuildCity(params2));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn back
//		
//		BuildCity_Input params3 = new BuildCity_Input(1, new VertexLocation(new HexLocation(0,1), VertexDirection.NorthWest)); //no resources
//		assertFalse(mf.canBuildCity(params3));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canOfferTrade_Test()
//	{
//		ResourceCards r = new ResourceCards(-1,-2,-1,-1,-3);
//		
//		OfferTrade_Input params1 = new OfferTrade_Input(0, r, 3); //yes
//		OfferTrade_Input params2 = new OfferTrade_Input(0, r, 0); //no
//		assertTrue(mf.canOfferTrade(params1));
//		assertFalse(mf.canOfferTrade(params2));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn
//		
//		OfferTrade_Input params3 = new OfferTrade_Input(1, r, 0); //no cards
//		assertTrue(mf.canOfferTrade(params3));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canAccepTrade_Test()
//	{
//		ResourceCards r = new ResourceCards(1,2,1,1,3);
//		clientModel.setTradeOffer(new TradeOffer());
//		clientModel.getTradeOffer().setOffer(r);
//		clientModel.getTradeOffer().setReceiver(0);
//		clientModel.getTradeOffer().setSender(1);
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn
//		
//		AcceptTrade_Input params1 = new AcceptTrade_Input(0, false); //good
//		AcceptTrade_Input params2 = new AcceptTrade_Input(0, true); //good
//		AcceptTrade_Input params3 = new AcceptTrade_Input(1, false); //wrong user
//		assertTrue(mf.canAcceptTrade(params1));
//		assertTrue(mf.canAcceptTrade(params2));
//		assertFalse(mf.canAcceptTrade(params3));
//		
//		clientModel.getTradeOffer().setReceiver(2);
//		AcceptTrade_Input params4 = new AcceptTrade_Input(2, true);
//		assertTrue(mf.canAcceptTrade(params4));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canMaritimeTrade_Test()
//	{
//		MaritimeTrade_Input params1 = new MaritimeTrade_Input(0, 3, ResourceType.ORE, ResourceType.WHEAT); //good
//		MaritimeTrade_Input params2 = new MaritimeTrade_Input(0, 2, ResourceType.ORE, ResourceType.WHEAT); //not a 2 port
//		MaritimeTrade_Input params3 = new MaritimeTrade_Input(0, 3, ResourceType.WOOD, ResourceType.WHEAT); //not enough resources
//		MaritimeTrade_Input params4 = new MaritimeTrade_Input(0, 4, ResourceType.ORE, ResourceType.WHEAT); //yassss
//		MaritimeTrade_Input params5 = new MaritimeTrade_Input(1, 4, ResourceType.ORE, ResourceType.WHEAT); //nooooo
//		
//		assertTrue(mf.canMaritimeTrade(params1));
//		assertFalse(mf.canMaritimeTrade(params2));
//		assertFalse(mf.canMaritimeTrade(params3));
//		assertTrue(mf.canMaritimeTrade(params4));
//		assertFalse(mf.canMaritimeTrade(params5));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(1); //switch turn
//		
//		MaritimeTrade_Input params6 = new MaritimeTrade_Input(1, 2, ResourceType.WOOD, ResourceType.WHEAT); //yesss
//		MaritimeTrade_Input params7 = new MaritimeTrade_Input(1, 3, ResourceType.WOOD, ResourceType.WHEAT); //nooooo
//		MaritimeTrade_Input params8 = new MaritimeTrade_Input(1, 2, ResourceType.ORE, ResourceType.WHEAT); //nooooo
//		
//		assertTrue(mf.canMaritimeTrade(params6));
//		assertFalse(mf.canMaritimeTrade(params7));
//		assertFalse(mf.canMaritimeTrade(params8));
//		
//		cf.getClientModel().getTurnTracker().setCurrentTurn(0); //switch turn back
//	}
//	
//	@Test
//	public void canDiscardCards_Test()
//	{
//		ResourceCards r1 = new ResourceCards(1,2,1,1,3);
//		ResourceCards r2 = new ResourceCards(3,2,1,1,3);
//		
//		DiscardCards_Input params1 = new DiscardCards_Input(0, r1); //yes
//		DiscardCards_Input params2 = new DiscardCards_Input(0, r2); //too many cards
//		DiscardCards_Input params3 = new DiscardCards_Input(1, r2); //wrong turn
//		
//		assertTrue(mf.canDiscardCards(params1));
//		assertFalse(mf.canDiscardCards(params2));
//		assertFalse(mf.canDiscardCards(params3));
//		
//		clientModel.getPlayerByIndex(0).setDiscarded(true);
//		
//		DiscardCards_Input params4 = new DiscardCards_Input(0, r1); //already discarded
//		assertFalse(mf.canDiscardCards(params4));
//	}

}
