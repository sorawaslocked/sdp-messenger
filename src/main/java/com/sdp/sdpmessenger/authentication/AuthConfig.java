package com.sdp.sdpmessenger.authentication;

import com.sdp.sdpmessenger.authentication.validators.AuthValidator;
import com.sdp.sdpmessenger.authentication.validators.TokenAuthValidator;
import com.sdp.sdpmessenger.authentication.validators.UserExistsAuthValidator;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    private JwtProvider jwtProvider;
    private UserService userService;

    public AuthConfig(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Bean
    public AuthValidator authValidator() {
        return AuthValidator.link(
                new TokenAuthValidator(jwtProvider),
                new UserExistsAuthValidator(userService, jwtProvider)
        );
    }
}
