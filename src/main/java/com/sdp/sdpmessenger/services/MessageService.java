package com.sdp.sdpmessenger.services;

import com.sdp.sdpmessenger.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAll();
    Message getById(int id);
    List<Message> getFromTo(int from, int to);
    List<Message> getAllInvolvingUser(int userId);
    Message create(Message message);
    Message update(Message message);
    boolean deleteById(int id);
}
