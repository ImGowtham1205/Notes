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
	
	//filed injection applied here
	@Autowired
	private TaskRepo taskrepo;
	
	//This method is use to add task for the users
	public void addTask(UserTask usertask) {
		taskrepo.save(usertask);
	}
	
	//This method is use to update task for the users
	public void updateTask(UserTask usertask) {
		taskrepo.save(usertask);
	}
	
	//This method is use to delete task for the users
	public void deleteTask(List<Integer> ids) {
		taskrepo.deleteAllByIdInBatch(ids);
	}
	
	//This method is use to fetch the all tasks for user
	public List<UserTask>fetchUserTask(Users user){
		return taskrepo.findByUser(user);
	}
	
	//This method is use to fetch the selected task for user
	public Optional<UserTask>findTaskById(int id) {
		return taskrepo.findById(id);
	}
}
