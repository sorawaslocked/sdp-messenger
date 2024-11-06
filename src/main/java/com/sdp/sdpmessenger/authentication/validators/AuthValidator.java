package com.sdp.sdpmessenger.authentication.validators;

import org.springframework.http.HttpStatus;

public abstract class AuthValidator {
    private AuthValidator next;

    public static AuthValidator link(AuthValidator first, AuthValidator... chain) {
        AuthValidator current = first;

        for (AuthValidator next : chain) {
            current.next = next;
            current = next;
        }

        return first;
    }

    public abstract HttpStatus validate(String token);

    protected HttpStatus validateNext(String token) {
        if (next == null) {
            return HttpStatus.OK;
        }

        return next.validate(token);
    }
}
