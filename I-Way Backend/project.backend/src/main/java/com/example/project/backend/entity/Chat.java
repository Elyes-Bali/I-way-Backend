package com.example.project.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;  // Store sender's ID
    private Long receiverId; // Store receiver's ID
    private String sender;
    private String receiver;
    @Column(columnDefinition = "TEXT")
    private String message;
    private String timestamp;
}
