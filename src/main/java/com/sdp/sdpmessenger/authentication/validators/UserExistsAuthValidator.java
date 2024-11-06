package com.sdp.sdpmessenger.authentication.validators;

import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.http.HttpStatus;

public class UserExistsAuthValidator extends AuthValidator {
    private UserService userService;
    private JwtProvider jwtProvider;

    public UserExistsAuthValidator(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public HttpStatus validate(String token) {
        if (userService.getById(jwtProvider.getUserIdFromToken(token)) == null) {
            return HttpStatus.NOT_FOUND;
        }

        return validateNext(token);
    }
}
