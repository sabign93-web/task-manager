package com.saba.taskmanager.service;

import com.saba.taskmanager.dto.UserRequestDto;
import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.exception.UserHasTasksException;
import com.saba.taskmanager.exception.UserNotFoundException;
import com.saba.taskmanager.repository.CompanyRepository;
import com.saba.taskmanager.repository.TaskRepository;
import com.saba.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final TaskRepository taskRepository;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.taskRepository = taskRepository;
    }

    public User createUser(User user, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        user.setCompany(company);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser, Long companyId) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setJobTitle(updatedUser.getJobTitle());
        existingUser.setActive(updatedUser.isActive());
        existingUser.setCompany(company);

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (taskRepository.existsByUserId(id)) {
            throw new UserHasTasksException("Cannot delete user because it still has tasks");
        }

        userRepository.delete(user);
    }

    public List<Task> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return taskRepository.findByUserId(user.getId());
    }
}