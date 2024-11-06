package com.sdp.sdpmessenger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private int receiverId;
    private String receiverUsername;
    private List<Message> messages;
}
