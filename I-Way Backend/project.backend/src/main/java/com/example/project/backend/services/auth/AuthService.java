package com.example.project.backend.services.auth;

import com.example.project.backend.dto.SignupRequest;
import com.example.project.backend.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
