package com.example.project.backend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Lob
    @Column(length = 5000)
    private String symptoms;
    @Lob
    @Column(length = 5000)
    private String prescriptions;
    @Lob
    @Column(length = 5000)
    private String additionalNotes;
    @Lob
    @Column(length = 200)
    private String reasons;

    @Column(length = 100)
    private String bloodType;

    @Column(length = 100)
    private String height;

    @Column(length = 100)
    private String weight;
    @Lob
    @Column(length = 1000)
    private String previousMedication;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 100, unique = true)
    private String code;

    // Constructor to generate code on object creation
    @PrePersist
    public void generateCode() {
        this.code = generateRandomCode();
    }

    // Method to generate a random alphanumeric code (e.g., "MF-123456")
    private String generateRandomCode() {
        String prefix = "MF-";  // Prefix for medical file code
        String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();  // Random part
        return prefix + randomPart;
    }
}
