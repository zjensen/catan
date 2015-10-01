package client.facade;

import shared.communication.moves.*;
import shared.models.ClientModel;

public class ClientFacade {
	
	private ClientModel clientModel;
	
	/**
	 * Constructs a moves facade
	 * @param clientModel
	 */
	public ClientFacade(ClientModel clientModel) {
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
		 return(params.getContent() != null && !params.getContent().isEmpty());
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
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		return isPlayersTurn(params.getPlayerIndex());
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
	 * 
	 * @param params
	 * @return true if we can yearOfPlenty with these params, else false
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		return( clientModel.canYearOfPlenty(params) && isPlayersTurn(params.getPlayerIndex()));
	}

	/**
	 * 
	 * @param params
	 * @return true if we can roadBuilding with these params, else false
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) //todo
	{
		return( clientModel.canRoadBuilding(params) && isPlayersTurn(params.getPlayerIndex()));
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
	 * 
	 * @param params
	 * @return true if we can monopoly with these params, else false
	 */
	public boolean canMonopoly(Monopoly_Input params)
	{
		return( clientModel.canMonopoly(params) && isPlayersTurn(params.getPlayerIndex()));
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
	 * 
	 * @param params
	 * @return true if we can buildRoad with these params, else false
	 */
	public boolean canBuildRoad(BuildRoad_Input params) //todo
	{
		return (clientModel.canBuildRoad(params) && isPlayersTurn(params.getPlayerIndex()));
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
	 * 
	 * @param params
	 * @return true if we can buildCity with these params, else false
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		return (clientModel.canBuildCity(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can offerTrade with these params, else false
	 */
	public boolean canOfferTrade(OfferTrade_Input params)
	{
		return(clientModel.canOfferTrade(params) && isPlayersTurn(params.getPlayerIndex()) && params.getPlayerIndex()!=params.getReceiver());
	}

	/**
	 * 
	 * @param params
	 * @return true if we can acceptTrade with these params, else false
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params) //todo
	{
		return( clientModel.canAcceptTrade(params));
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can maritimeTrade with these params, else false
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params) //todo
	{
		return( clientModel.canMaritimeTrade(params) && isPlayersTurn(params.getPlayerIndex()));
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

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}
	
	
}
