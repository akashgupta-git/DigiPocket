package com.digipocket.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
	@Value("${app.jwt.secret}")
	private String secret;
	@Value("${app.jwt.expiration-ms:86400000}")
	private long expirationMs;

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}

	public boolean isTokenValid(String token, String username) {
		final String extracted = extractUsername(token);
		return (extracted.equals(username) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	public String generateToken(String username) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);
		return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiry)
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
}
