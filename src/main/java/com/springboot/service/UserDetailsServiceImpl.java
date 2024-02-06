package com.springboot.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.model.User;
import com.springboot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springboot.dto.UserSignupRequest;
import com.springboot.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDetailsService userDetailsService;

	@Autowired
	public UserDetailsServiceImpl(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<String> createUser(UserSignupRequest userSignupRequest) {
		// Check if the user already exists
		if (userDetailsService.existsByUsername(userSignupRequest.getUsername())) {
			return ResponseEntity.badRequest().body("User already exists!");
		}

		// Create a new user
		User user = new User();
		user.setUsername(userSignupRequest.getUsername());
		user.setPassword(userSignupRequest.getPassword()); // Password should be encrypted before saving to the
															// database

		// Save the user to the database
		userRepository.signup(user);

		return ResponseEntity.ok("User created successfully!");
	}

	public ResponseEntity<String> getUserProfile() {
		// Fetch user profile based on authentication
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		// Fetch user profile from the database based on username
		// Replace the following line with your logic to fetch user profile
		String userProfile = "Profile for user: " + username;

		return ResponseEntity.ok(userProfile);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
