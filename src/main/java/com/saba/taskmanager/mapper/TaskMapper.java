package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.TaskRequestDto;
import com.saba.taskmanager.dto.TaskResponseDto;
import com.saba.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setCompleted(taskRequestDto.isCompleted());
        task.setStatus(taskRequestDto.getStatus());
        task.setPriority(taskRequestDto.getPriority());
        task.setDueDate(taskRequestDto.getDueDate());
        return task;
    }

    public TaskResponseDto toResponseDto(Task task) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDescription(task.getDescription());
        taskResponseDto.setCompleted(task.isCompleted());
        taskResponseDto.setStatus(task.getStatus());
        taskResponseDto.setPriority(task.getPriority());
        taskResponseDto.setDueDate(task.getDueDate());
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setUpdatedAt(task.getUpdatedAt());

        if (task.getUser() != null) {
            taskResponseDto.setUserId(task.getUser().getId());
            taskResponseDto.setUserName(task.getUser().getFirstName() + " " + task.getUser().getLastName());
        }
        if (task.getProject() != null) {
            taskResponseDto.setProjectId(task.getProject().getId());
            taskResponseDto.setProjectName(task.getProject().getName());
            taskResponseDto.setProjectKey(task.getProject().getProjectKey());
        }

        return taskResponseDto;
    }
}
