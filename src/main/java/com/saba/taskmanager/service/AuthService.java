package com.saba.taskmanager.service;

import com.saba.taskmanager.dto.AuthResponseDto;
import com.saba.taskmanager.dto.LoginRequestDto;
import com.saba.taskmanager.dto.RegisterRequestDto;
import com.saba.taskmanager.dto.RegisterResponseDto;
import com.saba.taskmanager.entity.Role;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.exception.EmailAlreadyExistsException;
import com.saba.taskmanager.exception.UserNotFoundException;
import com.saba.taskmanager.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {

        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setFirstName(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRole(Role.USER);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return new RegisterResponseDto(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRole().name()

        );

    }

    public AuthResponseDto login(@NonNull LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequestDto.getEmail()));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponseDto(
                token,
                "Bearer",
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

}