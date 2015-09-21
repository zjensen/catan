package client.server;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.models.MessageLine;
import shared.models.Player;
import shared.models.TradeOffer;

public class FakeServer implements IServer {

	@Override
	public void buildRoad(Player player, EdgeLocation edgeLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildSettlement(Player player, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgradeSettlement(Player player, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendTradeOffer(TradeOffer tradeOffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptTradeOffer(TradeOffer tradeOffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void purchaseDevelopmentCard(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playDevelopmentCard(Player player, int cardType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void produce(int rollValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRobber(HexLocation hexLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(MessageLine messageLine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String checkClientVersion(int version) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
