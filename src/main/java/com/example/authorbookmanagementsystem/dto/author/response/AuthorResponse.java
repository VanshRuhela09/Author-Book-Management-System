package com.example.authorbookmanagementsystem.dto.author.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private Long id;
    private String name;
    private String email;
    private String bio;
}