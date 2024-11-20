package com.danyatheworst.service;

import com.danyatheworst.dto.JwtUserDetailsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.token-ms}")
    private int expirationTimeMillis;

    public String generateToken(JwtUserDetailsDto jwtUserDetailsDto) {
        Claims claims = Jwts.claims();
        claims.put("id", jwtUserDetailsDto.getId());
        claims.put("username", jwtUserDetailsDto.getUsername());
        claims.put("authorities", jwtUserDetailsDto.getAuthorities());

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationTimeMillis)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }
}
