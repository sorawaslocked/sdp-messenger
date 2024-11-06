package com.sdp.sdpmessenger.authentication.validators.register;

import com.sdp.sdpmessenger.authentication.RegisterRequest;
import org.springframework.http.HttpStatus;
import java.util.regex.Pattern;

public class PasswordRegisterValidator extends RegisterValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public HttpStatus validate(RegisterRequest request) {
        String password = request.getPassword();

        if (!isPasswordValid(password)) {
            return HttpStatus.BAD_REQUEST;
        }

        return validateNext(request);
    }

    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
