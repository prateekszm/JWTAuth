package com.Auth.JwtAuth.payload;

public class JWTAuthRequest {
	private String email;
    private String password;
    public JWTAuthRequest() {
       super();
    }
    public JWTAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
