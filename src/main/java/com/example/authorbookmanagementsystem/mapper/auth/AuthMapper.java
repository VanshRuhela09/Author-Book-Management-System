package com.example.authorbookmanagementsystem.mapper.auth;

import com.example.authorbookmanagementsystem.dto.auth.request.RegisterRequest;
import com.example.authorbookmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toUser(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                // password will be encoded in service
                .build();
    }
}