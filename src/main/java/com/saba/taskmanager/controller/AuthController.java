package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.AuthResponseDto;
import com.saba.taskmanager.dto.LoginRequestDto;
import com.saba.taskmanager.dto.RegisterRequestDto;
import com.saba.taskmanager.dto.RegisterResponseDto;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return authService.register(registerRequestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}