package com.sdp.sdpmessenger.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    @JsonBackReference
    private Message message;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    @Column(name = "url_to_resource")
    private String urlToResource;

    @Column(name = "size_in_bytes")
    private long sizeInBytes;

    @Column(name = "created_at")
    private Date createdAt;
}
