package com.ecommerce.userservice.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 필수
//@EnableWebSecurity
@Configuration
public class SpringSecurityConfig{

	//private final BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests().antMatchers("/actuator/**").permitAll();
		http.authorizeHttpRequests().antMatchers("/users/**").permitAll();
		//http.headers().frameOptions().disable();
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}
