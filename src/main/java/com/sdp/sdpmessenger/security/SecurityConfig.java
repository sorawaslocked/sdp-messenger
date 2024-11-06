package com.sdp.sdpmessenger.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public JwtProvider jwtProvider(
            @Value("${secretKey:#{null}}") String secretKey,
            @Value("${expirationPeriod:0}") long expirationPeriod) {
        return new JwtProvider.JwtProviderBuilder()
                .setSecret(secretKey)
                .setExpirationPeriod(expirationPeriod)
                .setSignatureAlgorithm(Jwts.SIG.HS256)
                .build();
    }
}
