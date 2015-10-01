package shared.models;

public class TurnTracker {

	private int currentTurn; // index (0-3) of player whose turn it is
	private String status; // what's happening now
	private int longestRoad; // index of player currently holding longest road card
	private int largestArmy; // index of player currently holding largest army card
	
	public TurnTracker() {
		
		this.currentTurn = 0;
		this.status = "FirstRound";
		this.longestRoad = 0;
		this.largestArmy = 0;
		
	}

	
	
	
	@Override
	public String toString() {
		return "TurnTracker [" 
				+ "\n\tcurrentTurn = " + currentTurn 
				+ "\n\tstatus      = " + status 
				+ "\n\tlongestRoad = " + longestRoad
				+ "\n\tlargestArmy = " + largestArmy + "   ]";
	}




	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}

	public int getLargestArmy() {
		return largestArmy;
	}

	public void setLargestArmy(int largestArmy) {
		this.largestArmy = largestArmy;
	}
	
}
