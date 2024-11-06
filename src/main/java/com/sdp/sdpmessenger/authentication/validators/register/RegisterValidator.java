package com.sdp.sdpmessenger.authentication.validators.register;

import com.sdp.sdpmessenger.authentication.RegisterRequest;
import org.springframework.http.HttpStatus;

public abstract class RegisterValidator {
    private RegisterValidator next;

    public static RegisterValidator link(RegisterValidator first, RegisterValidator... chain) {
        RegisterValidator current = first;

        for (RegisterValidator next : chain) {
            current.next = next;
            current = next;
        }

        return first;
    }

    public abstract HttpStatus validate(RegisterRequest request);

    protected HttpStatus validateNext(RegisterRequest request) {
        if (next == null) {
            return HttpStatus.OK;
        }

        return next.validate(request);
    }
}
