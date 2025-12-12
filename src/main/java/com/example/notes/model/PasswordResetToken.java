package com.example.notes.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="password_token")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String token;
	private LocalDateTime expiryDate;
	
	@ManyToOne
	private Users user;
	
	 public boolean isExpired() {
	        return expiryDate.isBefore(LocalDateTime.now());
	    }

	 public int getId() {
		 return id;
	 }

	 public void setId(int id) {
		 this.id = id;
	 }

	 public String getToken() {
		 return token;
	 }

	 public void setToken(String token) {
		 this.token = token;
	 }

	 public LocalDateTime getExpiryDate() {
		 return expiryDate;
	 }

	 public void setExpiryDate(LocalDateTime expiryDate) {
		 this.expiryDate = expiryDate;
	 }

	 public Users getUser() {
		 return user;
	 }

	 public void setUser(Users user) {
		 this.user = user;
	 }
}
