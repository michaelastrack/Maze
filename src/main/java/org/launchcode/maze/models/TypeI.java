package org.launchcode.maze.models;

public class TypeI extends Block{
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	public TypeI (boolean s, boolean f) {
		super(s, f, true);
		this.north = true;
		this.east = false;
		this.south = false;
		this.west = false;
	}
	
	public String TryToMove (int orient) {
		if (orient != 3) {
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
