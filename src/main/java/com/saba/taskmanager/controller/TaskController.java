package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.TaskRequestDto;
import com.saba.taskmanager.dto.TaskResponseDto;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.mapper.TaskMapper;
import com.saba.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        Task task = taskMapper.toEntity(taskRequestDto);
        Task savedTask = taskService.createTask(task);
        return taskMapper.toResponseDto(savedTask);
    }

    @GetMapping
    public List<TaskResponseDto> getAllTasks(@RequestParam int page, @RequestParam int size) {
        return taskService.getAllTasks(page, size)
                .stream()
                .map(taskMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(taskMapper.toResponseDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id,
                                                      @Valid @RequestBody TaskRequestDto taskRequestDto) {
        Task task = taskMapper.toEntity(taskRequestDto);
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(taskMapper.toResponseDto(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
