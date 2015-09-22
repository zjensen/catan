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
	 * sends a chat message
	 * 
	 * @param params
	 * @return SendChat_Output
	 */
	public SendChat_Output sendChat(SendChat_Input params){
		
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
	public RollNumber_Output rollNumber(RollNumber_Input params){
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
		return false;
	}
	
	/**
	 * Moves the Robber, selecting the new robber position and the player to rob
	 * 
	 * @param params
	 * @return RobPlayer_Output
	 */
	public RobPlayer_Output robPlayer(RobPlayer_Input params){
		
		return null;
	}
	
	/**
	 * Used to finish your turn
	 * 
	 * @param params
	 * @return FinishTurn_Output
	 */
	public FinishTurn_Output finishTurn(FinishTurn_Input params){
		
//		/moves/finishTurn
		
		
		return null;
		
	}

	
	/**
	 * Used to buy a development card
	 * 
	 * @param params
	 * @return BuyDevCard_Output
	 */
	public BuyDevCard_Output buyDevCard(BuyDevCard_Input params){
//		/moves/buyDevCard
//		VALID INPUT
//		playerIndex: 2
		
		return null;
	}

	/**
	 * Plays a "Year of Plenty" card from your hand to gain 
	 * the two specified resources
	 * 
	 * @param params
	 * @return YearOfPlenty_Output
	 */
	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input params){
		
//		/moves/Year_of_Plenty
//		VALID INPUT
//		playerIndex: 3
//		resource1 : Ore
//		resource2 : Sheep
//		***note that the resource names must be capitalized***
		return null;
	}

	/**
	 * Plays a "Year of Plenty" card from your hand to build 
	 * two roads at the specified locations
	 * 
	 * @param params
	 * @return RoadBuilding_Output
	 */
	public RoadBuilding_Output roadBuilding(RoadBuilding_Input params){
		
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
	 * Plays a 'Soldier' from your hand, selecting the 
	 * new robber position and player to rob.
	 * 
	 * @param params
	 * @return Soldier_Output
	 */
	public Soldier_Output soldier(Soldier_Input params){
		
		
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
	 * Plays a 'Monopoly' card from your hand to monopolize the specified resource
	 * 
	 * @param params
	 * @return Monopoly_Output
	 */
	public Monopoly_Output monopoly(Monopoly_Input params){
		
//		/moves/Monopoly
//		VALID INPUT
//		resource : Brick
//		playerIndex: 0
		
		return null;
	}
	
	/**
	 * Plays a 'Monument' card from your hand to give you a victory point
	 * 
	 * @param params
	 * @return Monument_Output
	 */
	public Monument_Output monument(Monument_Input params){
		
//		/moves/Monument
//		VALID INPUT
//		playerIndex : 1
//		
		
		return null;
	}
	
	/**
	 * Builds a road at the specified location. 
	 * (Set 'free' to true during initial setup.)
	 * 
	 * @param params
	 * @return BuildRoad_Output
	 */
	public BuildRoad_Output buildRoad(BuildRoad_Input params){
		
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
	 * Builds a settlement at the specified location. 
	 * (Set 'free' to true during initial setup.)
	 * 
	 * @param params
	 * @return BuildSettlement_Output
	 */
	public BuildSettlement_Output buildSettlement(BuildSettlement_Input params){
		
//		/moves/buildSettlement
		
		return null;
	}
	
	/**
	 * Builds a city at the specified location.
	 * 
	 * @param params
	 * @return BuildCity_Output
	 */
	public BuildCity_Output buildCity(BuildCity_Input params){
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
	 * Used to accept or reject a trade offered to you
	 * 
	 * @param params
	 * @return AcceptTrade_Output
	 */
	public AcceptTrade_Output acceptTrade(AcceptTrade_Input params){
		
//		/moves/acceptTrade
//		VALID INPUT
//		playerIndex: 1
//		willAccept: true
//		
		return null;
	}
	
	/**
	 * Used to execute a maritime trade
	 * 
	 * @param params
	 * @return MaritimeTrade_Output
	 */
	public MaritimeTrade_Output maritimeTrade(MaritimeTrade_Input params){
		
		return null;
	}

	/**
	 * Discards the specified resource cards.
	 * 
	 * @param params
	 * @return DiscardCards_Output
	 */
	public DiscardCards_Output discardCards(DiscardCards_Input params){
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
