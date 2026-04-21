package com.example.authorbookmanagementsystem.service.auth;

import com.example.authorbookmanagementsystem.dto.auth.request.LoginRequest;
import com.example.authorbookmanagementsystem.dto.auth.request.RegisterRequest;
import com.example.authorbookmanagementsystem.dto.auth.response.AuthResponse;
import com.example.authorbookmanagementsystem.entity.Role;
import com.example.authorbookmanagementsystem.entity.User;
import com.example.authorbookmanagementsystem.entity.enums.RoleName;
import com.example.authorbookmanagementsystem.mapper.auth.AuthMapper;
import com.example.authorbookmanagementsystem.repository.role.RoleRepository;
import com.example.authorbookmanagementsystem.repository.user.UserRepository;
import com.example.authorbookmanagementsystem.security.jwt.JwtUtil;
import com.example.authorbookmanagementsystem.exception.DuplicateResourceException;
import com.example.authorbookmanagementsystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering user: {}", request.getUsername());
        // Check for duplicate username
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("User already exists with username: " + request.getUsername());
        }
        Role role = roleRepository.findByRole(RoleName.LIBRARIAN)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        User user = authMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        AuthResponse response = new AuthResponse();
        response.setMessage("Login successful");
        response.setToken(token);
        return response;
    }
}