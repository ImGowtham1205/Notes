package com.example.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager manager;
	
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