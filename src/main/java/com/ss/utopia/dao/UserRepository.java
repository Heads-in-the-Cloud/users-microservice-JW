package com.ss.utopia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ss.utopia.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value= "Select * from user where username= ?1", nativeQuery=true)
	public User findByUsername(String username);
}
