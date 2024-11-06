package com.sdp.sdpmessenger.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtProvider {
    private final SecretKey secretKey;
    private final long expirationPeriod;
    private final MacAlgorithm signatureAlgorithm;

    private JwtProvider(SecretKey secretKey, long expirationPeriod, MacAlgorithm signatureAlgorithm) {
        this.secretKey = secretKey;
        this.expirationPeriod = expirationPeriod;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public static class JwtProviderBuilder {
        private static final RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        private String secret;
        private long expirationPeriod;
        private MacAlgorithm signatureAlgorithm;

        public JwtProviderBuilder() {
        }

        public JwtProviderBuilder setSecret(String secret) {
            this.secret = secret;

            return this;
        }

        public JwtProviderBuilder setExpirationPeriod(long expirationPeriod) {
            this.expirationPeriod = expirationPeriod;

            return this;
        }

        public JwtProviderBuilder setSignatureAlgorithm(MacAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;

            return this;
        }

        public JwtProvider build() {
            if (secret == null) {
                secret = randomStringGenerator.generateRandomString();
            }

            if (expirationPeriod <= 0) {
                expirationPeriod = 1500000;
            }

            if (signatureAlgorithm == null) {
                signatureAlgorithm = Jwts.SIG.HS256;
            }

            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
            System.out.println(secret);

            return new JwtProvider(secretKey, expirationPeriod, signatureAlgorithm);
        }
    }

    public String generateToken(int userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationPeriod);

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, signatureAlgorithm)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public Integer getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Integer.class);
    }
}
