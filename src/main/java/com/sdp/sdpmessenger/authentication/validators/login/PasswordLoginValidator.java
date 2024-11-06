package com.sdp.sdpmessenger.authentication.validators.login;

import com.sdp.sdpmessenger.authentication.LoginRequest;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordLoginValidator extends LoginValidator {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public PasswordLoginValidator(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    public HttpStatus validate(LoginRequest request) {
        String hashedPassword = userService
                .getUserByUsername(request
                        .getUsername())
                .getPasswordHash();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), hashedPassword)) {
            return HttpStatus.UNAUTHORIZED;
        }

        return validateNext(request);
    }
}
