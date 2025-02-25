package com.example.backend.services;

import com.example.backend.dtos.responses.UserResponseDto;
import com.example.backend.enums.UserType;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsers_usersExist_returnsUserList() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User(), new User()));
        when(userMapper.entityToResponseDto(any(User.class))).thenReturn(
                new UserResponseDto(1L, "user", "user@mail.com", UserType.BASIC)
        );

        userService.getAllUsers();

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(3)).entityToResponseDto(any(User.class));
    }

    @Test
    public void getUserById_userExists_returnsUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));
        when(userMapper.entityToResponseDto(any(User.class))).thenReturn(
                new UserResponseDto(1L, "user", "user@mail.com", UserType.BASIC)
        );

        userService.getUserById(1L);

        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).entityToResponseDto(any(User.class));
    }

    @Test
    public void getUserById_userDoesNotExist_throwsResourceNotFoundException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(0)).entityToResponseDto(any(User.class));
    }

}