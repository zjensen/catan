package shared.models;

import java.util.ArrayList;
import java.util.Random;

import shared.definitions.DevCardType;

public class DevCards {
	
	private int soldier;
	private int monopoly;
	private int roadBuilding;
	private int yearOfPlenty;
	private int monument;
	
	public DevCards() {
		this.soldier = 0;
		this.monopoly = 0;
		this.roadBuilding = 0;
		this.yearOfPlenty = 0;
		this.monument = 0;
	}

	public DevCards(int soldier, int monopoly, int roadBuilding, int yearOfPlenty, int monument) {
		this.soldier = soldier;
		this.monopoly = monopoly;
		this.roadBuilding = roadBuilding;
		this.yearOfPlenty = yearOfPlenty;
		this.monument = monument;
	}
	/**
	 * 
	 * @param cardTotals
	 * 
	 * This function will be called to update the DevCardList object.
	 * The cardTotals is an array containing a number for each of the card types held in the DevCardList
	 * With each number held in cardTotals, it will correspond to a specific card type in order:
	 * soldier
	 * monopoly
	 * roadBuilding
	 * yearOfPlenty
	 * monument
	 * 
	 */
	public void updateCards(int[] cardTotals) {
		
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "DevCards [" 
				+ "\n\tsoldier      = " + soldier 
				+ "\n\tmonopoly     = " + monopoly 
				+ "\n\troadBuilding = " + roadBuilding
				+ "\n\tyearOfPlenty = " + yearOfPlenty 
				+ "\n\tmonument     = " + monument + "  ]";
	}

	public int getSoldier() {
		return soldier;
	}

	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}

	public int getMonopoly() {
		return monopoly;
	}

	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}

	public int getMonument() {
		return monument;
	}

	public void setMonument(int monument) {
		this.monument = monument;
	}
	
	public int cardCount() {
		return (this.monopoly + this.monument + this.roadBuilding + this.soldier + this.yearOfPlenty);
	}

	public String toJsonString()
	{
		return "{\"yearOfPlenty\":"+yearOfPlenty+",\"monopoly\":"+monopoly+",\"soldier\":"+soldier+",\"roadBuilding\":"+roadBuilding+",\"monument\":"+monument+"}";
	}

	public DevCardType getRandomCard()
	{
		ArrayList<DevCardType> cards = new ArrayList<DevCardType>();
		for(int i=0;i<soldier;i++)
		{
			cards.add(DevCardType.SOLDIER);
		}
		for(int i=0;i<monopoly;i++)
		{
			cards.add(DevCardType.MONOPOLY);
		}
		for(int i=0;i<roadBuilding;i++)
		{
			cards.add(DevCardType.ROAD_BUILD);
		}
		for(int i=0;i<yearOfPlenty;i++)
		{
			cards.add(DevCardType.YEAR_OF_PLENTY);
		}
		for(int i=0;i<monument;i++)
		{
			cards.add(DevCardType.MONUMENT);
		}
		if(cards.isEmpty())
		{
			return null;
		}
		Random rand = new Random();
	    int randomNum = rand.nextInt(cards.size());
	    switch(cards.get(randomNum))
	    {
	    	case SOLDIER:
	    		soldier--;
	    		break;
	    	case MONOPOLY:
	    		monopoly--;
	    		break;
	    	case ROAD_BUILD:
	    		roadBuilding--;
	    		break;
	    	case YEAR_OF_PLENTY:
	    		yearOfPlenty--;
	    		break;
	    	case MONUMENT:
	    		monument--;
	    		break;
	    }
	    return cards.get(randomNum);
	}

	public void addDevCard(DevCardType card)
	{
		switch(card)
	    {
	    	case SOLDIER:
	    		soldier++;
	    		break;
	    	case MONOPOLY:
	    		monopoly++;
	    		break;
	    	case ROAD_BUILD:
	    		roadBuilding++;
	    		break;
	    	case YEAR_OF_PLENTY:
	    		yearOfPlenty++;
	    		break;
	    	case MONUMENT:
	    		monument++;
	    		break;
	    }
	}
	
	public void subtractDevCard(DevCardType card)
	{
		switch(card)
	    {
	    	case SOLDIER:
	    		soldier--;
	    		break;
	    	case MONOPOLY:
	    		monopoly--;
	    		break;
	    	case ROAD_BUILD:
	    		roadBuilding--;
	    		break;
	    	case YEAR_OF_PLENTY:
	    		yearOfPlenty--;
	    		break;
	    	case MONUMENT:
	    		monument--;
	    		break;
	    }
	}

	public void addCards(DevCards newCards)
	{
		this.soldier+=newCards.getSoldier();
		this.monopoly+=newCards.getMonopoly();
		this.roadBuilding+=newCards.getRoadBuilding();
		this.yearOfPlenty+=newCards.getYearOfPlenty();
		this.monument+=newCards.getMonument();
	}
	
}
