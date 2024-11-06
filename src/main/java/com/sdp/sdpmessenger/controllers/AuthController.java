package com.sdp.sdpmessenger.controllers;

import com.sdp.sdpmessenger.authentication.LoginRequest;
import com.sdp.sdpmessenger.authentication.validators.AuthValidator;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthValidator authValidator;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider, AuthValidator authValidator) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authValidator = authValidator;
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken(HttpServletRequest request) {
        HttpStatus status = authValidator.validate(request.getHeader("Authorization"));

        return new ResponseEntity<>("token", status);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        User userFromDb = userService.getUserByUsername(loginRequest.getUsername());
//
//        if (userFromDb == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        return new ResponseEntity<>(jwtProvider.generateToken(2), HttpStatus.OK);
    }
}
