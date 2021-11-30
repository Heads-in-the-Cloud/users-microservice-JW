package com.ss.utopia.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.dao.UserRepository;
import com.ss.utopia.dao.UserRoleRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserRoleRepository userRoleCheck;
	
	public Iterable<User>getUsers(){
		return userRepo.findAll();
	}
	
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	public ResponseEntity<User> getUserById(Integer id){
		Optional<User>user = userRepo.findById(id);
		if(user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(user.get());
	}
	
	public ResponseEntity<String> createUser(User user){
		Optional<UserRole> role = userRoleCheck.findById(user.getRole_id());
		if(role.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user role");
		}
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Post Succeeded");
	}
	
	public ResponseEntity<String> updateUser(Integer id, User user){
		Optional<User> userExist = userRepo.findById(id);
		if(userExist.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this id does not exist");
		}
//		if(user.getId() != id) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID's do not match.");
//
//		}
		Optional<UserRole> role = userRoleCheck.findById(user.getRole_id());
		if(role.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user role");
		}
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Update Succeeded");
	}
	
	public ResponseEntity<String> deleteUser(Integer id){
		Optional<User> userExist = userRepo.findById(id);
		if(userExist.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
		}
		userRepo.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
	}
}
