package com.example.project.backend.dto;
import lombok.Data;
import java.time.LocalTime;

@Data
public class DoctorAvailabilityDto {
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    private LocalTime secondTime;
    private LocalTime secondendTime;

    private LocalTime thirdTime;
    private LocalTime thirdendTime;
}
