package com.smart.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.UserOTP;

public interface OtpRepo extends JpaRepository<UserOTP, Integer> {
	
	public UserOTP findByUserId(String UserId);
}
