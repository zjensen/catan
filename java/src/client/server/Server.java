package client.server;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.MessageLine;
import shared.models.Player;
import shared.models.TradeOffer;

public class Server implements IServer {

	@Override
	public String buildRoad(Player player, EdgeLocation edgeLocation) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String buildSettlement(Player player, VertexLocation vertexLocation) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String upgradeSettlement(Player player, VertexLocation vertexLocation) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String sendTradeOffer(TradeOffer tradeOffer) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String acceptTradeOffer(TradeOffer tradeOffer) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String purchaseDevelopmentCard(Player player) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String playDevelopmentCard(Player player, int cardType) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String produce(int rollValue) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String moveRobber(HexLocation hexLocation) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String sendMessage(MessageLine messageLine) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String checkClientVersion(int version) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
