package com.pj.jwt.security;

import com.pj.jwt.config.CoreProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT utility class that issues and validates JWT tokens.
 * Provides methods to generate, validate, and extract information from JWT tokens.
 *
 * @author Pavan Kumar Jadda
 * @since 2.0.0
 */
@Service
@Transactional
public class JwtUtil {
    private final CoreProperties coreProperties;

    public JwtUtil(CoreProperties coreProperties) {
        this.coreProperties = coreProperties;
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token to check
     *
     * @return true if the token is expired, false otherwise
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token to extract the expiration date from
     *
     * @return the expiration date of the token
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided claims resolver function.
     *
     * @param token          the JWT token to extract the claim from
     * @param claimsResolver the function to resolve the claim
     * @param <T>            the type of the claim
     *
     * @return the extracted claim
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token to extract the claims from
     *
     * @return the claims extracted from the token
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details to generate the token for
     *
     * @return the generated JWT token
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with the given claims and subject.
     *
     * @param claims  the claims to include in the token
     * @param subject the subject of the token
     *
     * @return the created JWT token
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey()).compact();
    }

    /**
     * Builds the secret key from the JWT secret stored in the application properties.
     *
     * @return the created secret key
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(coreProperties.getJwtSecret()));
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to validate the token against
     *
     * @return true if the token is valid, false otherwise
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token to extract the username from
     *
     * @return the username extracted from the token
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}