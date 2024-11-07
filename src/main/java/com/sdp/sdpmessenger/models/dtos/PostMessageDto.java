package com.sdp.sdpmessenger.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostMessageDto {
    private String textValue;
    private List<AttachmentDto> attachments;
}
