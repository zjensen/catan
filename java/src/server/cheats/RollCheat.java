package server.cheats;

import shared.communication.moves.RollNumber_Input;
import shared.models.ClientModel;
import shared.models.Player;

public class RollCheat implements Cheat 
{
	private int number;
	private Player player;
	private ClientModel model;
	
	public RollCheat(int number, Player player, ClientModel model)
	{
		this.number = number;
		this.player = player;
		this.model = model;
	}
	
	@Override
	public void execute() 
	{
		this.model.rollNumber(new RollNumber_Input(this.player.getIndex(), this.number));
	}

}
