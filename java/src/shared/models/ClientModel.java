package shared.models;

public class ClientModel {
	
	public ResourceList bank;
	public MessageList chat;
	public MessageList log;
	public Map map;
	public Array<Player> players;
	public TradeOffer tradeOffer;
	public TurnTracker turnTracker;
	public Integer version;
	public Integer winner;
	
	public ResourceList getBank() {
		return bank;
	}
	public void setBank(ResourceList bank) {
		this.bank = bank;
	}
	public MessageList getChat() {
		return chat;
	}
	public void setChat(MessageList chat) {
		this.chat = chat;
	}
	public MessageList getLog() {
		return log;
	}
	public void setLog(MessageList log) {
		this.log = log;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Array<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Array<Player> players) {
		this.players = players;
	}
	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}
	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}
	public TurnTracker getTurnTracker() {
		return turnTracker;
	}
	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		this.winner = winner;
	}

}
