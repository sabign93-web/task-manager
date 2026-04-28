package com.saba.taskmanager.service;

import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.entity.Project;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.exception.CompanyNotFoundException;
import com.saba.taskmanager.repository.CompanyRepository;
import com.saba.taskmanager.repository.ProjectRepository;
import com.saba.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, CompanyRepository companyRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.taskRepository = taskRepository;
    }

    public Project createProject(Project project, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

        project.setCompany(company);
        project.setCreatedAt(LocalDateTime.now());

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getProjectsByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

        return projectRepository.findByCompanyId(company.getId());
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        return taskRepository.findByProjectId(project.getId());
    }

    public List<User> getUsersByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        return taskRepository.findByProjectId(project.getId())
                .stream()
                .map(Task::getUser)
                .filter(user -> user != null)
                .distinct()
                .toList();
    }
}