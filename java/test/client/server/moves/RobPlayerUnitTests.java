package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.RobPlayer_Input;
import shared.communication.moves.RobPlayer_Output;
import shared.locations.HexLocation;

public class RobPlayerUnitTests 
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
		
	private Server server;

	@Before
	public void setUp() throws Exception 
	{
		this.server = new Server();
		server.setCatanUser("%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D");
		server.setCatanGame("0");
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void testRobPlayer() 
	{
		// major/minor error creating the hex
		HexLocation hex = new HexLocation(0, 0);
		RobPlayer_Input rob_player_input = new RobPlayer_Input(0, 1, hex);
		RobPlayer_Output rob_player_result = server.robPlayer(rob_player_input);
		assertNotNull(rob_player_result);
	}
}
