package com.sdp.sdpmessenger.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostMessageDto {
    private String textValue;
    @Getter
    private List<PostAttachmentDto> attachments;
}
