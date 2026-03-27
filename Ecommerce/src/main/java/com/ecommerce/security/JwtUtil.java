package com.ecommerce.security;

import java.security.Key;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String secret = "mysecretkeymysecretkeymysecretkey"; 
    private Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS256)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}