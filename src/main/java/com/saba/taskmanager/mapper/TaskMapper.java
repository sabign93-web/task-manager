package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.TaskRequestDto;
import com.saba.taskmanager.dto.TaskResponseDto;
import com.saba.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDto taskRequestDto){
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setCompleted(taskRequestDto.isCompleted());
        return task;
    }

    public TaskResponseDto toResponseDto(Task task){
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDescription(task.getDescription());
        taskResponseDto.setCompleted(task.isCompleted());
        return taskResponseDto;
    }
}
