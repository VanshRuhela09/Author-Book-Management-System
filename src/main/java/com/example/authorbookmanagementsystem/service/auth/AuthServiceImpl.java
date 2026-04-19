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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {

        Role role = roleRepository.findByRole(RoleName.LIBRARIAN)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = authMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // actual authentication handled by Spring Security
        return new AuthResponse("Login successful");
    }
}