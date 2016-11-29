package org.launchcode.maze.models;

public class TypeM extends Block {
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	public TypeM (boolean s, boolean f) {
		super(s, f, true);
		this.north = false;
		this.east = true;
		this.south = false;
		this.west = false;
	}
	
	public String TryToMove (int orient) {
		if (orient != 4) {
			throw new IllegalArgumentException();
		}
		return "Dead End. Game Over.";
		
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
