package com.example.authorbookmanagementsystem.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Register request body")
@Getter
@Setter
public class RegisterRequest {

    @Schema(description = "Desired username", example = "john_doe")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Password (minimum 6 characters)", example = "secret123")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}