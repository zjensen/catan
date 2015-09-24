package server.facade.moves;

import shared.communication.moves.*;
import shared.models.ClientModel;

public class MovesFacade {
	
	private ClientModel clientModel;
	
	/**
	 * Constructs a moves facade
	 * @param clientModel
	 */
	public MovesFacade(ClientModel clientModel) {
		super();
		this.clientModel = clientModel;
	}
	
	/**
	 * 
	 * @param playerIndex
	 * @return true if it is this players turn, else false
	 */
	public boolean isPlayersTurn(int playerIndex)
	{
		return (playerIndex == clientModel.getTurnTracker().getCurrentTurn());
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canSendChat(SendChat_Input params)
	{
		if(params.getContent() != null && !params.getContent().isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 * sends a chat message
	 * 
	 * @param params
	 * @return SendChat_Output
	 */
	public SendChat_Output sendChat(SendChat_Input params)
	{
		if(canSendChat(params))
		{
			
		}
		return null;
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRollNumber(RollNumber_Input params)
	{
		if(isPlayersTurn(params.getPlayerIndex()))
		{
			if(params.getNumber() <= 12 && params.getNumber() >= 2)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Used to roll a number at the beginning of your turn
	 * 
	 * @param params
	 * @return SendChat_Output
	 */
	public RollNumber_Output rollNumber(RollNumber_Input params)
	{
		if(canRollNumber(params))
		{
			
		}
		return null;
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRobPlayer(RobPlayer_Input params) //todo
	{
		//if player is not victim, and it is the players turn
		if(params.getPlayerIndex()!=params.getVictimIndex() && isPlayersTurn(params.getPlayerIndex()))
		{
			return clientModel.canRobPlayer(params);
		}
		return false;
	}
	
	/**
	 * Moves the Robber, selecting the new robber position and the player to rob
	 * 
	 * @param params
	 * @return RobPlayer_Output
	 */
	public RobPlayer_Output robPlayer(RobPlayer_Input params)
	{
		if(canRobPlayer(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		return isPlayersTurn(params.getPlayerIndex());
	}
	
	/**
	 * Used to finish your turn
	 * 
	 * @param params
	 * @return FinishTurn_Output
	 */
	public FinishTurn_Output finishTurn(FinishTurn_Input params)
	{
		if(canFinishTurn(params))
		{
			
		}
		return null;
	}

	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canBuyDevCard(BuyDevCard_Input params)
	{
		return (clientModel.canBuyDevCard(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Used to buy a development card
	 * 
	 * @param params
	 * @return BuyDevCard_Output
	 */
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input params)
	{
		if(canBuyDevCard(params))
		{
			
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can yearOfPlenty with these params, else false
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		return( clientModel.canYearOfPlenty(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	/**
	 * Plays a "Year of Plenty" card from your hand to gain 
	 * the two specified resources
	 * 
	 * @param params
	 * @return YearOfPlenty_Output
	 */
	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input params)
	{
		if(canYearOfPlenty(params))
		{
			
		}
		return null;
	}

	/**
	 * 
	 * @param params
	 * @return true if we can roadBuilding with these params, else false
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) //todo
	{
		return false;
	}
	
	/**
	 * Plays a "Road Building" card from your hand to build 
	 * two roads at the specified locations
	 * 
	 * @param params
	 * @return RoadBuilding_Output
	 */
	public RoadBuilding_Output roadBuilding(RoadBuilding_Input params)
	{
		if(canRoadBuilding(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can soldier with these params, else false
	 */
	public boolean canSoldier(Soldier_Input params) //todo
	{
		return( clientModel.canSoldier(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	
	/**
	 * Plays a 'Soldier' from your hand, selecting the 
	 * new robber position and player to rob.
	 * 
	 * @param params
	 * @return Soldier_Output
	 */
	public Soldier_Output soldier(Soldier_Input params)
	{
		if(canSoldier(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monopoly with these params, else false
	 */
	public boolean canMonopoly(Monopoly_Input params)
	{
		return( clientModel.canMonopoly(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	/**
	 * Plays a 'Monopoly' card from your hand to monopolize the specified resource
	 * 
	 * @param params
	 * @return Monopoly_Output
	 */
	public Monopoly_Output monopoly(Monopoly_Input params)
	{
		if(canMonopoly(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monument with these params, else false
	 */
	public boolean canMonument(Monument_Input params)
	{
		return( clientModel.canMonument(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Plays a 'Monument' card from your hand to give you a victory point
	 * 
	 * @param params
	 * @return Monument_Output
	 */
	public Monument_Output monument(Monument_Input params)
	{
		if(canMonument(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildRoad with these params, else false
	 */
	public boolean canBuildRoad(BuildRoad_Input params) //todo
	{
		return (clientModel.canBuildRoad(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Builds a road at the specified location. 
	 * (Set 'free' to true during initial setup.)
	 * 
	 * @param params
	 * @return BuildRoad_Output
	 */
	public BuildRoad_Output buildRoad(BuildRoad_Input params)
	{
		if(canBuildRoad(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildSettlement with these params, else false
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params) //todo
	{
		return (clientModel.canBuildSettlement(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Builds a settlement at the specified location. 
	 * (Set 'free' to true during initial setup.)
	 * 
	 * @param params
	 * @return BuildSettlement_Output
	 */
	public BuildSettlement_Output buildSettlement(BuildSettlement_Input params)
	{
		if(canBuildSettlement(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildCity with these params, else false
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		return (clientModel.canBuildCity(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * Builds a city at the specified location.
	 * 
	 * @param params
	 * @return BuildCity_Output
	 */
	public BuildCity_Output buildCity(BuildCity_Input params)
	{
		if(canBuildCity(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can offerTrade with these params, else false
	 */
	public boolean canOfferTrade(OfferTrade_Input params) //todo
	{
		return false;
	}
	
	/**
	 * Offers a domestic trade to another player
	 * 
	 * @param params
	 * @return OfferTrade_Output
	 */
	public OfferTrade_Output offerTrade(OfferTrade_Input params) 
	{
		if(canOfferTrade(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can acceptTrade with these params, else false
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params) //todo
	{
		return false;
	}
	
	/**
	 * Used to accept or reject a trade offered to you
	 * 
	 * @param params
	 * @return AcceptTrade_Output
	 */
	public AcceptTrade_Output acceptTrade(AcceptTrade_Input params)
	{
		if(canAcceptTrade(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can maritimeTrade with these params, else false
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params) //todo
	{
		return false;
	}
	
	/**
	 * Used to execute a maritime trade
	 * 
	 * @param params
	 * @return MaritimeTrade_Output
	 */
	public MaritimeTrade_Output maritimeTrade(MaritimeTrade_Input params)
	{
		if(canMaritimeTrade(params))
		{
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can discardCards with these params, else false
	 */
	public boolean canDiscardCards(DiscardCards_Input params)
	{
		return (clientModel.canDiscardCards(params));
	}

	
	/**
	 * Discards the specified resource cards.
	 * 
	 * @param params
	 * @return DiscardCards_Output
	 */
	public DiscardCards_Output discardCards(DiscardCards_Input params)
	{
		if(canDiscardCards(params))
		{
			
		}
		return null;
	}
	
}
