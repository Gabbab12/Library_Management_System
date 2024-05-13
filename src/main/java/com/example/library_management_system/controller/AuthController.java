package com.example.library_management_system.controller;

import com.example.library_management_system.dto.AuthenticationResponse;
import com.example.library_management_system.dto.LoginDto;
import com.example.library_management_system.dto.SignupDto;
import com.example.library_management_system.dto.UpdatePatronRequest;
import com.example.library_management_system.entity.User;
import com.example.library_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patron")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<User> registerPatron(@RequestBody SignupDto signupDto){
        return authService.registerPatron(signupDto);
    }

    @GetMapping("/get-all-patron")
    public List<User> getAllPatron(){
        return authService.getAllPatron();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDto request){
        return authService.login(request);
    }

    @GetMapping("/get-patron-details/{patronId}")
    public User getPatronById(@PathVariable Long patronId){
        return authService.getUserById(patronId);
    }

    @PutMapping("/update-patron-details")
    public ResponseEntity<User> updatePatronDetails(@RequestBody UpdatePatronRequest request){
        return authService.updatePatron(request);
    }

    @DeleteMapping("/remove-patron")
    public ResponseEntity<String> removePatron(@RequestParam Long patronId){
        return authService.deletePatronById(patronId);
    }
}
