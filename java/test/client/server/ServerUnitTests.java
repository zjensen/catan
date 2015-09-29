package client.server;

import org.junit.*;

import static org.junit.Assert.*;

public class ServerUnitTests 
{
	
	@Before
	public void setup() 
	{
		
	}
	
	@After
	public void teardown() 
	{
		
	}
	
	@Test
	public void test_1() 
	{		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{

		String[] testClasses = new String[] 
		{
				"client.server.ServerUnitTests",
				"client.server.user.LoginUnitTests",		// user tests
				"client.server.user.RegisterUnitTests",
				"client.server.games.ListAllUnitTests",		// games tests
				"client.server.games.CreateGameUnitTests",
				"client.server.games.JoinGameUnitTests",
				"client.server.game.GameResetUnitTests",    // game tests
				"client.server.game.ListAIUnitTests",
				"client.server.moves.SendChatUnitTests"		// moves tests
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}