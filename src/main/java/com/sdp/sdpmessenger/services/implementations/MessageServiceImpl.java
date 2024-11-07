package com.sdp.sdpmessenger.services.implementations;

import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.models.MessageStatus;
import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.models.dtos.PostMessageDto;
import com.sdp.sdpmessenger.repositories.MessageRepository;
import com.sdp.sdpmessenger.services.MessageService;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepo, UserService userService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
    }

    @Override
    public List<Message> getAll() {
        return messageRepo.findAll();
    }

    @Override
    public Message getById(int id) {
        return messageRepo.findById(id).orElse(null);
    }

    @Override
    public List<Message> getFromTo(int from, int to) {
        return messageRepo.findBySenderIdAndReceiverId(from, to);
    }

    @Override
    public List<Message> getAllInvolvingUser(int userId) {
        return messageRepo.findAllMessagesInvolvingUser(userId);
    }

    @Override
    public Message create(PostMessageDto message, int senderId, int receiverId) {
        User sender = userService.getById(senderId);
        User receiver = userService.getById(receiverId);

        Message createdMessage = new Message();
        createdMessage.setSender(sender);
        createdMessage.setReceiver(receiver);
        createdMessage.setTextValue(message.getTextValue());
        createdMessage.setStatus(MessageStatus.SENT);
        createdMessage.setCreatedAt(new Date());
        createdMessage.setUpdatedAt(new Date());

        return messageRepo.save(createdMessage);
    }

    @Override
    public Message update(Message message) {
        if (messageRepo.existsById(message.getId())) {
            return messageRepo.save(message);
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        if (messageRepo.existsById(id)) {
            messageRepo.deleteById(id);

            return true;
        }

        return false;
    }
}
