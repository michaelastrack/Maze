package org.launchcode.maze.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.maze.models.Block;
import org.launchcode.maze.models.Maze;
import org.launchcode.maze.models.User;
import org.launchcode.maze.models.daos.MazeDao;
import org.launchcode.maze.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MazeController extends AbstractController{
	
	int s;
	int size;
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
	long start;
	long time;
	List<Maze> mazes;
	String name;
	User user;
	HttpSession thisSession;
	String [][] path;
	
	@Autowired
	private MazeDao mazedao;
	
	@Autowired
	private UserDao userdao;
	
	@RequestMapping (value = "/start", method = RequestMethod.POST)
	public String StartMaze (HttpServletRequest request, Model model) {
		
		name = request.getParameter("choice");
		m = mazedao.findByName(name);
		int [][]intblock = m.getIntblock();
		blocks = Maze.InttoBlock(intblock);
		size = m.getSize();
		path = new String [size][size];
		m.initPath(blocks, path);
		displaymaze = m.DisplayPath(path);
		orient = m.getStartorient();
		for (int i = 0; i < m.getSize(); i++) {
			for (int j = 0; j < m.getSize(); j++) {
				if (blocks[i][j].isStart()) {
					m.setXpos(i);
					m.setYpos(j);
				}
			}
		x = m.getXpos();
		y = m.getYpos();
		
		if (orient != 3) {
			north = blocks[x][y].isNorth();
		}
		if (orient != 4) {
			east = blocks[x][y].isEast();
		}
		if (orient != 1) {
			south = blocks[x][y].isSouth();
		}
		if (orient != 2) {
			west = blocks[x][y].isWest();
		} 
		
		model.addAttribute("north", north);
		model.addAttribute("east", east);
		model.addAttribute("south", south);
		model.addAttribute("west", west);
		
		
		model.addAttribute("displaymaze", displaymaze);

		}
		
		start = System.currentTimeMillis();
		return "maze";
	}
	
	@RequestMapping (value = "/maze", method = RequestMethod.POST)
	public String PlayMaze (HttpServletRequest request, Model model) {
		orient = Integer.parseInt(request.getParameter("direction"));
		m.Move(orient, path);
		x=m.getXpos();
		y=m.getYpos();
		displaymaze = m.DisplayPath(path);
		
		if (blocks[x][y].isDeadend()) {
			thisSession = request.getSession();
			user = getUserFromSession (thisSession);
			user.fail();
			m.fail();
			mazedao.save(m);
			userdao.save(user);
			message = "You have run into a Dead End.  Game Over.";
			message += "  On average people were able to complete this maze " + m.percentage() + " percent of the time.  ";
			message += user.getUsername() + " , you have successfully completed mazes on " + user.percentage() + " percent of your sttempts.";
			model.addAttribute("message", message);
			return "GameOver";
		}
		if (blocks[x][y].isStart()) {
			thisSession = request.getSession();
			user = getUserFromSession (thisSession);
			user.fail();
			m.fail();
			mazedao.save(m);
			userdao.save(user);
			message = "You have returned back to the Start.  Game Over.";
			message += "  On average people were able to complete this maze " + m.percentage() + " percent of the time.  ";
			message += user.getUsername() + " , you have successfully completed mazes on " + user.percentage() + " percent of your sttempts.";
			model.addAttribute("message", message);
			return "GameOver";
		}
		if (blocks[x][y].isFinish()) {
			thisSession = request.getSession();
			user = getUserFromSession (thisSession);
			user.complete();
			time = (System.currentTimeMillis() - start) / 1000;
			m.complete(time);
			mazedao.save(m);
			userdao.save(user);
			message = "Congratulations, you have made it to the end of the maze!  It took you " + time + " seconds to complete the maze.";
			message += "  The Average person was able to complete this same maze in " + m.getAvgtime() + " seconds.";
			message += "  On average people have successfully completed this maze " + m.percentage() + " percent of the time.";
			message += user.getUsername() + " , you have successfully completed mazes on " + user.percentage() + " percent of your sttempts.";
			model.addAttribute("message", message);
			return "GameOver";
		}
		
		// Checking the conditions for whether or not buttons need to be displayed
		north = false;
		east = false;
		south = false;
		west = false;
		if (orient != 3) {
			north = blocks[x][y].isNorth();
		}
		if (orient != 4) {
			east = blocks[x][y].isEast();
		}
		if (orient != 1) {
			south = blocks[x][y].isSouth();
		}
		if (orient != 2) {
			west = blocks[x][y].isWest();
		} 
		
		model.addAttribute("north", north);
		model.addAttribute("east", east);
		model.addAttribute("south", south);
		model.addAttribute("west", west);
		
		
		model.addAttribute("displaymaze", displaymaze);
		
		return "maze";
	}

}
