package com.getir.readingisgood.security;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.security")
public class JwtConfig {

    private String key;
    private long hours;

    public static final String CLAIM_KEY = "authorities";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final String CLAIMS_ID = "id";

    public Date jwtExpiredTime() {
        return Date.from(LocalDateTime.now().plusHours(hours).atZone(ZoneId.systemDefault()).toInstant());
    }

    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
