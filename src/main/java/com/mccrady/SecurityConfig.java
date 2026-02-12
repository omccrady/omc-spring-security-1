package com.mccrady;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import lombok.extern.slf4j.Slf4j;

//https://www.geeksforgeeks.org/advance-java/implementing-database-authentication-and-authorization-with-spring-security-6/

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	@Autowired
   private UserService userService;
	
	@Bean
   public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setUserDetailsService(userService);
      provider.setPasswordEncoder(passwordEncoder());
       return provider;
   }

	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("Creating BCryptPasswordEncoder bean");
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//System.out.println("==== user encoded: " + encoder.encode("user"));
		//System.out.println("==== admin encoded: " + encoder.encode("admin"));
		//return encoder;
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Configuring SecurityFilterChain");
		http
			.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
			.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(
               authorizeRequest -> authorizeRequest
               //authorize -> authorize.requestMatchers("/").permitAll() 
               		  //.requestMatchers("/test/student").authenticated()
                       .requestMatchers("/test/student").hasAuthority("student")
                       .requestMatchers("/test/admin").hasAuthority("admin")
                       .anyRequest().permitAll()
					)
					.formLogin(Customizer.withDefaults());
		
		return http.build();
	}
	
}
