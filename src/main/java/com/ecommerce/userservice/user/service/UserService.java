package com.ecommerce.userservice.user.service;

import com.ecommerce.userservice.user.domain.dto.UserDto;
import com.ecommerce.userservice.user.domain.jpa.entity.UserEntity;

public interface UserService {
	
	/* 유저 생성 */
	UserDto createUser(UserDto userDto);
	
	/* 유저정보 조회 */
	UserDto getUserByUserId(String userId);
	
	/* 유저리스트 조회 */
	Iterable<UserEntity> getUserByAll();
	
}
