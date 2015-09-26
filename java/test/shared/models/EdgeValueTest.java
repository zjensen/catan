package shared.models;

import org.junit.*;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import static org.junit.Assert.*;

public class EdgeValueTest {
	
	EdgeValue myEdge;
	
	@Before
	public void init() throws Exception {
		HexLocation hexLoc = new HexLocation(0, 0);
		EdgeDirection dir = EdgeDirection.North;
		EdgeLocation myLoc = new EdgeLocation(hexLoc, dir);
		Player myPlayer = new Player();
		myPlayer.setPlayerID(0);
		
		myEdge = new EdgeValue(myPlayer, myLoc);
	}
	
	@Test 
	public void testEdgeExists() throws Exception {
		assertNotNull(myEdge);
		
	}
	
	@Test
	public void testEdgeComponents() throws Exception {
		
		assertEquals(0, myEdge.getOwner());
		
		HexLocation newHexLoc = new HexLocation(0, 0);
		assertTrue(myEdge.getLocation().getHexLoc().equals(newHexLoc));
		
		EdgeDirection newDir = EdgeDirection.North;
		assertTrue(myEdge.getLocation().getDir().equals(newDir));
	}

}
