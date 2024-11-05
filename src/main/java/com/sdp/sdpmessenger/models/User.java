package com.sdp.sdpmessenger.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String passwordHash;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Date created_at;
    private Date updated_at;
}
