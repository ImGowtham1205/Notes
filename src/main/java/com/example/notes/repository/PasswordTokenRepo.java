package com.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.notes.model.PasswordResetToken;

@Repository
public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Integer>{
	PasswordResetToken findByToken(String token);
}
