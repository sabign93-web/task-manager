package com.saba.taskmanager.exception;

public class UserHasTasksException extends RuntimeException {

    public UserHasTasksException(String message) {
        super(message);
    }
}