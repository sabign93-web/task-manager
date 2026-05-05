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

        if (task.getTags() != null && !task.getTags().isEmpty()) {
            taskResponseDto.setTagIds(
                    task.getTags()
                            .stream()
                            .map(tag -> tag.getId())
                            .toList()
            );

            taskResponseDto.setTagNames(
                    task.getTags()
                            .stream()
                            .map(tag -> tag.getName())
                            .toList()
            );
        }

        if (task.getReporter() != null) {
            taskResponseDto.setReporterId(task.getReporter().getId());
            taskResponseDto.setReporterName(
                    task.getReporter().getFirstName() + " " + task.getReporter().getLastName()
            );
        }

        if (task.getAssignee() != null) {
            taskResponseDto.setAssigneeId(task.getAssignee().getId());
            taskResponseDto.setAssigneeName(
                    task.getAssignee().getFirstName() + " " + task.getAssignee().getLastName()
            );
        }

        return taskResponseDto;
    }
}
