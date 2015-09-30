package client.server.moves;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.server.Server;
import shared.communication.moves.BuildRoad_Input;
import shared.communication.moves.BuildRoad_Output;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class BuildRoadUnitTests 
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
	public void testBuildRoad() 
	{
		EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North);
		BuildRoad_Input build_road_input = new BuildRoad_Input(0, edge, false);
		BuildRoad_Output build_road_result = server.buildRoad(build_road_input);
		assertNotNull(build_road_result);
	}
}
