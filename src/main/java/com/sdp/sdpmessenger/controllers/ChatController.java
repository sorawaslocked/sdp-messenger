package com.sdp.sdpmessenger.controllers;

import com.sdp.sdpmessenger.authentication.validators.auth.AuthValidator;
import com.sdp.sdpmessenger.models.ChatSimple;
import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.MessageService;
import com.sdp.sdpmessenger.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final MessageService messageService;
    private final UserService userService;
    private final AuthValidator authValidator;
    private final JwtProvider jwtProvider;

    public ChatController(MessageService messageService,
                          UserService userService,
                          AuthValidator authValidator,
                          JwtProvider jwtProvider) {
        this.messageService = messageService;
        this.userService = userService;
        this.authValidator = authValidator;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/simple")
    public ResponseEntity<List<ChatSimple>> simpleChats(HttpServletRequest request) {
        HttpStatus status = authValidator.validate(request.getHeader("Authorization"));

        if (status != HttpStatus.OK) {
            return new ResponseEntity<>(status);
        }

        String token = request.getHeader("Authorization").substring(7);

        int userId = jwtProvider.getUserIdFromToken(token);

        List<Message> messagesInvolvingUser = messageService.getAllInvolvingUser(userId);

        Set<ChatSimple> simple = new HashSet<>();

        for (Message msg : messagesInvolvingUser) {
            int receiverId = msg.getReceiver().getId();
            String receiverUsername = msg.getReceiver().getUsername();

            if (receiverId == userId) {
                receiverId = msg.getSender().getId();
                receiverUsername = msg.getSender().getUsername();
            }

            simple.add(new ChatSimple(receiverId, receiverUsername));
        }

        return new ResponseEntity<>(simple.stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Message>> getChat(@PathVariable int receiverId, HttpServletRequest request) {
        HttpStatus status = authValidator.validate(request.getHeader("Authorization"));

        if (status != HttpStatus.OK) {
            return new ResponseEntity<>(status);
        }

        String token = request.getHeader("Authorization").substring(7);

        int senderId = jwtProvider.getUserIdFromToken(token);

        List<Message> chat = new ArrayList<>();

        chat.addAll(messageService.getFromTo(senderId, receiverId));
        chat.addAll(messageService.getFromTo(receiverId, senderId));
        chat.sort(Comparator.comparing(Message::getCreatedAt).reversed());

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
}
