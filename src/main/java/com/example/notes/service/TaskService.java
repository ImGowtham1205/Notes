package com.example.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.notes.model.UserTask;
import com.example.notes.model.Users;
import com.example.notes.repository.TaskRepo;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepo taskrepo;
	
	public void addTask(UserTask usertask) {
		taskrepo.save(usertask);
	}
	
	public void updateTask(UserTask usertask) {
		taskrepo.save(usertask);
	}
	
	public void deleteTask(List<Integer> ids) {
		taskrepo.deleteAllByIdInBatch(ids);
	}
	
	public List<UserTask>fetchUserTask(Users user){
		return taskrepo.findByUser(user);
	}
	
	public Optional<UserTask>findTaskById(int id) {
		return taskrepo.findById(id);
	}
}
