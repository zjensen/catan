package shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import shared.definitions.ResourceType;

public class ResourceCards implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3734632712127421754L;
	private int sheep;
	private int wheat;
	private int wood;
	private int brick;
	private int ore;
	
	public ResourceCards() {
		this.sheep = 0;
		this.wheat = 0;
		this.wood = 0;
		this.brick = 0;
		this.ore = 0;
	}
	
	public ResourceCards(int sheep, int wheat, int wood, int brick, int ore) 
	{
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
		this.brick = brick;
		this.ore = ore;
	}
	
	/**
	 * 
	 * @param cardTotals
	 * 
	 * This function will be called to update the ResourceCardList object.
	 * The cardTotals is an array containing a number for each of the card types held in the ResourceCardList
	 * With each number held in cardTotals, it will correspond to a specific card type in order:
	 * sheep
	 * wheat
	 * wood
	 * brick
	 * ore
	 * 
	 */
	public void updateResources(int[] cardTotals) {
		
	}
	
	public boolean hasResources(ResourceCards resources)
	{
		boolean hasCards = true;
		if(this.sheep < resources.getSheep())
		{
			hasCards = false;
		}
		else if(this.wheat < resources.getWheat())
		{
			hasCards = false;
		}
		else if(this.wood < resources.getWood())
		{
			hasCards = false;
		}
		else if(this.brick < resources.getBrick())
		{
			hasCards = false;
		}
		else if(this.ore < resources.getOre())
		{
			hasCards = false;
		}
		return hasCards;
	}
	
	/**
	 * used to determine if the owner of this recourseCards can offer the specified cards
	 * offered cards are negative
	 * @param offer
	 * @return
	 */
	public boolean canOfferCards(ResourceCards offer)
	{
		boolean hasCards = true;
		if((offer.sheep < 0) && (this.sheep < Math.abs(offer.sheep)))
		{
			hasCards = false;
		}
		else if((offer.wheat < 0) && (this.wheat < Math.abs(offer.wheat)))
		{
			hasCards = false;
		}
		else if((offer.wood < 0) && (this.wood < Math.abs(offer.wood)))
		{
			hasCards = false;
		}
		else if((offer.brick < 0) && (this.brick < Math.abs(offer.brick)))
		{
			hasCards = false;
		}
		else if((offer.ore < 0) && (this.ore < Math.abs(offer.ore)))
		{
			hasCards = false;
		}
		return hasCards;
	}

	public int getResourceValue(ResourceType resource)
	{
		switch (resource.toString()) {
		case "WOOD":
			return wood;
		case "BRICK":
			return brick;
		case "SHEEP":
			return sheep;
		case "WHEAT":
			return wheat;
		case "ORE":
			return ore;
		}
		return -1;
	}
	
	public void addOne(ResourceType resource)
	{
		switch (resource.toString()) {
		case "WOOD":
			this.wood++;
			return;
		case "BRICK":
			this.brick++;
			return;
		case "SHEEP":
			this.sheep++;
			return;
		case "WHEAT":
			this.wheat++;
			return;
		case "ORE":
			this.ore++;
			return;
		}
	}
	
	public void addTwo(ResourceType resource)
	{
		switch (resource.toString()) {
		case "WOOD":
			this.wood+=2;
			return;
		case "BRICK":
			this.brick+=2;
			return;
		case "SHEEP":
			this.sheep+=2;
			return;
		case "WHEAT":
			this.wheat+=2;
			return;
		case "ORE":
			this.ore+=2;
			return;
		}
	}
	
	public void subtractOne(ResourceType resource)
	{
		switch (resource.toString()) {
		case "WOOD":
			this.wood--;
			return;
		case "BRICK":
			this.brick--;
			return;
		case "SHEEP":
			this.sheep--;
			return;
		case "WHEAT":
			this.wheat--;
			return;
		case "ORE":
			this.ore--;
			return;
		}
	}
	
	public void subtractTwo(ResourceType resource)
	{
		switch (resource.toString()) {
		case "WOOD":
			this.wood-=2;
			return;
		case "BRICK":
			this.brick-=2;
			return;
		case "SHEEP":
			this.sheep-=2;
			return;
		case "WHEAT":
			this.wheat-=2;
			return;
		case "ORE":
			this.ore-=2;
			return;
		}
	}
	
	public Boolean sendCheck()
	{
		Boolean canSend = false;
		
		if( (this.getWood() < 0 || 
			 this.getBrick() < 0 ||
			 this.getSheep() < 0 ||
			 this.getWheat() < 0 ||
			 this.getOre() < 0) ) 
		{
			canSend = true;
		}
		return canSend;
	}
	
	public Boolean receiveCheck()
	{
		Boolean canSend = false;
		if( (this.getWood() > 0 || 
			 this.getBrick() > 0 ||
			 this.getSheep() > 0 ||
			 this.getWheat() > 0 ||
			 this.getOre() > 0) )
		{
			canSend = true;
		}
		return canSend;
	}

	public void resetOneResourceValue(ResourceType resource)
	{
		switch (resource.toString()) {
		case "WOOD":
			this.wood = 0;
			return;
		case "BRICK":
			this.brick = 0;
			return;
		case "SHEEP":
			this.sheep = 0;
			return;
		case "WHEAT":
			this.wheat = 0;
			return;
		case "ORE":
			this.ore = 0;
			return;
		}
	}
	
	public void resetAllResourceValues()
	{
		this.wood = 0;
		this.brick = 0;
		this.sheep = 0;
		this.wheat = 0;
		this.ore = 0;
	}
	
	@Override
	public String toString() {
		return "ResourceCards [" 
				+ "\n\twood  = " + wood 
				+ "\n\tbrick = " + brick 
				+ "\n\tsheep = " + sheep 
				+ "\n\twheat = " + wheat 
				+ "\n\tore   = " + ore + "   ]";
	}

	
	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getTotal()
	{
		return brick + ore + wheat + wood + sheep;
	}
	
	public String toJsonString()
	{
		return "{\"brick\":"+brick+",\"ore\":"+ore+",\"sheep\":"+sheep+",\"wheat\":"+wheat+",\"wood\":"+wood+"}";
	}
	
	public void swapSigns()
	{
		this.sheep = -this.sheep;
		this.wheat = -this.wheat;
		this.wood = -this.wood;
		this.brick = -this.brick;
		this.ore = -this.ore;
	}
	
	public void changeSheep(int num) {
		sheep+=num;
	}

	public void changeWheat(int num) {
		wheat+=num;
	}

	public void changeWood(int num) {
		wood+=num;
	}

	public void changeBrick(int num) {
		brick+=num;
	}

	public void changeOre(int num) {
		ore+=num;
	}
	
	public boolean canBuildCity()
	{
		return false;
	}
	
	public boolean canChangeResource(ResourceType r, int num)
	{
		switch (r) 
		{
			case WOOD:
				return wood >= num;
			case BRICK:
				return brick >= num;
			case SHEEP:
				return sheep >= num;
			case WHEAT:
				return wheat >= num;
			case ORE:
				return ore >= num;
			default:
				return false;
		}
	}
	
	public void changeResource(ResourceType r, int num)
	{
		switch (r) 
		{
			case WOOD:
				wood += num;
				break;
			case BRICK:
				brick += num;
				break;
			case SHEEP:
				sheep += num;
				break;
			case WHEAT:
				wheat += num;
				break;
			case ORE:
				ore += num;
				break;
			default:
				break;
		}
	}
	
	public ResourceType getRandomCard()
	{
		ArrayList<ResourceType> cards = new ArrayList<ResourceType>();
		for(int i=0;i<wood;i++)
		{
			cards.add(ResourceType.WOOD);
		}
		for(int i=0;i<brick;i++)
		{
			cards.add(ResourceType.BRICK);
		}
		for(int i=0;i<sheep;i++)
		{
			cards.add(ResourceType.SHEEP);
		}
		for(int i=0;i<wheat;i++)
		{
			cards.add(ResourceType.WHEAT);
		}
		for(int i=0;i<ore;i++)
		{
			cards.add(ResourceType.ORE);
		}
		if(cards.isEmpty())
		{
			return null;
		}
		Random rand = new Random();
	    int randomNum = rand.nextInt(cards.size());
	    switch(cards.get(randomNum))
	    {
	    	case WOOD:
	    		wood--;
	    		break;
	    	case BRICK:
	    		brick--;
	    		break;
	    	case WHEAT:
	    		wheat--;
	    		break;
	    	case SHEEP:
	    		sheep--;
	    		break;
	    	case ORE:
	    		ore--;
	    		break;
	    }
	    return cards.get(randomNum);
	}
	
	public void buyDevCard()
	{
		this.sheep--;
		this.wheat--;
		this.ore--;
	}

	/////////////////////////////////ONLY FOR USE IN CLIENT MODEL BANK/////////////////////////////////////////
	
	public void cityBuilt()
	{
		this.wheat += 2;
		this.ore += 3;
	}

	public void roadBuilt()
	{
		this.wood++;
		this.brick++;
	}

	public void settlementBuilt()
	{
		this.wood++;
		this.brick++;
		this.wheat++;
		this.sheep++;
	}

	public void addCards(ResourceCards cards)
	{
		this.sheep += cards.getSheep();
		this.wheat += cards.getWheat();
		this.wood += cards.getWood();
		this.brick += cards.getBrick();
		this.ore += cards.getOre();
	}
	
	public void devCardBought()
	{
		this.sheep++;
		this.wheat++;
		this.ore++;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
