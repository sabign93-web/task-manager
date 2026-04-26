package com.saba.taskmanager.controller;

import com.saba.taskmanager.dto.CompanyRequestDto;
import com.saba.taskmanager.dto.CompanyResponseDto;
import com.saba.taskmanager.entity.Company;
import com.saba.taskmanager.mapper.CompanyMapper;
import com.saba.taskmanager.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
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
}