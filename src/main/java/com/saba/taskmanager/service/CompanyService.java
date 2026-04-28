package com.saba.taskmanager.service;

import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.entity.Project;
import com.saba.taskmanager.entity.User;
import com.saba.taskmanager.exception.CompanyHasUsersException;
import com.saba.taskmanager.exception.CompanyNotFoundException;
import com.saba.taskmanager.repository.CompanyRepository;
import com.saba.taskmanager.repository.ProjectRepository;
import com.saba.taskmanager.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository, ProjectRepository projectRepository){
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Company createCompany(Company company){
        company.setCreatedAt(LocalDateTime.now());
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id){
        return companyRepository.findById(id);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setIndustry(updatedCompany.getIndustry());
                    company.setWebsite(updatedCompany.getWebsite());
                    company.setCountry(updatedCompany.getCountry());
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));

        if (userRepository.existsByCompanyId(id)) {
            throw new CompanyHasUsersException("Cannot delete company because it still has users");
        }

        companyRepository.delete(company);
    }

    public List<User> getUsersByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

        return userRepository.findByCompanyId(company.getId());
    }

    public List<Project> getProjectsByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

        return projectRepository.findByCompanyId(company.getId());
    }
}
