package org.launchcode.maze.models;

public class TypeN extends Block{
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	public TypeN (boolean s, boolean f) {
		super(s, f, false);
		this.north = true;
		this.east = true;
		this.south = false;
		this.west = false;
	}
	
	public String TryToMove (int orient) {
		if (orient == 1 || orient == 2) {
			throw new IllegalArgumentException();
		}
		String outcome = "";
		if (orient == 3) {
			outcome = "2) East \n";
			return outcome;
		}
		else {
			outcome = "1) North \n";
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
