package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.MaritimeTrade_Input;
import shared.communication.moves.MaritimeTrade_Output;
import shared.definitions.ResourceType;

public class MaritimeTradeUnitTests 
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
	public void testMaritimeTrade() 
	{
		MaritimeTrade_Input maritime_trade_input = new MaritimeTrade_Input(0, 3, ResourceType.BRICK, ResourceType.SHEEP);
		MaritimeTrade_Output maritime_trade_result = server.maritimeTrade(maritime_trade_input);
		assertNotNull(maritime_trade_result);
	}
}
