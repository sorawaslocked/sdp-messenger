package com.sdp.sdpmessenger.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int senderId;
    private int receiverId;
    private String textValue;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    private Date createdAt;
    private Date updatedAt;
}
