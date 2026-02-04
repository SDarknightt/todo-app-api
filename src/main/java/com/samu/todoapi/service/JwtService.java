package com.samu.todoapi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtService {

    @Value("${api.security.token.secret-key}")
    private String secretKey;
    @Value("${api.security.token.expiration.minutes}")
    private int plusMinutes;
    
    public String createToken(Map<String, Object> claims, String username) {
        Instant now = Instant.now();
        Instant expiration = now.plus(plusMinutes, ChronoUnit.MINUTES);
        return Jwts.builder()
            .claims(claims)
            .issuer("JAVA - TODO API")
            .subject(username)
            .signWith(getSignKey())
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return getTokenExpiration(token).after(new Date());
    }

    private Date getTokenExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public SecretKey getSignKey() {
        // Transforms the key text into bytes
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Key instance is based on secret key size 32bytes -> 256bits -> HmacSHA256
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
