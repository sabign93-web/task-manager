package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.*;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.entity.TaskPriority;
import com.saba.taskmanager.entity.TaskStatus;
import com.saba.taskmanager.mapper.TaskMapper;
import com.saba.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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

        Task savedTask = taskService.createTask(
                task,
                taskRequestDto.getProjectId(),
                taskRequestDto.getTagIds(),
                taskRequestDto.getReporterId(),
                taskRequestDto.getAssigneeId()
        );

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

        Task updatedTask = taskService.updateTask(
                id,
                task,
                taskRequestDto.getProjectId(),
                taskRequestDto.getTagIds(),
                taskRequestDto.getReporterId(),
                taskRequestDto.getAssigneeId()
        );

        return ResponseEntity.ok(taskMapper.toResponseDto(updatedTask));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");    }

    @GetMapping("/search")
    public List<TaskResponseDto> searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTasksByTitle(title)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/tag/{tagId}")
    public List<TaskResponseDto> getTasksByTagId(@PathVariable Long tagId) {
        return taskService.getTasksByTagId(tagId)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/overdue")
    public List<TaskResponseDto> getOverdueTasks() {
        return taskService.getOverdueTasks()
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/status/{status}")
    public List<TaskResponseDto> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/priority/{priority}")
    public List<TaskResponseDto> getTasksByPriority(@PathVariable TaskPriority priority) {
        return taskService.getTasksByPriority(priority)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/dashboard-summary")
    public DashboardSummaryDto getDashboardSummary() {
        return taskService.getDashboardSummary();
    }

    @GetMapping("/chart/status")
    public List<TaskChartItemDto> getTasksByStatusChart() {
        return taskService.getTasksByStatusChart();
    }

    @GetMapping("/chart/priority")
    public List<TaskChartItemDto> getTasksByPriorityChart() {
        return taskService.getTasksByPriorityChart();
    }

    @GetMapping("/recent")
    public List<TaskResponseDto> getRecentTasks() {
        return taskService.getRecentTasks()
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/upcoming")
    public List<TaskResponseDto> getUpcomingTasks() {
        return taskService.getUpcomingTasks()
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/dashboard/overdue")
    public List<TaskResponseDto> getOverduePanelTasks() {
        return taskService.getOverduePanelTasks()
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/dashboard/assignee-summary")
    public List<AssigneeTaskSummaryDto> getAssigneeTaskSummary() {
        return taskService.getAssigneeTaskSummary();
    }

    @GetMapping("/dashboard/reporter-summary")
    public List<ReporterTaskSummaryDto> getReporterTaskSummary() {
        return taskService.getReporterTaskSummary();

    }

}
