package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.*;
import com.saba.taskmanager.entity.Project;
import com.saba.taskmanager.mapper.ProjectMapper;
import com.saba.taskmanager.mapper.TaskMapper;
import com.saba.taskmanager.mapper.UserMapper;
import com.saba.taskmanager.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, TaskMapper taskMapper, UserMapper userMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ProjectResponseDto createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        Project project = projectMapper.toEntity(projectRequestDto);
        Project savedProject = projectService.createProject(project, projectRequestDto.getCompanyId());
        return projectMapper.toResponseDto(savedProject);
    }

    @GetMapping
    public List<ProjectResponseDto> getAllProjects() {
        return projectService.getAllProjects()
                .stream()
                .map(projectMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(project -> ResponseEntity.ok(projectMapper.toResponseDto(project)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/tasks")
    public List<TaskResponseDto> getTasksByProjectId(@PathVariable Long id) {
        return projectService.getTasksByProjectId(id)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}/users")
    public List<UserResponseDto> getUsersByProjectId(@PathVariable Long id) {
        return projectService.getUsersByProjectId(id)
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @PostMapping("/{id}/users")
    public ProjectResponseDto addUserToProject(@PathVariable Long id,
                                               @RequestBody ProjectMemberRequestDto projectMemberRequestDto) {
        Project updatedProject = projectService.addUserToProject(id, projectMemberRequestDto.getUserId());
        return projectMapper.toResponseDto(updatedProject);
    }

    @GetMapping("/{id}/members")
    public List<UserResponseDto> getProjectMembers(@PathVariable Long id) {
        return projectService.getUsersByProjectMembership(id)
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }


}