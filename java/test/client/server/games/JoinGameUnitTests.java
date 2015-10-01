package client.server.games;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.games.JoinGame_Input;
import shared.communication.games.JoinGame_Output;

public class JoinGameUnitTests 
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
	public void testJoinGame() 
	{
		// test w/o catan_user set
		JoinGame_Input no_user_input = new JoinGame_Input(2, "orange");
		JoinGame_Output no_user_result = server.joinGame(no_user_input);
		assertEquals(no_user_result.getResponse(), "The catan.user HTTP cookie is missing.  You must login before calling this method.");
		
		//test bad color
		JoinGame_Input bad_color_input = new JoinGame_Input(2, "bad_color");
		JoinGame_Output bad_color_result = server.joinGame(bad_color_input);
		assertEquals(bad_color_result.getResponse(), "Invalid request: color has bad value");
		
		// test valid input
		server.setCatanUser("%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D");
		JoinGame_Input join_game_input = new JoinGame_Input(2, "blue");
		JoinGame_Output join_game_result = server.joinGame(join_game_input);
		assertEquals(join_game_result.getResponse(), "Success");
	}
}
