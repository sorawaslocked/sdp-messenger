package com.sdp.sdpmessenger.services.implementations;

import com.sdp.sdpmessenger.models.Attachment;
import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.models.MessageStatus;
import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.models.dtos.PostAttachmentDto;
import com.sdp.sdpmessenger.models.dtos.PostMessageDto;
import com.sdp.sdpmessenger.repositories.MessageRepository;
import com.sdp.sdpmessenger.services.AttachmentService;
import com.sdp.sdpmessenger.services.MessageService;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;
    private final UserService userService;
    private final AttachmentService attachmentService;

    public MessageServiceImpl(MessageRepository messageRepo, UserService userService, AttachmentService attachmentService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
        this.attachmentService = attachmentService;
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

        createdMessage = messageRepo.save(createdMessage);

        List<Attachment> attachments = new ArrayList<>();
        if (message.getAttachments() != null) {
            for (PostAttachmentDto postAttachmentDto : message.getAttachments()) {
                Attachment attachment = new Attachment();
                attachment.setMessage(createdMessage);
                attachment.setType(postAttachmentDto.getType());
                attachment.setUrlToResource(postAttachmentDto.getUrlToResource());
                attachment.setSizeInBytes(postAttachmentDto.getSizeInBytes());
                attachment.setCreatedAt(new Date());
                attachments.add(attachment);
            }
        }

        attachmentService.saveAll(attachments);

        createdMessage.setAttachments(attachments);

        return createdMessage;
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
