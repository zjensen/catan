package shared.models;

public class TradeOffer {

	private int sender; 
	private int receiver;
	private ResourceList offer;
	
	public TradeOffer() {
		this.sender = 0;
		this.receiver = 0;
		this.offer = new ResourceList();	
	}

	/**
	 * Tells them the total amount resources if they accepted the trade
	 * @return a ResourceList 
	 */
	public ResourceList benefitsOfTrade() { // what the resources would be if they accepted
		return new ResourceList();
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
		
}
