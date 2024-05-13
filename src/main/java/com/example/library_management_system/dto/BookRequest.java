package com.example.library_management_system.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.Year;

@Data
public class BookRequest {
    private String title;
    private String author;
    private Year publicationYear;
    public String isbn;
}
