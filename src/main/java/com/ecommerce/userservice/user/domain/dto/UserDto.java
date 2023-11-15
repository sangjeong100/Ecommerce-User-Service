package com.ecommerce.userservice.user.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.ecommerce.userservice.user.domain.vo.ResponseOrderVo;

import lombok.Data;

@Data
public class UserDto {

	private String email;
	private String password;
	private String name;
	private String userId;
	private LocalDate createDate;
	private String encryptedPwd;
	
	private List<ResponseOrderVo> orders;
}
