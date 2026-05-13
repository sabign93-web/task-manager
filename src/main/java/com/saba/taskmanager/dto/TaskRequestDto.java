package com.saba.taskmanager.dto;

import com.saba.taskmanager.entity.TaskPriority;
import com.saba.taskmanager.entity.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public class TaskRequestDto {

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;

    private Long projectId;

    private List<Long> tagIds;

    private Long reporterId;

    private Long assigneeId;

    public TaskRequestDto() {

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

    public Long getProjectId() {

        return projectId;

    }

    public void setProjectId(Long projectId) {

        this.projectId = projectId;

    }

    public List<Long> getTagIds() {

        return tagIds;

    }

    public void setTagIds(List<Long> tagIds) {

        this.tagIds = tagIds;

    }

    public Long getReporterId() {

        return reporterId;

    }

    public void setReporterId(Long reporterId) {

        this.reporterId = reporterId;

    }

    public Long getAssigneeId() {

        return assigneeId;

    }

    public void setAssigneeId(Long assigneeId) {

        this.assigneeId = assigneeId;

    }

}
