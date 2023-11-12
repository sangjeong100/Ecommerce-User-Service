package com.ecommerce.userservice.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.userservice.user.domain.dto.UserDto;
import com.ecommerce.userservice.user.domain.jpa.entity.UserEntity;
import com.ecommerce.userservice.user.domain.jpa.repo.UserRepo;
import com.ecommerce.userservice.user.domain.vo.ResponseOrderVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	
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
		userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
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
			throw new UsernameNotFoundException("User Not Found");
		}
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		List<ResponseOrderVo> orderList = new ArrayList<>();
		
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

	
}
