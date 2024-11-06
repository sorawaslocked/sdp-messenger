package com.sdp.sdpmessenger.authentication.validators.register;

import com.sdp.sdpmessenger.authentication.RegisterRequest;
import org.springframework.http.HttpStatus;
import java.util.regex.Pattern;

public class PasswordRegisterValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);


    // Method that validates the password based on defined rules
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
