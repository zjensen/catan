package client.server.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.game.AddAI_Input;
import shared.communication.game.AddAI_Output;

public class AddAIUnitTests 
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
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void testAddAI() 
	{
		// test w/o catan user
		AddAI_Input no_user_input = new AddAI_Input("LARGEST_ARMY");
		AddAI_Output no_user_result = server.addAI(no_user_input);
		assertEquals(no_user_result.getResponse(), "The catan.user HTTP cookie is missing.  You must login before calling this method.");
		
		// test w/o catan game
		server.setCatanUser("%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D");
		AddAI_Input no_game_input = new AddAI_Input("LARGEST_ARMY");
		AddAI_Output no_game_result = server.addAI(no_game_input);
		assertEquals(no_game_result.getResponse(), "The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		
		// test add to full game
		server.setCatanGame("2");
		AddAI_Input full_game_input = new AddAI_Input("LARGEST_ARMY");
		AddAI_Output full_game_result = server.addAI(full_game_input);
		assertEquals(full_game_result.getResponse(), "Could not add AI player  [LARGEST_ARMY]");
		
		// test invalid AIType
		server.setCatanGame("2");
		AddAI_Input bad_type_input = new AddAI_Input("NOT_REAL");
		AddAI_Output bad_type_result = server.addAI(bad_type_input);
		assertEquals(bad_type_result.getResponse(), "Could not add AI player  [NOT_REAL]");
	}
}
