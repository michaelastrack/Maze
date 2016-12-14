package org.launchcode.maze.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.maze.models.User;
import org.launchcode.maze.models.daos.MazeDao;
import org.launchcode.maze.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
	
	public abstract class AbstractController {

		@Autowired
	    protected UserDao userdao;
		
		@Autowired
		protected MazeDao mazedao;

	    public static final String userSessionKey = "user_id";

	    protected User getUserFromSession(HttpSession session) {
	    	
	        Integer userId = (Integer) session.getAttribute(userSessionKey);
	        return userId == null ? null : userdao.findByUid(userId);
	    }
	    
	    protected void setUserInSession(HttpSession session, User user) {
	    	session.setAttribute(userSessionKey, user.getUid());
	    }
		
	}


