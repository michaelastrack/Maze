package org.launchcode.maze.controllers;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.maze.models.Block;
import org.launchcode.maze.models.Maze;
import org.launchcode.maze.models.daos.MazeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreateController {
	
	int size;
	Block [][] blocks;
	Maze m;
	int row;
	int col;
	String name;
	boolean start;
	boolean finish;
	String type;
	String message;
	int s;
	int f;
	int so;
	
	@Autowired
	private MazeDao mazedao;
	
	@RequestMapping (value = "newcreate", method = RequestMethod.POST)
	public String StartCreate () {
		return "newcreate";
	}
	
	@RequestMapping (value = "startcreate", method = RequestMethod.POST)
	public String StartCreate (HttpServletRequest request, Model model) {
		
		// Get initial parameters of the size, name, and starting direction of the maze
		size = Integer.parseInt(request.getParameter("size"));
		name = request.getParameter("name");
		so = Integer.parseInt(request.getParameter("start"));
		
		// Initialize a maze of the correct size composed of empty blocks
		row = 0;
		col = 0;
		blocks = new Block [size][size];
		m = new Maze(size, row, col, blocks, name, so);
		model.addAttribute("row", row);
		model.addAttribute("col", col);
		return "create";
	}
	
	@RequestMapping (value = "create", method = RequestMethod.POST)
	public String Create (HttpServletRequest request, Model model) {
		
		// get parameters from user input to create the correct block
		start = Boolean.parseBoolean(request.getParameter("start"));
		finish = Boolean.parseBoolean(request.getParameter("finish"));
		type = request.getParameter("type");
		m.blocks [row][col] = m.CreateBlock(type, start, finish);
			
		// Update the row and column location for the next block
		if (col == size - 1) {
			row++;
			col = 0;
			}
		else {
			col++;
		}
		
		// If all blocks have been created check for errors in the maze, or add the maze to the database
		if (row == size) {
			// Error checking to ensure that every maze has one and only one start and finish
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (m.blocks[i][j].isFinish()) {
						f++;
					}
					if (m.blocks[i][j].isStart()) {
						s++;
					}
				}
			}
			message = "";
			if (s == 0) {
				message += "Your maze doesn't have a start.  ";
			}
			if (f == 0) {
				message += "Your maze doesn't have a finish.  ";
			}
			if (s > 1) {
				message += "Your maze has more than one start";
			}
			if (f > 1) {
				message += "Your maze has more than one finish";
			}
			if (message != "") {
				model.addAttribute("message", message);
				return "createerror";
			}
			
			// If no errors finish the maze creation process and add it to the database
			mazedao.save(m);
			model.addAttribute("name", name);
			return "endcreate";
		}
		// Move onto the next block if not finished
		model.addAttribute("row", row);
		model.addAttribute("col", col);
				
		return "create";
		
	}

}
