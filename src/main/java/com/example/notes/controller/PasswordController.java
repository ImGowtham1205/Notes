package com.example.notes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.Users;
import com.example.notes.service.PasswordService;

//This controller is use for to manage password activity
@Controller
public class PasswordController {
	
	private PasswordService passservice;
	private PasswordEncoder encorder;

	// Constructor injection applied here
	public PasswordController(PasswordService passservice,PasswordEncoder encorder) {
		this.passservice=passservice;
		this.encorder=encorder;
	}
	
	//This mapping is use for to display the forgot.jsp page
	@GetMapping("forgot")
	public String forgotPassword() {
		return "forgot";
	}
	
	//This mapping is use for sent mail for forgot password mail to the requested users
	@PostMapping("/reset")
	public String reset(@RequestParam("email") String email,Model m) {
		
		/*It checks the entered mail is available in database if true then sent the mail to the user
		 or else it sent the server side message to the user mail not found*/
		if(passservice.verifyMail(email)) {
			m.addAttribute("status", "success");			
			return "forgot";
		}
		else {
			m.addAttribute("status", "fail");
			return "forgot";
		}
	}
	
	//This mapping is use for to display the resetpassword.jsp page
	@GetMapping("/resetpassword")
	public String showResetPasswordPage(@RequestParam("token") String token,Model m) {
		m.addAttribute("token",token);
		return "resetpassword";
	}
	
	//This mapping is use for set new password 
	@PostMapping("/forgotpassword")
	public String resetPassword(@RequestParam("newPassword") String pass,@RequestParam("token") String token
			,Model m) {
		PasswordResetToken prt = new PasswordResetToken();
		
		//Getting details about token by calling checkToken() Method
		prt=passservice.checkToken(token);
		
		/*It checks the object is null or the token is expired if true then return server side message 
		 to the client or else set the new password to the user*/
		if(prt == null || prt.isExpired()) {
			m.addAttribute("status", "fail");
			return "resetpassword";
		}
		
		//Getting user detail by calling getUser() Method
		Users user=prt.getUser();
		
		//Set the new password to the user and encode using BCrypt Algorithm
		user.setPass(encorder.encode(pass));
		
		//Update the new password to the user by calling updatePassword() Method
		passservice.updatePassword(user);
		
		//After update the password delete the token from the database by calling deleteToken() Method
		passservice.deleteToken(prt);
		
		m.addAttribute("status", "success");
		return "resetpassword";
	}
	
	//This mapping is use for to display the changepassword.jsp page
	 @GetMapping("/showchangepassword")
	    public String showChangePasswordpage(Model m, Authentication authentication) {

		    //Using Authentication Class object fetch the logged in user mail id
		 	String email=null;		 
	        if (authentication != null && authentication.isAuthenticated()) {
	            email = authentication.getName();
	            m.addAttribute("Mail", email);
	        }
	        
	        //Getting user detail by calling getUser() method by passing mail as argument
	        Users user = passservice.getUser(email);
	        
	        //Send username to changepassword.jsp by using Model Interface
	        m.addAttribute("name", user.getUsername());
	        return "changepassword";
	    }
	
	//This mapping is use for update password to user 
	 @PostMapping("/changepassword")
	 public String changepassword(@RequestParam("currentPassword") String currentpass,
			 @RequestParam("newPassword") String newpass,Authentication authentication,Model m) { 
		 
		 //Using Authentication Class object fetch the logged in user mail id
		 String email=null;		 
		 if(authentication != null && authentication.isAuthenticated()) {
			 email = authentication.getName();
			 m.addAttribute("Mail", email);
		 }
		
		//Getting user detail by calling getUser() method by passing mail as argument
		Users user= passservice.getUser(email);
		
		/*It checks the current password of user if true then update user password
		 or else it send the sever side message to user as current password doesn't match*/
		if(passservice.verifyPassword(currentpass, user)) {
			
			//Set the new password to the user and encode using BCrypt Algorithm
			user.setPass(encorder.encode(newpass));
			
			//Update the new password to the user by calling updatePassword() Method
			passservice.updatePassword(user);
			m.addAttribute("status","success");
		}	
		else 
			 m.addAttribute("status","fail");
		
		//Send username to changepassword.jsp by using Model Interface
		 m.addAttribute("name", user.getUsername());
		 return "changepassword";
	 }
}
