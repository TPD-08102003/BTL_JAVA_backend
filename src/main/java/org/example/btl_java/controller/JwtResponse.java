package org.example.btl_java.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtResponse {
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static String generateToken(String email, String role, String accountName, String jwtSecret) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("account_name", accountName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public static String getEmailFromToken(String token, String jwtSecret) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("Error parsing token: " + e.getMessage()); // Debug
            return null;
        }
    }

    @Deprecated
    public static String getEmail(String token) {
        return getEmailFromToken(token, "your-secret-key-must-be-long-enough-256bit");
    }
}