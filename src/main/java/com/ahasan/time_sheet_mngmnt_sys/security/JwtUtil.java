package com.ahasan.time_sheet_mngmnt_sys.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔑 Must be at least 256 bits (32+ chars)
    private final String SECRET = "my-super-secret-key-which-is-at-least-32-chars-long";
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    // ✅ Declare as SecretKey directly
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)   // new style in 0.12.x
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = parseClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // ✅ Parsing in 0.12.x
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)   // key is SecretKey, no cast needed
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
