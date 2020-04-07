package com.example.EmergencyRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.EmergencyRoom.domain.UserDetailsServiceImpl;

//In this class we configure Spring Security

//First we use the annotation to enable Spring Security Web Support

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//We use our implementation of UserDetailsService
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	//We use BCrypt to encrypt passwords so they are not stored in plaintext
	//We are using 12 rounds for the hashing
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(12);
	}
	
	// We set here the config also to use HTTPS with Heroku
	// They provide the secure environment for apps on their service,
	// this is to handle the routing
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http
			.requiresChannel()
		    	.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
		    	.requiresSecure()
		    	.and()
			.authorizeRequests()
				.antMatchers("/css/**").permitAll()
				// The following endpoints only allowed for admins
				.antMatchers("/userList", "/updateUser/**", "/deleteUser/**", "/api/users", "/api/users/**").hasAuthority("ADMIN") 
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
}