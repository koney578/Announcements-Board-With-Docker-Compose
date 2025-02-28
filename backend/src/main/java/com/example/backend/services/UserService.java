package com.example.backend.services;

import com.example.backend.dtos.responses.UserResponseDto;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User with id: {} was not found", id);
                    return new ResourceNotFoundException("Nie znaleziono użytkownika o id: " + id);
                });
        return userMapper.entityToResponseDto(user);
    }
}
