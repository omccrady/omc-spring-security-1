package com.mccrady;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;



//select id, username,password,authorities from users;
//insert into users (id, username,password,authorities ) values (1, 'user', 'user'), ('admin', 'admin');
//delete from users where id = 1;
//update users set authorities='student:admin' where id=2;


@Data
//@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
	
	public User() {
	}
	
	private static final String AUTHORITIES_DELIMITER = "::";
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   
   private String username;

   private String password;
   
   private String authorities;
   

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(this.authorities.split(AUTHORITIES_DELIMITER))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
