package com.example.authorbookmanagementsystem.controller.auth;

import com.example.authorbookmanagementsystem.dto.auth.request.LoginRequest;
import com.example.authorbookmanagementsystem.dto.auth.request.RegisterRequest;
import com.example.authorbookmanagementsystem.dto.auth.response.AuthResponse;
import com.example.authorbookmanagementsystem.service.auth.AuthService;
import com.example.authorbookmanagementsystem.security.jwt.JwtBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private JwtBlacklistService jwtBlacklistService;

    @Autowired
    public void setJwtBlacklistService(JwtBlacklistService jwtBlacklistService) {
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtBlacklistService.blacklistToken(token);
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}