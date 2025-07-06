package com.example.project.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionDto {
    private Long doctorId;
    private Long patientId;
    private String symptoms;
    private List<MedicationDto> medications;
    private LocalDateTime createdAt;  // New field
    private String code;               // New field
    private boolean status;
}
