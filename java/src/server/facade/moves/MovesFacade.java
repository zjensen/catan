package server.facade.moves;

import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.BuyDevCard_Output;
import shared.communication.moves.FinishTurn_Input;
import shared.communication.moves.FinishTurn_Output;
import shared.communication.moves.Monopoly_Input;
import shared.communication.moves.Monopoly_Output;
import shared.communication.moves.Monument_Input;
import shared.communication.moves.Monument_Output;
import shared.communication.moves.RoadBuilding_Input;
import shared.communication.moves.RoadBuilding_Output;
import shared.communication.moves.RollNumber_Input;
import shared.communication.moves.RollNumber_Output;
import shared.communication.moves.Soldier_Input;
import shared.communication.moves.Soldier_Output;
import shared.communication.moves.YearOfPlenty_Input;
import shared.communication.moves.YearOfPlenty_Output;

public class MovesFacade {
	
	public RollNumber_Output rollNumber(RollNumber_Input params){
//		moves/rollNumber
//    VALID INPUT
//        playerIndex: 1
//        number: 8
		
		return null;
	}
	
	public FinishTurn_Output finishTurn(FinishTurn_Input params){
		
//		/moves/finishTurn
		
		
		return null;
		
	}

	public BuyDevCard_Output buyDevCard(BuyDevCard_Input params){
//		/moves/buyDevCard
//		VALID INPUT
//		playerIndex: 2
		
		return null;
	}

	public YearOfPlenty_Output yearOfPlenty(YearOfPlenty_Input params){
		
//		/moves/Year_of_Plenty
//		VALID INPUT
//		playerIndex: 3
//		resource1 : Ore
//		resource2 : Sheep
//		***note that the resource names must be capitalized***
		return null;
	}

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

	
	public Monopoly_Output monopoly(Monopoly_Input params){
		
//		/moves/Monopoly
//		VALID INPUT
//		resource : Brick
//		playerIndex: 0
		
		return null;
	}
	
	public Monument_Output monument(Monument_Input params){
		
//		/moves/Monument
//		VALID INPUT
//		playerIndex : 1
//		
		
		return null;
	}
}

/* 
/moves/buildRoad
    VALID INPUT
        playerIndex: 2
        roadLocation
            x: -1
            y: -1
            direction: "NE"
        free: false
/moves/buildSettlement
/moves/buildCity
    VALID INPUT
        playerIndex: 2
        vertexLocation
            x: -1
            y: -1
            direction: "NE"
        free: false

/moves/offerTrade
    VALID INPUT
        playerIndex: 3
        offer:
            brick: 1
            ore: 0
            sheep: 0
            wheat: 0
            wood: 2
        receiver: 2

/moves/acceptTrade
    VALID INPUT
        playerIndex: 1
        willAccept: true

/moves/discardCards
    VALID INPUT
        playerIndex: 0
        discardedCards:
            brick: 0
            ore: 1
            sheep: 1
            wheat: 3
            wood: 0
*/