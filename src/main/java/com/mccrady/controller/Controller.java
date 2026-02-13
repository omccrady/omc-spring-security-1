package com.mccrady.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mccrady.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RestController
@RequestMapping("/test")
public class Controller {

	@Autowired
	UserService userService;

	@Autowired
	private Environment env;

	@Value("${com.mccrady.info2}")
	private String info2;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@GetMapping("/home")
	public String home() {
		log.info("Running home() endpoint");

		log.info("Environment property com.mccrady.info: {}", env.getProperty("com.mccrady.info"));
		log.info("Environment property com.mccrady.info2: {}", info2);
		log.info("Active Spring profile: {}", activeProfile);
		
		log.info("Authenticated username: {}", SecurityContextHolder.getContext().getAuthentication().getName());		
		log.info("Authenticated authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		log.info("Authenticated principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());		
		log.info("Authenticated credentials: {}", SecurityContextHolder.getContext().getAuthentication().getCredentials());		
		log.info("Authenticated details: {}", SecurityContextHolder.getContext().getAuthentication().getDetails());

		
		
		return "This is Home";
	}

	@GetMapping("/student")
	public String student() {
		log.info("Running student() endpoint");
		log.info("Authenticated username: {}", SecurityContextHolder.getContext().getAuthentication().getName());		
		log.info("Authenticated authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		log.info("Authenticated principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());		
		log.info("Authenticated credentials: {}", SecurityContextHolder.getContext().getAuthentication().getCredentials());		
		log.info("Authenticated details: {}", SecurityContextHolder.getContext().getAuthentication().getDetails());
		
		return "This is Student";
	}

	@GetMapping("/admin")
	public String admin() {
		log.info("Running admin() endpoint");
		log.info("Authenticated username: {}", SecurityContextHolder.getContext().getAuthentication().getName());		
		log.info("Authenticated authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		log.info("Authenticated principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());		
		log.info("Authenticated credentials: {}", SecurityContextHolder.getContext().getAuthentication().getCredentials());		
		log.info("Authenticated details: {}", SecurityContextHolder.getContext().getAuthentication().getDetails());

		return "This is Admin";
	}

	@PostMapping("/create")
	public String create(@RequestParam("username") String username, @RequestParam("password") String password) {
		log.info("Running create() endpoint with username: {}", username);
		log.info("Authenticated username: {}", SecurityContextHolder.getContext().getAuthentication().getName());		
		log.info("Authenticated authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		log.info("Authenticated principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());		
		log.info("Authenticated credentials: {}", SecurityContextHolder.getContext().getAuthentication().getCredentials());		
		log.info("Authenticated details: {}", SecurityContextHolder.getContext().getAuthentication().getDetails());
		return userService.create(username, password);
	}

}
