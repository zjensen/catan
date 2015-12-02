package shared.communication.game;

import java.io.Serializable;

public class GameModel_Input implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9136512411611998000L;
	private int version;
	
	public GameModel_Input()
	{
		this.setVersion(-1);
	}
	
	public GameModel_Input(int version)
	{
		this.setVersion(version);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
