package com.example.backend.mappers;

import com.example.backend.dtos.requests.AnnouncementAddRequestDto;
import com.example.backend.dtos.responses.AnnouncementResponseDto;
import com.example.backend.models.Announcement;
import org.mapstruct.Mapper;


@Mapper
public interface AnnouncementMapper {
    Announcement requestDtoToEntity(AnnouncementAddRequestDto dto);
    AnnouncementResponseDto entityToResponseDto(Announcement announcement);
}
