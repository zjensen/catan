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
		return null;
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRollNumber(RollNumber_Input params)
	{
		if(clientModel.getTurnTracker().getCurrentTurn() == params.getPlayerIndex())
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
//		moves/rollNumber
//    VALID INPUT
//        playerIndex: 1
//        number: 8
		
		return null;
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRobPlayer(RobPlayer_Input params)
	{
		//if player is not victim, and it is the players turn
		if(params.getPlayerIndex()!=params.getVictimIndex()&&params.getPlayerIndex()==clientModel.getTurnTracker().getCurrentTurn())
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
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		return (params.getPlayerIndex()==clientModel.getTurnTracker().getCurrentTurn());
	}
	
	/**
	 * Used to finish your turn
	 * 
	 * @param params
	 * @return FinishTurn_Output
	 */
	public FinishTurn_Output finishTurn(FinishTurn_Input params)
	{
		return null;
	}

	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canBuyDevCard(BuyDevCard_Input params)
	{
		return clientModel.canBuyDevCard(params);
	}
	
	/**
	 * Used to buy a development card
	 * 
	 * @param params
	 * @return BuyDevCard_Output
	 */
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input params)
	{
//		/moves/buyDevCard
//		VALID INPUT
//		playerIndex: 2
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can yearOfPlenty with these params, else false
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		return false;
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
		
//		/moves/Year_of_Plenty
//		VALID INPUT
//		playerIndex: 3
//		resource1 : Ore
//		resource2 : Sheep
//		***note that the resource names must be capitalized***
		return null;
	}

	/**
	 * 
	 * @param params
	 * @return true if we can roadBuilding with these params, else false
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params)
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
		
//		/moves/Road_Building
//		VALID INPUT
//		playerIndex: 0
//		spot1
//		x: 0
//		y: 0
//		direction: NE in double quotes
//		spot1
//		x: 1
//		y: -1
//		direction: S in double quotes
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can soldier with these params, else false
	 */
	public boolean canSoldier(Soldier_Input params)
	{
		return false;
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
		
		
//		/moves/Soldier
//		VALID INPUT
//		playerIndex: 1
//		victimindex: 2
//		location:
//			x: -2
//			y: 0
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monopoly with these params, else false
	 */
	public boolean canMonopoly(Monopoly_Input params)
	{
		return false;
	}

	/**
	 * Plays a 'Monopoly' card from your hand to monopolize the specified resource
	 * 
	 * @param params
	 * @return Monopoly_Output
	 */
	public Monopoly_Output monopoly(Monopoly_Input params)
	{
		
//		/moves/Monopoly
//		VALID INPUT
//		resource : Brick
//		playerIndex: 0
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monument with these params, else false
	 */
	public boolean canMonument(Monument_Input params)
	{
		return false;
	}
	
	/**
	 * Plays a 'Monument' card from your hand to give you a victory point
	 * 
	 * @param params
	 * @return Monument_Output
	 */
	public Monument_Output monument(Monument_Input params)
	{
		
//		/moves/Monument
//		VALID INPUT
//		playerIndex : 1
//		
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildRoad with these params, else false
	 */
	public boolean canBuildRoad(BuildRoad_Input params)
	{
		return clientModel.canBuildRoad(params);
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
		
//		/moves/buildRoad
//		VALID INPUT
//		playerIndex: 2
//		roadLocation
//		x: -1
//		y: -1
//		direction: "NE"
//			free: false
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildSettlement with these params, else false
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params)
	{
		return false;
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
		
//		/moves/buildSettlement
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildCity with these params, else false
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		return false;
	}
	
	/**
	 * Builds a city at the specified location.
	 * 
	 * @param params
	 * @return BuildCity_Output
	 */
	public BuildCity_Output buildCity(BuildCity_Input params)
	{
//		/moves/buildCity
//		VALID INPUT
//		playerIndex: 2
//		vertexLocation
//		x: -1
//		y: -1
//		direction: "NE"
//			free: false
			
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can offerTrade with these params, else false
	 */
	public boolean canOfferTrade(OfferTrade_Input params)
	{
		return false;
	}
	
	/**
	 * Offers a domestic trade to another player
	 * 
	 * @param params
	 * @return OfferTrade_Output
	 */
	public OfferTrade_Output offerTrade(OfferTrade_Input params) {
	
//		/moves/offerTrade
//		VALID INPUT
//		playerIndex: 3
//		offer:
//			brick: 1
//			ore: 0
//			sheep: 0
//			wheat: 0
//			wood: 2
//			receiver: 2
//			
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can acceptTrade with these params, else false
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params)
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
		
//		/moves/acceptTrade
//		VALID INPUT
//		playerIndex: 1
//		willAccept: true
//		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can maritimeTrade with these params, else false
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params)
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
		
		return null;
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can discardCards with these params, else false
	 */
	public boolean canDiscardCards(DiscardCards_Input params)
	{
		return false;
	}

	/**
	 * Discards the specified resource cards.
	 * 
	 * @param params
	 * @return DiscardCards_Output
	 */
	public DiscardCards_Output discardCards(DiscardCards_Input params)
	{
//		/moves/discardCards
//		VALID INPUT
//		playerIndex: 0
//		discardedCards:
//			brick: 0
//			ore: 1
//			sheep: 1
//			wheat: 3
//			wood: 0
		
		return null;
	}
	
}
