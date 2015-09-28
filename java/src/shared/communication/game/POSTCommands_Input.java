package shared.communication.game;

public class POSTCommands_Input 
{
	// used only for testing purposes
	private boolean catan_user;
	private boolean catan_game;
	
	public POSTCommands_Input()
	{
		
	}

	public boolean hasCatan_user() {
		return catan_user;
	}

	public void setCatan_user(boolean catan_user) {
		this.catan_user = catan_user;
	}

	public boolean hasCatan_game() {
		return catan_game;
	}

	public void setCatan_game(boolean catan_game) {
		this.catan_game = catan_game;
	}
}
