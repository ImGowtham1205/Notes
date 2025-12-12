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
	
	public boolean verifyMail(String email) {
		Users user=userrepo.findByEmail(email);
		if(user !=null) {
			forgotPasswordMail(user);
			return true;
		}	
		else 
			return false;
	}

	public boolean verifyPassword(String password,Users user) {
		if(encorder.matches(password, user.getPass()))
			return true;
		else
			return false;
	}
	
	public PasswordResetToken checkToken(String token) {
		return passrepo.findByToken(token);
	}
	
	public Users getUser(String email) {
		return userrepo.findByEmail(email);
	}
	
	public void deleteToken(PasswordResetToken prt) {
		passrepo.delete(prt);
	}
	
	public void updatePassword(Users user) {
		userrepo.save(user);
	}
	
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
