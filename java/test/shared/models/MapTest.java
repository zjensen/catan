package shared.models;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.*;
import shared.models.*;
import shared.locations.*;

public class MapTest {
	
	private Hex[] myHexes;	
	private Port[] myPorts;
	private HashMap <EdgeLocation, Player> myRoads;
	private HashMap <VertexLocation, Player> mySettlements;
	private HashMap <VertexLocation, Player> myCities;
	private int radius;
	private HexLocation myRobber;
	
	public Map gameBoard;// = new Map(hexes, ports, roads, settlements, cities, radius, robber)
	
	
	@Before
	public void init(){
		
		gameBoard = new Map(myHexes, myPorts, myRoads, mySettlements, myCities, radius, myRobber);
	}

}
