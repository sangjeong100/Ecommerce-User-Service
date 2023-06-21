package com.ecommerce.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //EurekaClient는 좀 더 규격화 해서 상품화한 것
public class EcommerceUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceUserServiceApplication.class, args);
	}

}
