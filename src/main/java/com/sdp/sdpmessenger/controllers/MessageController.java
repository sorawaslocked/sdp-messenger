package com.sdp.sdpmessenger.controllers;

import com.sdp.sdpmessenger.authentication.validators.auth.AuthValidator;
import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.models.dtos.PostMessageDto;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.MessageService;
import com.sdp.sdpmessenger.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final AuthValidator authValidator;
    private final JwtProvider jwtProvider;

    public MessageController(MessageService messageService,
                             UserService userService,
                             AuthValidator authValidator,
                             JwtProvider jwtProvider) {
        this.messageService = messageService;
        this.userService = userService;
        this.authValidator = authValidator;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/{receiverId}")
    public ResponseEntity<Message> postMessage(@PathVariable int receiverId,
                                               @RequestBody PostMessageDto message,
                                               HttpServletRequest request) {
        var validationResult = validateTokenAndGetUserId(request);

        if (!validationResult.hasBody()) {
            return new ResponseEntity<>(validationResult.getStatusCode());
        }

        int senderId = validationResult.getBody();

        Message createdMessage = messageService.create(message, senderId, receiverId);

        if (createdMessage == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Message> deleteMessage(@PathVariable int messageId,
                                                 HttpServletRequest request) {
        var validationResult = validateTokenAndGetUserId(request);

        if (!validationResult.hasBody()) {
            return new ResponseEntity<>(validationResult.getStatusCode());
        }

        int senderId = validationResult.getBody();

        Message message = messageService.getById(messageId);

        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (message.getSender().getId() != senderId) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!messageService.deleteById(messageId)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<Integer> validateTokenAndGetUserId(HttpServletRequest request) {
        HttpStatus status = authValidator.validate(request.getHeader("Authorization"));
        if (status != HttpStatus.OK) {
            return new ResponseEntity<>(status);
        }

        String token = request.getHeader("Authorization").substring(7);

        int userId = jwtProvider.getUserIdFromToken(token);

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
