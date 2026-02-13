# Spring Security Application

## Demonstrating securing rest endpoints using Spring Security and Users H2 or SQL Server Database.

* http://locahost:8080/test/home  
* http://locahost:8080/test/student  
* http://locahost:8080/test/admin
* http://locahost:8080/test/create  
* http://localhost:8080/h2-console  
  
Authour: Owen McCrady  
Email: owen.m.mccrady@outlook.com  
Date: 2026-02-13


### 1. Add Spring Security to App  
Add spring security to pom.xml
```
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
```
A default user 'user' is created and password generated in console on boot.  
Invoke in:  
> browser - authentication screen displayed:   
http://locahost:8080/test/home 
> curl -u user:8e0c45dc-d28f-4426-9cd9-8cc010428b80 http://locahost:8080/test/home
> Postman. Select Basic authentication and enter username and password.  
N.B: Authentication is enabled but not authorisation. So, any authenticated user can access both endpoints.  
  
### 2. Create Security Config for Authentication  
Add PasswordEncoder bean using BCryptPasswordEncoder:  
```
	@Bean
	public PasswordEncoder passwordEncoder() {  
		return new BCryptPasswordEncoder();  
	}
```	
Add UserDetailsService bean with some users:  
```
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {  
		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build();  
		UserDetails user = User.withUsername("user").password(encoder.encode("user")).roles("USER").build();  
		return new InMemoryUserDetailsManager(admin, user, ops);  
	}  
```
> curl -u user:user http://localhost:8080/test/student    
> curl -u admin:admin http://localhost:8080/test/admin    
N.B: Authentication is enabled but not authorisation. So, any authenticated user can access both endpoints.  
  
  
  
### 3. Create users with Rest endpoint  
Create users using Postman and 'create' endpoint:
```   
http://localhost:8081/test/create?username=owen&password=secret  
http://localhost:8081/test/create?username=admin&password=admin  
http://localhost:8081/test/create?username=super&password=super
```  
Update authorities using SQL:
```  
update users set authorities='student:admin' where username='owen';  
update users set authorities='admin' where username='admin';  
update users set authorities='student:admin' where username='super';  
```
TODO: Enhance the 'create' enpoint to accept authorities as well.
  
### 4. Add Security Filter Chain for Authorisation  
Authorise all requests to /test/home. Authorise ADMIN role to /test/admin.  
```
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
```

### 5. Test endpoint security
Test with browser, curl and Postman
```
> curl http://localhost:8080/test/home  
This is Home  
> curl -u owen:secret http://localhost:8080/test/student   
This is Student
> curl -u owen:secret http://localhost:8080/test/admin    
{"timestamp":"2026-02-07T14:08:58.854+00:00","status":403,"error":"Forbidden","message":"Forbidden","path":"/api/v1/admin"}  
> curl -u admin:admin http://localhost:8080/test/admin    
This is Admin
> curl -u super:super http://localhost:8080/test/student 
This is Student  
> curl -u super:super http://localhost:8080/test/admin  
This is Admin  
```
