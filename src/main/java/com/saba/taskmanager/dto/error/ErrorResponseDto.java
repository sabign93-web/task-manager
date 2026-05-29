package com.saba.taskmanager.dto.error;

import java.time.OffsetDateTime;

public class ErrorResponseDto {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(int status, String error, String message, String path) {
        this.timestamp = OffsetDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}