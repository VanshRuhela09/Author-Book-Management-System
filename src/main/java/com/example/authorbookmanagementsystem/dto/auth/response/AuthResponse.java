package com.example.authorbookmanagementsystem.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String message;
    private String token;

    public AuthResponse(String message) {
        this.message = message;
    }
}