package client.server.game;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.game.ResetGame_Input;
import shared.communication.game.ResetGame_Output;

public class GameResetUnitTests 
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
	public void testGameReset() 
	{
		// test w/o catan_user set
		ResetGame_Input no_user_input = new ResetGame_Input();
		ResetGame_Output no_user_result = server.resetGame(no_user_input);
		assertEquals(no_user_result.getResponse(), "The catan.user HTTP cookie is missing.  You must login before calling this method.");
		
		// test w/o catan_game set
		server.setCatanUser("%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D");
		ResetGame_Input no_game_input = new ResetGame_Input();
		ResetGame_Output no_game_result = server.resetGame(no_game_input);
		assertEquals(no_game_result.getResponse(), "The catan.game HTTP cookie is missing.  You must join a game before calling this method.");
		
		// test valid input
		server.setCatanGame("0");
		ResetGame_Input reset_game_input = new ResetGame_Input();
		ResetGame_Output reset_game_result = server.resetGame(reset_game_input);
		// create regex to make sure our result contains the message
		Matcher m = Pattern.compile("\"deck\"").matcher(reset_game_result.getResponse());
		assertTrue(m.find());
	}
}
