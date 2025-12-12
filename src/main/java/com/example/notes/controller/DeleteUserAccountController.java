package com.example.notes.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.notes.model.Users;
import com.example.notes.service.DeleteUserService;
import com.example.notes.service.PasswordService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//This controller is use for to delete user account
@Controller
public class DeleteUserAccountController {
	
	private PasswordService passservice;
	private DeleteUserService deleteuserservice;
	
	// Constructor injection applied here
	public DeleteUserAccountController(PasswordService passservice, DeleteUserService deleteuserservice) {
		this.passservice = passservice;
		this.deleteuserservice = deleteuserservice;
	}
	
	//This mapping is use for to display the deleteuser.jsp page
	@GetMapping("/deleteuser")
	public String showDeleteUser(Authentication authentication,Model m) {
		
		//Using Authentication Class object fetch the logged in user mail id
		String mail=null;
		if(authentication != null && authentication.isAuthenticated()) {
			mail = authentication.getName();
			m.addAttribute("Mail", mail);
		}
		
		//Getting user detail by calling getUser() method by passing mail as argument
		Users user = passservice.getUser(mail);
		m.addAttribute("name", user.getUsername());
		return "deleteuser";
	}
	
	//This mapping is use for to delete the user account from the database
	@PostMapping("delete-user")
	public String deleteUser(@RequestParam("password") String password,Model m,
		HttpServletResponse response,HttpServletRequest request ,Authentication authentication) {
		
		//Using Authentication Class object fetch the logged in user mail id
		String mail=null;
		if(authentication != null && authentication.isAuthenticated()) {
			mail = authentication.getName();
			m.addAttribute("Mail", mail);
		}
		
		//Getting user detail by calling getUser() method by passing mail as argument
		Users user = passservice.getUser(mail);
		
		/*Checks user enter their correct password or not by calling verifyPassword() Method
		 if true then remove cookies and delete the user account 
		 or else send the server side message to the client */
		if(passservice.verifyPassword(password, user)) {
			deleteuserservice.deleteUser(user);
			ResponseCookie cookie= ResponseCookie.from("jwt", "")
					.httpOnly(true)
					.maxAge(0)
					.build();
			
			response.addHeader("Set-Cookie", cookie.toString());
			
			SecurityContextHolder.clearContext();
			return "redirect:/";
		}	
		else {
			m.addAttribute("status","fail");
			m.addAttribute("Mail",mail);
			m.addAttribute("name",user.getUsername());
			return "deleteuser";
		}
	}
}
