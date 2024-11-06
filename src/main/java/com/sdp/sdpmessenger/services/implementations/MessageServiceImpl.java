package com.sdp.sdpmessenger.services.implementations;

import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.repositories.MessageRepository;
import com.sdp.sdpmessenger.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;

    public MessageServiceImpl(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
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
    public Message create(Message message) {
        message.setId(0);

        return messageRepo.save(message);
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
