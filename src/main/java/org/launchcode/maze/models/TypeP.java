package org.launchcode.maze.models;

public class TypeP extends Block{
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	public TypeP (boolean s, boolean f) {
		super(s, f, true);
		this.north = false;
		this.east = false;
		this.south = false;
		this.west = false;
	}
	
	public String TryToMove (int orient) {
		throw new IllegalArgumentException();
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
