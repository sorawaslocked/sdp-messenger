package com.sdp.sdpmessenger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSimple {
    private int receiverId;
    private String chatName;
}
