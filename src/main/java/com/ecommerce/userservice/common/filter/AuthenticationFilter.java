package com.ecommerce.userservice.common.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.userservice.user.domain.vo.RequestUserVo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	/**
	 * 인증시도 필터 
	 *
	 * 1. 사용자 로그인, 패스워드를 인증매니저에게 인증 요청
	 * 2. 결과반환
	 * 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		
		try {
			RequestUserVo creds = new ObjectMapper().readValue(request.getInputStream(), RequestUserVo.class);
			
			// 인증 매니저에게 유저 토큰 인증 요청 및 결과 반환
			return getAuthenticationManager().authenticate( 
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(), 
							new ArrayList<>()
					)
			);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	/**
	 * 인증 성공 후 로직
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
	}

	
	

}
