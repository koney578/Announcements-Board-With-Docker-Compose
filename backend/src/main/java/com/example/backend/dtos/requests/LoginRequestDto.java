package com.example.backend.dtos.requests;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
