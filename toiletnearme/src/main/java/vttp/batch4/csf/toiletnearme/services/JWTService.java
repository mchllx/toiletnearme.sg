package vttp.batch4.csf.toiletnearme.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {
    
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    public String generateToken(String email) { 
        Map<String, Object> claims = new HashMap<>(); 
        return createToken(claims, email); 
    } 
  
    private String createToken(Map<String, Object> claims, String email) {
        System.out.println("jwt username:" + email);

        return Jwts.builder() 
                .claims(claims) 
                .subject(email) 
                .issuedAt(new Date(System.currentTimeMillis())) 
                .expiration(new Date(expiration)) 
                .signWith(getSignKey(), Jwts.SIG.HS512)
                .compact(); 
    } 
  
    private SecretKey getSignKey() { 
        return Jwts.SIG.HS512.key().build(); 
        // incorrect size, less secure/encrypted 
        // byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        // return Keys.hmacShaKeyFor(keyBytes); 
    } 
  
    public String extractEmail(String token) { 
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
        return Jwts 
                .parser() 
                .verifyWith(getSignKey()) 
                .build() 
                .parseSignedClaims(token) 
                .getPayload(); 
    } 
  
    private Boolean isTokenExpired(String token) { 
        return extractExpiration(token).before(new Date()); 
    } 
  
    public Boolean validateToken(String token, UserDetails userDetails) { 
        final String email = extractEmail(token);
        System.out.println("jwt username:" + email); 
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    }  
}