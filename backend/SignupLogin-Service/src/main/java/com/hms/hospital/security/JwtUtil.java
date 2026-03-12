package com.hms.hospital.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

	// Secret key used to sign the JWT token
	private final String SECRET_KEY = "thisismysecretkeyforthejwtauthentication";

	// Token expiration time
	private final long EXPIRE_TIME = 1000 * 60 * 60 * 5;

	// Convert the secret key string into a Key object for signing
	private Key signedKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	// Only token generation is needed - validation is now done at gateway level
	public String generateToken(Long id, String role) {
		return Jwts.builder()
				.setSubject(String.valueOf(id)) // Set user id as the token subject
				.claim("role", role) // Add user role as a claim
				.setIssuedAt(new Date(System.currentTimeMillis())) // Set token creation time
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // Set token expiry time
				.signWith(signedKey(), SignatureAlgorithm.HS256) // Sign token with secret key and algorithm
				.compact(); // Build the token as a string
	}
}
