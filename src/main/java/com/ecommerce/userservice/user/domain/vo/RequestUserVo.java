package com.ecommerce.userservice.user.domain.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RequestUserVo {

	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email not be less than two characters")
	@Email
	private String email;

	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Name not be less than two characters")
	private String name;
	
	@NotNull(message = "Email cannot be null")
	@Size(min = 8, message = "Password must be equal or grater than 8 characters and less than 16 characters")
	private String pwd;
}