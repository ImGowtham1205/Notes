package com.example.notes.model;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class UserPrincipal implements UserDetails{

	private Users user;
	
	public UserPrincipal(Users user) {
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.emptyList();
	}

	@Override
	public @Nullable String getPassword() {		
		return user.getPass();
	}

	@Override
	public String getUsername() {	
		return user.getEmail();
	}
}
