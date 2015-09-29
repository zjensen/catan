package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.YearOfPlenty_Input;
import shared.communication.moves.YearOfPlenty_Output;
import shared.definitions.ResourceType;

public class YearOfPlentyUnitTests 
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
	public void testYearOfPlenty() 
	{
		YearOfPlenty_Input year_of_plenty_input = new YearOfPlenty_Input(0, ResourceType.BRICK, ResourceType.WOOD);
		YearOfPlenty_Output year_of_plenty_result = server.yearOfPlenty(year_of_plenty_input);
		assertNotNull(year_of_plenty_result);
	}
}
