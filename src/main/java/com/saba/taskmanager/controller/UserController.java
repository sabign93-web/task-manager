package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.UserRequestDto;
import com.saba.taskmanager.dto.UserResponseDto;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.mapper.UserMapper;
import com.saba.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        User savedUser = userService.createUser(user, userRequestDto.getCompanyId());
        return userMapper.toResponseDto(savedUser);
    }
}