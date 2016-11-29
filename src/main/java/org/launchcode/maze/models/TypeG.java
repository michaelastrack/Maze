package org.launchcode.maze.models;

public class TypeG extends Block{
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	public TypeG (boolean s, boolean f) {
		super(s, f, false);
		this.north = false;
		this.east = false;
		this.south = true;
		this.west = true;
	}
	
	public String TryToMove (int orient) {
		if (orient == 3 || orient == 4) {
			throw new IllegalArgumentException ();
		}
		String outcome = "";
		if (orient == 1) {
			outcome = "4) West \n";
			return outcome;
		}
		else {
			outcome = "3) South \n";
			return outcome;
		}
		
	}

	public boolean isNorth() {
		return north;
	}

	public boolean isEast() {
		return east;
	}

	public boolean isSouth() {
		return south;
	}

	public boolean isWest() {
		return west;
	}

}
