package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.AuthRequest;
import com.springboot.dto.AuthResponse;
import com.springboot.dto.UserSignupRequest;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.security.JwtTokenUtil;
import com.springboot.service.UserDetailsService;
import com.springboot.service.UserDetailsServiceImpl;

@RestController
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	@Autowired
	private final UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
			UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<?> signupUser(@RequestBody UserSignupRequest userSignupRequest) {
		if (userDetailsService.existsByUsername(userSignupRequest.getUsername())) {
			return ResponseEntity.badRequest().body("Username already exists!");
		}

		// Encrypt the password
		String encryptedPassword = passwordEncoder.encode(userSignupRequest.getPassword());

		// Create a new user object
		User user = new User();
		user.setUsername(userSignupRequest.getUsername());
		user.setPassword(encryptedPassword);
		// Set other user properties as needed

		// Save the user to the database;
		userRepository.signup(user);

		return ResponseEntity.ok("User signed up successfully!");
	}
}
