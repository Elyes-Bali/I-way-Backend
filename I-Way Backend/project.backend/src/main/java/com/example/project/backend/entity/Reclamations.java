package com.example.project.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "Reclamation")
public class Reclamations implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idReclamation")
    private Long idReclamation; // Clé primaire
    @Column(length = 1000000)
    private String description;
    private Long OwnerId;
    private String email;
    public  String answer;
}
