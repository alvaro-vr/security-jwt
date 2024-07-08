package com.spring.security.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

}
