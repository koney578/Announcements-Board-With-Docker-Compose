package com.example.backend.dtos.requests;

import java.time.LocalDateTime;

public record AnnouncementAddRequestDto(
        String title,
        String description,
        Long categoryId,
        LocalDateTime endsAt
) {
}
