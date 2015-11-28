package server.cheats.helpers;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.models.ClientModel;
import shared.models.Player;

public class GiveCheatHelper implements CheatHelper
{
	private int number = -1;
	private String requestedObject;
	private Player player;
	private ClientModel model;
	
	public GiveCheatHelper(String[] params, Player player, ClientModel model)
	{
		this.number = Integer.parseInt(params[2]);
		this.requestedObject = params[1].toLowerCase();
		this.player = player;
		this.model = model;
	}

	@Override
	public void execute() 
	{
		// make sure there are the appropriate number of cards remaining
		if(verifyRequest())
		{
			for(int i = 0; i < this.number; i++)
				giveToPlayer();
		}
	}
	
	private void giveToPlayer()
	{
		switch(this.requestedObject)
		{
			case "wood":
				player.addCard(ResourceType.WOOD);
				break;
			case "brick":
				player.addCard(ResourceType.BRICK);
				break;
			case "ore":
				player.addCard(ResourceType.ORE);
				break;
			case "wheat":
				player.addCard(ResourceType.WHEAT);
				break;
			case "sheep":
				player.addCard(ResourceType.SHEEP);
				break;
			case "vp":
				player.addVictoryPoint();
				break;
			case "knight":
				player.getNewDevCards().addDevCard(DevCardType.SOLDIER);
				break;
			case "monument":
				player.getNewDevCards().addDevCard(DevCardType.MONUMENT);
				break;
			case "monopoly":
				player.getNewDevCards().addDevCard(DevCardType.MONOPOLY);
				break;
			case "road_build":
				player.getNewDevCards().addDevCard(DevCardType.ROAD_BUILD);
				break;
			case "yop":
				player.getNewDevCards().addDevCard(DevCardType.YEAR_OF_PLENTY);
				break;
		}
	}
	
	private boolean verifyRequest()
	{
		int count = 0;
		switch (this.requestedObject)
		{
			case "wood":
				count = this.model.getBank().getWood();
				break;
			case "brick":
				count = this.model.getBank().getBrick();
				break;
			case "ore":
				count = this.model.getBank().getOre();
				break;
			case "wheat":
				count = this.model.getBank().getWheat();
				break;
			case "sheep":
				count = this.model.getBank().getSheep();
				break;
			case "vp":
				return true;
			case "knight":
				count = this.model.getDeck().getSoldier();
				break;
			case "monument":
				count = this.model.getDeck().getMonument();
				break;
			case "monopoly":
				count = this.model.getDeck().getMonopoly();
				break;
			case "road_build":
				count = this.model.getDeck().getRoadBuilding();
				break;
			case "yop":
				count = this.model.getDeck().getYearOfPlenty();
				break;
		}
		if(this.number > count)
			return false;
		return true;
	}
}
