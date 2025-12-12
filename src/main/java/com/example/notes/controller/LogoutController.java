package com.example.notes.controller;

import org.springframework.http.ResponseCookie;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

//This controller is use for to logout user session
@Controller
public class LogoutController {
	
	//This mapping is use for to logout user session
	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		
		//Remove cookie and SecurityContextHolder after that redirect to login page
		ResponseCookie cookie= ResponseCookie.from("jwt", "")
				.httpOnly(true)
				.maxAge(0)
				.build();
		
		response.addHeader("Set-Cookie", cookie.toString());
		
		SecurityContextHolder.clearContext();
		
		return "redirect:/";
	}
}
