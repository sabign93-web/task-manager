package com.saba.taskmanager.mapper;

import com.saba.taskmanager.dto.UserRequestDto;
import com.saba.taskmanager.dto.UserResponseDto;
import com.saba.taskmanager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setJobTitle(userRequestDto.getJobTitle());
        user.setActive(userRequestDto.isActive());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setJobTitle(user.getJobTitle());
        userResponseDto.setActive(user.isActive());

        if (user.getCompany() != null) {
            userResponseDto.setCompanyId(user.getCompany().getId());
            userResponseDto.setCompanyName(user.getCompany().getName());
        }

        return userResponseDto;
    }
}
