package com.example.notes.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private AuthenticationManager manager;
	
	// Constructor injection applied here
	public AuthenticationService(AuthenticationManager manager) {
		this.manager =manager;
	}
	
	//This method checks the entered user credentials is valid or not
	public String verify(String email,String password) {
		try {
		Authentication auth=
				manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return auth.isAuthenticated() ? "Success" : "fail";		
	}
	catch(AuthenticationException e) {
		return "fail";
	}
}
}