package com.example.notes.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.notes.model.Users;
import com.example.notes.service.RegistrationService;

//This controller is use for to account registration 
@Controller
public class RegistrationController {

	private RegistrationService registerservice;
	private PasswordEncoder encoder;
	
	// Constructor injection applied here
	public RegistrationController(RegistrationService registerservice,PasswordEncoder encoder) {
		this.registerservice=registerservice;
		this.encoder=encoder;
	}
	
	//This mapping is use for to display the register.jsp page
	@GetMapping("/createuser")
	public String registerPage() {
		return "register";
	}
	
	//This mapping is use for to create a new user account
	@PostMapping("/register")
	public String register(@RequestParam("username") String username ,@RequestParam("email") String email,
			@RequestParam("password") String pass,Model m) {
		
		//Set user details and encode user password using BCrypt Algorithm
		Users user = new Users();
		user.setUsername(username);
		user.setPass(encoder.encode(pass));
		user.setEmail(email);
		
		/*It checks the entered mail is available in DataBase if true then sent server side message 
		 to the user or else create a account for the user and sent account creation mail to the 
		 registered mail*/
		if(registerservice.checkEmail(email)) {
			m.addAttribute("status","fail");
			return "register";
		}	
		
		registerservice.addUser(user);
		m.addAttribute("status","success");
		return "register";
	}
}
