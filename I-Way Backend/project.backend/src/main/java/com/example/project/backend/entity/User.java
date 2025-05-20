package com.example.project.backend.entity;
import com.example.project.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private UserRole role;

    @Column(length = 1000000)
    private String education;

    @Column(length = 1000000)
    private String experience;

    @Column(length = 1000000)
    private String statement;

    @Column(length = 1000000)
    private String skills;

    private String adress;

    private Long number;

    private String eAddress;

    private String speciality;

    private String matricule;

    @Column(nullable = false)
    private boolean verified = true;  // Default is true

    @Column(length = 1000)
    private String imgUrl;

    @Column(length = 1000)
    private String signatureUrl;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<DoctorAvailability> availabilities;
}
