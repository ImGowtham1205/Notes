package com.example.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.notes.model.UserTask;
import com.example.notes.model.Users;
import com.example.notes.service.PasswordService;
import com.example.notes.service.TaskService;

//This controller is use for to return the login , welcome and access.jsp pages
@Controller
public class HomeController {
	
	//Field injection applied here
	   @Autowired
	   private PasswordService passservice;
	   @Autowired
	   private TaskService taskservice;
	
	 //This mapping is use for to display the login.jsp page
	@GetMapping("/")
	public String greet() {
		return "login";
	}
	
	//This mapping is use for to display the access.jsp page
	@GetMapping("/access")
	public String access() {
		return "access";
	}
	
	//This mapping is use for to display the welcome.jsp page
	@GetMapping("/welcome")
    public String welcome(Model m, Authentication authentication) {
		
		//Using Authentication Class object fetch the logged in user mail id
		String email = null;
		
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            m.addAttribute("Mail", email);
        }
        
        //Getting user detail by calling getUser() method by passing mail as argument
        Users user = passservice.getUser(email);
        
        //Getting user task by calling fetchUserTask() method by passing user object as argument
        List<UserTask> task= taskservice.fetchUserTask(user);
        
        //Send user task and username to welcome.jsp by using Model Interface
		m.addAttribute("task", task);
		m.addAttribute("name", user.getUsername());

        
        return "welcome";
    }
}
