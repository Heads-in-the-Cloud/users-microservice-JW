package com.ss.utopia.controller;

import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.SQLException;

import com.ss.utopia.entity.User;
import com.ss.utopia.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping
	public @ResponseBody Iterable<User> getUsers() throws ClassNotFoundException, SQLException{
		return userService.getUsers();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<User> getUserById(@PathVariable Integer id){
		return userService.getUserById(id);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<String> addUser(@RequestBody User user){
		return userService.createUser(user);
	}
	
	@PutMapping
	public @ResponseBody ResponseEntity<String> updateUser(@RequestParam Integer id, @RequestBody User user){
		return userService.updateUser(id, user);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deleteUser(@PathVariable Integer id){
		return userService.deleteUser(id);
	}
}
