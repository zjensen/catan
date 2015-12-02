package shared.locations;

import java.io.Serializable;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4711603676676247189L;
	private HexLocation hexLoc;
	private EdgeDirection dir;
	
	public EdgeLocation(HexLocation hexLoc, EdgeDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public HexLocation getHexLoc()
	{
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}
	
	public EdgeDirection getDir()
	{
		return dir;
	}
	
	private void setDir(EdgeDirection dir)
	{
		this.dir = dir;
	}
	
	@Override
	public String toString()
	{
		return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		EdgeLocation other = (EdgeLocation)obj;
		if(dir != other.dir)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation()
	{
		
		// Return an EdgeLocation that has direction NW, N, or NE
		
		switch (dir)
		{
			case NorthWest:
			case North:
			case NorthEast:
				return this;
			case SouthWest:
			case South:
			case SouthEast:
				return new EdgeLocation(hexLoc.getNeighborLoc(dir),
										dir.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}
	
	
	/**
	 * determines whether or not the given edgelocation is connected to this edge location
	 * @param e2
	 * @return true if the edges are connected
	 */
	public boolean areNeighbors(EdgeLocation e2) //checks if e2 is connected to this edge location
	{
		EdgeLocation e1 = this.getNormalizedLocation();
		e2 = e2.getNormalizedLocation();
		
		if(e1.getDir() == EdgeDirection.NorthWest)
		{
			if(e2.getHexLoc().equals(e1.getHexLoc()) && e2.getDir() == EdgeDirection.North) //1
			{
				return true; //yeet yah!
			}
			else if(e2.getHexLoc().equals(e1.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest)) && e2.getDir() == EdgeDirection.NorthEast) //1
			{
				return true; //yeet yah!
			}
			else if(e2.getHexLoc().equals(e1.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest))) //2
			{
				if(e2.getDir() == EdgeDirection.North || e2.getDir() == EdgeDirection.NorthEast)
				{
					return true; //yeet yah!
				}
			}
		}
		else if(e1.getDir() == EdgeDirection.North)
		{
			if(e2.getHexLoc().equals(e1.getHexLoc()) && (e2.getDir() == EdgeDirection.NorthEast || e2.getDir() == EdgeDirection.NorthWest)) //2
			{
				return true; //yeet yah!
			}
			else if(e2.getHexLoc().equals(e1.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest)) && e2.getDir() == EdgeDirection.NorthEast) //1
			{
				return true; //yeet yah!
			}
			else if(e2.getHexLoc().equals(e1.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast)) && e2.getDir() == EdgeDirection.NorthWest) //1
			{
				return true; //yeet yah!
			}
			
		}
		else if(e1.getDir() == EdgeDirection.NorthEast)
		{
			if(e2.getHexLoc().equals(e1.getHexLoc()) && e2.getDir() == EdgeDirection.North) //1
			{
				return true; //yeet yah!
			}
			else if(e2.getHexLoc().equals(e1.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast)) && e2.getDir() == EdgeDirection.NorthWest) //1
			{
				return true; //yeet yah!
			}
			else if(e2.getHexLoc().equals(e1.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast))) //2
			{
				if(e2.getDir() == EdgeDirection.North || e2.getDir() == EdgeDirection.NorthWest)
				{
					return true; //yeet yah!
				}
			}
		}

		return false; //nahhhhhhh
	}
	
	public String toServerFormattedString()
	{
		switch (dir)
		{
			case NorthWest:
				return "NW";
			case North:
				return "N";
			case NorthEast:
				return "NE";
			case SouthWest:
				return "SW";
			case South:
				return "S";
			case SouthEast:
				return "SE";
		}
		return null;
	}
	
	public VertexLocation[] getAdjacentVertices() {
		EdgeLocation normalized = this.getNormalizedLocation();
		VertexLocation[] output = new VertexLocation[2];
		switch(normalized.getDir()){
			case NorthWest:
				output[0] = new VertexLocation(normalized.getHexLoc(), VertexDirection.NorthWest);
				output[1] = new VertexLocation(normalized.getHexLoc(), VertexDirection.West);
				break;
			case North:
				output[0] = new VertexLocation(normalized.getHexLoc(), VertexDirection.NorthWest);
				output[1] = new VertexLocation(normalized.getHexLoc(), VertexDirection.NorthEast);
				break;
			case NorthEast:
				output[0] = new VertexLocation(normalized.getHexLoc(), VertexDirection.NorthEast);
				output[1] = new VertexLocation(normalized.getHexLoc(), VertexDirection.East);
				break;
			default:
				assert false;
				return null;
		}
		return output;
	}
}

