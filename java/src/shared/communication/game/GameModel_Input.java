package shared.communication.game;

public class GameModel_Input 
{
	private int version;
	
	public GameModel_Input()
	{
		
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
