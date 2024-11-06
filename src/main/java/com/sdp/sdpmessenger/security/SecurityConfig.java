package com.sdp.sdpmessenger.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(crsf -> crsf.disable());
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
