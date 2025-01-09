package com.example.backend.services;

import com.example.backend.dtos.requests.LoginRequestDto;
import com.example.backend.dtos.requests.RegisterRequestDto;
import com.example.backend.dtos.responses.LoginResponseDto;
import com.example.backend.dtos.responses.UserResponseDto;
import com.example.backend.enums.UserType;
import com.example.backend.exceptions.ConflictException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final UserMapper userMapper;

    public void register(RegisterRequestDto registerRequestDto) {
        Optional<User> databaseUser = userRepository.findByUsername(registerRequestDto.getUsername());
        if (databaseUser.isPresent()) {
            logger.warn("User '{}' already exists", registerRequestDto.getUsername());
            throw new ConflictException("Użytkownik '" + registerRequestDto.getUsername() + "' już istnieje!");
        }

        Optional<User> databaseEmailUser = userRepository.findByEmail(registerRequestDto.getEmail());
        if (databaseEmailUser.isPresent()) {
            logger.warn("Email address '{}' is already used", registerRequestDto.getEmail());
            throw new ConflictException("Adres email '" + registerRequestDto.getEmail() + "' jest już zajęty!");
        }

        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setUserType(UserType.BASIC);
        userRepository.save(user);

        String title = "Utworzone nowe konto użytkownika";
        String message = "Utworzono nowe konto użytkownika. Twój login to: " + user.getUsername() + ".\n" +
                "Jeśli to nie ty skontaktuj się z administratorem javaproject334@gmail.com.";
        emailService.sendSimpleMessage(user.getEmail(), title, message);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword())
        );

        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow();
        UserResponseDto userResponseDto = userMapper.entityToResponseDto(user);
        String token = jwtUtils.generateToken(user);
        return new LoginResponseDto(token, userResponseDto);
    }
}