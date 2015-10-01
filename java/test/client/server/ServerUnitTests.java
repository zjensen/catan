package client.server;

import org.junit.*;

import static org.junit.Assert.*;

public class ServerUnitTests 
{
	
	@Before
	public void setup() 
	{
		
	}
	
	@After
	public void teardown() 
	{
		
	}
	
	@Test
	public void test_1() 
	{		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{

		String[] testClasses = new String[] 
		{
				"client.server.ServerUnitTests",
				"client.server.user.LoginUnitTests",		// user tests
				"client.server.user.RegisterUnitTests",
				"client.server.games.ListAllUnitTests",		// games tests
				"client.server.games.CreateGameUnitTests",
				"client.server.games.JoinGameUnitTests",
				"client.server.game.GameResetUnitTests",    // game tests
				"client.server.game.GetModelUnitTests",
				"client.server.game.ListAIUnitTests",
				"client.server.game.AddAIUnitTests",
				"client.server.moves.SendChatUnitTests",	// moves tests
				"client.server.moves.RollNumberUnitTests",
				"client.server.moves.RobPlayerUnitTests",
				"client.server.moves.FinishTurnUnitTests",
				"client.server.moves.BuyDevCardUnitTests",
				"client.server.moves.YearOfPlentyUnitTests",
				"client.server.moves.RoadBuildingUnitTests",
				"client.server.moves.SoldierUnitTests",
				"client.server.moves.MonopolyUnitTests",
				"client.server.moves.MonumentUnitTests",
				"client.server.moves.BuildRoadUnitTests",
				"client.server.moves.BuildSettlementUnitTests",
				"client.server.moves.BuildCityUnitTests",
				"client.server.moves.OfferTradeUnitTests",
				"client.server.moves.AcceptTradeUnitTests",
				"client.server.moves.MaritimeTradeUnitTests",
				"client.server.moves.DiscardCardsUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}