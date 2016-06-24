package com.clienteservidor.preciosclaros.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userService")
	UserDetailsService userDetailsService;

	@Override
	@Order(1)
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		/*
		http
		.antMatcher("/api_v1/**")                               
		.authorizeRequests()
			//.anyRequest().hasRole("ADMIN")
			.anyRequest().hasRole("USER")
			.and()
		.httpBasic();
		*/
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService);
			//.passwordEncoder(passwordEncoder());
			
			/*.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");*/
				
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}