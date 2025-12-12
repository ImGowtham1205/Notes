package com.example.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.notes.model.Users;
import com.example.notes.repository.UserRepo;

@Service
public class DeleteUserService {
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private SimpleMailMessage msg;
	
	public void deleteUser(Users user) {
		userrepo.deleteById(user.getId());
		deleteAccountMail(user);
	}
	
	private void deleteAccountMail(Users user) {
		String subject = "Your Notes Application Account Has Been Deleted";
		final String from="notes.hub.service@gmail.com";
		String receiver = user.getEmail();
		String body = "Dear "+user.getUsername()+",\r\n"
				+ "\r\n"
				+ "We would like to inform you that your account associated with the email "+user.getEmail()+" has been successfully deleted from the Notes Application.\r\n"
				+ "\r\n"
				+ "As part of this process, all your associated data — including saved notes, tasks, and personal preferences — has been permanently removed from our system in accordance with our data retention policy.\r\n"
				+ "\r\n"
				+ "If you did not initiate this deletion request, please contact our support team immediately.\r\n"
				+ "\r\n"
				+ "Thank you for using Notes Application.\r\n"
				+ "We appreciate the time you spent with us.\r\n"
				+ "\r\n"
				+ "Warm regards,\r\n"
				+ "Notes Application Support Team\r\n"
				+ from;
		
		msg.setSubject(subject);
		msg.setText(body);
		msg.setTo(receiver);
		sender.send(msg);
	}
}
