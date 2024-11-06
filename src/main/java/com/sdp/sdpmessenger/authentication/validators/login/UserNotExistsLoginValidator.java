package com.sdp.sdpmessenger.authentication.validators.login;

import com.sdp.sdpmessenger.authentication.LoginRequest;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.http.HttpStatus;

public class UserNotExistsLoginValidator extends LoginValidator {
    private UserService userService;

    public UserNotExistsLoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpStatus validate(LoginRequest request) {
        if (userService.getUserByUsername(request.getUsername()) == null) {
            return HttpStatus.NOT_FOUND;
        }

        return validateNext(request);
    }
}
