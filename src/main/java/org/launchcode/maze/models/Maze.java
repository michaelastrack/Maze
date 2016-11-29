package org.launchcode.maze.models;

import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "maze")
public class Maze {
	
	protected int xpos;
	protected int ypos;
	protected int size;
	public Block [][] blocks;
	protected String [][] path;
	protected String name;
	protected int startorient;
	private int uid;
	
	public Maze (int s, int x, int y, Block[][] b, String n, int so) {
		this.size = s;
		this.xpos = x;
		this.ypos = y;
		this.blocks = b;
		this.path = new String [s][s];
		this.name = n;
		this.startorient = so;
	}
	
	public Maze () {}
	
	@Id
	@GeneratedValue
	@NotNull
	@Column (name = "uid", unique = true)
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getStartorient() {
		return startorient;
	}

	public void setStartorient(int startorient) {
		this.startorient = startorient;
	}
	
	@NotNull
	@Column (name = "size")
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public String[][] getPath() {
		return path;
	}

	public void setPath(String[][] path) {
		this.path = path;
	}

	@NotNull
	@Column (name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	
	// Initializes the path 
	public void initPath () {
		for (int i = 0; i< this.size; i++) {
			for (int j = 0; j<this.size; j++) {
				if (this.blocks[i][j].isFinish()) {
					this.path[i][j] = "F";
				}
				else if (this.blocks[i][j].isStart()) {
					this.path[i][j] = "x";
				}
				else {
				this.path[i][j] = "O";
				}
			}
		}
	}
	
	// Displays the path through the maze that the user has taken
	public String DisplayPath () {
		String output = "";
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				output += this.path[i][j];
			}
			output += " \n";
		}
		
		return output;
	}
	
	/* Function used by the web app to populate the maze in the create portion of the program
	 * Takes in a String that is a letter, and returns a block of that type
	 */
	public Block CreateBlock (String value, boolean start, boolean finish) {
		
		if (value.equals("A")) {
			Block block = new TypeA (start, finish);
			return block;
		}
		else if (value.equals("B")) {
			Block block = new TypeB (start, finish);
			return block;
		}
		else if (value.equals("C")) {
			Block block = new TypeC (start, finish);
			return block;
		}
		else if (value.equals("D")) {
			Block block = new TypeD (start, finish);
			return block;
		}
		else if (value.equals("E")) {
			Block block = new TypeE (start, finish);
			return block;
		}
		else if (value.equals("F")) {
			Block block = new TypeF (start, finish);
			return block;
		}
		else if (value.equals("G")) {
			Block block = new TypeG (start, finish);
			return block;
		}
		else if (value.equals("H")) {
			Block block = new TypeH (start, finish);
			return block;
		}
		else if (value.equals("I")) {
			Block block = new TypeI (start, finish);
			return block;
		}
		else if (value.equals("J")) {
			Block block = new TypeJ (start, finish);
			return block;
		}
		else if (value.equals("K")) {
			Block block = new TypeK (start, finish);
			return block;
		}
		else if (value.equals("L")) {
			Block block = new TypeL (start, finish);
			return block;
		}
		else if (value.equals("M")) {
			Block block = new TypeM (start, finish);
			return block;
		}
		else if (value.equals("N")) {
			Block block = new TypeN (start, finish);
			return block;
		}
		else if (value.equals("O")) {
			Block block = new TypeO (start, finish);
			return block;
		}
		else {
			Block block = new TypeP (start, finish);
			return block;
		}
	}

	/* Updates the position following a move, and throws an exception if the move takes 
	 * the user outside of the bounds of the maze, also draws the path behind the user
	 */
	public void Move (int orient) {
		if (orient == 1) {
			this.path [this.xpos][this.ypos] = "|";
			this.xpos--;
			if (this.xpos < 0) {
				throw new IllegalArgumentException();
			} 
		}
		else if (orient == 2) {
			this.path [this.xpos][this.ypos] = "-";
			this.ypos++;
			if (this.ypos >= this.size) {
				throw new IllegalArgumentException();
			} 
		}
		else if (orient == 3) {
			this.path [this.xpos][this.ypos] = "|";
			this.xpos++;
			if (this.xpos >= this.size) {
				throw new IllegalArgumentException();
			} 
		}
		else if (orient == 4) {
			this.path [this.xpos][this.ypos] = "-";
			this.ypos--;
			if (this.ypos < 0) {
				throw new IllegalArgumentException();
			} 
		}
		this.path[this.xpos][this.ypos] = "x";
		
	}
	
	/* old function used when this was strictly a text based program running on the console
	public void RunMaze (int x, int y, int o) {
		boolean GoOn = true;
		this.setXpos(x);
		this.setYpos(y);
		this.initPath();
		int orient = o;
		s = new Scanner(System.in);
		int neworient;
		boolean invalidorient = true;
		
		while (GoOn) {
			this.path[xpos][ypos] = "x";
			System.out.println(this.DisplayPath());
			
			System.out.println(blocks [this.xpos] [this.ypos].TryToMove(orient));
			System.out.println("Enter in the number for the direction you want to move:");
			neworient = s.nextInt();
			while (invalidorient) {
				if (neworient + 2 == orient || neworient - 2 == orient) {
					System.out.println("You can't move backwards, please enter a new valid selection.");
					System.out.println(blocks [this.xpos] [this.ypos].TryToMove(orient));
					System.out.println("Enter in the number for the direction you want to move:");
					neworient = s.nextInt();
				}
				else {
					invalidorient = false;
				}
			}
			orient = neworient;
			invalidorient = true;
			this.Move(orient);
			if (blocks [this.xpos] [this.ypos].isDeadend()) {
				System.out.println("You lose, you have hit a dead end.");
				GoOn = false;
			}
			else if (blocks [this.xpos] [this.ypos].isFinish()) {
				System.out.println("Congratulations you made it to the end of the maze!!!!!!");
				GoOn = false;
			}
			else if (blocks [this.xpos][this.ypos].isStart()) {
				System.out.println("Game Over, you have returned back to the start.");
				GoOn = false;
			}
			
		}
		
		
	}  */

	public static void main(String[] args) {
		
	}

}
