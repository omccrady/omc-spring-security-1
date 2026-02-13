package com.mccrady.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mccrady.model.User;
import com.mccrady.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {
	
	@Autowired private UserRepo userRepo; 
	

	 public UserDetails loadUserByUsername(String username) { 
		log.info("Loading user by username: {}", username);
		
		UserDetails userDetails = userRepo.findByUsername(username);
		if (userDetails == null) {
			log.warn("User not found: {}", username);
			throw new RuntimeException("User not found");
		}
		//return userRepo.findByUsername(username);
		return userDetails;
	}
	
	public String create(String username, String password) {
      // Encodes the password and creates a new User object
		/*
      User user = User.builder()
              .username(username)
              .password(new BCryptPasswordEncoder().encode(password)) // Encrypts the password
              .authorities("student") // Assigns default authority
              .build();
     */
      User user1 = new User();
      user1.setUsername(username);
      user1.setPassword(new BCryptPasswordEncoder().encode(password));
      user1.setAuthorities("student");
      
      // Saves the new user to the database
      userRepo.save(user1);
      
      return "Create Successfully !"; // Returns a success message
  }

	/*
	@Override
	public UserDetails updatePassword(UserDetails user, @Nullable String newPassword) {
		// TODO Auto-generated method stub
		return null;
	}	*/

}
