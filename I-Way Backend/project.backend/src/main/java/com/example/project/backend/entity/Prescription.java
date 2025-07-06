package com.example.project.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;


    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Medication> medications;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 100, unique = true)
    private String code;
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
