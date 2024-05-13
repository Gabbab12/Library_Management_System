package com.example.library_management_system.dto;

import com.example.library_management_system.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class AuthenticationResponse {
        @JsonProperty("token")
        private String token;
        private User user;
}
