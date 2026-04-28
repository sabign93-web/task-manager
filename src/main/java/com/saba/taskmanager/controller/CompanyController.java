package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.CompanyRequestDto;
import com.saba.taskmanager.dto.CompanyResponseDto;
import com.saba.taskmanager.dto.ProjectResponseDto;
import com.saba.taskmanager.dto.UserResponseDto;
import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.mapper.CompanyMapper;
import com.saba.taskmanager.mapper.ProjectMapper;
import com.saba.taskmanager.mapper.UserMapper;
import com.saba.taskmanager.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper, UserMapper userMapper, ProjectMapper projectMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    @PostMapping
    public CompanyResponseDto createCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        Company company = companyMapper.toEntity(companyRequestDto);
        Company savedCompany = companyService.createCompany(company);
        return companyMapper.toResponseDto(savedCompany);
    }

    @GetMapping
    public List<CompanyResponseDto> getAllCompanies() {
        return companyService.getAllCompanies()
                .stream()
                .map(companyMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id)
                .map(company -> ResponseEntity.ok(companyMapper.toResponseDto(company)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public CompanyResponseDto updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDto companyRequestDto) {
        Company company = companyMapper.toEntity(companyRequestDto);
        Company updatedCompany = companyService.updateCompany(id, company);
        return companyMapper.toResponseDto(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Company deleted successfully");
    }

    @GetMapping("/{id}/users")
    public List<UserResponseDto> getUsersByCompanyId(@PathVariable Long id) {
        return companyService.getUsersByCompanyId(id)
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}/projects")
    public List<ProjectResponseDto> getProjectsByCompanyId(@PathVariable Long id) {
        return companyService.getProjectsByCompanyId(id)
                .stream()
                .map(projectMapper::toResponseDto)
                .toList();
    }
}