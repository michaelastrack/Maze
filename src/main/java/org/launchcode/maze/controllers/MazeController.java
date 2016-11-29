package org.launchcode.maze.controllers;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.maze.models.Block;
import org.launchcode.maze.models.Maze;
import org.launchcode.maze.models.TypeA;
import org.launchcode.maze.models.TypeB;
import org.launchcode.maze.models.TypeC;
import org.launchcode.maze.models.TypeD;
import org.launchcode.maze.models.TypeE;
import org.launchcode.maze.models.TypeF;
import org.launchcode.maze.models.TypeG;
import org.launchcode.maze.models.TypeH;
import org.launchcode.maze.models.TypeJ;
import org.launchcode.maze.models.TypeK;
import org.launchcode.maze.models.TypeL;
import org.launchcode.maze.models.TypeM;
import org.launchcode.maze.models.TypeN;
import org.launchcode.maze.models.TypeP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MazeController {
	
	int s;
	int x;
	int y;
	Block [][] blocks;
	Maze m;
	int orient;
	boolean north;
	boolean east;
	boolean south;
	boolean west;
	String displaymaze;
	String message;
	String S;
	
	@RequestMapping (value = "/", method = RequestMethod.GET)
	public String SlashPage () {
		
		return "redirect:/main";
	}
	
	@RequestMapping (value = "/main", method = RequestMethod.GET)
	public String MainPage () {
		return "main";
	}
	
	@RequestMapping (value = "/start", method = RequestMethod.POST)
	public String StartMaze (HttpServletRequest request, Model model) {
		
		x = 5;
		y = 0;
		s = 6;
		blocks = new Block [s][s];
		m = new Maze(s, x, y, blocks, "Demo", 1);
		orient = m.getStartorient();
		// row 0
		m.blocks[0][0] = new TypeJ (false, false);
		m.blocks[0][1] = new TypeK (false, false);
		m.blocks[0][2] = new TypeL (false, false);
		m.blocks[0][3] = new TypeE (false, false);
		m.blocks[0][4] = new TypeL (false, false);
		m.blocks[0][5] = new TypeD (false, true);
		
		// row 1
		m.blocks[1][0] = new TypeB (false, false);
		m.blocks[1][1] = new TypeA (false, false);
		m.blocks[1][2] = new TypeE (false, false);
		m.blocks[1][3] = new TypeD (false, false);
		m.blocks[1][4] = new TypeP (false, false);
		m.blocks[1][5] = new TypeH (false, false);
		
		// row 2
		m.blocks[2][0] = new TypeH (false, false);
		m.blocks[2][1] = new TypeH (false, false);
		m.blocks[2][2] = new TypeN (false, false);
		m.blocks[2][3] = new TypeA (false, false);
		m.blocks[2][4] = new TypeL (false, false);
		m.blocks[2][5] = new TypeD (false, false);
		
		// row 3
		m.blocks[3][0] = new TypeH (false, false);
		m.blocks[3][1] = new TypeN (false, false);
		m.blocks[3][2] = new TypeL (false, false);
		m.blocks[3][3] = new TypeA (false, false);
		m.blocks[3][4] = new TypeL (false, false);
		m.blocks[3][5] = new TypeF (false, false);
		
		// row 4
		m.blocks[4][0] = new TypeB (false, false);
		m.blocks[4][1] = new TypeG (false, false);
		m.blocks[4][2] = new TypeM (false, false);
		m.blocks[4][3] = new TypeA (false, false);
		m.blocks[4][4] = new TypeG (false, false);
		m.blocks[4][5] = new TypeJ (false, false);
		
		// row 5
		m.blocks[5][0] = new TypeB (true, false);
		m.blocks[5][1] = new TypeC (false, false);
		m.blocks[5][2] = new TypeL (false, false);
		m.blocks[5][3] = new TypeC (false, false);
		m.blocks[5][4] = new TypeC (false, false);
		m.blocks[5][5] = new TypeF (false, false);
		
		m.initPath();
		displaymaze = m.DisplayPath();
		
		if (orient != 3) {
			north = m.blocks[x][y].isNorth();
		}
		if (orient != 4) {
			east = m.blocks[x][y].isEast();
		}
		if (orient != 1) {
			south = m.blocks[x][y].isSouth();
		}
		if (orient != 2) {
			west = m.blocks[x][y].isWest();
		} 
		
		model.addAttribute("north", north);
		model.addAttribute("east", east);
		model.addAttribute("south", south);
		model.addAttribute("west", west);
		
		
		model.addAttribute("displaymaze", displaymaze);
		for (int i = 0; i < m.getSize(); i++) {
			for (int j = 0; j < m.getSize(); j++) {
				if (m.blocks[i][j].isStart()) {
					m.setXpos(i);
					m.setYpos(j);
				}
			}
		}
		
		return "maze";
	}
	
	@RequestMapping (value = "/maze", method = RequestMethod.POST)
	public String PlayMaze (HttpServletRequest request, Model model) {
		orient = Integer.parseInt(request.getParameter("direction"));
		m.Move(orient);
		x=m.getXpos();
		y=m.getYpos();
		displaymaze = m.DisplayPath();
		
		if (m.blocks[x][y].isDeadend()) {
			message = "You have run into a Dead End.  Game Over.";
			model.addAttribute("message", message);
			return "GameOver";
		}
		if (m.blocks[x][y].isStart()) {
			message = "You have returned back to the Start.  Game Over.";
			model.addAttribute("message", message);
			return "GameOver";
		}
		if (m.blocks[x][y].isFinish()) {
			message = "Congratulations, you have made it to the end of the maze!";
			model.addAttribute("message", message);
			return "GameOver";
		}
		
		// Checking the conditions for whether or not buttons need to be displayed
		north = false;
		east = false;
		south = false;
		west = false;
		if (orient != 3) {
			north = m.blocks[x][y].isNorth();
		}
		if (orient != 4) {
			east = m.blocks[x][y].isEast();
		}
		if (orient != 1) {
			south = m.blocks[x][y].isSouth();
		}
		if (orient != 2) {
			west = m.blocks[x][y].isWest();
		} 
		
		model.addAttribute("north", north);
		model.addAttribute("east", east);
		model.addAttribute("south", south);
		model.addAttribute("west", west);
		
		
		model.addAttribute("displaymaze", displaymaze);
		
		return "maze";
	}

}
