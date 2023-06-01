package com.samar.Springboot_CRUD.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfiguration {

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();
		auth.inMemoryAuthentication().passwordEncoder(encoder).withUser("user").password(encoder.encode("user123"))
				.roles("USER").and().withUser("admin").password(encoder.encode("admin123")).roles("USER", "ADMIN");

	}
	
	public void configure(HttpSecurity http)throws Exception {
		http.authorizeRequests().requestMatchers("/books").authenticated().requestMatchers("/books/**").hasRole("ADMIN").and().httpBasic().and().csrf().disable();

	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
