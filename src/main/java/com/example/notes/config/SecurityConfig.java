package com.example.notes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.notes.service.JwtFilter;


//This class is use to customize SpringBoot security
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService user;
	@Autowired
	JwtFilter filter;
	
	//This bean is use to customize SpringBoot security
	@Bean
	public SecurityFilterChain securityCongifure(HttpSecurity http) {
		
		return http
				.csrf(customizer->customizer.disable())
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.formLogin(AbstractHttpConfigurer::disable)
				.authenticationProvider(authentication())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.logout(AbstractHttpConfigurer::disable)
				.build();
	}
	
	//This bean is use to set PasswordEncorder to DaoAuthenticationProvider
	@Bean
	public PasswordEncoder encorder() {
		return new BCryptPasswordEncoder(12);
	}
	
	//This bean is responsible for validating users from DataBase
	@Bean
	public AuthenticationProvider authentication() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(user);
		provider.setPasswordEncoder(encorder());
		return provider;
	}
	
	/*This bean is use for validating users from database manually it takes the control from the 
	  Spring Security*/
	@Bean
	public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	//This bean is use to create a object for SimpleMailMessage Class
	@Bean
	public SimpleMailMessage message() {
		return new SimpleMailMessage();
	}
}
