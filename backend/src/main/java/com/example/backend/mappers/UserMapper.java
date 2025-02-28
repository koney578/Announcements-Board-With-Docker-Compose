package com.example.backend.mappers;

import com.example.backend.dtos.responses.UserResponseDto;
import com.example.backend.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto entityToResponseDto(User user);
}
