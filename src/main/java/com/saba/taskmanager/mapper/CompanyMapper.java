package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.CompanyRequestDto;
import com.saba.taskmanager.dto.CompanyResponseDto;
import com.saba.taskmanager.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyRequestDto companyRequestDto) {
        Company company = new Company();
        company.setName(companyRequestDto.getName());
        company.setIndustry(companyRequestDto.getIndustry());
        company.setWebsite(companyRequestDto.getWebsite());
        company.setCountry(companyRequestDto.getCountry());
        return company;
    }

    public CompanyResponseDto toResponseDto(Company company) {
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        companyResponseDto.setId(company.getId());
        companyResponseDto.setName(company.getName());
        companyResponseDto.setIndustry(company.getIndustry());
        companyResponseDto.setWebsite(company.getWebsite());
        companyResponseDto.setCountry(company.getCountry());
        return companyResponseDto;
    }
}