package com.example.project.backend.services.auth;

import com.example.project.backend.dto.SignupRequest;
import com.example.project.backend.dto.UserDto;
import com.example.project.backend.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    UserDto updateUser(Long userId, String name, String email, String education, String experience, String statement, String skills, String address, Long number, String eAddress, String speciality, String matricule, Boolean verified, MultipartFile image, MultipartFile signature, LocalDate birthDate) throws IOException;

    User getUserbyId(Long id);

    List<User> allUsers();

    List<User> getAllDoctors();

    List<User> getAllPatients();
    List<User> getAllPharmacy();

}
