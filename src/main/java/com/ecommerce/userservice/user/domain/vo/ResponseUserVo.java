package com.ecommerce.userservice.user.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // null값 데이터는 버리고 전달 
public class ResponseUserVo {

	private String email;
	private String name;
	private String userId;
	
	private List<ResponseOrderVo> orders;
}
