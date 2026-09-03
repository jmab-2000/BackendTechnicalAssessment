package com.beyondsoft.obt.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "obt.jwt")
public class JwtProperties {

  private String secret;
  private long ttlHours = 12;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public long getTtlHours() {
    return ttlHours;
  }

  public void setTtlHours(long ttlHours) {
    this.ttlHours = ttlHours;
  }
}
