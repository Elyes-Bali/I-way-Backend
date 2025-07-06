package com.example.project.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalTime secondTime;
    private LocalTime secondendTime;

    private LocalTime thirdTime;
    private LocalTime thirdendTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    // Getters and Setters
    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }
}
