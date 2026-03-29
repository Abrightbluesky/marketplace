package com.ruth.shop.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 SECRET KEY (HARUS MINIMAL 256 BIT)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 🔑 GENERATE TOKEN
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key) // ✅ NEW STYLE
                .compact();
    }

    // 🔍 EXTRACT EMAIL
    public String extractEmail(String token){
        return Jwts.parserBuilder() // ✅ NEW STYLE
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}