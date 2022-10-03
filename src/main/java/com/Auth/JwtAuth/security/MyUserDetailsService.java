package com.Auth.JwtAuth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Auth.JwtAuth.exception.ResourceNotFoundException;
import com.Auth.JwtAuth.model.User;
import com.Auth.JwtAuth.repository.MyUserRepository;

/*
 * This class is responsible to load user that is saved in data base we will be getting the userName and password through our controller
 * in my case the userName is email so my JPA method is findByEmail. This service layer is only responsible to load user and pass it to authentication manager
 * 
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	// This Class will load the user where it is saved
	@Autowired
	private MyUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Load user name to give to spring
		User user = this.userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException(username, "not found", "in db"));
		return user;
	}

}
