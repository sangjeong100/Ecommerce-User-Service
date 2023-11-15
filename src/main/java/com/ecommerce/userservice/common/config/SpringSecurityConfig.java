package com.ecommerce.userservice.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import com.google.common.base.Supplier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 필수
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig{

	//private final BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests().antMatchers("/users/**").permitAll();
		/*
		 * http.authorizeHttpRequests((authorize) ->{ authorize .requestMatchers("/**")
		 * .access(this::hasIpAddress); })
		 */
		//http.headers().frameOptions().disable();
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 허용 ip대역 따로 처리 
	 * 
	 * @param authentication
	 * @param object
	 * @return
	 */
	private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
		
		String ALLOWED_IP_ADDRESS = "172.30.1.93";
	    String SUBNET = "/32";
	    IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);
		
        return new AuthorizationDecision(
                        !(authentication.get() instanceof AnonymousAuthenticationToken)
                                && ALLOWED_IP_ADDRESS_MATCHER.matches(object.getRequest()
                        ));
    }
	
}
