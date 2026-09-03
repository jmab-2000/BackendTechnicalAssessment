package com.beyondsoft.obt.security;

import com.beyondsoft.obt.identity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final JwtProperties properties;

  public JwtService(JwtProperties properties) {
    this.properties = properties;
  }

  public String createToken(AuthUserPrincipal user) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(user.getId())
        .claim("id", user.getId())
        .claim("email", user.getEmail())
        .claim("fullName", user.getFullName())
        .claim("role", user.getRole().name())
        .claim("salesRepCode", user.getSalesRepCode())
        .claim("customerSapId", user.getCustomerSapId())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(properties.getTtlHours(), ChronoUnit.HOURS)))
        .signWith(key())
        .compact();
  }

  public AuthUserPrincipal parse(String token) {
    Claims claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
    String role = claims.get("role", String.class);
    return new AuthUserPrincipal(
        claims.get("id", String.class),
        claims.get("email", String.class),
        claims.get("fullName", String.class),
        UserRole.valueOf(role),
        claims.get("salesRepCode", String.class),
        claims.get("customerSapId", String.class));
  }

  private SecretKey key() {
    byte[] bytes = properties.getSecret().getBytes(StandardCharsets.UTF_8);
    if (bytes.length < 32) {
      byte[] padded = new byte[32];
      System.arraycopy(bytes, 0, padded, 0, bytes.length);
      bytes = padded;
    }
    return Keys.hmacShaKeyFor(bytes);
  }
}
