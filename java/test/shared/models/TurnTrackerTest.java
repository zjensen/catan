package shared.models;

import org.junit.*;
import static org.junit.Assert.*;

public class TurnTrackerTest {

	TurnTracker myTracker;
	
	@Before
	public void init(){
		myTracker = new TurnTracker();
	}
	
	@Test
	public void testComponents(){
		
		assertEquals(0, myTracker.getCurrentTurn());
		
		assertEquals("firstround", myTracker.getStatus());
		
		assertEquals(-1, myTracker.getLongestRoad());
		
		assertEquals(-1, myTracker.getLargestArmy());
		
	}
	
	@Test
	public void testChangingStuff(){
		myTracker.setCurrentTurn(4);
		myTracker.setLargestArmy(3);
		myTracker.setLongestRoad(6);
		myTracker.setStatus("Davis is debating whether or not to roll");
		
		assertEquals(4, myTracker.getCurrentTurn());
		
		assertEquals("Davis is debating whether or not to roll", myTracker.getStatus());
		
		assertEquals(6, myTracker.getLongestRoad());
		
		assertEquals(3, myTracker.getLargestArmy());
	}
	
	
}
