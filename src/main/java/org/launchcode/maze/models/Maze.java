package org.launchcode.maze.models;

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
	protected String name;
	protected int startorient;
	private int uid;
	private int completions;
	private int fails;
	private long avgtime;
	public int [][] Intblock;
	
	public Maze (int s, int[][] sb, String n, int so) {
		this.size = s;
		this.name = n;
		this.startorient = so;
		this.completions = 0;
		this.fails = 0;
		this.avgtime = 0;
		this.Intblock = sb;
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
	
	public int getCompletions() {
		return completions;
	}

	public void setCompletions(int completions) {
		this.completions = completions;
	}

	public long getAvgtime() {
		return avgtime;
	}

	public int getFails() {
		return fails;
	}

	public void setFails(int fails) {
		this.fails = fails;
	}

	public void setAvgtime(long avgtime) {
		this.avgtime = avgtime;
	}

	public int[][] getIntblock() {
		return Intblock;
	}

	public void setIntblock(int[][] intblock) {
		Intblock = intblock;
	}

	// After a maze has been successfully completed records the completion and adjusts the average time appropriately
	public void complete (long time) {
		long totaltime = this.avgtime * this.completions;
		totaltime += time;
		this.setCompletions((this.getCompletions() + 1));
		this.setAvgtime((totaltime/this.getCompletions()));
	}
	
	public void fail () {
		this.setFails((this.getFails() + 1));
	}
	
	// computes the percentage of time that users have successfully navigated a given maze
	public int percentage () {
		int percent;
		double total = this.getFails() + this.getCompletions();
		total = this.getCompletions()/total;
		percent = (int) (total * 100);
		return percent;
	}

	// Initializes the path 
	public void initPath (Block [][] blocks, String [][] path) {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (blocks[i][j].isFinish()) {
					path[i][j] = "F";
				}
				else if (blocks[i][j].isStart()) {
					path[i][j] = "x";
				}
				else {
				path[i][j] = "O";
				}
			}
		}
	}
	
	// Displays the path through the maze that the user has taken
	public String DisplayPath (String[][] path) {
		String output = "";
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				output += path[i][j];
			}
			output += " \n";
		}
		
		return output;
	}
	
	/* Function used by the web app to populate the maze in the create portion of the program
	 * Takes in a String that is a letter, and returns a block of that type
	 */
	public static Block CreateBlock (String value, boolean start, boolean finish) {
		
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
	
	// Takes a 2D array of Integers that is stored in the database and returns a 2D array of blocks
	public static Block[][] InttoBlock (int [][] IB) {
		int size = IB.length;
		int s;
		int f;
		int t;
		int temp;
		boolean finish;
		boolean start;
		String type;
		Block [][] b = new Block [size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j <size; j++) {
				f = IB[i][j]%10;
				temp = IB[i][j]/10;
				s = temp % 10;
				t = temp / 10;
				if (f == 1) 
					finish = true;
				else 
					finish = false;
				
				if (s == 1) 
					start = true;
				else 
					start = false;
				
				if (t == 10) 
					type = "A";
				else if (t == 11)
					type = "B";
				else if (t == 12)
					type = "C";
				else if (t == 13)
					type = "D";
				else if (t == 14)
					type = "E";
				else if (t == 15)
					type = "F";
				else if (t == 16)
					type = "G";
				else if (t == 17)
					type = "H";
				else if (t == 18)
					type = "I";
				else if (t == 19)
					type = "J";
				else if (t == 20)
					type = "K";
				else if (t == 21)
					type = "L";
				else if (t == 22) 
					type = "M";
				else if (t == 23)
					type = "N";
				else if (t == 24)
					type = "O";
				else 
					type = "P";
				
				b[i][j] = Maze.CreateBlock(type, start, finish);
			}
		}
		
		return b;
	}

	/* Updates the position following a move, and throws an exception if the move takes 
	 * the user outside of the bounds of the maze, also draws the path behind the user
	 */
	public void Move (int orient, String [][] path) {
		if (orient == 1) {
			path [this.xpos][this.ypos] = "|";
			this.xpos--;
			if (this.xpos < 0) {
				throw new IllegalArgumentException();
			} 
		}
		else if (orient == 2) {
			path [this.xpos][this.ypos] = "-";
			this.ypos++;
			if (this.ypos >= this.size) {
				throw new IllegalArgumentException();
			} 
		}
		else if (orient == 3) {
			path [this.xpos][this.ypos] = "|";
			this.xpos++;
			if (this.xpos >= this.size) {
				throw new IllegalArgumentException();
			} 
		}
		else if (orient == 4) {
			path [this.xpos][this.ypos] = "-";
			this.ypos--;
			if (this.ypos < 0) {
				throw new IllegalArgumentException();
			} 
		}
		path[this.xpos][this.ypos] = "x";
		
	}

	public static void main(String[] args) {
		int [][] ib ={ {2022, 2122, 1521}, {1122, 2122, 1622}, {2322, 2122, 1312} };
		//Maze m = new Maze (3, ib, "A", 1);
		Block [][] blocks = Maze.InttoBlock(ib);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.println(blocks[i][j]);
			}
		}
		
		
	}

}
