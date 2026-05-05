package com.saba.taskmanager.dto;

public class ProjectMemberRequestDto {

    private Long userId;

    public ProjectMemberRequestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}