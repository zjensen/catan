package client.server.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.game.ListAI_Input;
import shared.communication.game.ListAI_Output;

public class ListAIUnitTests 
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
	public void testListAI() 
	{
		// test valid input
		ListAI_Input list_ai_input = new ListAI_Input();
		ListAI_Output list_ai_result = server.listAI(list_ai_input);
		assertEquals(list_ai_result.getResponse(), "[\"LARGEST_ARMY\"]");
		System.out.println("List AI test complete");
	}
}
