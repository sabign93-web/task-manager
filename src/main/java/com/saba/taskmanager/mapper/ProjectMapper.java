package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.ProjectRequestDto;
import com.saba.taskmanager.dto.ProjectResponseDto;
import com.saba.taskmanager.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequestDto projectRequestDto) {
        Project project = new Project();
        project.setName(projectRequestDto.getName());
        project.setProjectKey(projectRequestDto.getProjectKey());
        project.setDescription(projectRequestDto.getDescription());
        return project;
    }

    public ProjectResponseDto toResponseDto(Project project) {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(project.getId());
        projectResponseDto.setName(project.getName());
        projectResponseDto.setProjectKey(project.getProjectKey());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setCreatedAt(project.getCreatedAt());

        if (project.getCompany() != null) {
            projectResponseDto.setCompanyId(project.getCompany().getId());
            projectResponseDto.setCompanyName(project.getCompany().getName());
        }

        return projectResponseDto;
    }
}