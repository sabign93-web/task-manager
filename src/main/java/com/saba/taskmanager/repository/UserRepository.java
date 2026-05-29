package com.saba.taskmanager.repository;

import com.saba.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    boolean existsByCompanyId (Long companyId);

    List<User> findByCompanyId(Long companyId);

    Optional<User> findByEmail(String email);


  //  @Query("""
//       SELECT new com.saba.taskmanager.dto.userResponseDto(
//           u.id,
//           u.firstName,
//           u.lastName,
//           u.email,
//           u.jobTitle,
//           u.active,
//           u.company_id,
//           u.companyName
//       )
//       FROM users u
//       WHERE u.company_id = :companyId
//       ORDER BY u.lastName DESC
//       """)
//    List<User> getByCompanyId(Long companyId);
}
