package com.example.backend.dtos.responses;

import com.example.backend.enums.UserType;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        UserType userType
) {
}
