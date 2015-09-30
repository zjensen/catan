package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.BuildSettlement_Input;
import shared.communication.moves.BuildSettlement_Output;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildSettlementUnitTests 
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
	public void testBuildSettlement() 
	{
		VertexLocation vertex = new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast);
		BuildSettlement_Input build_settlement_input = new BuildSettlement_Input(0, vertex, false);
		BuildSettlement_Output build_settlement_output = server.buildSettlement(build_settlement_input);
		assertNotNull(build_settlement_output);
	}
}
