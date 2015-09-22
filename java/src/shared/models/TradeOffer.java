package shared.models;

public class TradeOffer {

	private int sender; 
	private int receiver;
	private ResourceCards offer;
	
	public TradeOffer() {
		this.sender = 0;
		this.receiver = 0;
		this.offer = new ResourceCards();	
	}

	/**
	 * Tells them the total amount resources if they accepted the trade
	 * @return a ResourceList 
	 */
	public ResourceCards benefitsOfTrade() { // what the resources would be if they accepted
		return new ResourceCards();
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

	public ResourceCards getOffer() {
		return offer;
	}

	public void setOffer(ResourceCards offer) {
		this.offer = offer;
	}
		
}
