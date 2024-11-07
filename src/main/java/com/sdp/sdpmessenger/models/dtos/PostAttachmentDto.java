package com.sdp.sdpmessenger.models.dtos;

import com.sdp.sdpmessenger.models.AttachmentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAttachmentDto {
    @Enumerated(EnumType.STRING)
    private AttachmentType type;
    private String urlToResource;
    private int sizeInBytes;
}
