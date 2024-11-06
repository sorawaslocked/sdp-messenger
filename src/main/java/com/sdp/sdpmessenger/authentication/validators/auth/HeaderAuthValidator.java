package com.sdp.sdpmessenger.authentication.validators.auth;

import org.springframework.http.HttpStatus;

public class HeaderAuthValidator extends AuthValidator {
    @Override
    public HttpStatus validate(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return HttpStatus.BAD_REQUEST;
        }

        return validateNext(token.substring(7));
    }
}
