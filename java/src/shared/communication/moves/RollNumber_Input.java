package shared.communication.moves;

import java.io.Serializable;

import com.google.gson.Gson;

public class RollNumber_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6248661541206730973L;
	private int playerIndex;
	private int number;
	private final String type = "rollNumber";
	
	public RollNumber_Input(int playerIndex, int number) 
	{
		super();
		this.playerIndex = playerIndex;
		this.number = number;
	}

	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}

	/**
	 * @param playerIndex the playerIndex to set
	 */
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	/**
	 * @return the rollNumber
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param rollNumber the rollNumber to set
	 */
	public void setNumber(int rollNumber) {
		this.number = rollNumber;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	public String toJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
