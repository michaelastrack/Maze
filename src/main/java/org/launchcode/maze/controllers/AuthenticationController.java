package org.launchcode.maze.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.maze.models.Maze;
import org.launchcode.maze.models.User;
import org.launchcode.maze.models.daos.MazeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController{
	
	List<Maze> mazes;
	
	@Autowired
	private MazeDao mazedao;
	
	@RequestMapping (value = "/", method = RequestMethod.GET)
	public String SlashPage () {
		
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		if (!User.isValidPassword(password) || !User.isValidUsername(username) || !password.equals(verify)) {
			model.addAttribute("username", username);
			if (!User.isValidUsername(username)) {
				String username_error = "Invalid Username.";
				model.addAttribute("username_error", username_error);
			}
			
			if (!User.isValidPassword(password)) {
				String password_error = "Invalid Password.";
				model.addAttribute("password_error", password_error);
			}
			if (!password.equals(verify)) {
				String verify_error = "Passwords Do Not Match.";
				model.addAttribute("verify_error", verify_error);
			}
			return "signup";
		}
		
		User newuser = new User (username, password);
		userdao.save(newuser);
		HttpSession thisSession = request.getSession();
		setUserInSession (thisSession, newuser);
		
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userdao.findByUsername(username);
		if (!user.isMatchingPassword(password)) {
			String error = "Incorrect Password.";
			model.addAttribute("error", error);
			model.addAttribute("username", username);
			return "login";
		}
		HttpSession thisSession = request.getSession();
		setUserInSession (thisSession, user);
		
		return "redirect:/main";
	}
	
	@RequestMapping (value = "/main", method = RequestMethod.GET)
	public String MainPage (Model model) {
		mazes = mazedao.findAll();
		model.addAttribute("mazes", mazes);
		return "main";
	}

}
