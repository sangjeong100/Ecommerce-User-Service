package com.ecommerce.userservice.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.userservice.user.domain.dto.UserDto;
import com.ecommerce.userservice.user.domain.jpa.entity.UserEntity;
import com.ecommerce.userservice.user.domain.vo.RequestUserVo;
import com.ecommerce.userservice.user.domain.vo.ResponseUserVo;
import com.ecommerce.userservice.user.service.UserService;
import com.google.common.net.MediaType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 유저 컨트롤러
 * 
 * @author sjyu
 *
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

	private final Environment env;
	
	//@Value("${greeting.message}")
	//private String message;
	
	@GetMapping("/health_check")
	public String status(HttpServletRequest request) {
		return String.format("It's Working in User Service"
				+", port(local.server.port)=" + env.getProperty("local.server.port")
				+", port(server.port)=" + env.getProperty("server.port")
				+", with token secret=" + env.getProperty("token.secret")
				+", with token time=" + env.getProperty("token.expiration_time")
				);
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return env.getProperty("greeting.message");
	}
	
	private final UserService userService;
	
	/**
	 * 사용자 등록
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/users")
	public ResponseEntity<ResponseUserVo> createUser(@RequestBody RequestUserVo user) {
		
		ModelMapper mapper = new ModelMapper();
		
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(user, UserDto.class);
		userDto = userService.createUser(userDto);
		ResponseUserVo responseUserVo = mapper.map(userDto, ResponseUserVo.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUserVo);
		
	}
	
	/**
	 * 전체 유저 조회
	 * @return
	 */
	@GetMapping("/users")
	public ResponseEntity<List<ResponseUserVo>> getUserList(){
	
		Iterable<UserEntity> userList = userService.getUserByAll();
		
		List<ResponseUserVo> resultList = new ArrayList<>();
		
		userList.forEach(user -> {
			resultList.add(new ModelMapper().map(user, ResponseUserVo.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(resultList);
	}
	
	/**
	 * 유저정보 조회 by userid
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<ResponseUserVo> getUserByUserId(@PathVariable("userId") String userId){
		
		UserDto userDto = userService.getUserByUserId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ModelMapper().map(userDto, ResponseUserVo.class));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseUserVo> login(@RequestBody RequestUserVo userVo){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ModelMapper().map(new UserDto(), ResponseUserVo.class));
	}
	
}
