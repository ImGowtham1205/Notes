package com.example.notes.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.notes.service.AuthenticationService;
import com.example.notes.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;

//This Controller is use for to checks the user credentials
@Controller
public class LoginController {
	
	private AuthenticationService authservice;	
	private JwtService jwtservice;
	
	// Constructor injection applied here
	public LoginController(AuthenticationService authservice,JwtService jwtservice) {
		this.authservice=authservice;
		this.jwtservice=jwtservice;
	}
	
	//This mapping is use for to checks the user credentials
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,@RequestParam("password") String password
			,Model m,HttpServletResponse response) {
		
		//Checks the user credentials by calling verify() method
		String status=authservice.verify(email, password);
		
		/*It Checks if status variable contains success then generate a jwt token for user 
		 and redirect to welcome mapping if not then it display the login page*/
		if(status.equals("Success")) {	
			
			//Generate jwt token by calling generateToken() method
			String token=jwtservice.generateToken(email);
			
			//Set jwt token to the cookie
			ResponseCookie cookie=ResponseCookie.from("jwt",token)
					.httpOnly(true)
					.path("/")
					.maxAge(30*60)
					.build();
			response.addHeader("Set-Cookie", cookie.toString());
			
			m.addAttribute("Mail", email);		
			m.addAttribute("token", token);
			return "redirect:/welcome";
		}
			
		else {
			m.addAttribute("status","fail");
			return "login";
		}
			
	}
}
