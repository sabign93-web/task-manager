package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.PagedResponseDto;
import com.saba.taskmanager.dto.TaskRequestDto;
import com.saba.taskmanager.dto.TaskResponseDto;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.mapper.TaskMapper;
import com.saba.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
        Task savedTask = taskService.createTask(task, taskRequestDto.getUserId(), taskRequestDto.getProjectId());
        return taskMapper.toResponseDto(savedTask);
    }

    @GetMapping
    public PagedResponseDto<TaskResponseDto> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Task> taskPage = taskService.getAllTasks(page, size, sortBy, direction);

        PagedResponseDto<TaskResponseDto> response = new PagedResponseDto<>();
        response.setContent(
                taskPage.getContent()
                        .stream()
                        .map(taskMapper::toResponseDto)
                        .toList()
        );
        response.setPage(taskPage.getNumber());
        response.setSize(taskPage.getSize());
        response.setTotalElements(taskPage.getTotalElements());
        response.setTotalPages(taskPage.getTotalPages());

        return response;
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
        Task updatedTask = taskService.updateTask(id, task, taskRequestDto.getUserId(), taskRequestDto.getProjectId());
        return ResponseEntity.ok(taskMapper.toResponseDto(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/search")
    public List<TaskResponseDto> searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTasksByTitle(title)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/filter")
    public List<TaskResponseDto> findByCompleted (@RequestParam Boolean completed) {
        return taskService.findByCompleted(completed)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

}
