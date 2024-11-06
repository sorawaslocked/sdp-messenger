package com.sdp.sdpmessenger.authentication.validators.register;

import com.sdp.sdpmessenger.authentication.RegisterRequest;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.http.HttpStatus;

public class UsernameExistsRegisterValidator extends RegisterValidator {
    private UserService userService;

    public UsernameExistsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpStatus validate(RegisterRequest request) {
        if (userService.getUserByUsername(request.getUsername()) != null) {
            return HttpStatus.CONFLICT;
        }

        return validateNext(request);
    }
}
