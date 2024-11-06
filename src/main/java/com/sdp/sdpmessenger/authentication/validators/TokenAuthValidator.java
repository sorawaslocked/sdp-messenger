package com.sdp.sdpmessenger.authentication.validators;

import com.sdp.sdpmessenger.security.JwtProvider;
import org.springframework.http.HttpStatus;

public class TokenAuthValidator extends AuthValidator {
    private JwtProvider jwtProvider;

    public TokenAuthValidator(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public HttpStatus validate(String token) {
        if (!jwtProvider.validateToken(token)) {
            return HttpStatus.UNAUTHORIZED;
        }

        return validateNext(token);
    }
}
