package com.sdp.sdpmessenger.repositories;

import com.sdp.sdpmessenger.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderIdAndReceiverId(int sender, int receiver);

    @Query("from Message m where m.sender.id = :userId or m.receiver.id = :userId")
    List<Message> findAllMessagesInvolvingUser(@Param("userId") int userId);
}
