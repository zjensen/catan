package shared.communication.games;

import java.io.Serializable;

import com.google.gson.Gson;

public class CreateGame_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -997220044274241846L;
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String name;
	
	public CreateGame_Input()
	{
		
	}
	
	public CreateGame_Input(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.name = name;
	}

	public boolean isRandomTiles() {
		return randomTiles;
	}

	public void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}

	public boolean isRandomNumbers() {
		return randomNumbers;
	}

	public void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}

	public boolean isRandomPorts() {
		return randomPorts;
	}

	public void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
