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
      //DaoAuthenticationProvider provider = new DaoAuthenticationProvider();  // v3.0.0 to 3.5.0
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService); //v4.0.0 
      //provider.setUserDetailsService(userService);
      provider.setPasswordEncoder(passwordEncoder());
       return provider;
   }

	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("Creating BCryptPasswordEncoder bean");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		log.info("==== user encoded: " + encoder.encode("user"));
		log.info("==== admin encoded: " + encoder.encode("admin"));
		return encoder;
	}
	
	@Bean
	public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Configuring SecurityFilterChain");
		http
			.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
			.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(
               authorizeRequest -> authorizeRequest
                       .requestMatchers("/test/student").hasAuthority("student")
                       .requestMatchers("/test/admin").hasAuthority("admin")
                       .requestMatchers("/h2-console/**").permitAll()
                       .anyRequest().permitAll()
					)
					.formLogin(Customizer.withDefaults())
					.headers(headers -> headers //H2 Console needs this to work properly
							.frameOptions(frame -> frame
			                .sameOrigin())
			        )
					;
		
		return http.build();
	}
	
}
