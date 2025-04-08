package com.example.projectadmin.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "c55bb156cb8c7ba5ecb6a10cfeb3f80825253eba7ce5ae7681e633e7e9b22e93ce5c66cb9b84397d810a5c11188a3062969c858880587cc993ba18847b8e90d91606197ba4d078872a0e34f3bf18a380a457a7b5231513f394292aa9956e8abe7129369f4a92f4db5fe9aad71ba6867850df542d6b4c76c4a4ec099c17f2cca3a510b86eb040082500d151f3ba63839f4acdce08db8d35aa3c176592bd565154567590d5745723418a371c1c1ddad6da31de77a9487704db1b4cd29e18d7b6b771da8fbf670fb8b999a1c134be405a8e8e8a07e7ebeca61a32e794070b60e6bc3282267e98397a69c47c7c472079cb115dfeb9844300fc5f8edab91a5f9210ce";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

}
