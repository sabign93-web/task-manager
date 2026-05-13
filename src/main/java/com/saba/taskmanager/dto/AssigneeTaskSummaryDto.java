package com.saba.taskmanager.dto;

public class AssigneeTaskSummaryDto {

    private Long assigneeId;
    private String assigneeName;
    private long taskCount;

    public AssigneeTaskSummaryDto() {
    }

    public AssigneeTaskSummaryDto(Long assigneeId, String assigneeName, long taskCount) {
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
        this.taskCount = taskCount;
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

    public long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(long taskCount) {
        this.taskCount = taskCount;
    }
}