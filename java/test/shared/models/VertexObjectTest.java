package shared.models;

import  org.junit.*;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import static org.junit.Assert.*;

public class VertexObjectTest {

	public VertexObject myVertex;
	
	@Before
	public void init() throws Exception {
		Player owner = new Player();
		owner.setPlayerID(0);
		
		HexLocation hexLoc = new HexLocation(0, 0);
		VertexDirection dir = VertexDirection.East;
		VertexLocation location = new VertexLocation(hexLoc , dir );
		
		myVertex = new VertexObject(owner, location);
	}
	
	
	@Test
	public void testVertexExists(){
		assertNotNull(myVertex);
	}
	
	@Test
	public void testVertexComponents(){
		
		assertEquals(0, myVertex.getOwner());
		
		HexLocation newHexLoc = new HexLocation(0, 0);
		assertTrue(myVertex.getLocation().getHexLoc().equals(newHexLoc));
	
		VertexDirection newDir = VertexDirection.East;
		assertTrue(myVertex.getLocation().getDir().equals(newDir));
	}
	
}
