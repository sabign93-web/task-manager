package com.saba.taskmanager.service;

import com.saba.taskmanager.dto.AssigneeTaskSummaryDto;
import com.saba.taskmanager.dto.DashboardSummaryDto;
import com.saba.taskmanager.dto.ReporterTaskSummaryDto;
import com.saba.taskmanager.dto.TaskChartItemDto;
import com.saba.taskmanager.entity.*;
import com.saba.taskmanager.exception.TaskNotFoundException;
import com.saba.taskmanager.repository.ProjectRepository;
import com.saba.taskmanager.repository.TagRepository;
import com.saba.taskmanager.repository.TaskRepository;
import com.saba.taskmanager.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TagRepository tagRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository, TagRepository tagRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
    }

    public Task createTask(Task task,
                           Long projectId,
                           List<Long> tagIds,
                           Long reporterId,
                           Long assigneeId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Reporter not found with id: " + reporterId));

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee not found with id: " + assigneeId));

        List<Tag> tags = (tagIds == null || tagIds.isEmpty())
                ? Collections.emptyList()
                : tagRepository.findAllById(tagIds);

        task.setProject(project);
        task.setTags(tags);
        task.setReporter(reporter);
        task.setAssignee(assignee);

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }

        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }


    public Page<Task> getAllTasks(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable);
    }


    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }


    public Task updateTask(Long id,
                           Task updatedTask,
                           Long projectId,
                           List<Long> tagIds,
                           Long reporterId,
                           Long assigneeId) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Reporter not found with id: " + reporterId));

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee not found with id: " + assigneeId));

        List<Tag> tags = (tagIds == null || tagIds.isEmpty())
                ? Collections.emptyList()
                : tagRepository.findAllById(tagIds);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());
        task.setProject(project);
        task.setTags(tags);
        task.setReporter(reporter);
        task.setAssignee(assignee);
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }


    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
    }


    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Task> getTasksByTagId(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));

        return taskRepository.findByTagsId(tag.getId());
    }

    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), TaskStatus.DONE);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    public DashboardSummaryDto getDashboardSummary() {
        DashboardSummaryDto summaryDto = new DashboardSummaryDto();

        summaryDto.setTotalTasks(taskRepository.count());
        summaryDto.setTodoTasks(taskRepository.countByStatus(TaskStatus.TODO));
        summaryDto.setInProgressTasks(taskRepository.countByStatus(TaskStatus.IN_PROGRESS));
        summaryDto.setDoneTasks(taskRepository.countByStatus(TaskStatus.DONE));
        summaryDto.setOverdueTasks(
                taskRepository.countByDueDateBeforeAndStatusNot(LocalDate.now(), TaskStatus.DONE)
        );
        summaryDto.setHighPriorityTasks(taskRepository.countByPriority(TaskPriority.HIGH));

        return summaryDto;
    }

    public List<TaskChartItemDto> getTasksByStatusChart() {
        return List.of(
                new TaskChartItemDto("TODO", taskRepository.countByStatus(TaskStatus.TODO)),
                new TaskChartItemDto("IN_PROGRESS", taskRepository.countByStatus(TaskStatus.IN_PROGRESS)),
                new TaskChartItemDto("DONE", taskRepository.countByStatus(TaskStatus.DONE))
        );
    }

    public List<TaskChartItemDto> getTasksByPriorityChart() {
        return List.of(
                new TaskChartItemDto("HIGH", taskRepository.countByPriority(TaskPriority.HIGH)),
                new TaskChartItemDto("MEDIUM", taskRepository.countByPriority(TaskPriority.MEDIUM)),
                new TaskChartItemDto("LOW", taskRepository.countByPriority(TaskPriority.LOW))
        );
    }

    public List<Task> getRecentTasks() {
        return taskRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public List<Task> getUpcomingTasks() {
        return taskRepository
                .findTop5ByDueDateIsNotNullAndDueDateGreaterThanEqualAndStatusNotOrderByDueDateAsc(
                        LocalDate.now(),
                        TaskStatus.DONE
                );
    }

    public List<Task> getOverduePanelTasks() {
        return taskRepository.findTop5ByDueDateBeforeAndStatusNotOrderByDueDateAsc(
                LocalDate.now(),
                TaskStatus.DONE
        );
    }

    public List<AssigneeTaskSummaryDto> getAssigneeTaskSummary() {
        return taskRepository.getTaskCountByAssignee();
    }

    public List<ReporterTaskSummaryDto> getReporterTaskSummary() {
        return taskRepository.getTaskCountByReporter();
    }


}
