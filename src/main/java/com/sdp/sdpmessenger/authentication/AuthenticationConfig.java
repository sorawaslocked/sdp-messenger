package com.sdp.sdpmessenger.authentication;

import com.sdp.sdpmessenger.authentication.validators.auth.AuthValidator;
import com.sdp.sdpmessenger.authentication.validators.auth.HeaderAuthValidator;
import com.sdp.sdpmessenger.authentication.validators.auth.TokenAuthValidator;
import com.sdp.sdpmessenger.authentication.validators.auth.UserExistsAuthValidator;
import com.sdp.sdpmessenger.authentication.validators.login.LoginValidator;
import com.sdp.sdpmessenger.authentication.validators.login.PasswordLoginValidator;
import com.sdp.sdpmessenger.authentication.validators.login.UserNotExistsLoginValidator;
import com.sdp.sdpmessenger.authentication.validators.register.PhoneExistsRegisterValidator;
import com.sdp.sdpmessenger.authentication.validators.register.RegisterValidator;
import com.sdp.sdpmessenger.authentication.validators.register.UsernameExistsRegisterValidator;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthenticationConfig {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public AuthenticationConfig(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Bean
    public AuthValidator authValidator() {
        return AuthValidator.link(
                new HeaderAuthValidator(),
                new TokenAuthValidator(jwtProvider),
                new UserExistsAuthValidator(userService, jwtProvider)
        );
    }

    @Bean
    public RegisterValidator registerValidator() {
        return RegisterValidator.link(
                new UsernameExistsRegisterValidator(userService),
                new PhoneExistsRegisterValidator(userService)
        );
    }

    @Bean
    public LoginValidator loginValidator() {
        return LoginValidator.link(
                new UserNotExistsLoginValidator(userService),
                new PasswordLoginValidator(bCryptPasswordEncoder, userService)
        );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }
}
