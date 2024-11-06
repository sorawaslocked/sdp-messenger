package com.sdp.sdpmessenger.authentication.validators.login;

import com.sdp.sdpmessenger.authentication.LoginRequest;
import org.springframework.http.HttpStatus;

public abstract class LoginValidator {
    private LoginValidator next;

    public static LoginValidator link(LoginValidator first, LoginValidator... chain) {
        LoginValidator current = first;

        for (LoginValidator next : chain) {
            current.next = next;
            current = next;
        }

        return first;
    }

    public abstract HttpStatus validate(LoginRequest request);

    protected HttpStatus validateNext(LoginRequest request) {
        if (next == null) {
            return HttpStatus.OK;
        }

        return next.validate(request);
    }
}
