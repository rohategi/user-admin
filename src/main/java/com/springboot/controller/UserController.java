package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.springboot.dto.UserSignupRequest;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.service.UserDetailsService;

@RestController
@RequestMapping("/user")
public class UserController {
    
	private final UserDetailsService userDetailsService;
	@Autowired
	private UserRepository userRepository;

	public UserController(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createUser(@RequestBody UserSignupRequest userSignupRequest) {
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

	@GetMapping("/profile")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<String> getUserProfile() {
		// Fetch user profile based on authentication
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		// Fetch user profile from the database based on username
		// Replace the following line with your logic to fetch user profile
		String userProfile = "Profile for user: " + username;

		return ResponseEntity.ok(userProfile);
	}
}
