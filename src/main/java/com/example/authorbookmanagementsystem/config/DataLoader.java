package com.example.authorbookmanagementsystem.config;

import com.example.authorbookmanagementsystem.entity.Role;
import com.example.authorbookmanagementsystem.entity.enums.RoleName;
import com.example.authorbookmanagementsystem.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for (RoleName roleName : RoleName.values()) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(Role.builder().name(roleName).build());
            }
        }
    }
}

