package com.sdp.sdpmessenger.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {
    private String type;
    private String urlToResource;
    private int sizeInBytes;
}
