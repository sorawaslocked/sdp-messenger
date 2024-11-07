package com.sdp.sdpmessenger.controllers;

import com.sdp.sdpmessenger.authentication.validators.auth.AuthValidator;
import com.sdp.sdpmessenger.models.Chat;
import com.sdp.sdpmessenger.models.ChatSimple;
import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.security.JwtProvider;
import com.sdp.sdpmessenger.services.MessageService;
import com.sdp.sdpmessenger.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chats")
@CrossOrigin(origins = "http://localhost:3000")
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
        var validationResult = validateTokenAndGetUserId(request);

        if (!validationResult.hasBody()) {
            return new ResponseEntity<>(validationResult.getStatusCode());
        }

        int senderId = validationResult.getBody();

        List<Message> messagesInvolvingUser = messageService.getAllInvolvingUser(senderId);

        Set<ChatSimple> simple = new HashSet<>();

        for (Message msg : messagesInvolvingUser) {
            int receiverId = msg.getReceiver().getId();
            String receiverUsername = msg.getReceiver().getUsername();

            if (receiverId == senderId) {
                receiverId = msg.getSender().getId();
                receiverUsername = msg.getSender().getUsername();
            }

            simple.add(new ChatSimple(receiverId, receiverUsername));
        }

        return new ResponseEntity<>(simple.stream().toList(), HttpStatus.OK);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<Chat> getChatWith(@PathVariable int receiverId, HttpServletRequest request) {
        var validationResult = validateTokenAndGetUserId(request);

        if (!validationResult.hasBody()) {
            return new ResponseEntity<>(validationResult.getStatusCode());
        }

        int senderId = validationResult.getBody();

        List<Message> chatMessages = new ArrayList<>();

        chatMessages.addAll(messageService.getFromTo(senderId, receiverId));
        chatMessages.addAll(messageService.getFromTo(receiverId, senderId));
        chatMessages.sort(Comparator.comparing(Message::getCreatedAt).reversed());

        User receiver = userService.getById(receiverId);

        if (receiver == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Chat chat = new Chat(receiverId, receiver.getUsername(), chatMessages);

        return new ResponseEntity<>(chat, HttpStatus.OK);
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
