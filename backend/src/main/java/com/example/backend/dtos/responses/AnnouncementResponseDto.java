package com.example.backend.dtos.responses;

import com.example.backend.models.Category;

import java.time.LocalDateTime;

public record AnnouncementResponseDto(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime endsAt,
        Boolean isReviewed,
        Category category,
        AnnouncementUserResponseDto user
) {
}
