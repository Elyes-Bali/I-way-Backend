package com.example.project.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private String time;

    @Column(nullable = false)
    private boolean accepted = false;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private String fullDate;

    @PrePersist
    protected void onCreate() {
        this.fullDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
