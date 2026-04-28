package com.saba.taskmanager.repository;

import com.saba.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Long> {
    boolean existsByCompanyId (Long companyId);

    List<User> findByCompanyId(Long companyId);
}
