package com.example.authorbookmanagementsystem.security.config;

import com.example.authorbookmanagementsystem.security.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // AUTH APIs (public)
                        .requestMatchers("/auth/**").permitAll()

                        // READ access (GET → ADMIN + LIBRARIAN)
                        .requestMatchers(HttpMethod.GET, "/authors/**")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(HttpMethod.GET, "/books/**")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        // WRITE access (POST, PUT, DELETE → ADMIN only)
                        .requestMatchers("/authors/**").hasRole("ADMIN")
                        .requestMatchers("/books/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(myUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // secure for DB
    }
}