package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.DiscardCards_Input;
import shared.communication.moves.DiscardCards_Output;
import shared.models.ResourceCards;

public class DiscardCardsUnitTests 
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
	public void testDiscardCards() 
	{
		DiscardCards_Input discard_cards_input = new DiscardCards_Input(0, new ResourceCards(1, 1, 1, 1, 1));
		DiscardCards_Output discard_cards_result = server.discardCards(discard_cards_input);
		assertNotNull(discard_cards_result);
	}
}
