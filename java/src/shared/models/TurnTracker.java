package shared.models;

public class TurnTracker {

	private index currentTurn;
	private String status; // what's happening now
	private index longestRoad; 
	private index largestArmy;
	
	
	public TurnTracker() {
		
		this.currentTurn = 0;
		this.status = "FirstRound";
		this.longestRoad = 0;
		this.largestArmy = 0;
	}


	
	public index getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(index currentTurn) {
		this.currentTurn = currentTurn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public index getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(index longestRoad) {
		this.longestRoad = longestRoad;
	}

	public index getLargestArmy() {
		return largestArmy;
	}

	public void setLargestArmy(index largestArmy) {
		this.largestArmy = largestArmy;
	}
	
	
	
	
	
	
	
	
	
	
}
