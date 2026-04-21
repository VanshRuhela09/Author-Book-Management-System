package com.example.authorbookmanagementsystem.controller.auth;

import com.example.authorbookmanagementsystem.dto.auth.request.LoginRequest;
import com.example.authorbookmanagementsystem.dto.auth.request.RegisterRequest;
import com.example.authorbookmanagementsystem.dto.auth.response.AuthResponse;
import com.example.authorbookmanagementsystem.security.jwt.JwtBlacklistService;
import com.example.authorbookmanagementsystem.security.jwt.JwtFilter;
import com.example.authorbookmanagementsystem.security.jwt.JwtUtil;
import com.example.authorbookmanagementsystem.security.service.MyUserDetailsService;
import com.example.authorbookmanagementsystem.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean AuthService authService;
    @MockitoBean JwtBlacklistService jwtBlacklistService;
    @MockitoBean JwtUtil jwtUtil;
    @MockitoBean JwtFilter jwtFilter;
    @MockitoBean MyUserDetailsService myUserDetailsService;

    @Test
    void register_returnsOk() throws Exception {
        AuthResponse response = new AuthResponse("User registered successfully");
        Mockito.when(authService.register(any())).thenReturn(response);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"test\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void login_returnsOk() throws Exception {
        AuthResponse response = new AuthResponse("Login successful");
        Mockito.when(authService.login(any())).thenReturn(response);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"test\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk());
    }
}
