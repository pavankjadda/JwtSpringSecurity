package com.pj.jwt.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
	Intercepts all requests and checks for JWT token
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService)
	{
		this.jwtUtil = jwtUtil;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
	{
		final String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer"))
		{
			jwtToken = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwtToken);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwtToken, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
