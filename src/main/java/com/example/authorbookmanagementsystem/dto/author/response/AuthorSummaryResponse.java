package com.example.authorbookmanagementsystem.dto.author.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorSummaryResponse {
    private Long id;
    private String name;
    private String email;
}
