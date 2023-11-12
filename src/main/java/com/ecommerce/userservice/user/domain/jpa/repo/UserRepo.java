package com.ecommerce.userservice.user.domain.jpa.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.userservice.user.domain.jpa.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>{
	
	public UserEntity findByUserId(String userId);
	
}
