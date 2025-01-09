package com.example.backend.dtos.responses;

public record LoginResponseDto(String token, UserResponseDto user) {
}
