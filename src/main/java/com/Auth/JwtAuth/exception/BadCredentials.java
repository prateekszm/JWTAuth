package com.Auth.JwtAuth.exception;

public class BadCredentials extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String email;
	String password;
	
	public BadCredentials(String email, String password) {
		super(String.format("%s email or password %s is worng", email, password));
		this.email = email;
		this.password = password;
	}
	public BadCredentials() {
		super();
	}

}
