package com.example.project.backend.dto;

import lombok.Data;

@Data
public class MedicationDto {
    private String name;
    private String usage;
    private String duration;
    private boolean delivered;
}
