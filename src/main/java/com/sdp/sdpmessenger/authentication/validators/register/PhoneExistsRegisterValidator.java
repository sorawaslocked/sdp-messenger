package com.sdp.sdpmessenger.authentication.validators.register;

import com.sdp.sdpmessenger.authentication.RegisterRequest;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.http.HttpStatus;

public class PhoneExistsRegisterValidator extends RegisterValidator {
    private UserService userService;

    public PhoneExistsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpStatus validate(RegisterRequest request) {
        if (userService.getUserByPhone(request.getPhone()) != null) {
            return HttpStatus.CONFLICT;
        }

        return validateNext(request);
    }
}
