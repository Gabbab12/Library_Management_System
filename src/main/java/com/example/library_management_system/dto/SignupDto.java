package com.example.library_management_system.dto;

import lombok.Data;

@Data
public class SignupDto {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
}
