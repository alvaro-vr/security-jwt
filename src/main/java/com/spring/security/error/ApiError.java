package com.spring.security.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int statusCode;
    private String message;
    private LocalDate date;

}
