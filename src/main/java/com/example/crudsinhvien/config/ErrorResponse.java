package com.example.crudsinhvien.config;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.error = message;
    }
}

