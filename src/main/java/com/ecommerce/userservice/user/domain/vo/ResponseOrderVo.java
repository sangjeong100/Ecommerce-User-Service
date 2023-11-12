package com.ecommerce.userservice.user.domain.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ResponseOrderVo {

	private String productId;
	private String qty;
	private String unitPrice;
	private String totalPrice;
	private LocalDate createDate;
	
	private String orderId;
	
}
