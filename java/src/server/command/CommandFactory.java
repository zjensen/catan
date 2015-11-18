package server.command;

import com.sun.net.httpserver.HttpExchange;

import server.command.game.Model_Command;
import server.command.games.*;
import server.command.moves.*;
import server.command.user.*;

public class CommandFactory implements ICommandFactory {

	/**
	 * Creates and returns an IAction based on the instructions found within the HttpExchange object
	 * @param exchange the HttpExchange object
	 * @return an IAction containing the appropriate behavior to be executed when appropriate
	 */
	@Override
	public ServerCommand create(HttpExchange exchange) {
		// parse out the HttpExchange object
		// create appropriate command object
		
		String uri = exchange.getRequestURI().toString();
		String[] arguments = uri.split("/");
		String request = arguments[arguments.length-1];
		
		return getCommand(request, new ExchangeWrapper(exchange));
		
	}
	
	public ServerCommand getCommand(String request, ExchangeWrapper exchange){
		if (request.startsWith("model"))
		{
			//System.out.println("stripping version off model request");
			request = "model";
		}
		
		switch (request) {
		case "login":
			return new Login_Command(exchange);
		case "register":
			return new Register_Command(exchange);
		case "list":
			return new List_Command(exchange);
		case "create":
			return new Create_Command(exchange);
		case "join":
			return new Join_Command(exchange);
		case "model":
			return new Model_Command(exchange);
		case "sendChat":
			return new SendChat_Command(exchange);
		case "robPlayer":
			return new RobPlayer_Command(exchange);
		case "rollNumber":
			return new RollNumber_Command(exchange);
		case "finishTurn":
			return new FinishTurn_Command(exchange);
		case "buyDevCard":
			return new BuyDevCard_Command(exchange);
		case "Year_of_Plenty":
			return new YearOfPlenty_Command(exchange);
		case "Road_Building":
			return new RoadBuilding_Command(exchange);
		case "Soldier":
			return new Soldier_Command(exchange);
		case "acceptTrade":
			return new AcceptTrade_Command(exchange);
		case "Monopoly":
			return new Monopoly_Command(exchange);
		case "Monument":
			return new Monument_Command(exchange);
		case "buildRoad":
			return new BuildRoad_Command(exchange);
		case "buildSettlement":
			return new BuildSettlement_Command(exchange);
		case "buildCity":
			return new BuildCity_Command(exchange);
		case "offerTrade":
			return new OfferTrade_Command(exchange);
		case "maritimeTrade":
			return new MaritimeTrade_Command(exchange);
		case "discardCards":
			return new DiscardCards_Command(exchange);
		default:
			break;
		}
		
		return null;
	}



}
