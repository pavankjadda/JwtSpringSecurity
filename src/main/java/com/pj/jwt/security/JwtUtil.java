package com.pj.jwt.security;


import com.pj.jwt.config.CoreProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT utility class that issues and validates JWT tokens
 *
 * @author Pavan Jadda
 * @version 1.0.0
 */
@Service
@Transactional
public class JwtUtil
{
	private final CoreProperties coreProperties;

	public JwtUtil(CoreProperties coreProperties)
	{
		this.coreProperties = coreProperties;
	}

	private boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(coreProperties.getJwtSecret().getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String generateToken(UserDetails userDetails)
	{
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject)
	{
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(Keys.hmacShaKeyFor(coreProperties.getJwtSecret().getBytes()), SignatureAlgorithm.HS512).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails)
	{
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String extractUsername(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}
}
