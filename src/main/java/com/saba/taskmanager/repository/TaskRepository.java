package com.saba.taskmanager.repository;

import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.entity.TaskPriority;
import com.saba.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByTagsId(Long tagId);

    List<Task> findByAssigneeId(Long assigneeId);

    List<Task> findByReporterId(Long reporterId);

    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    long countByStatus(TaskStatus status);

    long countByPriority(TaskPriority priority);

    long countByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);

    List<Task> findTop5ByOrderByCreatedAtDesc();

    List<Task> findTop5ByDueDateIsNotNullAndDueDateGreaterThanEqualAndStatusNotOrderByDueDateAsc(
            LocalDate date,
            TaskStatus status
    );
}
