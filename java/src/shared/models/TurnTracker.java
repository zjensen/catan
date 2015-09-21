package shared.models;

public class TurnTracker {

	private int currentTurn;
	private String status; // what's happening now
	private int longestRoad; 
	private int largestArmy;
	
	public TurnTracker() {
		
		this.currentTurn = 0;
		this.status = "FirstRound";
		this.longestRoad = 0;
		this.largestArmy = 0;
		
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
