package com.saba.taskmanager.repository;

import com.saba.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByCompleted(boolean completed);
}
