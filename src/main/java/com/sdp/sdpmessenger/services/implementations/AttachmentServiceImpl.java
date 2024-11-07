package com.sdp.sdpmessenger.services.implementations;

import com.sdp.sdpmessenger.models.Attachment;
import com.sdp.sdpmessenger.repositories.AttachmentRepository;
import com.sdp.sdpmessenger.services.AttachmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepo;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    @Override
    public List<Attachment> saveAll(List<Attachment> attachments) {
        return attachmentRepo.saveAll(attachments);
    }
}

