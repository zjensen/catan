package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.OfferTrade_Input;
import shared.communication.moves.OfferTrade_Output;
import shared.models.ResourceCards;

public class OfferTradeUnitTests 
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
	public void testOfferTrade() 
	{
		OfferTrade_Input offer_trade_input = new OfferTrade_Input(0, new ResourceCards(0, 1, -1, 0, 0), 1);
		OfferTrade_Output offer_trade_result = server.offerTrade(offer_trade_input);
		assertNotNull(offer_trade_result);
	}
}
