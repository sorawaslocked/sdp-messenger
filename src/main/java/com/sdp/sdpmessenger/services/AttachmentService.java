package com.sdp.sdpmessenger.services;

import com.sdp.sdpmessenger.models.Attachment;

import java.util.List;

public interface AttachmentService {
    List<Attachment> saveAll(List<Attachment> attachments);
}
