package com.sdp.sdpmessenger.repositories;

import com.sdp.sdpmessenger.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

}
