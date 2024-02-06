package com.springboot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.dto.UserSignupRequest;

public interface UserDetailsService {
	
	ResponseEntity<String> createUser(UserSignupRequest userSignupRequest);

	ResponseEntity<String> getUserProfile();

	UserDetails loadUserByUsername(String username);

	boolean existsByUsername(String username);
}