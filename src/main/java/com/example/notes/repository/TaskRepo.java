package com.example.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.notes.model.UserTask;
import com.example.notes.model.Users;

@Repository
public interface TaskRepo extends JpaRepository<UserTask, Integer>{
	
	List<UserTask> findByUser(Users user);
}
