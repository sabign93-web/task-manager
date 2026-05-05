package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.TaskResponseDto;
import com.saba.taskmanager.dto.UserRequestDto;
import com.saba.taskmanager.dto.UserResponseDto;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.mapper.TaskMapper;
import com.saba.taskmanager.mapper.UserMapper;
import com.saba.taskmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    public UserController(UserService userService, UserMapper userMapper, TaskMapper taskMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        User savedUser = userService.createUser(user, userRequestDto.getCompanyId());
        return userMapper.toResponseDto(savedUser);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        User updatedUser = userService.updateUser(id, user, userRequestDto.getCompanyId());
        return userMapper.toResponseDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{id}/tasks")
    public List<TaskResponseDto> getTasksByUserId(@PathVariable Long id) {
        return userService.getTasksByUserId(id)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}/assigned-tasks")
    public List<TaskResponseDto> getAssignedTasksByUserId(@PathVariable Long id) {
        return userService.getAssignedTasksByUserId(id)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}/reported-tasks")
    public List<TaskResponseDto> getReportedTasksByUserId(@PathVariable Long id) {
        return userService.getReportedTasksByUserId(id)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }
}