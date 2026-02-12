package com.mccrady;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class Controller {
	
	 @Autowired
    UserService userService;
	
	/*
	@GetMapping("/public")
	public String getPublic() {
		log.info("Running getPublic() endpoint");
		System.out.println("Running getPublic() endpoint");
		return "Hello from Public API!";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String getAdmin() {
		log.info("Running getAdmin() endpoint");
		System.out.println("Running getAdmin() endpoint");
		return "Hello from Admin API!";
	}
	
	@PreAuthorize("hasRole('OPS')")
	@GetMapping("/ops")
	public String opsTask() {
		System.out.println("Running opsTask() endpoint");
		return "Hello from Ops API!";
	}	

	@PreAuthorize("hasRole('ADMIN') or hasRole('OPS')")
	@GetMapping("/adminops")
	public String getAdminOrOps() {
		System.out.println("Running getAdminOrOps() endpoint");
		return "Hello from Admin Ops API!";
	}
	
	//https://www.baeldung.com/spring-security-jdbc-authentication
	@GetMapping("/principal")
   public Principal retrievePrincipal(Principal principal) {
		System.out.println("Running retrievePrincipal() endpoint");
      return principal;
   }
	*/
	
	
	@GetMapping("/home")
   public  String home(){
		log.info("Running home() endpoint");
      return "This is Home";
   }
   @GetMapping("/student")
   public  String student(){
   	log.info("Running student() endpoint");
       return "This is Student";
   }
   @GetMapping("/admin")
   public  String admin(){
   	log.info("Running admin() endpoint");
       return "This is Admin";
   }
   @PostMapping("/create")
   public String create(@RequestParam("username") String username, @RequestParam("password") String password){
   	log.info("Running create() endpoint with username: {}", username);
      return  userService.create(username, password);
   }	
	
}
