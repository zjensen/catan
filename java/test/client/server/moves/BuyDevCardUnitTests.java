package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.BuyDevCard_Input;
import shared.communication.moves.BuyDevCard_Output;

public class BuyDevCardUnitTests 
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
	public void testBuyDevCard() 
	{
		BuyDevCard_Input buy_dev_card_input = new BuyDevCard_Input(0);
		BuyDevCard_Output buy_dev_card_result = server.buyDevCard(buy_dev_card_input);
		assertNotNull(buy_dev_card_result);
	}
}
