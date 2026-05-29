package com.saba.taskmanager.repository;

import com.saba.taskmanager.dto.AssigneeTaskSummaryDto;
import com.saba.taskmanager.dto.ReporterTaskSummaryDto;
import com.saba.taskmanager.entity.Task;
import com.saba.taskmanager.entity.TaskPriority;
import com.saba.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByTagsId(Long tagId);

    List<Task> findByAssigneeId(Long assigneeId);

    boolean existsByAssigneeId(Long assigneeId);

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

    List<Task> findTop5ByDueDateBeforeAndStatusNotOrderByDueDateAsc(LocalDate date, TaskStatus status);


    @Query("""
       SELECT new com.saba.taskmanager.dto.AssigneeTaskSummaryDto(
           a.id,
           CONCAT(a.firstName, ' ', a.lastName),
           COUNT(t)
       )
       FROM Task t
       JOIN t.assignee a
       WHERE t.status <> com.saba.taskmanager.entity.TaskStatus.DONE
       GROUP BY a.id, a.firstName, a.lastName
       ORDER BY COUNT(t) DESC
       """)
    List<AssigneeTaskSummaryDto> getTaskCountByAssignee();

    @Query("""
       SELECT new com.saba.taskmanager.dto.ReporterTaskSummaryDto(
           r.id,
           CONCAT(r.firstName, ' ', r.lastName),
           COUNT(t)
       )
       FROM Task t
       JOIN t.reporter r
       WHERE t.status <> com.saba.taskmanager.entity.TaskStatus.DONE
       GROUP BY r.id, r.firstName, r.lastName
       ORDER BY COUNT(t) DESC
       """)
    List<ReporterTaskSummaryDto> getTaskCountByReporter();

}
