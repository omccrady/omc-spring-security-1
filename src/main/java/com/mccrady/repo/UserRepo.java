package com.mccrady.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mccrady.model.User;

public interface UserRepo extends JpaRepository<User, Integer> { 
	
	User findByUsername(String username);

}
