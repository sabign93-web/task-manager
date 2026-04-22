package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.TaskRequestDto;
import com.saba.taskmanager.dto.TaskResponseDto;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setCompleted(taskRequestDto.isCompleted());

        Task savedTask = taskService.createTask(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(savedTask.getId());
        taskResponseDto.setTitle(savedTask.getTitle());
        taskResponseDto.setDescription(savedTask.getDescription());
        taskResponseDto.setCompleted(savedTask.isCompleted());

        return taskResponseDto;
    }

    @GetMapping
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(task -> {
                    TaskResponseDto taskResponseDto = new TaskResponseDto();
                    taskResponseDto.setId(task.getId());
                    taskResponseDto.setTitle(task.getTitle());
                    taskResponseDto.setDescription(task.getDescription());
                    taskResponseDto.setCompleted(task.isCompleted());
                    return taskResponseDto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> {
                    TaskResponseDto taskResponseDto = new TaskResponseDto();
                    taskResponseDto.setId(task.getId());
                    taskResponseDto.setTitle(task.getTitle());
                    taskResponseDto.setDescription(task.getDescription());
                    taskResponseDto.setCompleted(task.isCompleted());

                    return ResponseEntity.ok(taskResponseDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id,
                                                      @Valid @RequestBody TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setCompleted(taskRequestDto.isCompleted());

        Task updatedTask = taskService.updateTask(id, task);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(updatedTask.getId());
        taskResponseDto.setTitle(updatedTask.getTitle());
        taskResponseDto.setDescription(updatedTask.getDescription());
        taskResponseDto.setCompleted(updatedTask.isCompleted());

        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
