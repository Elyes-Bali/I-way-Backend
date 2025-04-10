package com.example.project.backend.dto;
import com.example.project.backend.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class SignupRequest {
    private String email;

    private String password;

    private String name;

    private UserRole userRole;

    private List<DoctorAvailabilityDto> availabilities= new ArrayList<>();;

    private String matricule;
}
