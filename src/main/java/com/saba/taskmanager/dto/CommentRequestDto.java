package com.saba.taskmanager.dto;

public class CommentRequestDto {

    private String content;
    private Long userId;

    public CommentRequestDto() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}