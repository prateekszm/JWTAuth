package com.Auth.JwtAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Auth.JwtAuth.Configure.AppConstants;
import com.Auth.JwtAuth.model.Role;
import com.Auth.JwtAuth.model.User;
import com.Auth.JwtAuth.repository.RoleRepository;
import com.Auth.JwtAuth.repository.UserRepository;
import com.Auth.JwtAuth.security.MyUserDetailsService;
import com.Auth.JwtAuth.util.JwtUtils;

@RestController
public class MyUserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home() {
		return "Home";
	}

	@GetMapping("/user")
	public String user() {

		return "User";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "Admin";
	}
	
	@PostMapping("/register")
	public User register( @RequestBody User user) {
		System.out.println(user.getPassword());
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		 Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		 user.getRoles().add(role);
		return this.userRepository.save(user);
	}

	

	
}
