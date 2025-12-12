package com.example.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.notes.model.Users;
import com.example.notes.repository.UserRepo;

@Service
public class RegistrationService {

	@Autowired
	private UserRepo repo;	
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private SimpleMailMessage msg;
	
	public void addUser(Users user) {
		repo.save(user);
		registerMailToUsers(user);
	}
	
	public boolean checkEmail(String mail) {
		List<String> emails=repo.fetchEmail();
		for(String email :emails) {
			if(mail.equals(email))
				return true;
		}
		return false;
	}
	
	private void registerMailToUsers(Users user) {
		String subject="Welcome to Notes Portal – Your Account Has Been Successfully Created";
		String support="notes.hub.service@gmail.com";
		String body="Dear "+user.getUsername()+",\r\n"
				+ "\r\n"
				+ "We are pleased to inform you that your account has been successfully created in the Notes Portal application. You can now log in and access all available features, including uploading notes, viewing study materials, and managing your personal dashboard.\r\n"
				+ "\r\n"
				+ "Account Details:\r\n"
				+ "\r\n"
				+ "Username: "+user.getUsername()+"\r\n"
				+ "\r\n"
				+ "Registered Email: "+user.getEmail()+"\r\n"
				+ "\r\n"
				+ "If you did not request this account creation, please contact our support team immediately.\r\n"
				+ "\r\n"
				+ "Thank you for registering with us.\r\n"
				+ "We look forward to providing you with a seamless notes-sharing experience.\r\n"
				+ "\r\n"
				+ "Warm regards,\r\n"
				+ "Notes Portal Team\r\n"
				+ support;
		msg.setTo(user.getEmail());
		msg.setSubject(subject);
		msg.setText(body);
		sender.send(msg);
	}
}
