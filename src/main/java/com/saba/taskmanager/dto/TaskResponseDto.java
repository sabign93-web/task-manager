package com.saba.taskmanager.dto;

import com.saba.taskmanager.entity.TaskPriority;
import com.saba.taskmanager.entity.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskResponseDto {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;

    private String userName;

    private Long projectId;

    private String projectName;

    private String projectKey;

    private List<Long> tagIds;

    private List<String> tagNames;

    private Long reporterId;

    private String reporterName;

    private Long assigneeId;

    private String assigneeName;

    public TaskResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Long getUserId() {

        return userId;

    }

    public void setUserId(Long userId) {

        this.userId = userId;

    }

    public String getUserName() {

        return userName;

    }

    public void setUserName(String userName) {

        this.userName = userName;

    }

    public Long getProjectId() {

        return projectId;

    }

    public void setProjectId(Long projectId) {

        this.projectId = projectId;

    }

    public String getProjectName() {

        return projectName;

    }

    public void setProjectName(String projectName) {

        this.projectName = projectName;

    }

    public String getProjectKey() {

        return projectKey;

    }

    public void setProjectKey(String projectKey) {

        this.projectKey = projectKey;

    }

    public List<Long> getTagIds() {

        return tagIds;

    }

    public void setTagIds(List<Long> tagIds) {

        this.tagIds = tagIds;

    }

    public List<String> getTagNames() {

        return tagNames;

    }

    public void setTagNames(List<String> tagNames) {

        this.tagNames = tagNames;

    }

    public Long getReporterId() {

        return reporterId;

    }

    public void setReporterId(Long reporterId) {

        this.reporterId = reporterId;

    }

    public String getReporterName() {

        return reporterName;

    }

    public void setReporterName(String reporterName) {

        this.reporterName = reporterName;

    }

    public Long getAssigneeId() {

        return assigneeId;

    }

    public void setAssigneeId(Long assigneeId) {

        this.assigneeId = assigneeId;

    }

    public String getAssigneeName() {

        return assigneeName;

    }

    public void setAssigneeName(String assigneeName) {

        this.assigneeName = assigneeName;

    }
}