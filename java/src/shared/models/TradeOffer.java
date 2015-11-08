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

	public TradeOffer(int sender, int receiver, ResourceCards offer)
	{
		this.sender = sender;
		this.receiver = receiver;
		this.offer = offer;
	}

	@Override
	public String toString() {
		return "===Trade Offer==="
				+ "\nSender   = " + sender 
				+ "\nReceiver = " + receiver 
				+ "\nOFFER\n" 
				+ offer.toString()
				+ "\n=================";
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
