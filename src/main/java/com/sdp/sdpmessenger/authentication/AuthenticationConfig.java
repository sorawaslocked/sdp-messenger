package com.sdp.sdpmessenger.authentication;

import com.sdp.sdpmessenger.authentication.validators.auth.AuthValidator;
import com.sdp.sdpmessenger.authentication.validators.auth.HeaderAuthValidator;
import com.sdp.sdpmessenger.authentication.validators.auth.TokenAuthValidator;
import com.sdp.sdpmessenger.authentication.validators.auth.UserExistsAuthValidator;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {
    private JwtProvider jwtProvider;
    private UserService userService;

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
}
