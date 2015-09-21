package shared.models;

public class ClientModel {
	
	private ResourceList bank;
	private MessageList chat;
	private MessageList log;
	private Map map;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private int version;
	private int winner;
	
	/**
	 * parses json to update member variables
	 * 
	 * @param json -> json with the new client model information
	 */
	public void updateClient(String json)
	{

	}
	
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
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
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
