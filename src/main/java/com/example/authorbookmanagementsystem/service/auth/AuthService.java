package com.example.authorbookmanagementsystem.service.auth;

import com.example.authorbookmanagementsystem.dto.auth.request.LoginRequest;
import com.example.authorbookmanagementsystem.dto.auth.request.RegisterRequest;
import com.example.authorbookmanagementsystem.dto.auth.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}