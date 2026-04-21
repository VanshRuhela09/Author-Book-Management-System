package com.example.authorbookmanagementsystem.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Login request body")
@Getter
@Setter
public class LoginRequest {

    @Schema(description = "Username of the user", example = "john_doe")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Password of the user", example = "secret123")
    @NotBlank(message = "Password is required")
    private String password;

}