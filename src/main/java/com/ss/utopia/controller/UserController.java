package com.ss.utopia.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import com.ss.utopia.config.JwtTokenUtil;
import com.ss.utopia.entity.JwtRequest;
import com.ss.utopia.entity.JwtResponse;
import com.ss.utopia.entity.User;
import com.ss.utopia.service.JWTUserDetailService;
import com.ss.utopia.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JWTUserDetailService userDetailService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		}
		catch(DisabledException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Disabled");		
		}
		catch(BadCredentialsException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");		
		}
		final UserDetails userDetails = userDetailService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	@GetMapping("/verify")
	public @ResponseBody ResponseEntity<String> getUserFromToken() {
		return ResponseEntity.status(HttpStatus.OK).body("Ok");
//		return userService.getUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
	}
	
	@GetMapping
	public @ResponseBody Iterable<User> getUsers() throws ClassNotFoundException, SQLException{
		return userService.getUsers();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<User> getUserById(@PathVariable Integer id){
		return userService.getUserById(id);
	}
	
	@PostMapping("/register")
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
