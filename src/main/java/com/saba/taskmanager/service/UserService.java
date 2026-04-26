package com.saba.taskmanager.service;

import com.saba.taskmanager.dto.UserRequestDto;
import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.repository.CompanyRepository;
import com.saba.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public User createUser(User user, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        user.setCompany(company);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
