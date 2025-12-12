package com.example.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.notes.model.UserPrincipal;
import com.example.notes.model.Users;
import com.example.notes.repository.UserRepo;

@Service
public class MyUsersService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Users user=repo.findByEmail(email);
		if(user==null)
			throw new UsernameNotFoundException("User Not Found");
		
		return new UserPrincipal(user);
	}

}
