package com.ss.utopia.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ss.utopia.dao.UserRepository;

@Service
public class JWTUserDetailService implements UserDetailsService{
	
	@Autowired 
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.ss.utopia.entity.User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		String encodePass = bcryptEncoder.encode(user.getPassword());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), encodePass,
				new ArrayList<>());
	}
}
