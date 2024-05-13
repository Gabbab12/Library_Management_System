package com.example.library_management_system.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdatePatronRequest {
    private String name;
    private String address;
    private String phoneNumber;
}
