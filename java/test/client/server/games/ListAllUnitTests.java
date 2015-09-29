package client.server.games;

import static org.junit.Assert.*;

import org.junit.*;

import client.server.Server;
import shared.communication.games.ListGames_Input;
import shared.communication.games.ListGames_Output;

public class ListAllUnitTests 
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
	public void testListAll() 
	{
		// test valid input
		ListGames_Input list_games_input = new ListGames_Input();
		ListGames_Output list_games_result = server.listGames(list_games_input);
		assertNotNull(list_games_result);
	}
}
