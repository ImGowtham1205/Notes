package com.example.notes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.notes.model.UserTask;
import com.example.notes.model.Users;
import com.example.notes.service.PasswordService;
import com.example.notes.service.TaskService;

//This controller is use for to manage task activity
@Controller
public class TaskController {
	   
	   private PasswordService passservice;
	   private TaskService taskservice;
	
	   // Constructor injection applied here
	   public TaskController(PasswordService passservice,TaskService taskservice) {
		   this.passservice=passservice;
		   this.taskservice=taskservice;
	   }
	
	 //This mapping is use for to display the addtask.jsp page
    @GetMapping("/addtask")
    public String addTask(Model m, Authentication authentication) {
    	
    	//Using Authentication Class object fetch the logged in user mail id
    	String email=null;  	
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            m.addAttribute("Mail", email);
        } 
        return "addtask";
    }
    
    //This mapping is use for to add task for user
    @PostMapping("/newtask")
    public String newTask(@RequestParam("title") String title,
    		@RequestParam("description") String description,@RequestParam("mail") String mail,
    		Model m) {
    	m.addAttribute("Mail", mail);
    	if(title == null || description == null)
    		return "addtask";
    	
    	else {
    		//Getting user detail by calling getUser() method by passing mail as argument
    		Users user = passservice.getUser(mail);
    		
    		UserTask usertask = new UserTask();
    		usertask.setTitle(title);
    		usertask.setDescription(description);
    		usertask.setCreated_at(LocalDateTime.now());
    		usertask.setUser(user);
    		taskservice.addTask(usertask);
    		
    		return "redirect:/welcome";   
    	}
  }
   
   //This mapping is use for to view page for update the selected task
    @GetMapping("/edittask/{taskId}")
   public String editTask(@PathVariable int taskId,Model m, Authentication authentication) {
    
    	//Using Authentication Class object fetch the logged in user mail id
    	String mail=null;	
    	if(authentication != null && authentication.isAuthenticated()) {
    		mail = authentication.getName();
    		m.addAttribute("Mail", mail);
    	}
    	
    	//getting selected task details by calling findTaskById() Method
    	UserTask task = taskservice.findTaskById(taskId).orElse(null);
    	
    	if(task == null)
    		return "welcome";
    	
    	int id = task.getTask_id();
    	String title = task.getTitle();
    	String description = task.getDescription();
    	
    	m.addAttribute("id", id);
    	m.addAttribute("title", title);
    	m.addAttribute("description", description);
    	m.addAttribute("task", task);
	   return "updatetask";
   }
    
  //This mapping is use for to update the selected task
    @PostMapping("edittask/updatetask") 
    public String updateTask(@RequestParam("title") String title,
    		@RequestParam("description") String description,@RequestParam("taskId") int taskid) {
    	UserTask task =taskservice.findTaskById(taskid).orElse(null);
    	task.setTitle(title);
    	task.setDescription(description);
    	taskservice.updateTask(task);
    	return "redirect:/welcome";
    }
    
  //This mapping is use for to delete the selected tasks
    @PostMapping("deletetask")
    public String deleteTask(@RequestParam("taskids") List<Integer> ids) {
    	taskservice.deleteTask(ids);
    	return "redirect:/welcome";
    }
}
