package com.saba.taskmanager.repository;

import com.saba.taskmanager.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
