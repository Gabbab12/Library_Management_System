package com.example.library_management_system.service;

import com.example.library_management_system.dto.AuthenticationResponse;
import com.example.library_management_system.dto.LoginDto;
import com.example.library_management_system.dto.SignupDto;
import com.example.library_management_system.dto.UpdatePatronRequest;
import com.example.library_management_system.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {
    ResponseEntity<User> registerPatron(SignupDto signupDto);

    ResponseEntity<AuthenticationResponse> login(LoginDto request);

    User getUserById(Long patronId);

    List<User> getAllPatron();

    ResponseEntity<User> updatePatron(UpdatePatronRequest request);

    ResponseEntity<String> deletePatronById(Long patronId);
}
