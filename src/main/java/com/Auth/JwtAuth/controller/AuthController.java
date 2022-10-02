package com.Auth.JwtAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auth.JwtAuth.exception.BadCredentials;
import com.Auth.JwtAuth.payload.JWTAuthRequest;
import com.Auth.JwtAuth.payload.JWTAuthResponse;
import com.Auth.JwtAuth.security.MyUserDetailsService;
import com.Auth.JwtAuth.util.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private MyUserDetailsService myUserdetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest authRequest) throws Exception{
	
		this.authenticate(authRequest.getEmail(), authRequest.getPassword());
		
		UserDetails userDetails = this.myUserdetailsService.loadUserByUsername(authRequest.getEmail());
		String token = this.jwtUtils.generateToken(userDetails);
		JWTAuthResponse response = new JWTAuthResponse();
		response.setToken(token);
		return ResponseEntity.ok(response);
	}

	
	private void authenticate(String email, String Password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				Password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			//change class name to MyBadCredentilalsException
			System.err.println("BadCredentials not worked");
			throw new BadCredentials(email, Password);
			//throw  new Exception("bad cred");
		} catch (DisabledException e) {
			throw new Exception("User is disable");
		} catch (InternalAuthenticationServiceException e) {
			System.out.println("No user found");
		}

	}
	
	
	
}
