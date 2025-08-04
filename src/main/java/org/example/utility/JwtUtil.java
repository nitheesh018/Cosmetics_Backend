package org.example.utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 Secret key (must be at least 32 chars for HS256)
    private static final String SECRET = "mySuperSecretKey1234567890cosmeticsJWTsecure";
    private static final long EXPIRATION_TIME = 3600000; // 1 hour = 3600 * 1000

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Generate token with email and role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Get email (subject) from token
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // ✅ Get user role from token
    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // ✅ Validate JWT token signature and expiry
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("❌ Unsupported token: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("❌ Malformed token: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("❌ Invalid signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Empty token: " + e.getMessage());
        }
        return false;
    }

    // ✅ Parse token and return Claims
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
