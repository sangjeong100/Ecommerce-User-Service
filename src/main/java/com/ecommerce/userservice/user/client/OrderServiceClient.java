package com.ecommerce.userservice.user.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.userservice.user.domain.vo.ResponseOrderVo;

@FeignClient("order-service")
public interface OrderServiceClient {

	@GetMapping("/order-service/{userId}/orders1")
	List<ResponseOrderVo> getOrders(@PathVariable String userId);
	
}
