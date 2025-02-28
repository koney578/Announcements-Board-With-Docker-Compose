package com.example.backend.services;

import com.example.backend.dtos.requests.LoginRequestDto;
import com.example.backend.dtos.requests.RegisterRequestDto;
import com.example.backend.exceptions.ConflictException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private EmailService emailService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_userDoesNotExist_savesUser() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("user1");
        registerRequestDto.setPassword("password");
        registerRequestDto.setEmail("mail1@mailtest.com");

        authenticationService.register(registerRequestDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void register_usernameExists_throwsConflictException() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(new User()));

        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("user2");
        registerRequestDto.setPassword("password");
        registerRequestDto.setEmail("mail2@mailtest.com");

        assertThrows(ConflictException.class, () -> authenticationService.register(registerRequestDto));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void register_emailExists_throwsConflictException() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new User()));

        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("user3");
        registerRequestDto.setPassword("password");
        registerRequestDto.setEmail("mail3@mailtest.com");

        assertThrows(ConflictException.class, () -> authenticationService.register(registerRequestDto));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void login_userExists_returnsLoginResponseDto() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(new User()));

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("user");
        loginRequestDto.setPassword("password");

        authenticationService.login(loginRequestDto);

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void login_userDoesNotExist_throwsAuthenticationException() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("user");
        loginRequestDto.setPassword("password");

        assertThrows(Exception.class, () -> authenticationService.login(loginRequestDto));

        verify(authenticationManager, times(1)).authenticate(any());
    }

}