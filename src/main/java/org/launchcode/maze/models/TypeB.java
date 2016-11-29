package org.launchcode.maze.models;

public class TypeB extends Block{
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	public TypeB (boolean s, boolean f) {
		super(s, f, false);
		this.north = true;
		this.east = true;
		this.south = true;
		this.west = false;
	}
	
	public String TryToMove (int orient) {
		if (orient == 2) {
			throw new IllegalArgumentException();
		}
		String output = "";
		if (orient == 1) {
			output = "1) North \n";
			output += "2) East \n";
			return output;
		}
		else if (orient == 3) {
			output = "2) East \n";
			output += "3_ South \n";
			return output;
		}
		else {
			output = "1) North \n";
			output += "3) South \n";
			return output;
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
