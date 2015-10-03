package client.server;

import static org.junit.Assert.*;

import org.junit.*;

import shared.communication.game.*;
import shared.communication.games.*;
import shared.communication.moves.*;
import shared.communication.user.*;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.ResourceCards;

public class FakeServerTests {
	public String gameModel = "{\"deck\": {\"yearOfPlenty\": 2,\"monopoly\": 2,\"soldier\": 14,\"roadBuilding\": 2,\"monument\": 5},"
			+ "\"map\": {\"hexes\": [{\"location\": {\"x\": 0,\"y\": -2}},{\"resource\": \"brick\",\"location\": {\"x\": 1,\"y\": -2},"
			+ "\"number\": 4},{\"resource\": \"wood\",\"location\": {\"x\": 2,\"y\": -2},\"number\": 11},"
			+ "{\"resource\": \"brick\",\"location\": {\"x\": -1,\"y\": -1},\"number\": 8},"
			+ "{\"resource\": \"wood\",\"location\": {\"x\": 0,\"y\": -1},\"number\": 3},"
			+ "{\"resource\": \"ore\",\"location\": {\"x\": 1,\"y\": -1},\"number\": 9},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": 2,\"y\": -1},\"number\": 12},"
			+ "{\"resource\": \"ore\",\"location\": {\"x\": -2,\"y\": 0},\"number\": 5},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": -1,\"y\": 0},\"number\": 10},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": 0,\"y\": 0},\"number\": 11},"
			+ "{\"resource\": \"brick\",\"location\": {\"x\": 1,\"y\": 0},\"number\": 5},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": 2,\"y\": 0},\"number\": 6},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": -2,\"y\": 1},\"number\": 2},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": -1,\"y\": 1},\"number\": 9},"
			+ "{\"resource\": \"wood\",\"location\": {\"x\": 0,\"y\": 1},\"number\": 4},"
			+ "{\"resource\": \"sheep\",\"location\": {\"x\": 1,\"y\": 1},\"number\": 10},"
			+ "{\"resource\": \"wood\",\"location\": {\"x\": -2,\"y\": 2},\"number\": 6},"
			+ "{\"resource\": \"ore\",\"location\": {\"x\": -1,\"y\": 2},\"number\": 3},"
			+ "{\"resource\": \"wheat\",\"location\": {\"x\": 0,\"y\": 2},\"number\": 8}],"
			+ "\"roads\": [],\"cities\": [],\"settlements\": [],\"radius\": 3,\"ports\": [{\"ratio\": 3,"
			+ "\"direction\": \"N\",\"location\": {\"x\": 0,\"y\": 3}},{\"ratio\": 3,\"direction\": \"NW\",\"location\": {\"x\": 2,\"y\": 1}},"
			+ "{\"ratio\": 2,\"resource\": \"sheep\",\"direction\": \"NW\",\"location\": {\"x\": 3,\"y\": -1}},"
			+ "{\"ratio\": 3,\"direction\": \"SW\",\"location\": {\"x\": 3,\"y\": -3}},"
			+ "{\"ratio\": 2,\"resource\": \"wheat\",\"direction\": \"S\",\"location\": {\"x\": -1,\"y\": -2}},"
			+ "{\"ratio\": 2,\"resource\": \"wood\",\"direction\": \"NE\",\"location\": {\"x\": -3,\"y\": 2}},"
			+ "{\"ratio\": 2,\"resource\": \"brick\",\"direction\": \"NE\",\"location\": {\"x\": -2,\"y\": 3}},"
			+ "{\"ratio\": 3,\"direction\": \"SE\",\"location\": {\"x\": -3,\"y\": 0}},"
			+ "{\"ratio\": 2,\"resource\": \"ore\",\"direction\": \"S\",\"location\": {\"x\": 1,\"y\": -3}}],"
			+ "\"robber\": {\"x\": 0,\"y\": -2}},\"players\": [{\"resources\": {\"brick\": 0,\"wood\": 0,\"sheep\": 0,\"wheat\": 0,\"ore\": 0},"
			+ "\"oldDevCards\": {\"yearOfPlenty\": 0,\"monopoly\": 0,\"soldier\": 0,\"roadBuilding\": 0,\"monument\": 0},"
			+ "\"newDevCards\": {\"yearOfPlenty\": 0,\"monopoly\": 0,\"soldier\": 0,\"roadBuilding\": 0,\"monument\": 0},"
			+ "\"roads\": 15,\"cities\": 4,\"settlements\": 5,\"soldiers\": 0,\"victoryPoints\": 0,\"monuments\": 0,\"playedDevCard\": false,"
			+ "\"discarded\": false,\"playerID\": 12,\"playerIndex\": 0,\"name\": \"Test\",\"color\": \"orange\"},null,null,null],"
			+ "\"log\": {\"lines\": []},\"chat\": {\"lines\": []},\"bank\": {\"brick\": 24,\"wood\": 24,\"sheep\": 24,\"wheat\": 24,\"ore\": 24},"
			+ "\"turnTracker\": {\"status\": \"FirstRound\",\"currentTurn\": 0,\"longestRoad\": -1,\"largestArmy\": -1},"
			+ "\"winner\": -1,\"version\": 0}";

	public IServer server;
	
	@Before
	public void init(){
		server = new FakeServer();
	}
	
	@Test
	public void testLogin() {

		Login_Input input = new Login_Input("jack", "jack");
		
		assertEquals("Failed to login - bad username or password.", server.login(input).getResponse());
		
		input.setUsername("valid");
		
		assertEquals("Success", server.login(input).getResponse());
		
	}
	
	@Test
	public void testListgames(){
		ListGames_Input listGame = new ListGames_Input();
		
		assertEquals("[{\"title\": \"Default Game\",\"id\": 0,\"players\": "
				+ "[{\"color\": \"orange\",\"name\": \"Sam\",\"id\": 0},"
				+ "{\"color\": \"blue\",\"name\": \"Brooke\",\"id\": 1},"
				+ "{\"color\": \"red\",\"name\": \"Pete\",\"id\": 10},"
				+ "{\"color\": \"green\",\"name\": \"Mark\",\"id\": 11}]}]", server.listGames(listGame).getResponse());
	}

	@Test
	public void testCreateGame(){
		CreateGame_Input create = new CreateGame_Input();
		
		assertEquals("{\"title\": \"New Game\",\"id\": 1,\"players\": [{},{},{},{}]}", server.createGame(create).getResponse());
	}

	@Test
	public void testJoinGame(){
		JoinGame_Input join = new JoinGame_Input();

		join.setId(1);
		assertEquals("Success", server.joinGame(join).getResponse());
		
		join.setColor("bad_color_value");
		join.setId(0);
		assertEquals("Invalid request: color has bad value", server.joinGame(join).getResponse());
		
		join.setId(3);
		assertEquals("The player could not be added to the specified game.", server.joinGame(join).getResponse());
	
		join.setColor("blue");
		join.setId(0);
		assertEquals("The catan.user HTTP cookie is missing.  You must login before calling this method.", server.joinGame(join).getResponse());	
	}

	@Test
	public void testSaveGame(){
		// not implemented for this Phase
		assertTrue(true);
	}

	@Test
	public void testLoadGame(){
		// not implemented for this Phase
		assertTrue(true);
	}

	@Test
	public void testGetModel(){
		GameModel_Input input = new GameModel_Input();
		input.setVersion(0);
		
		assertEquals("true", server.getModel(input).getResponse());
		
		
		input.setVersion(10);
		
		assertEquals(gameModel, server.getModel(input).getResponse());
	}

	@Test
	public void testResetGame(){
		ResetGame_Input input = new ResetGame_Input();
		input.setCatan_user(false);
		
		assertEquals("The catan.user HTTP cookie is missing.  You must login before calling this method.", server.resetGame(input).getResponse());
	
		input.setCatan_user(true);
		input.setCatan_game(false);
		
		assertEquals("The catan.game HTTP cookie is missing.  You must join a game before calling this method.", server.resetGame(input).getResponse());
	
		input.setCatan_game(true);
		assertEquals(gameModel, server.resetGame(input).getResponse());
		
	}
	
	@Test
	public void testPostCommands(){
		POSTCommands_Input input = new POSTCommands_Input();
		input.setCatan_user(false);
		
		assertEquals("The catan.user HTTP cookie is missing.  You must login before calling this method.", server.postCommands(input).getResponse());
	
		input.setCatan_user(true);
		input.setCatan_game(false);
		
		assertEquals("The catan.game HTTP cookie is missing.  You must join a game before calling this method.", server.postCommands(input).getResponse());
	
		input.setCatan_game(true);
		assertEquals(gameModel, server.postCommands(input).getResponse());
	}
	
	@Test
	public void testAddAI(){
		AddAI_Input input = new AddAI_Input();
		input.setCatan_user(false);
		
		assertEquals("The catan.user HTTP cookie is missing.  You must login before calling this method.", server.addAI(input).getResponse());
	
		input.setCatan_user(true);
		input.setCatan_game(false);
		
		assertEquals("The catan.game HTTP cookie is missing.  You must join a game before calling this method.", server.addAI(input).getResponse());
	
		input.setCatan_game(true);
		input.setAi_type("NULL");
		assertEquals("Could not add AI player  [" + input.getAi_type() + "]", server.addAI(input).getResponse());
		
		input.setAi_type("VALID_AI");
		assertEquals("Success", server.addAI(input).getResponse());
	}

	@Test
	public void testListAI(){
		ListAI_Input input = new ListAI_Input();
		assertEquals("[\"LARGEST_ARMY\"]", server.listAI(input).getResponse());
	}

	@Test
	public void testSendChat(){
		SendChat_Input input = new SendChat_Input(0, "test message");
		assertEquals(gameModel, server.sendChat(input).getResponse());
	}

	@Test
	public void testRollNumber(){
		RollNumber_Input input = new RollNumber_Input(0,12);
		assertEquals(gameModel, server.rollNumber(input).getResponse());
	}
	
	@Test
	public void testRobPlayer(){
		RobPlayer_Input input = new RobPlayer_Input(0,1, new HexLocation(2, 2));
		assertEquals(gameModel, server.robPlayer(input).getResponse());
	}
	
	@Test
	public void testFinishTurn(){
		FinishTurn_Input input = new FinishTurn_Input(0);
		assertEquals(gameModel, server.finishTurn(input).getResponse());
	}
	
	@Test
	public void testBuyDevCard(){
		BuyDevCard_Input input = new BuyDevCard_Input(0);
		assertEquals(gameModel, server.buyDevCard(input).getResponse());
	}
	
	@Test
	public void testYearOfPlenty(){
		YearOfPlenty_Input input = new YearOfPlenty_Input(0,ResourceType.BRICK,ResourceType.WOOD);
		assertEquals(gameModel, server.yearOfPlenty(input).getResponse());
	}
	
	@Test
	public void testRoadBuilding(){
		RoadBuilding_Input input = new RoadBuilding_Input(0,new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North), new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast));
		assertEquals(gameModel, server.roadBuilding(input).getResponse());
	}
	
	@Test
	public void testSoldier(){
		Soldier_Input input = new Soldier_Input(0,1, new HexLocation(2, 2));
		assertEquals(gameModel, server.soldier(input).getResponse());
	}
	
	@Test
	public void testMonopoly(){
		Monopoly_Input input = new Monopoly_Input(0, ResourceType.BRICK);
		assertEquals(gameModel, server.monopoly(input).getResponse());
	}
	
	@Test
	public void testMonument(){
		Monument_Input input = new Monument_Input(0);
		assertEquals(gameModel, server.monument(input).getResponse());
	}
	
	@Test
	public void testBuildRoad(){
		BuildRoad_Input input = new BuildRoad_Input(0,new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North), false);
		assertEquals(gameModel, server.buildRoad(input).getResponse());
	}
	
	@Test
	public void testBuildSettlement(){
		BuildSettlement_Input input = new BuildSettlement_Input(0, new VertexLocation(new HexLocation(0, 0), VertexDirection.East), false);
		assertEquals(gameModel, server.buildSettlement(input).getResponse());
	}
	
	@Test
	public void testBuildCity(){
		BuildCity_Input input = new BuildCity_Input(0, new VertexLocation(new HexLocation(0, 0), VertexDirection.East));
		assertEquals(gameModel, server.buildCity(input).getResponse());
	}
	
	@Test
	public void testOfferTrade(){
		OfferTrade_Input input = new OfferTrade_Input(0, new ResourceCards(), 1);
		assertEquals(gameModel, server.offerTrade(input).getResponse());
	}
	
	@Test
	public void testAcceptTrade(){
		AcceptTrade_Input input = new AcceptTrade_Input(1, true);
		assertEquals(gameModel, server.acceptTrade(input).getResponse());
	}
	
	@Test
	public void testMaritimeTrade(){
		MaritimeTrade_Input input = new MaritimeTrade_Input(1, 3, ResourceType.BRICK, ResourceType.WOOD);
		assertEquals(gameModel, server.maritimeTrade(input).getResponse());
	}
	
	@Test
	public void testDiscardCards(){
		DiscardCards_Input input = new DiscardCards_Input(0, new ResourceCards());
		assertEquals(gameModel, server.discardCards(input).getResponse());
	}
	
}
