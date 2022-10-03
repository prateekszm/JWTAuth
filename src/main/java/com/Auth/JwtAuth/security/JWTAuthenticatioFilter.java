package com.Auth.JwtAuth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Auth.JwtAuth.util.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

/**
 * @author {Prateek Raj Kushwaha} Servlet filter that will run before hitting
 *         the dispatcher Servlet contoller. to perform authorization and
 *         authentication
 */
@Component
public class JWTAuthenticatioFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtils jwtUtils;

	/*
	 * This internal filter will extract the token from header and validate the
	 * token and then it will extract the username and and match the userName from
	 * database and then it will set UsernamePasswordAuthenticationToken and it will
	 * pass it to next Filter chain
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = request.getHeader("Authorization");
		// System.out.println(requestToken);
		String userEmail = null;
		String token = null;
		if (requestToken != null && requestToken.startsWith("Bearer ")) {
			token = requestToken.substring(7);
			try {
				userEmail = jwtUtils.extractUsername(token);
			} catch (IllegalArgumentException e) {
				System.out.println(e + "Unable to parse");
			} catch (ExpiredJwtException e) {
				System.out.println("Token Expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid Exception");
			}

		} else {
			System.out.println("Not Valid Token");
		}

		// Once we get the token validate it with db
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			if (this.jwtUtils.validateToken(token, userDetails)) {
				// good now authenticate
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Invalid Username or  token");
			}

		} else {
			System.out.println("Username is null or context is not null");
		}
		filterChain.doFilter(request, response);

	}

}
