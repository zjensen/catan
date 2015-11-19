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
				"client.interpreter.InterpreterTest",		// interpreter tests
				"client.facade.CanDoMasterTest",
				"client.server.FakeServerTests",
				"server.facade.MovesFacade_Test", 
				"server.command.CreateCommand_Test",		// command object tests
				"server.command.JoinCommand_Test",
				"server.command.ListCommand_Test",
				"server.command.LoginCommand_Test",
				"server.command.ModelCommand_Test",
				"server.command.RegisterCommand_Test",
				"server.command.moves.AcceptTradeCommand_Test",
				"server.command.moves.BuildCityCommand_Test",
				"server.command.moves.BuildRoadCommand_Test",
				"server.command.moves.BuildSettlement_Test",
				"server.command.moves.BuyDevCardCommand_Test",
				"server.command.moves.DiscardCardsCommand_Test",
				"server.command.moves.FinishTurnCommand_Test",
				"server.command.moves.MaritimeTradeCommand_Test",
				"server.command.moves.MonopolyCommand_Test",
				"server.command.moves.MonumentCommand_Test",
				"server.command.moves.OfferTradeCommand_Test",
				"server.command.moves.RoadBuildingCommand_Test",
				"server.command.moves.RobPlayerCommand_Test",
				"server.command.moves.RollNumberCommand_Test",
				"server.command.moves.SendChatCommand_Test",
				"server.command.moves.SoldierCommand_Test",
				"server.command.moves.YearOfPlentyCommand_Test"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}
