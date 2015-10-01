package client.server.games;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.games.CreateGame_Input;
import shared.communication.games.CreateGame_Output;

public class CreateGameUnitTests 
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
	public void testCreateGame() 
	{
		// test create game
		CreateGame_Input create_game_input = new CreateGame_Input(false, false, false, "My new game");
		CreateGame_Output create_game_result = server.createGame(create_game_input);
		// create regex to make sure our result contains the title
		Matcher m = Pattern.compile("\"title\":\"My new game\"").matcher(create_game_result.getResponse());
		assertTrue(m.find());
	}
}
