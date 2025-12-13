package com.example.notes.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.Users;
import com.example.notes.repository.PasswordTokenRepo;
import com.example.notes.repository.UserRepo;

@Service
public class PasswordService {
	
	// Constructor injection applied here
	public PasswordService(JavaMailSender sender, PasswordTokenRepo passrepo, SimpleMailMessage msg, UserRepo userrepo,
			PasswordEncoder encorder) {
		this.sender = sender;
		this.passrepo = passrepo;
		this.msg = msg;
		this.userrepo = userrepo;
		this.encorder = encorder;
	}
	
	private JavaMailSender sender;	
	private PasswordTokenRepo passrepo;	
	private SimpleMailMessage msg;
	private UserRepo userrepo;
	private PasswordEncoder encorder;
	
	/*This method checks the entered email is available in database if 
	 then sent forgot password to the entered mail*/
	public boolean verifyMail(String email) {
		Users user=userrepo.findByEmail(email);
		if(user !=null) {
			forgotPasswordMail(user);
			return true;
		}	
		else 
			return false;
	}

	//This method checks the current password of the user is valid or not 
	public boolean verifyPassword(String password,Users user) {
		if(encorder.matches(password, user.getPass()))
			return true;
		else
			return false;
	}
	
	//This method is use for to fetch the password reset token details
	public PasswordResetToken checkToken(String token) {
		return passrepo.findByToken(token);
	}
	
	//This method is use for to fetch the user details
	public Users getUser(String email) {
		return userrepo.findByEmail(email);
	}
	
	//This method is use to delete password reset token from the database
	public void deleteToken(PasswordResetToken prt) {
		passrepo.delete(prt);
	}
	
	//This method is use to update the user password
	public void updatePassword(Users user) {
		userrepo.save(user);
	}
	
	//This method is use to sent forgot password mail to registered user mail
	private void forgotPasswordMail(Users user) {
		
		String token=UUID.randomUUID().toString();
		PasswordResetToken prt = new PasswordResetToken();
		prt.setToken(token);
		prt.setUser(user);
		prt.setExpiryDate(LocalDateTime.now().plusMinutes(15));
		passrepo.save(prt);
		
		final String from="notes.hub.service@gmail.com";
		String receiver=user.getEmail();
		final String subject="Reset Your Notes Portal Password";
		String url="http://localhost:8080/resetpassword?token="+token;
		String body="Dear "+user.getUsername()+",\r\n"
				+ "\r\n"
				+ "We received a request to reset the password for your Notes Portal account.\r\n"
				+ "If you initiated this request, please click the link below to set a new password:\r\n"
				+ "\r\n"
				+ "Reset Password Link:\r\n"
				+ url+"\r\n"
				+ "\r\n"
				+ "For security reasons, this link will expire in 15 minutes.\r\n"
				+ "If you did not request a password reset, please ignore this email. Your account will remain secure.\r\n"
				+ "\r\n"
				+ "If you need further assistance, feel free to contact our support team.\r\n"
				+ "\r\n"
				+ "Warm regards,\r\n"
				+ "Notes Portal Support Team\r\n"
				+ from;
		
		msg.setTo(receiver);
		msg.setSubject(subject);
		msg.setText(body);
		sender.send(msg);
	}
}
