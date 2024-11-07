package com.sdp.sdpmessenger.services;

import com.sdp.sdpmessenger.models.Message;
import com.sdp.sdpmessenger.models.dtos.PostMessageDto;

import java.util.List;

public interface MessageService {
    List<Message> getAll();
    Message getById(int id);
    List<Message> getFromTo(int from, int to);
    List<Message> getAllInvolvingUser(int userId);
    Message create(PostMessageDto message, int senderId, int receiverId);
    Message update(Message message);
    boolean deleteById(int id);
}
