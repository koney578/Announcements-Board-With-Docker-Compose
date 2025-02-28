package com.example.backend.controllers;

import com.example.backend.dtos.requests.LoginRequestDto;
import com.example.backend.dtos.requests.RegisterRequestDto;
import com.example.backend.dtos.responses.LoginResponseDto;
import com.example.backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequestDto registerRequestDto){
        logger.info("Registering new user");
        authenticationService.register(registerRequestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        logger.info("Logging in user: {}", loginRequestDto.getUsername());
        return authenticationService.login(loginRequestDto);
    }
}