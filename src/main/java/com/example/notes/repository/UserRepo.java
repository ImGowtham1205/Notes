package com.example.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.notes.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>{

	Users findByEmail(String email);
	
	@Query("select u.email from Users u")
	List<String> fetchEmail();
}
