package com.example.library_management_system.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserAlreadyExistException extends RuntimeException{
    private String message;
    private String status;

    public UserAlreadyExistException(String message, HttpStatus status){
        this.message = message;
        this.status = String.valueOf(status);
    }
}
