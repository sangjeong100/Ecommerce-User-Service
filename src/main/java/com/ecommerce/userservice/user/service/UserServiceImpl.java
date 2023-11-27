package com.ecommerce.userservice.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.UnavailableException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.userservice.user.client.OrderServiceClient;
import com.ecommerce.userservice.user.domain.dto.UserDto;
import com.ecommerce.userservice.user.domain.jpa.entity.UserEntity;
import com.ecommerce.userservice.user.domain.jpa.repo.UserRepo;
import com.ecommerce.userservice.user.domain.vo.ResponseOrderVo;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	
	
	private final Environment env;
	private final UserRepo userRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final RestTemplate restTemplate;
	private final OrderServiceClient orderServiceClient;
	
	/**
	 * 유저 생성 
	 */
	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		
		/* user Dto -> userEntity 변환  */
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));
		userRepo.save(userEntity);
		
		return userDto;
	}

	/**
	 * 유저 조회 by userid
	 */
	@Override
	public UserDto getUserByUserId(String userId) {
		
		UserEntity userEntity = userRepo.findByUserId(userId);
		
		if(null == userEntity) {
			throw new UsernameNotFoundException("User Name Not Found");
		}
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
//		String orderUrl = String.format(env.getProperty("order-service.url"), userId);
//		
//		ResponseEntity<List<ResponseOrderVo>> orderListResponse = 
//				restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//						new ParameterizedTypeReference<List<ResponseOrderVo>>() {
//						});
//		
//		List<ResponseOrderVo> orderList = orderListResponse.getBody();
		
		List<ResponseOrderVo> orderList = new ArrayList<ResponseOrderVo>();
		
		// try catch 사
//		try {
//			orderList = orderServiceClient.getOrders(userId);
//		} catch(FeignClientException e) {
//			log.error(e.getMessage());
//		}
		
		//feign decoder 사용시 
		orderList = orderServiceClient.getOrders(userId);
		
		userDto.setOrders(orderList);
		return userDto;
	}

	/**
	 * 유저 전체 조회
	 */
	@Override
	public Iterable<UserEntity> getUserByAll() {
		return userRepo.findAll();
	}

	/**
	 * email로 유저 조회 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepo.findByEmail(username);
		
		if(null == userEntity) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPwd()
				, true, true, true, true, new ArrayList<>());
		
		
	}

	
}
