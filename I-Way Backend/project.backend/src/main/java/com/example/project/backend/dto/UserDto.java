package com.example.project.backend.dto;
import com.example.project.backend.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;

    private String email;

    private String name;

    private UserRole userRole;
    private String education;   // Added field
    private String experience;  // Added field
    private String statement;   // Added field
    private String skills;
    private String address;
    private Long number;
    private String eAddress;
    private String speciality;
    private String matricule;
    private boolean verified;
    private String imgUrl;
    private String signatureUrl;

}
