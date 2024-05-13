package com.example.library_management_system.dto;

import lombok.Data;

@Data
public class UpdateBookRequest {
    private String author;
    private String title;
}
