package com.sdp.sdpmessenger.controllers;

import com.sdp.sdpmessenger.authentication.LoginRequest;
import com.sdp.sdpmessenger.authentication.RegisterRequest;
import com.sdp.sdpmessenger.authentication.validators.login.LoginValidator;
import com.sdp.sdpmessenger.authentication.validators.register.RegisterValidator;
import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.models.UserStatus;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final JwtProvider jwtProvider;
    private final LoginValidator loginValidator;
    private final RegisterValidator registerValidator;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(JwtProvider jwtProvider,
                          LoginValidator loginValidator,
                          RegisterValidator registerValidator,
                          UserService userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtProvider = jwtProvider;
        this.loginValidator = loginValidator;
        this.registerValidator = registerValidator;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        HttpStatus status = loginValidator.validate(request);

        if (status != HttpStatus.OK) {
            return new ResponseEntity<>(status);
        }

        return new ResponseEntity<>(
                jwtProvider.generateToken(
                        userService.getUserByUsername(request.getUsername()).getId()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        HttpStatus status = registerValidator.validate(request);

        if (status != HttpStatus.OK) {
            return new ResponseEntity<>(status);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setStatus(UserStatus.OFFLINE);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userService.create(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
