package com.saba.taskmanager.service;

import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
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
}
