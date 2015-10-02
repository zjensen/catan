package phase_one;

public class RunAllTests 
{
	public static void main(String[] args) 
	{
		String[] testClasses = new String[] 
		{
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
				"client.server.moves.DiscardCardsUnitTests",
				"shared.models.EdgeValueTest",				// model tests
				"shared.models.PlayerTest",
				"shared.models.TurnTrackerTest",
				"shared.models.UserTest",
				"shared.models.VertexObjectTest",
				"client.interpreter.InterpreterTest"		// interpreter tests
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}
