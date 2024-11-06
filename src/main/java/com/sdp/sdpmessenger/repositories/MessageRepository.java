package com.sdp.sdpmessenger.repositories;

import com.sdp.sdpmessenger.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderIdAndReceiverId(int sender, int receiver);
}
